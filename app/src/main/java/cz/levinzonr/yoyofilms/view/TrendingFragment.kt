package cz.levinzonr.yoyofilms.view


import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import cz.levinzonr.yoyofilms.R
import cz.levinzonr.yoyofilms.model.Movie
import cz.levinzonr.yoyofilms.presenter.TrendingPresenter


class TrendingFragment : Fragment(), TrendingView {

    private lateinit var presenter: TrendingPresenter

    companion object {
        const val TAG = "Presenter Fragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trending, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = TrendingPresenter()
        presenter.attachView(this)
        presenter.fetchNowPlaying()
    }


    override fun onLoadingStarted() {
        Log.d(TAG, "Laoding started")
    }

    override fun onLoadingFinished(items: ArrayList<Movie>) {
        Log.d(TAG, "Loadted: ${items.size}")
    }

    override fun onLoadingError(error: String) {
        Log.d(TAG, "Error: $error")
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}
