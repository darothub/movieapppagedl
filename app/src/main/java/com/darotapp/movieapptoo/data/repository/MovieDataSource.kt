package com.darotapp.movieapptoo.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.darotapp.movieapptoo.data.api.FIRST_PAGE
import com.darotapp.movieapptoo.data.api.TheMovieDbInterface
import com.darotapp.movieapptoo.data.vo.Movies
import com.darotapp.movieapptoo.data.vo.PopMoviesClass
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieDataSource(private val apiService:TheMovieDbInterface, private val compositeDisposable: CompositeDisposable):PageKeyedDataSource<Int, Movies>() {

    var page = FIRST_PAGE
    val networkState = MutableLiveData<NetworkState>()
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movies>
    ) {
        networkState.postValue(NetworkState.LOADING)


        try {
            compositeDisposable.add(
                apiService.getPopularMovies(page)
                    .subscribe({it->
                        callback.onResult(it.results, null, page+1)
                        Log.i("MovieDetailsResponse", "${it.results}")
                        networkState.postValue(NetworkState.LOADED)
                    },{
                        networkState.postValue(NetworkState.ERROR)
                        Log.e("MovieDetailsResponse", it.message)
                    })
            )
        }
        catch (e:Exception){
            Log.e("MovieDetailsResponse", e.message)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movies>) {
        networkState.postValue(NetworkState.LOADING)


        try {
            compositeDisposable.add(
                apiService.getPopularMovies(params.key)
                    .subscribe({it->
                        if(it.totalPages >= params.key){
                            callback.onResult(it.results,  params.key+1)
                            networkState.postValue(NetworkState.LOADED)
                        }
                        else{
                            networkState.postValue(NetworkState.END_OF_LIST)
                        }
                    },{
                        networkState.postValue(NetworkState.ERROR)
                        Log.e("MovieDetailsResponse", it.message)
                    })
            )
        }
        catch (e:Exception){
            Log.e("MovieDetailsResponse", e.message)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movies>) {

    }
}