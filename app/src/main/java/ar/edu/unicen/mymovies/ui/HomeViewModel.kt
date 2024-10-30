package ar.edu.unicen.mymovies.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ar.edu.unicen.mymovies.R
import ar.edu.unicen.mymovies.ddl.models.MovieHome
import ar.edu.unicen.mymovies.ddl.data.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    @ApplicationContext private val context: Context //Para poder obtener los mensajes de error en el idioma adecuado
): ViewModel() {

    private val _loading = MutableStateFlow(true)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _movies = MutableStateFlow<List<MovieHome>>(emptyList())
    val movies: StateFlow<List<MovieHome>> = _movies.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        getMovies(context)
    }

    fun getMovies(context: Context) {
        viewModelScope.launch {
            try {
                _loading.value = true
                _error.value = null
                val movieList = movieRepository.getMovies()
                if (!movieList.isNullOrEmpty()) {
                    _movies.value = movieList
                } else {
                    _error.value = context.getString(R.string.no_movies_found)
                }
            } catch (e: IOException) {
                _error.value = context.getString(R.string.network_error)
            } catch (e: Exception) {
                _error.value = context.getString(R.string.fetch_error)
            } finally {
                _loading.value = false
            }
        }
    }

}