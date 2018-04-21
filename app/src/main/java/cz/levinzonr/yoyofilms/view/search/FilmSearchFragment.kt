package cz.levinzonr.yoyofilms.view.search


import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.*

import cz.levinzonr.yoyofilms.R
import cz.levinzonr.yoyofilms.model.Film
import cz.levinzonr.yoyofilms.presenter.FilmSearchPresenter
import cz.levinzonr.yoyofilms.view.MovieListAdapter
import cz.levinzonr.yoyofilms.view.rvutils.VerticalSpaceDecoration
import cz.levinzonr.yoyofilms.view.moviedetail.MovieDetailActivity
import kotlinx.android.synthetic.main.fragment_film_search.*
import kotlinx.android.synthetic.main.view_empty_search.*


class FilmSearchFragment : Fragment(), FilmSearchView, SearchView.OnQueryTextListener {

    private lateinit var searchMenuItem: MenuItem
    private lateinit var presenter: FilmSearchPresenter
    private lateinit var rvAdapter: MovieListAdapter

    companion object {
        const val TAG = "FilmSearchFragment"
        const val SAVED_QUERY = "SavedQuery"
        const val SAVED_STATE = "IsSearchActive"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_film_search, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvAdapter = MovieListAdapter({
            MovieDetailActivity.startAsIntent(context, it)
        })
        recycler_view.apply {
            adapter = rvAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(VerticalSpaceDecoration())
        }
        presenter = FilmSearchPresenter()
        presenter.attachView(this)
        onEmptyQuery()

        if (savedInstanceState != null) {
            presenter.query = savedInstanceState.getString(SAVED_QUERY)
            presenter.active = savedInstanceState.getBoolean(SAVED_STATE)
        }

    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_film_search, menu)
        menu?.findItem(R.id.action_search)?.let { searchMenuItem = it}
       if (presenter.query.isEmpty() || presenter.active) searchMenuItem.expandActionView()

        ( searchMenuItem.actionView as SearchView).apply {
            setOnQueryTextListener(this@FilmSearchFragment)
            setQuery(presenter.query, false)
            setOnCloseListener { Log.d(TAG, "closed"); true }
        }
        searchMenuItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener{
            override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                presenter.cancelSearch()
                return true
            }
        })

    }

    override fun onEmptyQuery() {
        progress_bar.visibility = View.GONE
        recycler_view.visibility = View.GONE
        empty_view.visibility = View.VISIBLE
        empty_msg.text = getString(R.string.empty_query)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putString(SAVED_QUERY, presenter.query)
        outState?.putBoolean(SAVED_STATE, searchMenuItem.isActionViewExpanded)
    }

    override fun onNothingFound() {
        progress_bar.visibility = View.GONE
        recycler_view.visibility = View.GONE
        empty_view.visibility = View.VISIBLE
        empty_msg.text = getString(R.string.empty_msg)
    }

    override fun onLoadingStarted() {
        progress_bar.visibility = View.VISIBLE
        recycler_view.visibility = View.GONE
        empty_view.visibility = View.GONE    }

    override fun onLoadingFinished(items: ArrayList<Film>) {
        progress_bar.visibility = View.GONE
        recycler_view.visibility = View.VISIBLE
        rvAdapter.items = items
        empty_view.visibility = View.GONE
    }

    override fun onLoadingError(error: String) {
        progress_bar.visibility = View.GONE
        recycler_view.visibility = View.GONE
        empty_view.visibility = View.GONE
        Snackbar.make(recycler_view, R.string.error_loading_msg, Snackbar.LENGTH_SHORT)
                .setAction(R.string.action_retry, {})
    }



    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        newText?.let { presenter.setSearchQuery(it) }
        return  true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG, "DestroView")
        presenter.detachView()
    }


}
