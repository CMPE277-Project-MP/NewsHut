package sjsu.cmpelkk.myappandroid.Network

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import sjsu.cmpelkk.myappandroid.R


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NewsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewsFragment : Fragment() {

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var datalist: List<Article> = mutableListOf()
    lateinit var recyclerCard: RecyclerView

    private lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.news_fragment, container, false)
        return view
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        recyclerCard = itemView.findViewById(R.id.newscardrecyclerview)

        viewModel = ViewModelProviders.of(this).get(NewsViewModel::class.java)

        viewModel._response.observe(viewLifecycleOwner, Observer { newresponse ->
            datalist = newresponse //display the raw json data
            recyclerCard.adapter = NewsCardAdapter(datalist) //(carddefaultdata)
        })


        val newsSearchView: SearchView = itemView.findViewById(R.id.newsSearchView) as SearchView // inititate a search view
        val id: Int = newsSearchView.getContext()
            .getResources()
            .getIdentifier("android:id/search_src_text", null, null)
        val textView = newsSearchView.findViewById(id) as TextView
        textView.setTextColor(Color.WHITE)

        newsSearchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                if (newText == null || newText.isEmpty()) {
                    viewModel.getNewsProperties()
                    viewModel._response.observe(viewLifecycleOwner, Observer { newresponse ->
                        datalist = newresponse //display the raw json data
                        recyclerCard.adapter = NewsCardAdapter(datalist) //(carddefaultdata)
                    })
                    return false
                }
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.getNewsPropertiesOnSearch(query)
                viewModel._response.observe(viewLifecycleOwner, Observer { newresponse ->
                    datalist = newresponse //display the raw json data
                    recyclerCard.adapter = NewsCardAdapter(datalist) //(carddefaultdata)
                })
                return false
            }
        }
        )

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SecondFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NewsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

class NewsCardViewHolder(val cardView: CardView) : RecyclerView.ViewHolder(cardView) {
    val title: TextView = cardView.findViewById(R.id.newsTitletextView)
    val story: TextView = cardView.findViewById(R.id.newsDescriptionTextView)
    val image: ImageView = cardView.findViewById(R.id.newsUrlToImageView)
    val placeHolderImage = "https://st2.depositphotos.com/1278966/7719/i/600/depositphotos_77195177-stock-photo-world-business-background.jpg"
    fun bind(oneitem: Article) {
        title.text = oneitem.title
        story.text = oneitem.description

        if (oneitem.urlToImage == null || oneitem.urlToImage.isEmpty()) {
            Picasso.get()
                .load(placeHolderImage)
                .centerCrop()
                .fit()
                .into(image)
        } else {
            Picasso.get()
                .load(oneitem.urlToImage)
                .centerCrop()
                .fit()
                .into(image)
        }

        val context = cardView.context
        cardView.setOnClickListener {
            var position: Int = adapterPosition
            Snackbar.make(it, "Click detected on item $position",
                Snackbar.LENGTH_LONG).setAction("Action", null).show()

            val intent = Intent(context, NewsContent::class.java).apply {
                //putExtra("DataItem", oneitem.title)

                putExtra("DataItemTitle", oneitem.title)
                putExtra("DataItemUrl", oneitem.urlToImage)
                putExtra("DataItemAuthor", oneitem.author)
                putExtra("DataItemContent", oneitem.content)
                //get the object with: val object = intent.extras.get("DataItem") as DataItem
            }


            context.startActivity(intent)
        }
        //header.setTextColor(Color.parseColor("#ffffff"))
        title.setTextColor(context.getColor(R.color.primaryDarkColor))
        //description.setTextColor(Color.parseColor("#ffa270"))
        story.setTextColor(context.getColor(R.color.secondaryDarkColor))

        val toggle: ToggleButton = cardView.findViewById(R.id.toggleFavourite)
        toggle.setBackgroundResource(R.drawable.favgray);
        toggle.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // The toggle is enabled
                toggle.setBackgroundResource(R.drawable.favyellow);
            } else {
                // The toggle is disabled
                toggle.setBackgroundResource(R.drawable.favgray);
            }
        }

        }

    }



class NewsCardAdapter(var data: List<Article>) : RecyclerView.Adapter<NewsCardViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsCardViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val view = layoutInflater
            .inflate(R.layout.news_card_item_view, parent, false) as CardView

        return NewsCardViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: NewsCardViewHolder, position: Int) {
        holder.bind(data[position])
    }

}