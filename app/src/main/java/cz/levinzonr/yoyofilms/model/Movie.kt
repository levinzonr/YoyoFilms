package cz.levinzonr.yoyofilms.model

class Movie {

    companion object {
       const val IMG_SRC = "https://image.tmdb.org/t/p/w500"
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
    get() = "$IMG_SRC$field"



    var posterPath: String? = null
    get() = "$IMG_SRC$field"

    override fun toString(): String {
        return "Movie(title='$title', overview=$overview, voteAverage=$voteAverage, status=$status)"
    }


}