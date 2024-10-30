package ar.edu.unicen.mymovies.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import ar.edu.unicen.mymovies.databinding.MovieInfoBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MovieInfoActivity: AppCompatActivity() {
    private lateinit var binding: MovieInfoBinding
    private val viewModel by viewModels<InfoViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MovieInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getLongExtra("movie_id", -1)
        if (id != -1L)
            viewModel.getMovieDetails(id, this)

        binding.navigationButton.setOnClickListener {
            val intent = Intent(this, MovieHomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        }

        binding.retryButton.setOnClickListener {
            binding.error.visibility = android.view.View.INVISIBLE
            binding.retryButton.visibility = android.view.View.INVISIBLE
            viewModel.getMovieDetails(id, this)
        }

        subscribeToViewModel()
    }

    private fun subscribeToViewModel() {
        viewModel.loading.onEach { loading ->
            if (loading) {
                binding.infoProgressBar.visibility = android.view.View.VISIBLE
                binding.navigationButton.visibility = android.view.View.INVISIBLE
            } else {
                binding.infoProgressBar.visibility = android.view.View.INVISIBLE
                binding.navigationButton.visibility = android.view.View.VISIBLE
            }
        }.launchIn(lifecycleScope)

        viewModel.movie.onEach { movie ->
            movie?.let {
                binding.movieInfoTitle.text = it.title
                binding.movieInfoSynopsis.text = it.synopsis
                binding.movieInfoGenres.text = "Genres: ${it.genres?.joinToString(", ")}"
                binding.movieInfoRating.text = "Rating: ${it.rating}"

                val baseUrl = "https://image.tmdb.org/t/p/w500"
                val posterUrl = baseUrl + it.poster

                Glide.with(this)
                    .load(posterUrl)
                    .into(binding.movieInfoImage)
            }

        }.launchIn(lifecycleScope)

        viewModel.error.onEach { error ->
            if (error != null) {
                binding.error.text = viewModel.error.value
                binding.error.visibility = android.view.View.VISIBLE
                binding.retryButton.visibility = android.view.View.VISIBLE
            } else {
                binding.error.visibility = android.view.View.INVISIBLE
                binding.retryButton.visibility = android.view.View.INVISIBLE
            }
        }.launchIn(lifecycleScope)
    }
}