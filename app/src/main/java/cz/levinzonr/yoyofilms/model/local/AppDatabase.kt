package cz.levinzonr.yoyofilms.model.local

import android.arch.persistence.room.*
import cz.levinzonr.yoyofilms.App
import cz.levinzonr.yoyofilms.model.Film

@Database(version = 1, entities = [Film::class])
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    private object Holder { val instance = Room.databaseBuilder(App.getContext(), AppDatabase::class.java, DB_NAME ).build() }

    companion object {
        const val DB_NAME = "MyDatabase"
        val instance: AppDatabase by lazy { Holder.instance }

    }


    abstract fun  filmsDao() : FilmsDao

}