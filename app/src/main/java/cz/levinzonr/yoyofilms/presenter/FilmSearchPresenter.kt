package cz.levinzonr.yoyofilms.presenter

import android.util.Log
import cz.levinzonr.yoyofilms.model.remote.Repository
import cz.levinzonr.yoyofilms.model.remote.Responce
import cz.levinzonr.yoyofilms.view.search.FilmSearchView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class FilmSearchPresenter : BasePresenter<FilmSearchView> {

    private val repository = Repository()
    private var disposable: Disposable? = null
    private var view: FilmSearchView? = null

    override fun attachView(view: FilmSearchView) {
      this.view = view
    }

    fun setSearchQuery(query : String) {
        if (query.isEmpty()) {
            view?.onNothingFound()
            return
        }
        view?.onLoadingStarted()
        disposable?.let { if(!it.isDisposed) it.dispose() }
        disposable = repository.searchForMovies(query).delaySubscription(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        {t: Responce? -> t?.let {
                        if (!t.results.isEmpty()) view?.onLoadingFinished(t.results)
                        else view?.onNothingFound() }
                        },
                        {error: Throwable? -> view?.onLoadingError(error.toString()) }
                )
    }

    fun cancelSearch() {
        disposable?.let {
            if (!it.isDisposed) it.dispose()
        }
    }

    override fun detachView() {
        view = null
        disposable?.let { if (!it.isDisposed) it.dispose() }
    }


}