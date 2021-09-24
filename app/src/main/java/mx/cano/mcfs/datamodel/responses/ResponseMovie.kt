package mx.cano.mcfs.datamodel.responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import mx.cano.mcfs.datamodel.entities.Movie

data class ResponseMovie (
    @SerializedName("page") @Expose val page: Int,
    @SerializedName("results") @Expose val results: List<Movie>,
    @SerializedName("total_results") @Expose val total_results: Int,
    @SerializedName("total_pages") @Expose val total_pages: Int

)