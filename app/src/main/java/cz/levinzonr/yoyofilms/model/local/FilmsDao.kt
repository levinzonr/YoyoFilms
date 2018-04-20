package cz.levinzonr.yoyofilms.model.local

import android.arch.persistence.room.*
import cz.levinzonr.yoyofilms.model.Movie
import io.reactivex.Flowable

@Dao
interface FilmsDao {


    @Query("SELECT * FROM Movie WHERE id LIKE :id")
    fun findById(id: Int) : Flowable<Movie>

    @Query("SELECT * FROM Movie")
    fun findAll() : Flowable<List<Movie>>

    @Update
    fun updateAll(films: List<Movie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(films: List<Movie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(film: Movie)

    @Update
    fun update(film: Movie)

    @Delete
    fun delete(film: Movie)


}