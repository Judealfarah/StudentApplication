package com.example.gradproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_admin_welcome.*

class adminWelcome : AppCompatActivity() {

    lateinit var mAuth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_welcome)

        val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
        val userEmail= mAuth.currentUser!!.email.toString()
       showUserEmailAdminTV.text = "Signed in as admin: $userEmail"
        switchView.setOnCheckedChangeListener { _, isChecked ->
            startActivity(Intent(this, Welcome::class.java))
            Toast.makeText(this, "Student Page", Toast.LENGTH_SHORT).show()
        }

        signOutAdminBu.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent (this, Login::class.java)
            startActivity(intent)
            finish()
        }

        announcementLO.setOnClickListener {
            val intent = Intent(this, addPost::class.java)
            startActivity(intent)
        }

        directoryLL.setOnClickListener {
            startActivity(Intent(this, addProf::class.java))
        }
        manageLL.setOnClickListener {
            startActivity(Intent(this, newUser::class.java))
        }

        addDirectoryBu.setOnClickListener {
            startActivity(Intent(this, addProf::class.java))
        }

        newAnnouncementBu.setOnClickListener {
            val intent = Intent(this, addPost::class.java)
            startActivity(intent)
        }

        addStudentBu.setOnClickListener {
            startActivity(Intent(this, newUser::class.java))
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