package cz.levinzonr.yoyofilms.view.moviedetail

import cz.levinzonr.yoyofilms.model.Movie
import cz.levinzonr.yoyofilms.view.BaseView

interface MovieDetailView : BaseView<Movie>{

    fun setInFavorites(favorite: Boolean)

    fun onAddedToFavorites()

    fun onRequestConfirmation(callback: (Boolean) -> Unit)

    fun onDeletedFromFavorites()

}