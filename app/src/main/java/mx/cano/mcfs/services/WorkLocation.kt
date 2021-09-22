package mx.cano.mcfs.services

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import mx.cano.mcfs.storage.StorageLocationOnline
import mx.cano.mcfs.utils.RuntimePermissions
import java.util.*

class WorkLocation(val context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams), LocationListener {

    private val TAG: String = WorkLocation::class.java.name
    private var locationManager: LocationManager? = null
    private val permissions = mutableListOf<RuntimePermissions.Permission>()
    private var isForRequest = true

    init {
        permissions.add(RuntimePermissions.Permission.LOCATION_BKG)
        permissions.add(RuntimePermissions.Permission.LOCATION_COARSE)
        permissions.add(RuntimePermissions.Permission.LOCATION_FINE)
    }


    @SuppressLint("WrongConstant")
    override fun doWork(): Result {
        isForRequest = true
        if (validatePermission()){
            locationManager = context.getSystemService("location") as LocationManager
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return Result.failure()
            }
            locationManager!!.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, 0F, this)
        }
        return Result.failure()
    }

    private fun validatePermission(): Boolean{
        return RuntimePermissions().checkPermissionGranted(context, permissions)
    }

    override fun onLocationChanged(p0: Location) {
        if (isForRequest){
            StorageLocationOnline().sendLocation(context, p0)
                ?.addOnSuccessListener {
                    isForRequest = false
                }
                ?.addOnFailureListener {
                    it.printStackTrace()
                }
            Log.e(TAG, "Tarea ejecutada" + Date().toString())
        }
    }

    override fun onProviderEnabled(provider: String) {
        super.onProviderEnabled(provider)
    }

    override fun onProviderDisabled(provider: String) {
        super.onProviderDisabled(provider)
    }
}