package mx.cano.mcfs.storage

import android.content.Context
import android.content.SharedPreferences
import mx.cano.mcfs.BuildConfig

enum class StorageSession {
    EMAIL,
    NAME,
    PHOTO_URL;


    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(BuildConfig.SESSION_USER_STORAGE, Context.MODE_PRIVATE)
    }

    private fun getEditor(context: Context): SharedPreferences.Editor{
        return getPreferences(context).edit()
    }

    fun save(context: Context, value: String){
        val editor = getEditor(context)
        editor.putString(this.name, value)
        editor.commit()
    }

    fun get(context: Context): String{
        return getPreferences(context).getString(this.name, "") ?: ""
    }

    fun clearAll(context: Context){
        val editor = getEditor(context)
        editor.clear()
        editor.commit()
    }
}