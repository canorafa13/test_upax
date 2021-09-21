package mx.cano.mcfs.services

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.*

class WorkLocation(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    private val TAG: String = WorkLocation::class.java.name


    override fun doWork(): Result {
        Log.e(TAG, "Tarea ejecutada" + Date().toString())
        return Result.success()
    }
}