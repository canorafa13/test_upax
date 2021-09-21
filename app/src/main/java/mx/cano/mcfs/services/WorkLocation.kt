package mx.cano.mcfs.services

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class WorkLocation(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    private val TAG: String = WorkLocation::class.java.name


    override fun doWork(): Result {
        //val db = Firebase.firestore

        Log.e(TAG, "Tarea ejecutada" + Date().toString())
        return Result.success()
    }
}