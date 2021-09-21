package mx.cano.mcfs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import mx.cano.mcfs.databinding.ActivityMainBinding
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


    }
}