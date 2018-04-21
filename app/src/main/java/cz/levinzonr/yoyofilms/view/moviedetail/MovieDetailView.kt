package cz.levinzonr.yoyofilms.view.moviedetail

import cz.levinzonr.yoyofilms.model.Film
import cz.levinzonr.yoyofilms.view.BaseView

interface MovieDetailView : BaseView<Film>{

    fun setInFavorites(favorite: Boolean)

    fun onAddedToFavorites()

    fun onRequestConfirmation(callback: (Boolean) -> Unit)

    fun onDeletedFromFavorites()

}