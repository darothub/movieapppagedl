package com.darotapp.movieapptoo.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.darotapp.movieapptoo.data.repository.MovieDetailsRepository
import com.darotapp.movieapptoo.data.repository.MoviePagedListRepository

class ViewModelFactory(private val movieDetailsRepository: MovieDetailsRepository, private val id:Int): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>):T =
        (SingleMovieViewModel(movieDetailsRepository, id) as T)
}


class MainViewModelFactory(private val moviePagedListRepository: MoviePagedListRepository): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>):T =
        (MainViewModel(moviePagedListRepository) as T)
}


