package ar.edu.unicen.mymovies.ddl.data

import ar.edu.unicen.mymovies.ddl.models.MovieHome
import ar.edu.unicen.mymovies.ddl.models.MovieInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class MovieRemoteDataSource @Inject constructor(
    private val movieApi: MovieApi
) {

    suspend fun getMovies(): List<MovieHome>? {
        return withContext(Dispatchers.IO) {
            try {
                val response = movieApi.getMovies()
                return@withContext response.body()?.results?.map { it.toMovie() }
            } catch (e: IOException) {
                throw IOException("Error de red: ${e.message}")
            } catch (e: Exception) {
                throw Exception("Error desconocido: ${e.message}")
            }
        }
    }

    suspend fun getMovie(id: Long): MovieInfo? {
        return withContext(Dispatchers.IO) {
            try {
                val movie = movieApi.getMovie(id)
                return@withContext movie.body()?.toMovie(id)
            } catch (e: IOException) {
                throw IOException("Error de red: ${e.message}")
            } catch (e: Exception) {
                throw Exception("Error desconocido: ${e.message}")
            }
        }
    }
}