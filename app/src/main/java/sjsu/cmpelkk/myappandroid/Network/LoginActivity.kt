package sjsu.cmpelkk.myappandroid.Network

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import sjsu.cmpelkk.myappandroid.MainActivity
import sjsu.cmpelkk.myappandroid.R
import sjsu.cmpelkk.myappandroid.Register

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginButton : Button = findViewById(R.id.loginButton)
        val registerTxt : TextView = findViewById(R.id.tv_register)
        registerTxt.setOnClickListener{
            startActivity(Intent(this@LoginActivity, Register::class.java))
        }

        val loginEmail: EditText = findViewById(R.id.et_login_email)
        val loginPassword: EditText = findViewById(R.id.et_login_password)

        loginButton.setOnClickListener{
            when{
                TextUtils.isEmpty(loginEmail.text.toString().trim{it <= ' '})->{
                    Toast.makeText(
                        this@LoginActivity,
                        "Please Enter Email",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(loginPassword.text.toString().trim{it <= ' '})->{
                    Toast.makeText(
                        this@LoginActivity,
                        "Please Enter Password",
                        Toast.LENGTH_SHORT
                    ).show()
                }else-> {
                val email:String = loginEmail.text.toString().trim{it <=' '}
                val password:String = loginPassword.text.toString().trim{it <=' '}

                //register the user with the email and password
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener{
                        // onCompleteListener<AuthResult>
                            task ->
                        //if registered successfully
                        if (task.isSuccessful){
                            val firebaseUser: FirebaseUser = task.result!!.user!!
                            Toast.makeText(
                                this@LoginActivity,
                                "Signed In successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            intent.putExtra("user_id", firebaseUser.uid)
                            intent.putExtra("email_id", email)
                            startActivity(intent)
                            finish()
                        }else{
                            Toast.makeText(
                                this@LoginActivity,
                                task.exception!!.message.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

            }
            }
        }
    }
}