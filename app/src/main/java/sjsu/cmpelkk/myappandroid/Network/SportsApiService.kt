package sjsu.cmpelkk.myappandroid.Network

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://newsapi.org/v2/"//


/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
private val moshi = Moshi.Builder()
    .add(NULL_TO_EMPTY_STRING_ADAPTER)
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 * Use the Retrofit builder to build a retrofit object using a Scalar converter
 * want Retrofit to fetch a JSON response from the web service, and return it as a String
 */
//private val retrofit = Retrofit.Builder()
//    .addConverterFactory(ScalarsConverterFactory.create()) //Retrofit has a ScalarsConverter that supports strings and other primitive types
//    .baseUrl(BASE_URL)
//    .build() //create the Retrofit object.

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

object NULL_TO_EMPTY_STRING_ADAPTER {
    @FromJson
    fun fromJson(reader: JsonReader): String {
        if (reader.peek() != JsonReader.Token.NULL) {
            return reader.nextString()
        }
        reader.nextNull<Unit>()
        return ""
    }
}
/**
 * A public interface that exposes the [getProperties] method
 */
interface SportsApiService {
    @GET("everything")
    fun getSportsNews(@Query("q") q: String, @Query("apiKey") apiKey: String):
            Call<TopHeadlines>

//    @GET("top-headlines")
//    fun getUserSearchInput(@Query("apiKey") apiKey: String, @Query("q") q: String):
//            Call<TopHeadlines>
}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 * each time your app calls Api.retrofitService, it will get a singleton Retrofit object that implements ApiService.
 */
object SportsApi {
    val retrofitService : SportsApiService by lazy {
        retrofit.create(SportsApiService::class.java) }
    //The Retrofit create() method creates the Retrofit service itself with the ApiService interface.
}