package cz.levinzonr.yoyofilms.presenter

import cz.levinzonr.yoyofilms.model.Movie
import cz.levinzonr.yoyofilms.model.Repository
import cz.levinzonr.yoyofilms.view.moviedetail.MovieDetailView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieDetailPresenter : BasePresenter<MovieDetailView> {

    private val repository = Repository()
    private val cd = CompositeDisposable()
    private var view : MovieDetailView? = null

    override fun attachView(view: MovieDetailView) {
        this.view = view
    }

    fun fetchMovieDetails(id: Int) {
        if (id == -1) {
            view?.onLoadingError("Bad id")
            return
        }
        view?.onLoadingStarted()
        cd.add(repository.getMovieDetails(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        {resp: Movie? -> view?.onLoadingFinished(resp!!) },
                        {t: Throwable? -> view?.onLoadingError(t.toString()) }
                ))
    }

    override fun detachView() {
        if (!cd.isDisposed) cd.dispose()
        view = null
    }
}