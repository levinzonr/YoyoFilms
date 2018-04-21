package cz.levinzonr.yoyofilms.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable

@Entity
class Film() : Parcelable {

    companion object {
       const val IMG_SRC = "https://image.tmdb.org/t/p/w500"
        @JvmField val CREATOR = object : Parcelable.Creator<Film> {
            override fun createFromParcel(parcel: Parcel): Film {
                return Film(parcel)
            }

            override fun newArray(size: Int): Array<Film?> {
                return arrayOfNulls(size)
            }
        }

    }
    @PrimaryKey
    var id: Int = -1
    var budget: Int = -1
    var releaseDate: String = String()
    var title: String = String()
    var overview: String? = null
    var voteAverage: Double? = -1.0
    var status: String? = String()

    var genres = ArrayList<Genre>()
    var tagline: String? = null
    var revenue: Int = -1
    var runtime: Int? = null

    var backdropPath: String? = null




    var posterPath: String? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        budget = parcel.readInt()
        releaseDate = parcel.readString()
        title = parcel.readString()
        overview = parcel.readString()
        status = parcel.readString()
        tagline = parcel.readString()
        revenue = parcel.readInt()
        runtime = parcel.readValue(Int::class.java.classLoader) as? Int
    }


    override fun toString(): String {
        return "Film(title='$title', overview=$overview, voteAverage=$voteAverage, status=$status)"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(budget)
        parcel.writeString(releaseDate)
        parcel.writeString(title)
        parcel.writeString(overview)
        parcel.writeString(status)
        parcel.writeString(tagline)
        parcel.writeInt(revenue)
        parcel.writeValue(runtime)
    }

    override fun describeContents(): Int {
        return 0
    }

    fun getPoster() : String {
        return "$IMG_SRC$posterPath"
    }

    fun getBackdrop() : String {
        return "$IMG_SRC${backdropPath?: posterPath}"
    }

    fun getBudget() : String {
        return "$${budget.asString()}"
    }

    fun getRevenue() : String {
        return "$${revenue.asString()}"
    }


    fun Int.asString() : String {
        val str = this.toString()
        var ret = String()
        str.forEachIndexed { index, c ->
            val coma = index % 3 == 0 && index != 0
            ret += if (coma) ",$c"
            else "$c"
        }
        return ret
    }

}