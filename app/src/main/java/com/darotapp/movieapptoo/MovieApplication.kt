package com.darotapp.movieapptoo

import android.app.Application
import android.graphics.Movie
import android.util.Log
import com.darotapp.movieapptoo.data.repository.MovieDetailsRepository

class MovieApplication: Application() {
    val movieDetailsRepository:MovieDetailsRepository?
        get() = ServiceLocator.provideMoviesRepository(this)




    override fun onCreate() {
        super.onCreate()
        Log.i("Movieapp", "MovieApplication called")
    }
}