package cz.levinzonr.yoyofilms.model.remote

import cz.levinzonr.yoyofilms.model.Film

class Response(
    val page: Int,
    val totalResults: Int,
    val totalPages: Int,
    val results: ArrayList<Film>
)  {
    fun hasMore() : Boolean =  page < totalPages
}