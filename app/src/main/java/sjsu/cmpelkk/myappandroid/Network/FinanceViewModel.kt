package sjsu.cmpelkk.myappandroid.Network

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val NewsAPPID = "dd7b85a45a0e43d5b32b1d8a39f0ee55"

class FinanceViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    // The internal MutableLiveData String that stores the most recent response
    val _response = MutableLiveData<List<Article>>()

    /**
     * Call getNewsProperties() on init so we can display status immediately.
     */
    init {
        getFinanceProperties()
    }

    fun getFinanceProperties() {
        FinanceApi.retrofitService.getFinanceNews("finance", NewsAPPID).enqueue(
            object : Callback<TopHeadlines> {
                override fun onFailure(call: Call<TopHeadlines>, t: Throwable) {
                    //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    // _response.value = "Failure: " + t.message
                }

                override fun onResponse(
                    call: Call<TopHeadlines>,
                    response: Response<TopHeadlines>
                ) {
                    _response.value = response.body()?.articles
                }

            }
        )

    }
}