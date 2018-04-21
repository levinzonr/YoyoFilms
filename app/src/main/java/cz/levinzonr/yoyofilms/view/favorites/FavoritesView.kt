package cz.levinzonr.yoyofilms.view.favorites

import cz.levinzonr.yoyofilms.model.Film
import cz.levinzonr.yoyofilms.view.BaseView

interface FavoritesView : BaseView<ArrayList<Film>> {

    fun onEmptyView()

}