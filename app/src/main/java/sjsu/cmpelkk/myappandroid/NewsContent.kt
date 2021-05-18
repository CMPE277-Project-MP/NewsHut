package sjsu.cmpelkk.myappandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.lifecycle.ViewModelProviders
import com.squareup.picasso.Picasso
import org.json.JSONObject
import sjsu.cmpelkk.myappandroid.Network.FavoritesViewModel

class NewsContent : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_content)

        lateinit var viewModel: FavoritesViewModel
        viewModel = ViewModelProviders.of(this).get(FavoritesViewModel::class.java)

        val dataItemTitle: String? = intent.extras?.get("DataItemTitle") as? String
        val dataItemUrl: String? = intent.extras?.get("DataItemUrl") as? String
        val dataItemAuthor: String? = intent.extras?.get("DataItemAuthor") as? String
        val dataItemContent: String? = intent.extras?.get("DataItemContent") as? String
        val DataItemDescription: String? = intent.extras?.get("DataItemDescription") as? String

        val favObject= JSONObject()
        favObject.put("author", dataItemAuthor)
        favObject.put("title", dataItemTitle)
        favObject.put("content", dataItemContent)
        favObject.put("urlToImage", dataItemUrl)
        favObject.put("description", DataItemDescription)
        favObject.put("username", "prajakta")



        val newsDetailsImage = findViewById<ImageView>(R.id.newsContentImage)
        val author = findViewById<TextView>(R.id.newsContentAuthor)
        val newsDetailsTitle = findViewById<TextView>(R.id.newsContentTitle)
        val newsDetailsStory = findViewById<TextView>(R.id.newsContentStory)
        val placeHolderImage = "https://st2.depositphotos.com/1278966/7719/i/600/depositphotos_77195177-stock-photo-world-business-background.jpg"

        newsDetailsTitle.text = dataItemTitle
        if (dataItemUrl == null || dataItemUrl.isEmpty()) {
            Picasso.get()
                .load(placeHolderImage)
                .centerCrop()
                .fit()
                .into(newsDetailsImage)
        } else {
            Picasso.get()
                .load(dataItemUrl)
                .centerCrop()
                .fit()
                .into(newsDetailsImage)
        }
        author.text = "By Author: " + dataItemAuthor
        newsDetailsStory.text = dataItemContent

//        val favList= viewModel._getfavoritesResponse
//        favList.any{ object -> object.title == DataItemTitle }

        val toggle: ToggleButton = findViewById(R.id.toggleFavourite)
        toggle.setBackgroundResource(R.drawable.favgray);
        toggle.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // The toggle is enabled
                viewModel.addToFavorites(favObject.toString())
                toggle.setBackgroundResource(R.drawable.favyellow);

            } else {
                // The toggle is disabled
                toggle.setBackgroundResource(R.drawable.favgray);
            }
        }
    }
}