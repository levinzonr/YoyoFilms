package cz.levinzonr.yoyofilms.model.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import cz.levinzonr.yoyofilms.App
import cz.levinzonr.yoyofilms.model.Movie

@Database(version = 1, entities = [Movie::class])
abstract class AppDatabase(context: Context) : RoomDatabase() {

    private object Holder { val instance = Room.databaseBuilder(App.getContext(), AppDatabase::class.java, DB_NAME ).build() }

    companion object {
        const val DB_NAME = "MyDatabase"
        val instance: AppDatabase by lazy { Holder.instance }

    }


    abstract fun  filmsDao() : FilmsDao

}