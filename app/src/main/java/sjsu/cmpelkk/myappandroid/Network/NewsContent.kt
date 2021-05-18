package sjsu.cmpelkk.myappandroid.Network

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import sjsu.cmpelkk.myappandroid.R

class NewsContent : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_content)

        val dataItemTitle: String? = intent.extras?.get("DataItemTitle") as? String
        val dataItemUrl: String? = intent.extras?.get("DataItemUrl") as? String
        val dataItemAuthor: String? = intent.extras?.get("DataItemAuthor") as? String
        val dataItemContent: String? = intent.extras?.get("DataItemContent") as? String

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

    }
}