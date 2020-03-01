package com.darotapp.movieapptoo.data.api

import com.darotapp.movieapptoo.data.vo.MovieDetails
import com.darotapp.movieapptoo.data.vo.PopMoviesClass
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDbInterface {
//    "https://api.themoviedb.org/3/movie/popular?api_key=f1e256985ebc2be710bf1f4ed754da11&language=en-US&page=1"
//    "https://api.themoviedb.org/3/movie/{id}?api_key=f1e256985ebc2be710bf1f4ed754da11&language=en-US&page=${page?:1}"
//    "https://api.themoviedb.org/3/"

    @GET("movie/popular")
    fun getPopularMovies(@Query("page") page:Int):Single<PopMoviesClass>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") id:Int): Single<MovieDetails>
}