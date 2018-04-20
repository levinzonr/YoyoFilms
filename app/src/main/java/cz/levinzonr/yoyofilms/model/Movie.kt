package cz.levinzonr.yoyofilms.model

import android.os.Parcel
import android.os.Parcelable

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

    var id: Int = -1
    var budget: Int = -1
    var releaseDate: String = String()
    var title: String = String()
    var overview: String? = null
    var voteAverage: Number? = null
    var status: String? = null

    var revenue: Int = 0
    var runtime: Int? = null

    var backdropPath: String? = null
    get() {
        if (field == null)  return field
        return "$IMG_SRC$field"
    }



    var posterPath: String? = null
    get() = "$IMG_SRC$field"

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        budget = parcel.readInt()
        releaseDate = parcel.readString()
        title = parcel.readString()
        overview = parcel.readString()
        status = parcel.readString()
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
        parcel.writeInt(revenue)
        parcel.writeValue(runtime)
    }

    override fun describeContents(): Int {
        return 0
    }


}