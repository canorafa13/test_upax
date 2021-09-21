package mx.cano.mcfs.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import mx.cano.mcfs.databinding.ActivityMainBinding
import mx.cano.mcfs.datamodel.collections.Device
import mx.cano.mcfs.datamodel.collections.Position
import mx.cano.mcfs.services.WorkLocation
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val TAG: String = "TAG_APP"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val worker = PeriodicWorkRequest.Builder(WorkLocation::class.java, 15, TimeUnit.MINUTES).build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork("UNIQUE", ExistingPeriodicWorkPolicy.KEEP, worker)

        WorkManager.getInstance(applicationContext).getWorkInfoByIdLiveData(worker.id)
            .observe(this, { workInfo ->
                Log.e(TAG, "Worker status: " + workInfo.state.name)
            })

        val db = Firebase.firestore
/*
        val device = hashMapOf(
            "uuid" to "1234567890",
            "name" to "Rafael Cano"
        )

        db.collection("user")
            .document(device.get("uuid")!!.toString())
            .set(device, SetOptions.merge())
            .addOnCompleteListener {
                Log.e("TAG", "Success")
            }
            .addOnFailureListener {
                it.printStackTrace()
                Log.e("TAG", it.toString())
            }
            */


        val device = Device(
            "1234567890",
            "Rafael Cano"
        )
/*
        db.collection("user")
            .document(device.uuid!!)
            .set(device)
            .addOnCompleteListener {
                Log.e("TAG", "Success")
            }
            .addOnFailureListener {
                it.printStackTrace()
                Log.e("TAG", it.toString())
            }*/

        db.collection("user")
            .document(device.uuid!!)
            .update("positions", FieldValue.arrayUnion(Position(latitude = 12.211, longitude = 14.323)))
    }
}