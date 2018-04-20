package cz.levinzonr.yoyofilms.model

import cz.levinzonr.yoyofilms.App
import cz.levinzonr.yoyofilms.androidutils.NetManager
import cz.levinzonr.yoyofilms.model.local.LocalDataSource
import cz.levinzonr.yoyofilms.model.remote.RemoteDataSource
import cz.levinzonr.yoyofilms.model.remote.Response
import io.reactivex.*

class Repository {

    private val remote = RemoteDataSource()
    private val local = LocalDataSource()
    private val netManager = NetManager(App.getContext())

     fun getNowPlaying(page: Int = 1): Flowable<Response> {
        return remote.getNowPlaying(page)
    }

    fun searchForMovies(query: String) : Flowable<Response> {
        return remote.searchForMovies(query)
    }

    fun getMovieDetails(id: Int) : Flowable<Movie> {
        return if (netManager.isConnected())
            remote.getFilmDetails(id).flatMap {
                return@flatMap local.updateFilm(it).toSingleDefault(it).toFlowable()
            }
        else
            local.getFilmDetails(id).toFlowable()
    }

    fun getFavorites() : Flowable<List<Movie>> {
        return local.getFavorites()
    }

    fun isInFavorites(id: Int) : Single<Boolean> {
        return local.getFilmDetails(id).switchIfEmpty(Maybe.just(Movie()))
                .toSingle()
                .flatMap {
                    return@flatMap Single.just(it.id != -1)
                }
    }

    fun addToFavorites(movie: Movie) : Completable {
        return local.addToFavorites(movie)
    }
    fun removeFromFavorites(movie: Movie) : Completable {
        return local.removeFromFavorites(movie)
    }

}