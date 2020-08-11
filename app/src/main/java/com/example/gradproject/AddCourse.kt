package com.example.gradproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add_course.*

class AddCourse : AppCompatActivity() {


    private var myRef= FirebaseDatabase.getInstance().getReference("studentSchedule")
    var courseDayString : String ?= null
    var courseTimeString : String ?= null
    val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val userEmail= mAuth.currentUser!!.email.toString()

    private lateinit var username : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)

        val courseDay= resources.getStringArray(R.array.courseDay)
        val courseTime= resources.getStringArray(R.array.courseTime)

        val user = userName(userEmail)
        val userData =  myRef.child(user)

        //courseDay Spinner
        if (courseDay_Spinner != null){
            val adapter = ArrayAdapter(this, R.layout.spinner_text, courseDay)
            courseDay_Spinner.adapter = adapter
            courseDay_Spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                     courseDayString = courseDay[position]
                }
                override fun onNothingSelected(parent: AdapterView<*>) {
                    courseDayString = ""
                }
            }
        }
        //courseTime Spinner
        if (courseTime_Spinner != null){
            val adapter = ArrayAdapter(this, R.layout.spinner_text, courseTime)
            courseTime_Spinner.adapter = adapter
            courseTime_Spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                   courseTimeString = courseTime[position].toString()
                }
                override fun onNothingSelected(parent: AdapterView<*>) {
                    courseTimeString = ""
                }
            }
        }

    addCourseBu.setOnClickListener {

        if(courseNameET.text.isNotEmpty() && sectionET.text.isNotEmpty() && courseRoomET.text.isNotEmpty()){
                when (courseDayString){
                    "Sunday" -> {
                        val course = Course(courseNameET.text.toString(),
                            sectionET.text.toString().toInt(),
                            courseRoomET.text.toString(), courseTimeString!!)
                     userData.child("Sunday").child(courseTimeString!!).setValue(course)
                    }
                    "Monday" -> {
                        val course = Course(courseNameET.text.toString(),
                            sectionET.text.toString().toInt(),
                            courseRoomET.text.toString(), courseTimeString!!)
                     userData.child("Monday").child(courseTimeString!!).setValue(course)
                    }
                    "Tuesday" -> {
                        val course = Course(courseNameET.text.toString(),
                            sectionET.text.toString().toInt(),
                            courseRoomET.text.toString(), courseTimeString!!)
                      userData.child("Tuesday").child(courseTimeString!!).setValue(course)
                    }
                    "Wednesday" -> {
                        val course = Course(courseNameET.text.toString(),
                            sectionET.text.toString().toInt(),
                            courseRoomET.text.toString(), courseTimeString!!)
                        userData.child("Wednesday").child(courseTimeString!!).setValue(course)
                    }
                    "Thursday" -> {
                        val course = Course(courseNameET.text.toString(),
                            sectionET.text.toString().toInt(),
                            courseRoomET.text.toString(), courseTimeString!!)
                      userData.child("Thursday").child(courseTimeString!!).setValue(course)
                    }


                }
            finish()

            }else{
                Toast.makeText(this, "Fields cannot be empty!", Toast.LENGTH_LONG).show()
            }
    }



    }

    private fun userName(Email : String):String{
        val removeAt = Email.replace("@gju.edu.jo","")
    val removePeriod = removeAt.replace(".","")

    return removePeriod
}


}