package mx.cano.mcfs.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import java.lang.Exception

interface CallbackOnEnableGPS{
    fun onSuccess(locationManager: LocationManager)
}

enum class UtilsLocation {
    SINGLETON;

    fun getLastKnowLocation(context: Context, locationManager: LocationManager): Location? {
        var location: Location? = null
        val providers = locationManager.getProviders(true)
        providers.forEach {
            it.let {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                }
                location = locationManager.getLastKnownLocation(it)
            }
        }
        return location
    }

    fun enableGPS(context: Context, locationListener: LocationListener, code: Int, callbackOnEnableGPS: CallbackOnEnableGPS){
        val locationRequest = LocationRequest.create()
        locationRequest.interval = 1000
        locationRequest.fastestInterval = 5000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        val locationSettingRequest = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val client = LocationServices.getSettingsClient(context)
        val task = client.checkLocationSettings(locationSettingRequest.build())
        task.addOnSuccessListener(context as Activity, object: OnSuccessListener<LocationSettingsResponse>{
            @SuppressLint("WrongConstant")
            override fun onSuccess(p0: LocationSettingsResponse?) {
                val lm = context.getSystemService("location") as LocationManager
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return
                }
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, 0F, locationListener)
                callbackOnEnableGPS.onSuccess(lm)
            }
        })

        task.addOnFailureListener(context, object: OnFailureListener{
            override fun onFailure(p0: Exception) {
                if (p0 is ResolvableApiException){
                    try{
                        p0.startResolutionForResult(context, code)
                    }catch (sendEx: IntentSender.SendIntentException){}
                }
            }
        })

    }
}