package mx.cano.mcfs.repository

import android.content.Context
import mx.cano.mcfs.connection.Services
import mx.cano.mcfs.datamodel.responses.ResponseMovie
import mx.cano.mcfs.room.DatabaseMovies
import mx.cano.mcfs.viewmodel.ViewModelMovies
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RepositoryMovie {
    companion object {

        var db: DatabaseMovies? = null

        private fun initializeDB(context: Context) : DatabaseMovies {
            return DatabaseMovies.getDataseClient(context)
        }

        suspend fun getMoviesByTitle(context: Context, title: String, page: Int, viewModel: ViewModelMovies){
            if (db == null) {
                db = initializeDB(context)
            }

            var retrofit = Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(Services::class.java)
            try {
                val call = retrofit.searchMovies(query = title, page = page)
                if (call.code() == 200) {
                    val body = call.body()
                    if (body?.results!!.isNotEmpty()) {
                        /**Insertamos los datos nuevos*/
                        db!!.daoMovie().insertMovie(*body?.results.toTypedArray())
                        /**Actualizamos la tabla*/
                    }else{
                        viewModel.movieLiveData.value = ResponseMovie(1, emptyList(), 0, 1)
                    }
                }else{
                    viewModel.movieLiveData.value = ResponseMovie(1, emptyList(), 0, 1)
                }
            }catch (e: Exception){
                e.printStackTrace()

                /**Si no hay internet se hace una busqueda local*/
                val list = db!!.daoMovie().searchMoviesByName("%$title%")
                if (list != null && list.isNotEmpty()){
                    viewModel.movieLiveData.value = ResponseMovie(1, list, list.size, 1)
                }else{
                    viewModel.movieLiveData.value = ResponseMovie(1, emptyList(), 0, 1)
                }
            }
        }
    }
}