package com.darotapp.movieapptoo.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.darotapp.movieapptoo.data.repository.MovieDetailsRepository
import com.darotapp.movieapptoo.data.repository.NetworkState
import com.darotapp.movieapptoo.data.vo.MovieDetails
import io.reactivex.disposables.CompositeDisposable

class SingleMovieViewModel(private val movieDetailsRepository: MovieDetailsRepository, id:Int):ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val movieDetails:LiveData<MovieDetails> by lazy {
        movieDetailsRepository.fetchSingleMovie(compositeDisposable, id)
    }

    val networkState:LiveData<NetworkState> by lazy {
        movieDetailsRepository.getNetworkDataState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}