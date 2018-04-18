package cz.levinzonr.yoyofilms.model.remote

import cz.levinzonr.yoyofilms.model.Movie

class Responce(
    val page: Int,
    val totalResults: Int,
    val totalPages: Int,
    val results: ArrayList<Movie>
)  {
    fun hasMore() : Boolean =  page < totalPages
}