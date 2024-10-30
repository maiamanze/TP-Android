package ar.edu.unicen.mymovies.ddl.data.dto

import ar.edu.unicen.mymovies.ddl.models.Genre
import ar.edu.unicen.mymovies.ddl.models.MovieInfo
import com.google.gson.annotations.SerializedName

data class MovieInfoDTO(
    @SerializedName("title")
    val title: String,
    @SerializedName("poster_path")
    val poster: String,
    @SerializedName("overview")
    val synopsis: String,
    @SerializedName("genres")
    val genres: List<Genre>,
    @SerializedName("vote_average")
    val rating: Float
) {

    fun toMovie(movieId: Long): MovieInfo {
        return MovieInfo(
            id = movieId,
            title = title,
            poster = poster,
            synopsis = synopsis,
            genres = genres.map { it.name },
            rating = rating
        )
    }

}