package com.example.gradproject


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*
import kotlin.system.exitProcess


class Login : AppCompatActivity() {


    //Firebase references
    private lateinit var mAuth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()
        FirebaseAuth.getInstance().signOut()


        stloginBu.setOnClickListener {
            loginUser()

        }

        adloginBu.setOnClickListener{
            loginAdmin()
        }

        guestLogin.setOnClickListener{


            loginGuest()
        }



    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.finishAffinity()
    }

    //login guest using anonymous firebase
    private fun loginGuest(){

        mAuth.signInAnonymously().addOnCompleteListener (this){ task ->

            if(task.isSuccessful){
                startActivity(Intent(this, guestWelcome::class.java))
            }
            else{
                Toast.makeText(this, "Try Again!", Toast.LENGTH_SHORT).show()
            }
        }

    }

    //login admin using firebase
    private fun loginAdmin() {
        val email: String = etusername.text.toString()
        val password: String = etpassword.text.toString()


        if (email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Please enter your Gju Credentials", Toast.LENGTH_SHORT).show()
        }else if (email.toLowerCase().equals("admin@admin.gju")) {

            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        startActivity(Intent(this, adminWelcome::class.java))
                    } else {
                        Toast.makeText(this@Login, "Error: " + task.exception!!.message.toString(), Toast.LENGTH_LONG).show()
                    }
                }
        }else{
            Toast.makeText(this, "Access Denied: You are not an admin!", Toast.LENGTH_SHORT).show()
        }
    }

   //login user using firebase
    private fun loginUser(){

       val email: String = etusername.text.toString()
       val password: String = etpassword.text.toString()

        if(email.isEmpty()){
            Toast.makeText(this@Login, "Please enter your GJU Email", Toast.LENGTH_SHORT).show()
        }else if(password.isEmpty()){
            Toast.makeText(this@Login, "Please enter your GJU Password", Toast.LENGTH_SHORT).show()
        }else{
            mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        val intent = Intent(this, Welcome::class.java)
                        startActivity(intent)


                    }else{
                        Toast.makeText(this@Login, "Error: "+task.exception!!.message.toString(), Toast.LENGTH_LONG).show()
                    }
                }
        }

    }

}