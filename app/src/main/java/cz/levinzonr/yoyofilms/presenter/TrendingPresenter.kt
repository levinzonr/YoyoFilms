package cz.levinzonr.yoyofilms.presenter

import cz.levinzonr.yoyofilms.model.Repository
import cz.levinzonr.yoyofilms.model.Responce
import cz.levinzonr.yoyofilms.view.BaseView
import cz.levinzonr.yoyofilms.view.TrendingView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class TrendingPresenter : BasePresenter<TrendingView> {

    private val repository = Repository()
    private val cd = CompositeDisposable()
    private var view: TrendingView? = null

    override fun attachView(view: TrendingView) {
        this.view = view
    }

    fun fetchNowPlaying() {
        view?.onLoadingStarted()
        cd.add(repository.getNowPlaying(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        {resp: Responce? -> view?.onLoadingFinished(resp!!.results) },
                        {t: Throwable? -> view?.onLoadingError(t.toString())  }))
    }

    override fun detachView() {
        view = null
        if (!cd.isDisposed) cd.dispose()
    }
}