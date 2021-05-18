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
    val _response = MutableLiveData<List<Article>>()

    /**
     * Call getNewsProperties() on init so we can display status immediately.
     */
    init {
        // getPost()
    }


    fun getPost() {
        PostApi.retrofitService.getPost().enqueue(
            object : Callback<List<Article>> {
                override fun onFailure(call: Call<List<Article>>, t: Throwable) {
                    //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    // _response.value = "Failure: " + t.message
                }
                override fun onResponse(
                    call: Call<List<Article>>,
                    response: Response<List<Article>>
                ) {
                    _response.value = response.body()
                }
            }
        )
    }
}