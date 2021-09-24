package mx.cano.mcfs.views

import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import mx.cano.mcfs.databinding.ActivityHomeBinding
import mx.cano.mcfs.services.ServiceLocation

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var locationRequest: LocationRequest
    private lateinit var fusedLocationProviderRequest: FusedLocationProviderClient
    private val TAG: String = "TAG_APP"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            startActivity(Intent(this, DBActivity::class.java))
        }
        updateLoction()

    }

    private fun updateLoction() {
        buildLocationRequest()
        fusedLocationProviderRequest = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        fusedLocationProviderRequest.requestLocationUpdates(locationRequest, getPendingIntent())
    }

    private fun getPendingIntent(): PendingIntent {
        val intent = Intent(this, ServiceLocation::class.java).apply {
            action = ServiceLocation.ACTION_PROCESS_UPDATE
        }
        return PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private fun buildLocationRequest() {
        locationRequest = LocationRequest.create()
        locationRequest.interval = 1000
        locationRequest.fastestInterval = 5000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }
}