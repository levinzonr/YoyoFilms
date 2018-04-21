package cz.levinzonr.yoyofilms.model.remote

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import cz.levinzonr.yoyofilms.model.Film
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

class RemoteDataSource {

    private val gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
    private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    private val service = retrofit.create(MovieService::class.java)


    fun getNowPlaying(page: Int) : Flowable<Response> {
        return service.getNowPlaying(page)
    }

    fun searchForMovies(query: String) : Flowable<Response> {
        return service.searchForMovies(query)
    }

    fun getFilmDetails(id : Int) : Single<Film> {
        return service.getMovieDetail(id)
    }


    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val API_KEY = "4cb1eeab94f45affe2536f2c684a5c9e"
    }


    interface MovieService {
        @GET("movie/now_playing?api_key=$API_KEY&language=en-US")
        fun getNowPlaying(@Query("page") page: Int) : Flowable<Response>

        @GET("search/movie?api_key=$API_KEY&language=en-US")
        fun searchForMovies(@Query("query") query: String) : Flowable<Response>

        @GET("movie/{id}?api_key=$API_KEY")
        fun getMovieDetail(@Path("id") id: Int) : Single<Film>

    }

}