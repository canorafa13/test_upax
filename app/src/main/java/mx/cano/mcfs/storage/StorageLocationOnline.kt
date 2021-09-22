package mx.cano.mcfs.storage

import android.content.Context
import android.location.Location
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import mx.cano.mcfs.datamodel.collections.Position
import mx.cano.mcfs.utils.Collections

class StorageLocationOnline {
    fun sendLocation(context: Context, p0: Location): Task<Void>?{
        if (StorageSession.EMAIL.get(context) != ""){
            val db = Firebase.firestore
            return db.collection(Collections.user.name)
                .document(StorageSession.EMAIL.get(context))
                .update(Collections.positions.name, FieldValue.arrayUnion(Position(latitude = p0.latitude, longitude = p0.longitude)))
        }
        return null
    }
}