package mx.cano.mcfs.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.core.app.ActivityCompat

class RuntimePermissions {
    enum class Permission{
        LOCATION_COARSE{ override fun value(): String { return Manifest.permission.ACCESS_COARSE_LOCATION } },
        LOCATION_FINE { override fun value(): String { return Manifest.permission.ACCESS_FINE_LOCATION } },
        LOCATION_BKG { override fun value(): String { return Manifest.permission.ACCESS_BACKGROUND_LOCATION } };

        public abstract fun value(): String
    }

    fun intentSetting(context: Context): Intent{
        return Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.parse("package:${context.packageName}")
            putExtra("app_package", context.packageName)
            putExtra("app_uid", context.packageName)
            putExtra("android.provider.extra.APP_PACKAGE", context.packageName)
        }
    }


    fun onRequestPermissionsResuelt(grantResults: IntArray, callback: Callback){
        if (grantResults.isNotEmpty()){
            grantResults.forEach { grantResult ->
                if (grantResult == PackageManager.PERMISSION_GRANTED){
                    callback.onPermissionGranted()
                }else{
                    callback.onPermissionDenied()
                }
            }
        }

    }

    fun checkPermissionGranted(context: Context, permission: Permission): Boolean{
        return ActivityCompat.checkSelfPermission(context, permission.value()) == PackageManager.PERMISSION_GRANTED
    }

    fun checkPermissionGranted(context: Context, permissions: List<Permission>): Boolean{
        if (permissions.isEmpty())
            return false
        var countPermissionsIsGranted = 0
        permissions.forEach { permission ->
            if(ActivityCompat.checkSelfPermission(context, permission.value()) == PackageManager.PERMISSION_GRANTED)
                countPermissionsIsGranted++
        }
        return countPermissionsIsGranted == permissions.size
    }

    fun checkPermissionGranted(activity: Activity, permissions: List<Permission>, code: Int){
        if (permissions.isEmpty())
            return
        val permissionsValue = mutableListOf<String>()
        permissions.forEach { permission ->
            permissionsValue.add(permission.value())
        }
        return ActivityCompat.requestPermissions(activity, permissionsValue.toTypedArray(), code)
    }

    interface Callback{
        fun onPermissionGranted()
        fun onPermissionDenied()
    }
}