package cz.levinzonr.yoyofilms.view.moviedetail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.squareup.picasso.Picasso
import cz.levinzonr.yoyofilms.R
import cz.levinzonr.yoyofilms.model.Movie
import cz.levinzonr.yoyofilms.presenter.MovieDetailPresenter
import kotlinx.android.synthetic.main.activity_movie_detail.*
import kotlinx.android.synthetic.main.card_details.*
import kotlinx.android.synthetic.main.content_movie_detail.*

class MovieDetailActivity : AppCompatActivity(), MovieDetailView {

    private lateinit var presenter: MovieDetailPresenter

    companion object {
        private const val TAG = "DetailActivity"
        private const val MOVIE_ID = "MovieID"

        fun startAsIntent(context: Context, id: Int) {
            val intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra(MOVIE_ID, id)
            context.startActivity(intent)
        }

    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
        setSupportActionBar(toolbar)
        presenter = MovieDetailPresenter()
        presenter.attachView(this)
        presenter.fetchMovieDetails(intent.getIntExtra(MOVIE_ID, -1))
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }


    override fun onLoadingStarted() {
        Log.d(TAG, "Started")
    }

    override fun onLoadingFinished(items: Movie) {
        toolbar_layout.title = items.title
        movie_budget.text = items.budget.toString()
        movie_overview.text = items.overview
        movie_revenue.text = items.revenue.toString()
        movie_runtime.text = items.runtime.toString()
        Log.d(TAG, items.toString())
        Picasso.get()
                .load(items.backdropPath ?: items.posterPath)
                .into(movie_image)
    }

    override fun onLoadingError(error: String) {
        Log.d(TAG, "Error: $error")
    }
}
