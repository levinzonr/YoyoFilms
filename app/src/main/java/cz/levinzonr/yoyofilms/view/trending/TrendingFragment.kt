package cz.levinzonr.yoyofilms.view.trending


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cz.levinzonr.stackquestions.screens.viewutils.InfiniteScrollListener

import cz.levinzonr.yoyofilms.R
import cz.levinzonr.yoyofilms.model.Movie
import cz.levinzonr.yoyofilms.presenter.TrendingPresenter
import cz.levinzonr.yoyofilms.view.MovieListAdapter
import cz.levinzonr.yoyofilms.view.VerticalSpaceDecoration
import cz.levinzonr.yoyofilms.view.moviedetail.MovieDetailActivity
import kotlinx.android.synthetic.main.fragment_trending.*
import kotlinx.android.synthetic.main.view_error.*


class TrendingFragment : Fragment(), TrendingView, InfiniteScrollListener.InfiniteScrollCallbacks{

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
        rvAdapter = MovieListAdapter(
                {MovieDetailActivity.startAsIntent(context, it) }, {presenter.fetchNowPlayingPage()}
        )
        val lm = LinearLayoutManager(context)
        recycler_view.apply {
            layoutManager = lm
            adapter = rvAdapter
            addItemDecoration(VerticalSpaceDecoration())
            addOnScrollListener(InfiniteScrollListener( this@TrendingFragment, lm))
        }


        presenter = TrendingPresenter()
        presenter.attachView(this)
        presenter.fetchNowPlayingPage(1)
    }

    override fun onLoadMore(pageToLoad: Int) {
        presenter.fetchNowPlayingPage(pageToLoad)
    }

    override fun onLoadingStarted() {
        Log.d(TAG, "Laoding started")
        recycler_view.post{
            rvAdapter.isLoading = true
            rvAdapter.shoError = false
        }

    }

    override fun onLoadingFinished(items: ArrayList<Movie>) {
        Log.d(TAG, "Loadted: ${items.size}")
        recycler_view.post({
            rvAdapter.items.addAll(items)
            rvAdapter.isLoading = false
            rvAdapter.shoError = false
        })

    }

    override fun onLoadingError(error: String) {
        Log.d(TAG, "Error: $error")
        recycler_view.post {
            rvAdapter.isLoading = false
            rvAdapter.shoError = true
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "DestroView")
        presenter.detachView()
    }
}
