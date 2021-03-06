package sjsu.cmpelkk.myappandroid

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.amazonaws.mobile.client.AWSMobileClient
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import sjsu.cmpelkk.myappandroid.myutil.SwipeToDeleteCallback
import java.io.Serializable
import com.amazonaws.services.*
import com.amplifyframework.core.Amplify
import sjsu.cmpelkk.myappandroid.Firebase.LoginViewModel

const val POST_REQUEST_CODE = 32
class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController
    lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var listner: NavController.OnDestinationChangedListener
//    private val loginviewModel by viewModels<LoginViewModel>()
    private lateinit var headerView: View

    companion object {
        const val TAG = "MainActivity"
        const val SIGN_IN_RESULT_CODE = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //get the user details via the intent
        val userId = intent.getStringExtra("user_id")
        val emailId = intent.getStringExtra("email_id")

//        set the intent of email id to the post activity
        val intent = Intent(this@MainActivity, PostActivity::class.java)
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra("email_id", emailId)

        Amplify().intializeAmplify(this@MainActivity);
        drawerLayout = findViewById(R.id.drawer_layout)
        navController = findNavController(R.id.fragment)
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        navigationView = findViewById(R.id.navigationView)
        navigationView.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)

        bottomNavigationView = findViewById(R.id.bottom_nav_view)
        bottomNavigationView.setupWithNavController(navController)

        }



    override fun onSupportNavigateUp(): Boolean {
        navController = findNavController(R.id.fragment)
        return navController.navigateUp(appBarConfiguration) ||
        return super.onSupportNavigateUp()
    }
}
