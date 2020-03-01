package com.darotapp.movieapptoo.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.darotapp.movieapptoo.data.api.POST_PER_PAGE
import com.darotapp.movieapptoo.data.api.TheMovieDbInterface
import com.darotapp.movieapptoo.data.vo.Movies
import io.reactivex.disposables.CompositeDisposable

class MoviePagedListRepository(private val apiService:TheMovieDbInterface) {

    lateinit var moviePagedList:LiveData<PagedList<Movies>>
    lateinit var movieDataSourceFactory: MovieDataSourceFactory

    fun fetchLiveMoviePagedList(compositeDisposable: CompositeDisposable):LiveData<PagedList<Movies>>{
        movieDataSourceFactory = MovieDataSourceFactory(apiService, compositeDisposable)
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        moviePagedList = LivePagedListBuilder(movieDataSourceFactory, config).build()
        Log.i("moviePagedList", "$moviePagedList")
        return moviePagedList
    }
    fun getNetworkState():LiveData<NetworkState>{
        return Transformations.switchMap<MovieDataSource, NetworkState>(
            movieDataSourceFactory.movieLiveDataSource, MovieDataSource::networkState
        )
    }
}