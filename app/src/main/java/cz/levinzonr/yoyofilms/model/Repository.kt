package cz.levinzonr.yoyofilms.model

import cz.levinzonr.yoyofilms.model.local.LocalDataSource
import cz.levinzonr.yoyofilms.model.remote.RemoteDataSource
import cz.levinzonr.yoyofilms.model.remote.Responce
import io.reactivex.Completable
import io.reactivex.Flowable

class Repository {

    private val remote = RemoteDataSource()
    private val local = LocalDataSource()

     fun getNowPlaying(page: Int = 1): Flowable<Responce> {
        return remote.getNowPlaying(page)
    }

    fun searchForMovies(query: String) : Flowable<Responce> {
        return remote.searchForMovies(query)
    }

    fun getMovieDetails(id: Int) : Flowable<Movie> {
        return remote.getMovieDetails(id)
    }

    fun getFavorites() : Flowable<List<Movie>> {
        return local.getFavorites()
    }

    fun addToFavorites(movie: Movie) : Completable {
        return local.addToFavorites(movie)
    }
    fun removeFromFavorites(movie: Movie) : Completable {
        return local.removeFromFavorites(movie)
    }

}