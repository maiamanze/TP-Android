package ar.edu.unicen.mymovies.ddl.data.dto

import com.google.gson.annotations.SerializedName

class MovieHomeResponseDTO(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<MovieHomeDTO>
)

