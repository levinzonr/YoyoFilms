package cz.levinzonr.yoyofilms.view.search


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.*

import cz.levinzonr.yoyofilms.R
import cz.levinzonr.yoyofilms.model.Movie
import cz.levinzonr.yoyofilms.presenter.FilmSearchPresenter


class FilmSearchFragment : Fragment(), FilmSearchView,
        SearchView.OnCloseListener, SearchView.OnQueryTextListener {

    private lateinit var searchMenuItem: MenuItem
    private lateinit var presenter: FilmSearchPresenter

    companion object {
        const val TAG = "FilmSearchFragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_film_search, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = FilmSearchPresenter()
        presenter.attachView(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_film_search, menu)
        menu?.findItem(R.id.action_search)?.let { searchMenuItem = it}
        ( searchMenuItem.actionView as SearchView).apply {
            setOnQueryTextListener(this@FilmSearchFragment)
            setOnCloseListener(this@FilmSearchFragment)
        }
    }

    override fun onNothingFound() {
        Log.d(TAG, "Something found")
    }

    override fun onLoadingStarted() {
        Log.d(TAG,"Search Started")
    }

    override fun onLoadingFinished(items: ArrayList<Movie>) {
        Log.d(TAG, "Something found: ${items.size}")
    }

    override fun onLoadingError(error: String) {
        Log.d(TAG, "Eror: $error")
    }

    override fun onClose(): Boolean {
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        newText?.let { presenter.setSearchQuery(it) }
        return  true
    }
}
