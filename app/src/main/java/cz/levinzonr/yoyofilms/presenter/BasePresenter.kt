package cz.levinzonr.yoyofilms.presenter


interface BasePresenter<in V> {

    fun attachView(view: V)

    fun detachView()
}