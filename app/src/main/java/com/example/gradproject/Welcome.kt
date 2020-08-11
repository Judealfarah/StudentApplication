package com.example.gradproject


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_directory.*
import kotlinx.android.synthetic.main.activity_welcome.*


class Welcome : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
         val userEmail= mAuth.currentUser!!.email.toString()

        if (userEmail.toLowerCase() == "admin@admin.gju") {
            showUserEmailWelcomeTV.text = "Signed in as admin: $userEmail"
            switchView.visibility = View.VISIBLE

        }else {
            showUserEmailWelcomeTV.text = "Signed in Student: $userEmail"
        }

        signOutWelcomeBu.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent (this, Login::class.java)
            startActivity(intent)
            finish()
        }

        switchView.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                startActivity(Intent(this, adminWelcome::class.java))
                Toast.makeText(this, "Admin Page", Toast.LENGTH_SHORT).show()
            }
        }



        affairsBu.setOnClickListener {
            val intent = Intent(this, Affairs::class.java)
            startActivity(intent)
        }

        feedBu.setOnClickListener {
            val intent = Intent(this, Feed::class.java)
            startActivity(intent)
        }


        directoryBu.setOnClickListener {
            val intent = Intent(this, Directory::class.java)
            startActivity(intent)
        }


    }

    override fun onBackPressed() {
        super.onBackPressed()
        Toast.makeText(this, "You have signed out", Toast.LENGTH_SHORT).show()
        FirebaseAuth.getInstance().signOut()
        startActivity(Intent(this, Login::class.java))
        finish()
    }

}

