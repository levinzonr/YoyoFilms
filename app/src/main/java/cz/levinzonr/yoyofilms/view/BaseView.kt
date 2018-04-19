package cz.levinzonr.yoyofilms.view

import cz.levinzonr.yoyofilms.model.Movie

interface BaseView<in V>{

    fun onLoadingStarted()

    fun onLoadingFinished(items: V)

    fun onLoadingError(error: String)


}