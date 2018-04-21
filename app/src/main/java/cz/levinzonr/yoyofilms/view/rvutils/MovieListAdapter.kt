package cz.levinzonr.yoyofilms.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import cz.levinzonr.yoyofilms.R
import cz.levinzonr.yoyofilms.model.Film
import kotlinx.android.synthetic.main.item_movie.view.*
import kotlinx.android.synthetic.main.rv_item_progress.view.*
import kotlinx.android.synthetic.main.view_error.view.*

class MovieListAdapter(val onClick: (Film) -> Unit, val onRetry: ()->Unit = {}) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val HOLDER_ITEM = 0
        private const val HOLDER_PROGRESS = 1
    }

    var items = ArrayList<Film>()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    var isLoading: Boolean = false
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    var shoError: Boolean = false
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    fun String.letString() : String {
        if (this.length >= 160) return "${this.substring(0, 160 )}..."
        return this
    }


    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bindView(film: Film) {
            view.setOnClickListener({onClick(film)})
            view.movie_title.text = film.title
            view.movie_overview.text = film.overview?.letString()
            view.movie_rating.text = film.voteAverage.toString()
            Picasso.get()
                    .load(film.getPoster())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(view.movie_image)
        }
    }

    inner class ProgressHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private var progressBar = view.list_progress
        private var errorView = view.error_view

        fun showError() {
            progressBar.visibility = View.GONE
            errorView.visibility = View.VISIBLE
            view.button_retry.setOnClickListener({onRetry()})
        }

        fun showLoading() {
            progressBar.visibility = View.VISIBLE
            errorView.visibility = View.GONE
        }

        fun hideAll() {
            progressBar.visibility = View.GONE
            errorView.visibility = View.GONE
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent?.context)
        return  when (viewType) {
            HOLDER_PROGRESS -> {
                val view = inflater.inflate(R.layout.rv_item_progress, parent, false)
                ProgressHolder(view)
            }
            HOLDER_ITEM -> {
                val view = inflater.inflate(R.layout.item_movie, parent, false)
                ViewHolder(view)
            }
            else -> throw  IllegalStateException()
        }

    }

    override fun getItemCount(): Int = items.size + 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is ViewHolder)
            holder.bindView(items[position])
        else if (holder is ProgressHolder) {

            when {
                isLoading -> holder.showLoading()
                shoError -> holder.showError()
                else -> holder.hideAll()
            }

        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (position < items.size) HOLDER_ITEM
        else HOLDER_PROGRESS
    }

}