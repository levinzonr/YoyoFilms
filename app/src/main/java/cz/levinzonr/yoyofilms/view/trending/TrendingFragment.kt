package cz.levinzonr.yoyofilms.view.trending


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import cz.levinzonr.yoyofilms.R
import cz.levinzonr.yoyofilms.model.Movie
import cz.levinzonr.yoyofilms.presenter.TrendingPresenter
import cz.levinzonr.yoyofilms.view.MovieListAdapter
import cz.levinzonr.yoyofilms.view.VerticalSpaceDecoration
import cz.levinzonr.yoyofilms.view.moviedetail.MovieDetailActivity
import kotlinx.android.synthetic.main.fragment_trending.*


class TrendingFragment : Fragment(), TrendingView{

    private lateinit var presenter: TrendingPresenter
    private lateinit var rvAdapter: MovieListAdapter

    companion object {
        const val TAG = "Presenter Fragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trending, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        Log.d(TAG, "ViewCreated")
        super.onViewCreated(view, savedInstanceState)
        rvAdapter = MovieListAdapter({MovieDetailActivity.startAsIntent(context, it)
        })
        recycler_view.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = rvAdapter
            addItemDecoration(VerticalSpaceDecoration())
        }
        presenter = TrendingPresenter()
        presenter.attachView(this)
        presenter.fetchNowPlaying()
    }



    override fun onLoadingStarted() {
        Log.d(TAG, "Laoding started")
        progress_bar.visibility = View.VISIBLE
        recycler_view.visibility = View.GONE
    }

    override fun onLoadingFinished(items: ArrayList<Movie>) {
        Log.d(TAG, "Loadted: ${items.size}")
        rvAdapter.items = items
        progress_bar.visibility = View.GONE
        recycler_view.visibility = View.VISIBLE
    }

    override fun onLoadingError(error: String) {
        Log.d(TAG, "Error: $error")
        progress_bar.visibility = View.GONE
        recycler_view.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "DestroView")
        presenter.detachView()
    }
}
