package com.darotapp.movieapptoo.data.vo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MovieDetails(
    var title: String?,
    @SerializedName("poster_path")
    var movieImage: String?,
    @SerializedName("vote_average")
    var rating: Int?,
    var overview: String?,
    @SerializedName("release_date")
    var releaseDate: String?,
    val status: String?,
    val revenue: String?
):Serializable {
}