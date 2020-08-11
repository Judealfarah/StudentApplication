package com.example.gradproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_guest_welcome.*

class guestWelcome : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guest_welcome)

        planBu.setOnClickListener {
            val intent = Intent(this, StudyPlan::class.java)
            startActivity(intent)
        }

        announcementBu.setOnClickListener {
            startActivity(Intent(this, Announcements::class.java))
        }

        gpaBu.setOnClickListener {
            startActivity(Intent(this, Calculator::class.java))
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, Login::class.java))
        finish()
    }


}