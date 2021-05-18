package sjsu.cmpelkk.myappandroid

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.os.Environment.DIRECTORY_DOWNLOADS
import android.provider.MediaStore
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.amplifyframework.core.Amplify.Storage
import sjsu.cmpelkk.myappandroid.myutil.imageUtil
import java.io.Serializable
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import com.amplifyframework.api.ApiException
import com.amplifyframework.api.rest.RestOptions
import com.amplifyframework.api.rest.RestResponse
import com.amplifyframework.core.Amplify
import com.amplifyframework.storage.options.StorageDownloadFileOptions
import com.amplifyframework.util.Environment
import com.google.firebase.auth.FirebaseAuth
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sjsu.cmpelkk.myappandroid.Firebase.LoginViewModel
import sjsu.cmpelkk.myappandroid.Network.*
import sjsu.cmpelkk.myappandroid.myutil.S3File
import java.io.File


const val EXTRA_MESSAGE = "sjsu.cmpelkk.MyAppAndroid.MESSAGE"
const val IMAGE_REQUEST_CODE = 33

class PostActivity : AppCompatActivity() {
    lateinit var myImage: ImageView
    lateinit var textmultiline: EditText
    lateinit var submitbutton: Button
    lateinit var titletextView: TextView
    lateinit var nametext: TextView
    lateinit var imageuri: String //Uri
    lateinit var emailid: String
    lateinit var viewModel: PostViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        //set a default image
        imageuri = Uri.parse("android.resource://" + packageName + "/" + R.drawable.sjsu1).toString()

        //val myImage: ImageView = findViewById(R.id.uploadimageView)
        myImage = findViewById(R.id.uploadimageView)
        myImage.setOnClickListener{changeImage()}

        titletextView = findViewById(R.id.entertitletextview)

        submitbutton = findViewById(R.id.submitbutton)
        submitbutton.setOnClickListener {
            senddataBack()
        }

        textmultiline = findViewById(R.id.editTextMultiLine)

        nametext = findViewById<EditText>(R.id.nameTextfield)

        //When the multiline textview is inside the scrollview, the textview cannot scroll, using the following code to enable the scroll of the textview
        textmultiline.setOnTouchListener { view, event ->
            view.parent.requestDisallowInterceptTouchEvent(true)
            if ((event.action and MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
                view.parent.requestDisallowInterceptTouchEvent(false)
            }
            return@setOnTouchListener false
        }

    }

    private fun senddataBack() {
        //create an instance of an Intent object.
        val data = Intent()
        emailid = FirebaseAuth.getInstance().currentUser?.email.toString()

        //set the value/data to pass back
        data.setData(Uri.parse(titletextView.text.toString()))
        val bitmap = (myImage.getDrawable() as BitmapDrawable).bitmap
        val newimageuri = imageUtil.writeToTempImageAndGetPathUri(this, bitmap, "test" )

        //val mynewdata = DataItem(nametext.text.toString(), titletextView.text.toString(),textmultiline.text.toString(),false,0, R.drawable.imageupload)
        val mynewdata = DataItem(nametext.text.toString(), titletextView.text.toString(), textmultiline.text.toString(), false, 0, newimageuri.toString())
        data.putExtra("NewDataItem", mynewdata as Serializable)

//        upload file to s3 bucket
            uploadFile(newimageuri!!)
//

//        insert the data in db
        val imageurl:String = "https://d297hqcfwf28mv.cloudfront.net/public/" + mynewdata.name.toString() + "_" + mynewdata.title.toString()
        val userPost= JSONObject()
        userPost.put("author", mynewdata.name)
        userPost.put("title", mynewdata.title)
        userPost.put("content", mynewdata.story)
        userPost.put("urlToImage", imageurl)
        userPost.put("description", "DataItemDescription")
        userPost.put("username", emailid)

        viewModel = ViewModelProviders.of(this).get(PostViewModel::class.java)
        viewModel.createuserposts(userPost.toString())

        //set a result code, It is either RESULT_OK or RESULT_CANCELLED
        setResult(RESULT_OK, data)
        //Close the activity
        finish()
    }

