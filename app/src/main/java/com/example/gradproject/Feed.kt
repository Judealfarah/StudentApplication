package com.example.gradproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_feed.*

class Feed : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
        val userEmail= mAuth.currentUser!!.email.toString()
        showUserEmailFeedTV.text = "Signed in Student: $userEmail"

        signOutFeedBu.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent (this, Login::class.java)
            startActivity(intent)
            finish()
        }

        announcementBu.setOnClickListener {
            startActivity(Intent(this, Announcements::class.java))
            finish()
        }

        lostBu.setOnClickListener {
            startActivity(Intent(this, LostFound::class.java))
        }

        usedBu.setOnClickListener {
            val intent = Intent(this, UsedBooks::class.java)
            startActivity(intent)
        }


    }
}