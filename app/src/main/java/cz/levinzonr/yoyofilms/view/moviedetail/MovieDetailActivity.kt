package cz.levinzonr.yoyofilms.view.moviedetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.squareup.picasso.Picasso
import cz.levinzonr.yoyofilms.R
import cz.levinzonr.yoyofilms.model.Genre
import cz.levinzonr.yoyofilms.model.Film
import cz.levinzonr.yoyofilms.presenter.MovieDetailPresenter
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.card_details.*
import kotlinx.android.synthetic.main.content_movie_detail.*
import kotlinx.android.synthetic.main.view_error.*

class MovieDetailActivity : AppCompatActivity(), MovieDetailView {

    private lateinit var presenter: MovieDetailPresenter

    companion object {
        private const val TAG = "DetailActivity"
        private const val ARG_MOVIE = "Film"

        fun startAsIntent(context: Context, film: Film) {
            val intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra(ARG_MOVIE, film)
            context.startActivity(intent)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        setSupportActionBar(toolbar)

        val movie = intent.getParcelableExtra<Film>(ARG_MOVIE)

        presenter = MovieDetailPresenter()
        presenter.attachView(this)
        presenter.fetchMovieDetails(movie.id)
        toolbar_layout.title = movie.title
        movie_overview.text = movie.overview
        button_favorites.setOnClickListener { view ->
            presenter.onFavoriteButtonClicked()
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { onBackPressed() }

        button_retry.setOnClickListener({
            presenter.fetchMovieDetails(movie.id)
        })

    }

    override fun setInFavorites(favorite: Boolean) {
        Log.d(TAG, "FAvorite? $favorite")
        val dr = if (favorite)
            ContextCompat.getDrawable(this, R.drawable.ic_favorite_white_24dp)
        else ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_white_24dp)
        button_favorites.setImageDrawable(dr)
    }

    override fun onLoadingStarted() {
        Log.d(TAG, "Started")
        details_view.visibility = View.GONE
        details_progressbar.visibility = View.VISIBLE
        error_view.visibility = View.GONE
        button_favorites.isEnabled = false
    }

    fun ArrayList<Genre>.asString() : String {
        val string = this.toString()
        return string.substring(1, string.length-1)
    }

    override fun onAddedToFavorites() {
        Toast.makeText(this, R.string.favorites_added_message, Toast.LENGTH_SHORT).show()
        button_favorites.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_white_24dp))
    }

    override fun onDeletedFromFavorites() {
        button_favorites.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_white_24dp))

        Snackbar.make(details_view,R.string.favorites_deleted_message, Snackbar.LENGTH_SHORT)
                .setAction(R.string.action_undo, {presenter.onFavoriteButtonClicked()}).show()
    }

    override fun onRequestConfirmation(callback: (Boolean) -> Unit) {
        ConfirmationDialog.show(supportFragmentManager, callback)
    }

    override fun onLoadingFinished(items: Film) {
        error_view.visibility = View.GONE
        Log.d(TAG, items.getBackdrop())
        button_favorites.isEnabled = true
        toolbar_layout.title = items.title
        movie_budget.text = items.getBudget()
        movie_rd.text = items.releaseDate
        movie_genre.text = items.genres.asString()
        movie_overview.text = items.overview
        movie_revenue.text = items.getRevenue()
        movie_runtime.text = getString(R.string.value_minutes, items.runtime)
        movie_rating.text = items.voteAverage.toString()
        Log.d(TAG, items.toString())
        Picasso.get()
                .load(items.getBackdrop())
                .into(movie_image)
        details_view.visibility = View.VISIBLE
        details_progressbar.visibility = View.GONE
    }

    override fun onLoadingError(error: String) {
        error_view.visibility = View.VISIBLE
        details_view.visibility = View.GONE
        details_progressbar.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}
