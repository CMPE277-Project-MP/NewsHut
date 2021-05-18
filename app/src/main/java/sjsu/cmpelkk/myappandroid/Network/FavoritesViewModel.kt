package sjsu.cmpelkk.myappandroid.Network

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavoritesViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    // The internal MutableLiveData String that stores the most recent response
    val _addToFavoritesResponse = MutableLiveData<Boolean>()
    val _getfavoritesResponse = MutableLiveData<List<Article>>()

    /**
     * Call getNewsProperties() on init so we can display status immediately.
     */
    init {
        // addToFavorites()
    }

    fun addToFavorites(dataItem: String) {
        FavoritesApi.retrofitService.addToFavorites(dataItem).enqueue(
            object : Callback<Article> {
                override fun onFailure(call: Call<Article>, t: Throwable) {
                    //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    // _response.value = "Failure: " + t.message
                }
                override fun onResponse(
                    call: Call<Article>,
                    response: Response<Article>
                ) {
                    _addToFavoritesResponse.value = true
                }
            }
        )
    }

    fun getfavorites(emailId:String) {
        FavoritesApi.retrofitService.getfavorites(emailId).enqueue(
            object : Callback<List<Article>> {
                override fun onFailure(call: Call<List<Article>>, t: Throwable) {
                    //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    // _response.value = "Failure: " + t.message
                }
                override fun onResponse(
                    call: Call<List<Article>>,
                    response: Response<List<Article>>
                ) {
                    _getfavoritesResponse.value = response.body()
                }
            }
        )
    }
}