package mx.cano.mcfs.datamodel.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Movie")
data class Movie (
    @SerializedName("id") @Expose @PrimaryKey @ColumnInfo(name = "id") val Id: Int,
    @SerializedName("poster_path") @Expose @ColumnInfo(name = "poster_path") val poster_path: String? = null,
    @SerializedName("adult") @Expose @ColumnInfo(name = "adult") val adult: Boolean,
    @SerializedName("overview") @Expose @ColumnInfo(name = "overview") val overview: String,
    @SerializedName("release_date") @Expose @ColumnInfo(name = "release_date") val release_date: String,
    @SerializedName("original_title") @Expose @ColumnInfo(name = "original_title") val original_title: String,
    @SerializedName("original_language") @Expose @ColumnInfo(name = "original_language") val original_language: String,
    @SerializedName("title") @Expose @ColumnInfo(name = "title") val title: String,
    @SerializedName("backdrop_path") @Expose @ColumnInfo(name = "backdrop_path") val backdrop_path: String? = null,
    @SerializedName("popularity") @Expose @ColumnInfo(name = "popularity") val popularity: Double,
    @SerializedName("vote_count") @Expose @ColumnInfo(name = "vote_count") val vote_count: Int,
    @SerializedName("video") @Expose @ColumnInfo(name = "video") val video: Boolean,
    @SerializedName("vote_average") @Expose @ColumnInfo(name = "vote_average") val vote_average: Double
)