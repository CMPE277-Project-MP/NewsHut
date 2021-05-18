package sjsu.cmpelkk.myappandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.*
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val registerButton: Button= findViewById(R.id.registerbutton)
        val registerEmail: EditText = findViewById(R.id.et_register_email)
        val registerPassword: EditText = findViewById(R.id.et_register_password)
        registerButton.setOnClickListener{
            when{
                TextUtils.isEmpty(registerEmail.text.toString().trim{it <= ' '})->{
                    Toast.makeText(
                        this@Register,
                        "Please Enter Email",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                TextUtils.isEmpty(registerPassword.text.toString().trim{it <= ' '})->{
                    Toast.makeText(
                        this@Register,
                        "Please Enter Password",
                        Toast.LENGTH_SHORT
                    ).show()
                }else-> {
                    val email:String = registerEmail.text.toString().trim{it <=' '}
                    val password:String = registerPassword.text.toString().trim{it <=' '}

                //register the user with the email and password
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener{
                       // onCompleteListener<AuthResult>
                        task ->
                            //if registered successfully
                            if (task.isSuccessful){
                                val firebaseUser: FirebaseUser = task.result!!.user!!
                                Toast.makeText(
                                    this@Register,
                                    "You are registered successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                                val intent = Intent(this@Register, MainActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                intent.putExtra("user_id", firebaseUser.uid)
                                intent.putExtra("email_id", email)
                                startActivity(intent)
                                finish()
                            }else{
                                Toast.makeText(
                                    this@Register,
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