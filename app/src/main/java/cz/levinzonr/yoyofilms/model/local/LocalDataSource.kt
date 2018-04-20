package cz.levinzonr.yoyofilms.model.local

import cz.levinzonr.yoyofilms.model.Movie
import io.reactivex.Completable
import io.reactivex.Flowable

class LocalDataSource {

    private val db = AppDatabase.instance


    fun addToFavorites(movie: Movie) : Completable {
        return  Completable.fromCallable {
            db.filmsDao().insert(movie)
        }
    }

    fun removeFromFavorites(movie: Movie) : Completable {
        return Completable.fromCallable {
            db.filmsDao().delete(movie)
        }
    }

    fun getFavorites() : Flowable<List<Movie>> {
        return db.filmsDao().findAll()
    }

    fun getFilmDetails(id: Int) : Flowable<Movie> {
        return db.filmsDao().findById(id)
    }

}