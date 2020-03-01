package com.darotapp.movieapptoo.data.repository

import androidx.lifecycle.LiveData
import com.darotapp.movieapptoo.data.api.TheMovieDbInterface
import com.darotapp.movieapptoo.data.vo.MovieDetails
import io.reactivex.disposables.CompositeDisposable

class MovieDetailsRepository(private val apiService:TheMovieDbInterface) {

    lateinit var movieDetailsNetworkData: MovieDetailsNetworkData

    fun fetchSingleMovie(compositeDisposable: CompositeDisposable, id:Int):LiveData<MovieDetails>{
        movieDetailsNetworkData = MovieDetailsNetworkData(apiService, compositeDisposable)
        movieDetailsNetworkData.fetchMovieDetails(id)

        return movieDetailsNetworkData.movieDetailsResponse
    }

    fun getNetworkDataState():LiveData<NetworkState>{
        return movieDetailsNetworkData.networkState
    }
}