package cz.levinzonr.yoyofilms.view


interface BaseView<in V>{

    fun onLoadingStarted()

    fun onLoadingFinished(items: V)

    fun onLoadingError(error: String)


}