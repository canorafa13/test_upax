    package mx.cano.mcfs.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.google.android.gms.location.LocationResult
import mx.cano.mcfs.storage.StorageOnline

class ServiceLocation : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        if (p1 != null && p0 != null){
            val action = p1.action
            if (action == ACTION_PROCESS_UPDATE){
                val result = LocationResult.extractResult(p1)
                if (result != null){
                    location = result.lastLocation
                    context = p0
                    if (!isUpdate){
                        StorageOnline().sendLocation(context!!, location!!)
                            ?.addOnSuccessListener {
                                isUpdate = true
                            }
                            ?.addOnFailureListener {
                                it.printStackTrace()
                            }
                    }
                }
            }

            if (handler == null){
                handler = Handler(Looper.getMainLooper())
                handler!!.post(taskUpdateLocation)
            }
        }
    }

    companion object{
        val ACTION_PROCESS_UPDATE: String = "edmt.dev.googlelocationbackground.UPDATE_LOCATION"
        var handler: Handler? = null
        private var interval = 1000 * 60 * 30
        private var location: Location? = null
        private var context: Context? = null
        private var isUpdate = false

        val taskUpdateLocation: Runnable = object : Runnable {
            override fun run() {
                Log.e("TAG", "RUNNABLE")
                if (location != null && context != null) {
                    StorageOnline().sendLocation(context!!, location!!)
                        ?.addOnSuccessListener {
                            isUpdate = true
                            Log.e("TAG", "UPDATE LOCATION ONLINE")
                        }
                        ?.addOnFailureListener {
                            it.printStackTrace()
                        }
                }
                handler?.postDelayed(this, interval.toLong())
            }
        }


        fun removeTask(){
            handler?.removeCallbacks(taskUpdateLocation)
        }
    }

}