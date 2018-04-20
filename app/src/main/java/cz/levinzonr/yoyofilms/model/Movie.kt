package cz.levinzonr.yoyofilms.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import android.os.Parcel
import android.os.Parcelable
import cz.levinzonr.yoyofilms.model.local.Converters

@Entity
class Movie() : Parcelable {

    companion object {
       const val IMG_SRC = "https://image.tmdb.org/t/p/w500"
        @JvmField val CREATOR = object : Parcelable.Creator<Movie> {
            override fun createFromParcel(parcel: Parcel): Movie {
                return Movie(parcel)
            }

            override fun newArray(size: Int): Array<Movie?> {
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
        return "Movie(title='$title', overview=$overview, voteAverage=$voteAverage, status=$status)"
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

}