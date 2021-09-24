package mx.cano.mcfs.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_dbactivity.*
import mx.cano.mcfs.R
import mx.cano.mcfs.databinding.ActivityDbactivityBinding
import mx.cano.mcfs.viewmodel.ViewModelMovies

class DBActivity : AppCompatActivity() {
    private lateinit var viewModel: ViewModelMovies
    private lateinit var binding: ActivityDbactivityBinding
    private var search = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDbactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(ViewModelMovies::class.java)


        binding.btnSearch.setOnClickListener {
            if (!binding.txtMovie.text.toString().isEmpty()){
                if (search.uppercase() == binding.txtMovie.text.toString().uppercase()){
                    if(viewModel.movieLiveData?.value?.page!! < viewModel.movieLiveData?.value?.total_pages!!){
                        Log.e("SEARCH", "${binding.txtMovie.text.toString()}, ${viewModel.movieLiveData?.value?.page!! + 1}")
                        viewModel.getMoviesByTitle(this, binding.txtMovie.text.toString(), viewModel.movieLiveData?.value?.page!! + 1)
                    }
                }else{
                    search = binding.txtMovie.text.toString()
                    Log.e("SEARCH", "${binding.txtMovie.text.toString()}")
                    viewModel.getMoviesByTitle(this, binding.txtMovie.text.toString())
                }
            }else{
                Log.e("TAG", "AGREGA ALGO")
            }
        }

        viewModel.movieLiveData.observe(this, {
            Log.e("PAGE", it.page.toString())
            Log.e("TOTAL_PAGE", it.total_pages.toString())
            Log.e("TOTAL_RESULTS", it.total_results.toString())
            Log.e("RESULTS", it.results.toString())
        })
    }
}