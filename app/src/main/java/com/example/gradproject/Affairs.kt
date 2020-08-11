package com.example.gradproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_affairs.*
import kotlinx.android.synthetic.main.activity_welcome.*

class Affairs : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_affairs)

        val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
        val userEmail= mAuth.currentUser!!.email.toString()
        showUserEmailAffairsTV.text = "Signed in Student: $userEmail"

        signOutAffairsBu.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent (this, Login::class.java)
            startActivity(intent)
            finish()
        }

        schedBu.setOnClickListener {
            startActivity(Intent(this, Schedule::class.java))
        }

        planBu.setOnClickListener {
            val intent = Intent(this, StudyPlan::class.java)
            startActivity(intent)
        }

        gpaBu.setOnClickListener {
            val intent = Intent(this, Calculator::class.java)
            startActivity(intent)
        }



    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, Welcome::class.java)
        startActivity(intent)
    }

}