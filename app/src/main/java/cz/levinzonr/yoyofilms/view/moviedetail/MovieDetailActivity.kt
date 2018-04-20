package cz.levinzonr.yoyofilms.view.moviedetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.squareup.picasso.Picasso
import cz.levinzonr.yoyofilms.R
import cz.levinzonr.yoyofilms.model.Genre
import cz.levinzonr.yoyofilms.model.Movie
import cz.levinzonr.yoyofilms.presenter.MovieDetailPresenter
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.card_details.*
import kotlinx.android.synthetic.main.content_movie_detail.*

class MovieDetailActivity : AppCompatActivity(), MovieDetailView {

    private lateinit var presenter: MovieDetailPresenter

    companion object {
        private const val TAG = "DetailActivity"
        private const val ARG_MOVIE = "Movie"

        fun startAsIntent(context: Context, movie: Movie) {
            val intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra(ARG_MOVIE, movie)
            context.startActivity(intent)
        }

    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        setSupportActionBar(toolbar)

        val movie = intent.getParcelableExtra<Movie>(ARG_MOVIE)

        presenter = MovieDetailPresenter()
        presenter.attachView(this)
        presenter.fetchMovieDetails(movie.id)
        toolbar_layout.title = movie.title
        movie_overview.text = movie.overview
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }


    override fun onLoadingStarted() {
        Log.d(TAG, "Started")
        details_view.visibility = View.GONE
        details_progressbar.visibility = View.VISIBLE
    }

    fun ArrayList<Genre>.asString() : String {
        val string = this.toString()
        return string.substring(1, string.length-1)
    }

    override fun onLoadingFinished(items: Movie) {
        toolbar_layout.title = items.title
        movie_budget.text = getString(R.string.global_currency_usd, items.budget)
        movie_rd.text = items.releaseDate
        movie_genre.text = items.genres.asString()
        movie_overview.text = items.overview
        movie_revenue.text = getString(R.string.global_currency_usd, items.revenue)
        movie_runtime.text = getString(R.string.value_minutes, items.runtime)
        movie_rating.text = items.voteAverage.toString()
        Log.d(TAG, items.toString())
        Picasso.get()
                .load(items.backdropPath ?: items.posterPath)
                .into(movie_image)
        details_view.visibility = View.VISIBLE
        details_progressbar.visibility = View.GONE
    }

    override fun onLoadingError(error: String) {
        details_view.visibility = View.VISIBLE
        details_progressbar.visibility = View.GONE
    }
}
