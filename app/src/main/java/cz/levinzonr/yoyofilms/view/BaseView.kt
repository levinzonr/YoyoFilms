package cz.levinzonr.yoyofilms.view

import cz.levinzonr.yoyofilms.model.Movie

interface BaseView{

    fun onLoadingStarted()

    fun onLoadingFinished(items: ArrayList<Movie>)

    fun onLoadingError(error: String)


}