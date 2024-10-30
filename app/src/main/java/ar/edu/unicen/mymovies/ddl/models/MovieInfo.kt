package ar.edu.unicen.mymovies.ddl.models

class MovieInfo(
    val id: Long,
    val title: String,
    val poster: String,
    val synopsis: String,
    val genres: List<String>,
    val rating: Float
)