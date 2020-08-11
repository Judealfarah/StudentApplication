package com.example.gradproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_thursday.*


class Thursday : AppCompatActivity() {

    val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val userEmail= mAuth.currentUser!!.email.toString()
    private var myRef= FirebaseDatabase.getInstance().getReference("studentSchedule")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thursday)
        showUserEmailScheduleTV.text = "Signed in Student: $userEmail"
        val username = userName(userEmail)
        loadThurs(username)

        addClassBu.setOnClickListener {
            startActivity(Intent(this, AddCourse::class.java))
        }

        tvSun.setOnClickListener {
            startActivity(Intent(this, Schedule::class.java))
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }
        tvMon.setOnClickListener {
            startActivity(Intent(this, Monday::class.java))
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }
        tvTues.setOnClickListener {
            startActivity(Intent(this, Tuesday::class.java))
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }
        tvWed.setOnClickListener {
            startActivity(Intent(this, Wednesday::class.java))
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }
        tvThurs.setOnClickListener {
            loadThurs(username)
        }

        resetCBu.setOnClickListener {

            val builder = AlertDialog.Builder(this@Thursday)
            builder.setTitle("Clear all courses!")
            builder.setMessage("Are you sure you want to clear all courses from schedule?")
            builder.setPositiveButton("Yes") { _, _ ->
                myRef.child(username).removeValue()
                startActivity(Intent(this,Schedule::class.java))
                overridePendingTransition(R.anim.blink, R.anim.blink)
            }
            builder.setNegativeButton("Cancel", null)


            val alert = builder.create()

            alert.setOnShowListener{
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(resources.getColor(R.color.backgroundColor))
                alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(resources.getColor(R.color.backgroundColor))
            }

            alert.show()
        }

        signOutScheduleBu.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent (this, Login::class.java)
            startActivity(intent)
            finish()
        }

    }

    override fun onStart() {
        super.onStart()
        val username = userName(userEmail)
        loadThurs(username)
    }


    private fun userName(Email : String):String{
        val removeAt = Email.replace("@gju.edu.jo","")
        val removePeriod = removeAt.replace(".","")
        return removePeriod
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, Affairs::class.java))
        finish()
    }


    private fun loadThurs(username: String){
        myRef.child(username).child("Thursday").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){


                    for(ds in snapshot.children) {
                        val course = ds.getValue(Course::class.java)

                        when (course!!.courseTime){
                            "8:00–9:30"-> {
                                name1.text = course.courseName
                                sec1.text = course.section.toString()
                                room1.text = course.courseRoom
                            }
                            "9:30–11:00"->{
                                name2.text = course.courseName
                                sec2.text = course.section.toString()
                                room2.text = course.courseRoom
                            }
                            "11:00–12:30"->{
                                name3.text = course.courseName
                                sec3.text = course.section.toString()
                                room3.text = course.courseRoom
                            }
                            "12:30–2:00"->{
                                name4.text = course.courseName
                                sec4.text = course.section.toString()
                                room4.text = course.courseRoom
                            }
                            "2:00–3:30"->{
                                name5.text = course.courseName
                                sec5.text = course.section.toString()
                                room5.text = course.courseRoom
                            }
                            "3:30–5:00"->{
                                name6.text = course.courseName
                                sec6.text = course.section.toString()
                                room6.text = course.courseRoom
                            }

                        }

                    }
                }
            }
        })
    }
}