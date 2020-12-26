package com.example.foodcloud2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.math.BigInteger
import java.security.MessageDigest

class LoginActivity : AppCompatActivity() {
    lateinit var username: EditText
    lateinit var password: EditText
    lateinit var database: FirebaseDatabase
    internal lateinit var mFirebaseAuth: FirebaseAuth
    lateinit var ref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        mFirebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        ref = database.getReference("Admins")
        val error_msg = this.resources.getString(R.string.error_login)
        var signinbtn: Button=findViewById(R.id.button)
        signinbtn.setOnClickListener{
            var user: String = username.text.toString()
            var pwd: String = password.text.toString()
            if(user.isEmpty()){
                username.error = this.resources.getString(R.string.please_username)
                username.requestFocus()
            } else if(pwd.isEmpty()){
                password.error = this.resources.getString(R.string.please_pass)
                password.requestFocus()
            } else if(user.isNotEmpty() && pwd.isNotEmpty()){
                val postListener = object: ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        for(postSnapshot in snapshot.children){
                            var User = postSnapshot.getValue(User::class.java)
                            if(User!=null && User.Username == user && User.Password == pwd.md5()){
                                startActivity(Intent(this@LoginActivity, Qrscan::class.java))

                                Toast.makeText(this@LoginActivity, "Signed in", Toast.LENGTH_SHORT).show()
                            }


                        }

                    }

                    override fun onCancelled(error: DatabaseError) {

                    }

                }

                ref.addListenerForSingleValueEvent(postListener)

            }
        }

    }
    fun String.md5(): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(toByteArray())).toString(16).padStart(32, '0')
    }


}