package cz.levinzonr.yoyofilms.presenter

import cz.levinzonr.yoyofilms.model.Movie
import cz.levinzonr.yoyofilms.model.Repository
import cz.levinzonr.yoyofilms.view.favorites.FavoritesView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class FavoritesPresenter : BasePresenter<FavoritesView> {

    private var view: FavoritesView? =  null
    private val repository = Repository()
    private val cd = CompositeDisposable()

    override fun attachView(view: FavoritesView) {
        this.view = view
    }

    fun getFavorites() {
        cd.add(repository.getFavorites()
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({t: List<Movie>? -> t?.let { view?.onLoadingFinished(ArrayList(t)) } }))
    }

    override fun detachView() {
        view = null
        if (!cd.isDisposed) cd.dispose()
    }
}