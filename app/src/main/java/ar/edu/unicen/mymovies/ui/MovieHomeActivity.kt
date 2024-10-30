package ar.edu.unicen.mymovies.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import ar.edu.unicen.mymovies.R
import ar.edu.unicen.mymovies.databinding.MovieHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MovieHomeActivity : AppCompatActivity() {

    private lateinit var binding: MovieHomeBinding
    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MovieHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.retryButton.setOnClickListener {
            binding.error.visibility = android.view.View.INVISIBLE
            binding.retryButton.visibility = android.view.View.INVISIBLE
            viewModel.getMovies(this)
        }

        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        subscribeToViewModel()
    }

    private fun subscribeToViewModel() {
        viewModel.loading.onEach { loading ->
            if (loading) {
                binding.progressBar.visibility = android.view.View.VISIBLE
            } else {
                binding.progressBar.visibility = android.view.View.INVISIBLE
            }
        }.launchIn(lifecycleScope)

        viewModel.movies.onEach { movies ->
            binding.popularList.adapter = MovieAdapter(
                movies = movies ?: emptyList(),
                onUserClick = { movie ->
                    val intent = Intent(this, MovieInfoActivity::class.java)
                    intent.putExtra("movie_id", movie.id)
                    startActivity(intent)
                }
            )
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