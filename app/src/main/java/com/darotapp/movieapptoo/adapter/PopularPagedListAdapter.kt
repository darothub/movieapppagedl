package com.darotapp.movieapptoo.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.darotapp.movieapptoo.R
import com.darotapp.movieapptoo.data.api.POSTER_URL
import com.darotapp.movieapptoo.data.repository.NetworkState
import com.darotapp.movieapptoo.data.vo.Movies
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_single_movie_screen.*
import kotlinx.android.synthetic.main.network_state_item.view.*
import kotlinx.android.synthetic.main.recycler_item.view.*

class PopularPagedListAdapter(val context: Context):PagedListAdapter<Movies, RecyclerView.ViewHolder>(MovieDiffCallBack()) {
    val MOVIE_VIEWTYPE = 1
    val NETWORK_VIEWTYPE = 3

    private var networkState:NetworkState?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view:View

        if(viewType == MOVIE_VIEWTYPE){
            view = layoutInflater.inflate(R.layout.recycler_item, parent, false)
            return MovieItemViewHolder(view)
        }
        else{
            view = layoutInflater.inflate(R.layout.network_state_item, parent, false)
            return MovieItemViewHolder(view)
        }
    }


    fun hasExtraRow():Boolean{
        return networkState!=null && networkState != NetworkState.LOADED
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(getItemViewType(position) == MOVIE_VIEWTYPE){
            (holder as MovieItemViewHolder).bind(getItem(position), context)
        }
        else{
            networkState?.let { (holder as NetworkStateItemViewHolder).bind(it) }
        }
    }

    class MovieDiffCallBack : DiffUtil.ItemCallback<Movies>(){
        override fun areItemsTheSame(oldItem: Movies, newItem: Movies): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movies, newItem: Movies): Boolean {
            return oldItem.equals(newItem)
        }

    }
    class MovieItemViewHolder(view: View) : RecyclerView.ViewHolder(view){

        fun bind(movies: Movies?, context: Context){
            itemView.title.text = movies?.title
            itemView.releaseDate.text = movies?.releaseDate

            val posterImageUrl = POSTER_URL + movies?.movieImage

            Picasso.get().load(posterImageUrl).into(itemView.thumbnail)
            Log.i("bind", "AdapterBound")

            itemView.setOnClickListener{
                Log.i("to the next", "Lets go")
            }

        }
    }

    class NetworkStateItemViewHolder(view: View):RecyclerView.ViewHolder(view){

        fun bind(networkState: NetworkState){
            if(networkState != null && networkState == NetworkState.LOADING){
                itemView.progress_bar.visibility   = View.VISIBLE
            }
            else{
                itemView.progress_bar.visibility   = View.GONE
            }
            if(networkState != null && networkState == NetworkState.ERROR){
                itemView.progress_bar.visibility   = View.VISIBLE
                itemView.text_error.text = networkState.message
            }
            else{
                itemView.progress_bar.visibility   = View.GONE
            }
            if(networkState != null && networkState == NetworkState.END_OF_LIST){
                itemView.progress_bar.visibility   = View.VISIBLE
                itemView.text_error.text = networkState.message
            }
            else{
                itemView.progress_bar.visibility   = View.GONE
            }
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if(hasExtraRow()) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if(hasExtraRow() && position == itemCount-1){
            NETWORK_VIEWTYPE
        }else{
            MOVIE_VIEWTYPE
        }
    }

    fun setNetworkState(newNetworkState: NetworkState){
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()

        if(hadExtraRow != hasExtraRow){
            if(hadExtraRow){
                notifyItemRemoved(super.getItemCount())
            }
            else{
                notifyItemInserted(super.getItemCount())
            }
        }
        else if(hasExtraRow && previousState != newNetworkState){
            notifyItemChanged(itemCount-1)
        }
    }
}