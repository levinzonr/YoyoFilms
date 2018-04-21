package cz.levinzonr.yoyofilms.view.search

import cz.levinzonr.yoyofilms.model.Film
import cz.levinzonr.yoyofilms.view.BaseView

interface FilmSearchView : BaseView<ArrayList<Film>> {

    fun onNothingFound()

    fun onEmptyQuery()
}