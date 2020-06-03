import android.net.Uri
import com.example.netflixremake.BuildConfig
import com.example.netflixremake.kotlin.common.extensions.toMD5
import com.example.netflixremake.kotlin.data.model.Character
import com.example.netflixremake.kotlin.data.model.Series
import com.example.netflixremake.kotlin.data.response.Response

import com.google.gson.GsonBuilder
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url
import java.net.URI
import java.util.*


private const val API_PUBLIC_KEY = BuildConfig.MARVEL_API_KEY_PUBLIC
private const val API_PRIVATE_KEY = BuildConfig.MARVEL_API_KEY_PRIVATE

interface MarvelApi {
    @GET
    fun allSeries(@Url seriesURI: String): Observable<Response<Series>>

    @GET
    fun allCharactersBySeries(@Url charactersURI: String): Observable<Response<Character>>

    @GET("characters")
    fun allCharacters(@Query("offset") offset: Int? = 0): Observable<Response<Character>>


    companion object {
        fun getService(): MarvelApi {
            /*
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            */

            val httpClient = OkHttpClient.Builder()
            //httpClient.addInterceptor(logging)

            /* Adiciona interceptor para colocar as chaves da api, timestamp e hash em todas as requisiçoes */
            httpClient.addInterceptor { chain ->
                val original = chain.request()
                val originalHttpUrl = original.url()

                val ts = (Calendar.getInstance(TimeZone.getTimeZone("UTC")).timeInMillis / 1000L).toString()
                val url = originalHttpUrl.newBuilder()
                        .addQueryParameter("apikey", API_PUBLIC_KEY)
                        .addQueryParameter("ts", ts)
                        .addQueryParameter("hash", "$ts$API_PRIVATE_KEY$API_PUBLIC_KEY".toMD5())
                        .build()

                chain.proceed(original.newBuilder().url(url).build())
            }

            val gson = GsonBuilder().setLenient().create()
            val retrofit = Retrofit.Builder()
                    .baseUrl("https://gateway.marvel.com/v1/public/")
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(httpClient.build())
                    .build()

            return retrofit.create<MarvelApi>(MarvelApi::class.java)
        }
    }
}