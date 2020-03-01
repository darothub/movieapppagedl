package com.darotapp.movieapptoo.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.darotapp.movieapptoo.data.repository.MoviePagedListRepository
import com.darotapp.movieapptoo.data.repository.NetworkState
import com.darotapp.movieapptoo.data.vo.Movies
import io.reactivex.disposables.CompositeDisposable

class MainViewModel(private val movieRepository:MoviePagedListRepository): ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val moviePagedList : LiveData<PagedList<Movies>> = movieRepository.fetchLiveMoviePagedList(compositeDisposable)


    val networkState:LiveData<NetworkState> by lazy {
        movieRepository.getNetworkState()
    }

    fun isListEmpty():Boolean{
        return moviePagedList.value?.isEmpty()?: true
    }

    override fun onCleared() {
        super.onCleared()

        compositeDisposable.dispose()
    }
}