package com.darotapp.movieapptoo.screens


import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import com.darotapp.movieapptoo.MovieApplication

import com.darotapp.movieapptoo.R
import com.darotapp.movieapptoo.data.api.POSTER_URL
import com.darotapp.movieapptoo.data.api.TheMovieDbClient
import com.darotapp.movieapptoo.data.repository.MovieDetailsRepository
import com.darotapp.movieapptoo.data.repository.NetworkState
import com.darotapp.movieapptoo.data.viewmodel.SingleMovieViewModel
import com.darotapp.movieapptoo.data.viewmodel.ViewModelFactory
import com.darotapp.movieapptoo.data.vo.MovieDetails
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_single_movie_screen.*
import kotlin.properties.Delegates

/**
 * A simple [Fragment] subclass.
 */
class SingleMovieScreen : Fragment() {

    //    //View model factory to inject or instantiate movieViewModel
//    private val movieDetailViewModel by viewModels<SingleMovieViewModel> {
//        ViewModelFactory((requireContext().applicationContext as MovieApplication).movieDetailsRepository!!)
    private lateinit var viewModel: SingleMovieViewModel
    private lateinit var movieDetailsRepository: MovieDetailsRepository
    private var movieId by Delegates.notNull<Int>()
    //    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        movieId = arguments?.let { SingleMovieScreenArgs.fromBundle(it).id }!!

//        val moveId = 1
        val apiService = TheMovieDbClient.getClient()
        movieDetailsRepository = MovieDetailsRepository(apiService)
        viewModel = getViewModel(movieId)
        viewModel.movieDetails.observe(this, Observer {
            bindUI(it)
        })

        viewModel.networkState.observe(this, Observer {
            progress_bar.visibility = if(it == NetworkState.LOADING) View.VISIBLE else View.GONE
            text_error.visibility = if(it == NetworkState.ERROR) View.VISIBLE else View.GONE
        })
        return inflater.inflate(R.layout.fragment_single_movie_screen, container, false)
    }

    private fun bindUI(movieDetails: MovieDetails?) {
        releaseDate.setText(movieDetails?.releaseDate)
        overview.setText(movieDetails?.overview)
        val posterImageUrl = POSTER_URL + movieDetails?.movieImage

        Picasso.get().load(posterImageUrl).into(image)


    }


    private fun getViewModel(id: Int): SingleMovieViewModel {
        return ViewModelFactory(movieDetailsRepository, id).create(SingleMovieViewModel::class.java)
    }

}
