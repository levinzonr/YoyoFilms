package cz.levinzonr.yoyofilms.presenter

import android.util.Log
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
    private lateinit var movie: Movie
    private var isFavorite: Boolean = false

    override fun attachView(view: MovieDetailView) {
        this.view = view
    }

    fun fetchMovieDetails(id: Int) {
        if (id == -1) {
            view?.onLoadingError("Bad id")
            return
        }

        cd.add(repository.isInFavorites(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({t: Boolean? -> t?.let { isFavorite = t; view?.setInFavorites(t) } }))

        view?.onLoadingStarted()
        cd.add(repository.getMovieDetails(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        {resp: Movie? -> resp?.let {  movie = resp;view?.onLoadingFinished(resp) } },
                        {t: Throwable? -> view?.onLoadingError(t.toString()) }
                ))
    }

    fun onFavoriteButtonClicked() {
        if (isFavorite) {
            view?.onRequestConfirmation {
                if (it)
                    removeFromFavorites()
            }
        } else
            addToFavorites()
    }

    private fun addToFavorites() {
        cd.add(repository.addToFavorites(movie)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ isFavorite = true; view?.onAddedToFavorites()})
        )
    }

    private fun removeFromFavorites() {
        cd.add(repository.removeFromFavorites(movie)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({isFavorite = false; view?.onDeletedFromFavorites()}))
    }

    override fun detachView() {
        if (!cd.isDisposed) cd.dispose()
        view = null
    }
}