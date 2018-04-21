package cz.levinzonr.yoyofilms.model.local

import cz.levinzonr.yoyofilms.model.Film
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe

class LocalDataSource {

    private val db = AppDatabase.instance


    fun addToFavorites(film: Film) : Completable {
        return  Completable.fromCallable {
            db.filmsDao().insert(film)
        }
    }

    fun removeFromFavorites(film: Film) : Completable {
        return Completable.fromCallable {
            db.filmsDao().delete(film)
        }
    }

    fun getFavorites() : Flowable<List<Film>> {
        return db.filmsDao().findAll()
    }

    fun getFilmDetails(id: Int) : Maybe<Film> {
        return db.filmsDao().findById(id)
    }

    fun updateFilm(film: Film) : Completable {
        return  Completable.fromCallable {
            db.filmsDao().update(film)
        }
    }

}