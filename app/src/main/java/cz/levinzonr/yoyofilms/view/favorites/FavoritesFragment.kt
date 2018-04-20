package cz.levinzonr.yoyofilms.view.favorites


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import cz.levinzonr.yoyofilms.R
import cz.levinzonr.yoyofilms.model.Movie
import cz.levinzonr.yoyofilms.presenter.FavoritesPresenter
import cz.levinzonr.yoyofilms.view.MovieListAdapter
import cz.levinzonr.yoyofilms.view.VerticalSpaceDecoration
import cz.levinzonr.yoyofilms.view.moviedetail.MovieDetailActivity
import kotlinx.android.synthetic.main.fragment_favorites.*


class FavoritesFragment : Fragment(), FavoritesView {

    private lateinit var presenter: FavoritesPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_view.apply {
            adapter = MovieListAdapter({MovieDetailActivity.startAsIntent(context, it)})
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(VerticalSpaceDecoration())
        }
        presenter = FavoritesPresenter()
        presenter.attachView(this)
        presenter.getFavorites()

    }

    override fun onLoadingStarted() {
        progress_bar.visibility = View.VISIBLE
        recycler_view.visibility = View.GONE

    }

    override fun onLoadingFinished(items: ArrayList<Movie>) {
        progress_bar.visibility = View.GONE
        recycler_view.visibility = View.VISIBLE
        (recycler_view.adapter as MovieListAdapter).items = items
    }

    override fun onLoadingError(error: String) {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        presenter.detachView()
    }

}
