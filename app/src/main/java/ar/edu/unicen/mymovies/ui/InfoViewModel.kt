package ar.edu.unicen.mymovies.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unicen.mymovies.R
import ar.edu.unicen.mymovies.ddl.data.MovieRepository
import ar.edu.unicen.mymovies.ddl.models.MovieInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class InfoViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    @ApplicationContext private val context: Context //Para poder obtener los mensajes de error en el idioma adecuado
): ViewModel() {

    private val _loading = MutableStateFlow(true)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _movie = MutableStateFlow<MovieInfo?>(null)
    val movie: StateFlow<MovieInfo?> = _movie.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun getMovieDetails(id: Long, context: Context) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                val movie = movieRepository.getMovie(id)
                if (movie != null) {
                    _movie.value = movie
                    _loading.value = false
                } else {
                    _error.value = context.getString(R.string.no_movie_found)
                }
            } catch (e: IOException) {
                e.printStackTrace()
                _error.value = context.getString(R.string.network_error)
            } catch (e: Exception) {
                e.printStackTrace()
                _error.value = context.getString(R.string.fetch_error)
            } finally {
                _loading.value = false
            }
        }
    }
}