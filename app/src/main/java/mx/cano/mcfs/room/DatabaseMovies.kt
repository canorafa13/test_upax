package mx.cano.mcfs.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import mx.cano.mcfs.BuildConfig
import mx.cano.mcfs.datamodel.entities.Movie

@Database(entities = arrayOf(Movie::class), version = 1, exportSchema = false)
abstract class DatabaseMovies: RoomDatabase() {
    abstract fun daoMovie(): DaoMovie

    companion object{
        @Volatile
        private var INSTANCE: DatabaseMovies? = null

        fun getDataseClient(context: Context) : DatabaseMovies {

            if (INSTANCE != null) return INSTANCE!!

            synchronized(this) {
                INSTANCE = Room
                    .databaseBuilder(context, DatabaseMovies::class.java, BuildConfig.MOVIE_DATABASE)
                    .fallbackToDestructiveMigration()
                    .build()

                return INSTANCE!!

            }
        }
    }
}