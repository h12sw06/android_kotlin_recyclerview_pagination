package com.android.kotlinrecyclerviewpagination.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.kotlinrecyclerviewpagination.R
import com.android.kotlinrecyclerviewpagination.models.Movie
import com.android.kotlinrecyclerviewpagination.ui.adapters.viewHolders.LoadingVH as LoadingVH1
import com.android.kotlinrecyclerviewpagination.ui.adapters.viewHolders.MovieVH as MovieVH1


class MovieListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val ITEM = 0
    private val LOADING = 1

    private var moviesList: MutableList<Movie> = ArrayList()
    private var isLoadingAdded: Boolean = false

    fun setMovies(moviesList: MutableList<Movie>) {
        this.moviesList = moviesList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            ITEM -> {
                val item = inflater.inflate(R.layout.movie_list_item, parent, false)
                MovieVH1(item)
            }
            LOADING -> {
                val item = inflater.inflate(R.layout.load_more_progress, parent, false)
                LoadingVH1(item)
            }
            else -> throw IllegalArgumentException("Wrong view type")
        }
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == moviesList.size - 1 && isLoadingAdded) LOADING else ITEM
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ITEM -> {
                val movie = moviesList[position]
                (holder as MovieVH1).setMovie(movie)
            }
            LOADING -> {
            }
        }
    }

    private fun add(r: Movie) {
        moviesList.add(r)
        notifyItemInserted(moviesList.size - 1)
    }

    fun addAll(moveMovies: List<Movie>) {
        for (result in moveMovies) {
            add(result)
        }
    }

    fun addLoadingFooter() {
        isLoadingAdded = true
        add(Movie())
    }

    fun removeLoadingFooter() {
        isLoadingAdded = false

        val position = moviesList.size - 1
        getItem(position)

        moviesList.removeAt(position)
        notifyItemRemoved(position)
    }

    private fun getItem(position: Int): Movie {
        return moviesList[position]
    }
}