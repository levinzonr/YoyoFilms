package cz.levinzonr.yoyofilms.model.remote

import cz.levinzonr.yoyofilms.model.Movie
import io.reactivex.Flowable

class Repository {

    private val remote = RemoteDataSource()

     fun getNowPlaying(page: Int = 1): Flowable<Responce> {
        return remote.getNowPlaying(page)
    }

    fun searchForMovies(query: String) : Flowable<Responce> {
        return remote.searchForMovies(query)
    }

    fun getMovieDetails(id: Int) : Flowable<Movie> {
        return remote.getMovieDetails(id)
    }

}