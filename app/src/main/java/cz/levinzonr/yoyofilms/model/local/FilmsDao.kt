package cz.levinzonr.yoyofilms.model.local

import android.arch.persistence.room.*
import cz.levinzonr.yoyofilms.model.Film
import io.reactivex.Flowable
import io.reactivex.Maybe

@Dao
interface FilmsDao {


    @Query("SELECT * FROM Film WHERE id LIKE :id")
    fun findById(id: Int) : Maybe<Film>

    @Query("SELECT * FROM Film")
    fun findAll() : Flowable<List<Film>>

    @Update
    fun updateAll(films: List<Film>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(films: List<Film>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(film: Film)

    @Update
    fun update(film: Film)

    @Delete
    fun delete(film: Film)


}