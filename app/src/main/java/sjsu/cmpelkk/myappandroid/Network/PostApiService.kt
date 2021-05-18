package sjsu.cmpelkk.myappandroid.Network

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*

//aws api gateway url for creating and fetching user created posts
private const val BASE_URL = "https://nuytoo6odb.execute-api.us-east-1.amazonaws.com/dev/"


/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
private val moshi = Moshi.Builder()
    .add(NULL_TO_EMPTY_STRING_ADAPTER1)
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

/**
 * A public interface that exposes the [getProperties] method
 */
interface PostApiService {
    @GET("getpost")
    fun getpost(@Query("emailId") emailId :String):
            Call<List<Article>>

    @Headers("Content-Type: application/json")
    @POST("createuserposts")
    fun createuserposts(@Body dataItem: String):
            Call<Article>

}

/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 * each time your app calls Api.retrofitService, it will get a singleton Retrofit object that implements ApiService.
 */
object PostApi {
    val retrofitService : PostApiService by lazy {
        retrofit.create(PostApiService::class.java) }
    //The Retrofit create() method creates the Retrofit service itself with the ApiService interface.
}