    private fun uploadFile(Uri: Uri) {
        Amplify().intializeAmplify(this@PostActivity);
        println("Upload: $Uri")
        val exampleInputStream = getContentResolver().openInputStream(Uri)
        println("Upload: $exampleInputStream")
//        val randomNumber = (1000..9999).random()

        exampleInputStream?.let {
            Amplify.Storage.uploadInputStream(
                 nametext.text.toString() + "_" + titletextView.text.toString(),
                it,
                { result -> Toast.makeText(this, "News Posted successfully:" + result.key, Toast.LENGTH_SHORT).show() },
                { error -> Log.e("MyAmplifyApp", "Upload failed", error) }
            )
        }

    }


    private  fun downloadfiles(file: ArrayList<String>)  {

//        val downloadFolder = " " //getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
//
//        val filepaths = arrayListOf<S3File>()
//        file.size
//        file.forEach { item-> val randomNumber = (1000..9999).random()
//
//            Amplify.Storage.downloadFile(
//                item,
//                File("$downloadFolder/download$randomNumber.jpg"),
//                StorageDownloadFileOptions.defaultInstance(),
//                { progress ->
//                    Log.d("MyAmplifyApp", "Fraction completed: ${progress.fractionCompleted}")
//                },
//                { result -> Log.d("MyAmplifyApp", "Successfully downloaded: ${result.getFile().name} Path: ${result.file.absolutePath}")
////                    downloadprogress(result.getFile().name)
//                    val fileobj = S3File(
//                        path = result.file.absolutePath,
//                        key = result.file.name,
//                    )
//                    filepaths += fileobj
//                    println("filelists${filepaths.size} ${file.size}")
////                    if(filepaths.size == file.size){
////                        populaterv(filepaths)
////                    }
//                },
//                { error -> Log.d("MyAmplifyApp", "Download Failure", error) }
//            )
//
//        }


    }

    private fun changeImage() {
        //val myImage: ImageView = findViewById(R.id.uploadimageView)
        //myImage.setImageResource(R.drawable.ic_baseline_star_rate_24)//add a drawable

        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }

    fun sendMessage(view: View) {
        Toast.makeText(this, "Button clicked", Toast.LENGTH_LONG).show()

//        val editText = findViewById<EditText>(R.id.nameTextfield)
//        val message = editText.text.toString()
        val message = nametext.text.toString()
//        val intent = Intent(this, DisplayMessageActivity::class.java).apply {
//            putExtra(EXTRA_MESSAGE, message)
//        }
        // startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode != IMAGE_REQUEST_CODE)
        {
            Toast.makeText(this, "Activity result received", Toast.LENGTH_LONG).show()
        }

        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_REQUEST_CODE) {
            //Option1, use image uri
            myImage.setImageURI(data?.data)
            if (data?.data != null) {
                val tempuri = data?.data!!
                if ("com.google.android.apps.photos.contentprovider".equals(tempuri.getAuthority()) ) {
                    val pathUri: String = tempuri.getPath()!!
                    val startindex = pathUri.indexOf("content")
                    val lastindex = pathUri.lastIndex
                    val newUri: String = pathUri.substring(startindex, lastindex)
                    imageuri=newUri //data?.data!!
                    Log.i("PostActivity",newUri)
                }
            }
            //Other options
            //val uri = Uri.parse("android.resource://" + packageName + "/" + R.drawable.sjsu1)
            //myImage.setImageURI(uri)
//            val uri = data?.data
//            //val inputStream = contentResolver.openInputStream(uri)
//            val inputStream = uri?.let { contentResolver.openInputStream(it) }
//            val b = BitmapFactory.decodeStream(inputStream)//Creates Bitmap objects
//            Log.i("PostActivity", "width: "+b.width+", height:"+b.height)//width: 960, height:1280
//            val yourDrawable: Drawable = BitmapDrawable(resources, imageUtil.Bitmapscale(b,0.5))
//            myImage.setImageDrawable(yourDrawable)

//            val yourDrawable  = ResourcesCompat.getDrawable(resources, R.drawable.sjsu1, null)
//            myImage.setImageDrawable(yourDrawable)

        }
    }
}