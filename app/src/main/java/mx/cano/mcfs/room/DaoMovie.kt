package mx.cano.mcfs.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import mx.cano.mcfs.datamodel.entities.Movie

@Dao
interface DaoMovie {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovie(vararg movie: Movie)

    @Query("SELECT * FROM Movie WHERE original_title LIKE :name OR title LIKE :name")
    suspend fun searchMoviesByName(name: String?) : List<Movie>
}