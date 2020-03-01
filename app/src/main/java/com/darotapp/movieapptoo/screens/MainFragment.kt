package com.darotapp.movieapptoo.screens


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.darotapp.movieapptoo.R
import com.darotapp.movieapptoo.adapter.PopularPagedListAdapter
import com.darotapp.movieapptoo.data.api.TheMovieDbClient
import com.darotapp.movieapptoo.data.repository.MoviePagedListRepository
import com.darotapp.movieapptoo.data.repository.NetworkState
import com.darotapp.movieapptoo.data.viewmodel.MainViewModel
import com.darotapp.movieapptoo.data.viewmodel.MainViewModelFactory
import com.darotapp.movieapptoo.data.vo.Movies
import kotlinx.android.synthetic.main.fragment_main.progress_bar
import kotlinx.android.synthetic.main.fragment_main.text_error

/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment() {

    lateinit var mainViewModel: MainViewModel
    lateinit var moviePagedListRepository: MoviePagedListRepository
    var recyclerView: RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment



        return inflater.inflate(R.layout.fragment_main, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val apiService = TheMovieDbClient.getClient()

        moviePagedListRepository  = MoviePagedListRepository(apiService)



        mainViewModel = getViewModel(moviePagedListRepository)

        val adapter = context?.let { PopularPagedListAdapter(it) }



        val gridLayoutManager = GridLayoutManager(context, 3)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup(){
            override fun getSpanSize(position: Int): Int {
                val viewType = adapter?.getItemViewType(position)
                if(viewType == adapter?.MOVIE_VIEWTYPE) return 1
                else return 3
            }

        }



        mainViewModel.moviePagedList.observe(this, Observer {movies->

            adapter?.submitList(movies)
            Log.i("movies", "$movies")
            recyclerView = view!!.findViewById(R.id.recycler_view_movies)
            recyclerView!!.layoutManager = gridLayoutManager
            recyclerView!!.setHasFixedSize(true)
            recyclerView!!.adapter = adapter
        })


//
//        mainViewModel.moviePagedList.observe(this, Observer {
//            Log.i("movies", "$it")
//            adapter?.submitList(it)
//        })



        mainViewModel.networkState.observe(this, Observer {
            Log.i("networkSTate", "${it.message}")
            progress_bar.visibility = if(mainViewModel.isListEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
            text_error.visibility = if(mainViewModel.isListEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE

            if(!mainViewModel.isListEmpty()){
                adapter?.setNetworkState(it)
            }
        })

    }

    private fun getViewModel(moviePagedListRepository:MoviePagedListRepository): MainViewModel {
        return MainViewModelFactory(moviePagedListRepository).create(MainViewModel::class.java)
    }
}
