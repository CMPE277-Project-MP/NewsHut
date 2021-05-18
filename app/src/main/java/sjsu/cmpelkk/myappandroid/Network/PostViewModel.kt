package sjsu.cmpelkk.myappandroid.Network

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    // The internal MutableLiveData String that stores the most recent response
    val _createUserPostsResponse = MutableLiveData<Boolean>()
    val _getUserPostsResponse = MutableLiveData<List<Article>>()

    /**
     * Call getNewsProperties() on init so we can display status immediately.
     */
    init {
        // addToFavorites()
    }

    fun createuserposts(dataItem: String) {
        PostApi.retrofitService.createuserposts(dataItem).enqueue(
            object : Callback<Article> {
                override fun onFailure(call: Call<Article>, t: Throwable) {
                    //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    // _response.value = "Failure: " + t.message
                }
                override fun onResponse(
                    call: Call<Article>,
                    response: Response<Article>
                ) {
                    _createUserPostsResponse.value = true
                }
            }
        )
    }

    fun getpost(emailId:String) {
        PostApi.retrofitService.getpost(emailId).enqueue(
            object : Callback<List<Article>> {
                override fun onFailure(call: Call<List<Article>>, t: Throwable) {
                    //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    // _response.value = "Failure: " + t.message
                }
                override fun onResponse(
                    call: Call<List<Article>>,
                    response: Response<List<Article>>
                ) {
                    _getUserPostsResponse.value = response.body()
                }
            }
        )
    }
}