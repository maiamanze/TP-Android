package ar.edu.unicen.mymovies.ddl.data

import ar.edu.unicen.mymovies.ddl.data.dto.MovieHomeResponseDTO
import ar.edu.unicen.mymovies.ddl.data.dto.MovieInfoDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApi {
    @GET("movie/popular")
    suspend fun getMovies(): Response<MovieHomeResponseDTO>

    @GET("movie/{id}")
    suspend fun getMovie(@Path("id") id: Long): Response<MovieInfoDTO>


}