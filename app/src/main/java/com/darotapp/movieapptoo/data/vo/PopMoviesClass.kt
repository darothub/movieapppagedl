package com.darotapp.movieapptoo.data.vo

import com.google.gson.annotations.SerializedName

data class PopMoviesClass(
    @SerializedName("total_pages")
    var totalPages:Int,
    @SerializedName("results")
    var results: List<Movies>
) {
}



class Movies(
    @SerializedName("title")
    var title:String?,
    @SerializedName("poster_path")
    var movieImage:String?,
    @SerializedName("vote_average")
    var rating:Float?,
    var overView:String?,
    @SerializedName("release_date")
    var releaseDate:String?,
    var id:Int

) {

}