package cz.levinzonr.yoyofilms.view.search

import cz.levinzonr.yoyofilms.model.Movie
import cz.levinzonr.yoyofilms.view.BaseView

interface FilmSearchView : BaseView<ArrayList<Movie>> {

    fun onNothingFound()

}