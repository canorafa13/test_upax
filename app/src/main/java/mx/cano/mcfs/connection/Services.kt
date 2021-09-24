package mx.cano.mcfs.connection

import mx.cano.mcfs.BuildConfig
import mx.cano.mcfs.datamodel.responses.ResponseMovie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Services {
    @GET("search/movie")
    suspend fun searchMovies(
        @Query("api_key") api_key: String = BuildConfig.API_MOVIEDB_V3,
        @Query("language") language: String = "es-ES",
        @Query("page") page: Int,
        @Query("query") query: String
    ): Response<ResponseMovie>
}