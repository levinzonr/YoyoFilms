package cz.levinzonr.yoyofilms.view

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import cz.levinzonr.yoyofilms.R
import cz.levinzonr.yoyofilms.model.Movie
import kotlinx.android.synthetic.main.item_movie.view.*

class MovieListAdapter(val listener: OnClickListener) : RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {
    var items = ArrayList<Movie>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    fun String.letString() : String {
        if (this.length >= 160) return "${this.substring(0, 160 )}..."
        return this
    }

    interface OnClickListener {
        fun onItemSelected(movie: Movie)
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bindView(movie: Movie) {
            view.setOnClickListener({listener.onItemSelected(movie)})
            view.movie_title.text = movie.title
            view.movie_overview.text = movie.overview?.letString()
            view.movie_rating.text = movie.voteAverage.toString()
            Log.d("IMAGE:", movie.posterPath)
            Picasso.get()
                    .load(movie.posterPath)
                    .error(R.drawable.ic_home_black_24dp)
                    .into(view.movie_image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        val view = inflater.inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindView(items[position])
    }
}