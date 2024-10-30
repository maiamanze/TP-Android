package ar.edu.unicen.mymovies.ddl.data

import ar.edu.unicen.mymovies.ddl.models.MovieHome
import ar.edu.unicen.mymovies.ddl.models.MovieInfo
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieRemoteDataSource: MovieRemoteDataSource
) {
    suspend fun getMovies(): List<MovieHome>? {
        return movieRemoteDataSource.getMovies()
    }

    suspend fun getMovie(id: Long): MovieInfo? {
        return movieRemoteDataSource.getMovie(id)
    }
}