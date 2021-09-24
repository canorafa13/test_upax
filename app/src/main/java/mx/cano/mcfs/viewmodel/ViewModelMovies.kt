package mx.cano.mcfs.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mx.cano.mcfs.datamodel.responses.ResponseMovie
import mx.cano.mcfs.repository.RepositoryMovie

class ViewModelMovies: ViewModel() {

    val movieLiveData = MutableLiveData<ResponseMovie>()

    fun getMoviesByTitle(context: Context, title: String, page: Int = 1){
        viewModelScope.launch {
            RepositoryMovie.getMoviesByTitle(context, title, page, this@ViewModelMovies)
        }
    }

}