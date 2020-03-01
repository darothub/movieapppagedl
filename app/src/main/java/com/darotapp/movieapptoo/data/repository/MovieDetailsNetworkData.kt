package com.darotapp.movieapptoo.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.darotapp.movieapptoo.data.api.TheMovieDbInterface
import com.darotapp.movieapptoo.data.vo.MovieDetails
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.internal.disposables.ArrayCompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieDetailsNetworkData(private val apiservice:TheMovieDbInterface, private val compositeDisposable: CompositeDisposable) {

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState:LiveData<NetworkState>
        get() = _networkState

    private val _movieDetailsResponse = MutableLiveData<MovieDetails>()
    val movieDetailsResponse:LiveData<MovieDetails>
        get() = _movieDetailsResponse


    fun fetchMovieDetails(id:Int){
        _networkState.postValue(NetworkState.LOADING)

        try {
            compositeDisposable.add(
                apiservice.getMovieDetails(id)
                    .subscribeOn(Schedulers.io())
                    .subscribe({it->
                        _movieDetailsResponse.postValue(it)
                        _networkState.postValue(NetworkState.LOADED)
                    },{
                        _networkState.postValue(NetworkState.ERROR)
                        Log.e("MovieDetailsResponse", it.message)
                    })
            )
        }
        catch (e:Exception){
            Log.e("MovieDetailsResponse", e.message)
        }
    }
}