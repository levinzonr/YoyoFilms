package cz.levinzonr.yoyofilms.model.local

import android.arch.persistence.room.TypeConverter
import android.arch.persistence.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import cz.levinzonr.yoyofilms.model.Genre


class Converters {

    @TypeConverter
    fun listToString(list: ArrayList<Genre>?): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun stringToList(string: String?): ArrayList<Genre> {
        if (string == null)
            return ArrayList()

        val listType = object : TypeToken<ArrayList<Genre>>() {}.type
        return Gson().fromJson(string, listType)
    }

}


