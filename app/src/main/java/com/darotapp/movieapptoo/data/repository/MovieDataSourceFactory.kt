package com.darotapp.movieapptoo.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.darotapp.movieapptoo.data.api.TheMovieDbInterface
import com.darotapp.movieapptoo.data.vo.Movies
import io.reactivex.disposables.CompositeDisposable

class MovieDataSourceFactory(private val apiService: TheMovieDbInterface, private val compositeDisposable: CompositeDisposable):DataSource.Factory<Int, Movies>() {

    val movieLiveDataSource = MutableLiveData<MovieDataSource>()
    override fun create(): DataSource<Int, Movies> {
        val movieDataSource = MovieDataSource(apiService, compositeDisposable)
        movieLiveDataSource.postValue(movieDataSource)
        return movieDataSource
    }
}