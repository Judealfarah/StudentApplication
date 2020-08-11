package com.example.gradproject

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TableRow
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_calculator.*

class Calculator : AppCompatActivity() {

    private var totalChsPassed :Int = 0
    private var cumulativeGpa : Double = 0.0
    private var semGpa : Double = 0.0

    private var grades = IntArray(8)
    private var chs = IntArray(8)
    private var gxc = IntArray(8)
    private var count = 1

    @SuppressLint("DefaultLocale", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
        val userEmail= mAuth.currentUser!!.email.toString()
        val user = mAuth.currentUser

        //user checker
        when {
            userEmail.toLowerCase() == "admin@admin.gju" -> {
                showUserEmailCalcTV.text = "Signed in as admin: $userEmail"
            }
            user!!.isAnonymous -> {
                showUserEmailCalcTV.visibility = View.INVISIBLE
                signOutCalcBu.visibility = View.INVISIBLE
            }
            else -> {
                showUserEmailCalcTV.text = "Signed in Student: $userEmail"
            }
        }
        //user sign out button
        signOutCalcBu.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent (this, Login::class.java)
            startActivity(intent)
            finish()
        }

        //calculate gpa button
        calculateBu.setOnClickListener {

            //Course 1, ID = 0
            if (course1.visibility == View.VISIBLE && grade1.text != null && ch1.text != null){
                calcGpa(0, grade1.text.toString().toInt(), ch1.text.toString().toInt())
            }else if(course1.visibility == View.VISIBLE && (grade1.text == null || ch1.text == null)){
                Toast.makeText(applicationContext, "Course cannot be empty", Toast.LENGTH_SHORT).show()
            }
            //Course 2, ID = 1
            if (course2.visibility == View.VISIBLE && grade2.text != null && ch2.text != null){
                calcGpa(1, grade2.text.toString().toInt(), ch2.text.toString().toInt())
            }else if(course2.visibility == View.VISIBLE && (grade2.text.isEmpty() || ch2.text.isEmpty())){
                Toast.makeText(applicationContext, "Course cannot be empty", Toast.LENGTH_SHORT).show()
            }
            //Course 3, ID = 2
            if (course3.visibility == View.VISIBLE && grade3.text != null && ch3.text != null){
                calcGpa(2, grade3.text.toString().toInt(), ch3.text.toString().toInt())
            }else if(course3.visibility == View.VISIBLE && (grade3.text.isEmpty() || ch3.text.isEmpty())){
                Toast.makeText(applicationContext, "Course cannot be empty", Toast.LENGTH_SHORT).show()
            }
            //Course 4, ID = 3
            if (course4.visibility == View.VISIBLE && grade4.text != null && ch4.text != null){
                calcGpa(3, grade4.text.toString().toInt(), ch4.text.toString().toInt())
            }else if(course4.visibility == View.VISIBLE && (grade4.text.isEmpty() || ch4.text.isEmpty())){
                Toast.makeText(applicationContext, "Course cannot be empty", Toast.LENGTH_SHORT).show()
            }
            //Course 5, ID = 4
            if (course5.visibility == View.VISIBLE && grade5.text != null && ch5.text != null){
                calcGpa(4, grade5.text.toString().toInt(), ch5.text.toString().toInt())
            }else if(course5.visibility == View.VISIBLE && (grade5.text.isEmpty() || ch5.text.isEmpty())){
                Toast.makeText(applicationContext, "Course cannot be empty", Toast.LENGTH_SHORT).show()
            }
            //Course 6, ID = 5
            if (course6.visibility == View.VISIBLE && grade6.text != null && ch6.text != null){
                calcGpa(5, grade6.text.toString().toInt(), ch6.text.toString().toInt())
            }else if(course6.visibility == View.VISIBLE && (grade6.text.isEmpty() || ch6.text.isEmpty())){
                Toast.makeText(applicationContext, "Course cannot be empty", Toast.LENGTH_SHORT).show()
            }
            //Course 7, ID = 6
            if (course7.visibility == View.VISIBLE && grade7.text != null && ch7.text != null){
                calcGpa(6, grade7.text.toString().toInt(), ch7.text.toString().toInt())
            }else if(course7.visibility == View.VISIBLE && (grade7.text.isEmpty() || ch7.text.isEmpty())){
                Toast.makeText(applicationContext, "Course cannot be empty", Toast.LENGTH_SHORT).show()
            }
            //Course 8, ID = 7
            if (course8.visibility == View.VISIBLE && grade8.text != null && ch8.text != null){
                calcGpa(7, grade8.text.toString().toInt(), ch8.text.toString().toInt())
            }else if(course8.visibility == View.VISIBLE && (grade8.text.isEmpty() || ch8.text.isEmpty())){
                Toast.makeText(applicationContext, "Course cannot be empty", Toast.LENGTH_SHORT).show()
            }
            //Course 9, ID = 8
            if (course9.visibility == View.VISIBLE && grade9.text != null && ch9.text != null){
                calcGpa(8, grade9.text.toString().toInt(), ch9.text.toString().toInt())
            }else if(course9.visibility == View.VISIBLE && (grade9.text.isEmpty() || ch9.text.isEmpty())){
                Toast.makeText(applicationContext, "Course cannot be empty", Toast.LENGTH_SHORT).show()
            }

            showResults()

        }
        //add new course button
        addCourseBu.setOnClickListener {
            when (count) {
                1 -> {
                    course1.visibility = View.VISIBLE
                    calculateBu.isEnabled = true
                    count = 2
                }
                2 -> {
                    course2.visibility = View.VISIBLE
                    count = 3
                }
                3 -> {
                    course3.visibility = View.VISIBLE
                    count = 4
                }
                4 -> {
                    course4.visibility = View.VISIBLE
                    count = 5
                }
                5 -> {
                    course5.visibility = View.VISIBLE
                    count = 6
                }
                6 -> {
                    course6.visibility = View.VISIBLE
                    count = 7
                }
                7 -> {
                    course7.visibility = View.VISIBLE
                    count = 8
                }
                8 -> {
                    course8.visibility = View.VISIBLE
                    count = 9
                }
                9 -> {
                    course9.visibility = View.VISIBLE
                }
                else -> {
                    Toast.makeText(applicationContext, "Maximum number of courses is 9!", Toast.LENGTH_SHORT).show()
                }
            }
        }
        //resets and deletes data button
        resetBu.setOnClickListener {

            calculateBu.isEnabled = false

            count = 1
            etCurrentGpa.text.clear()
            etPassedCh.text.clear()

            course1.visibility = View.INVISIBLE
            grade1.text.clear()
            ch1.text.clear()
            fail1.isChecked = false
            oldGrade1.text.clear()
            oldGrade1.isEnabled = false

            resetFun(course2, grade2, ch2, fail2, oldGrade2)
            resetFun(course3, grade3, ch3, fail3, oldGrade3)
            resetFun(course4, grade4, ch4, fail4, oldGrade4)
            resetFun(course5, grade5, ch5, fail5, oldGrade5)
            resetFun(course6, grade6, ch6, fail6, oldGrade6)
            resetFun(course7, grade7, ch7, fail7, oldGrade7)
            resetFun(course8, grade8, ch8, fail8, oldGrade8)
            resetFun(course9, grade9, ch9, fail9, oldGrade9)
        }
        //remove course button
        removeBu.setOnClickListener {
            when (count) {
                1 -> {

                    course1.visibility = View.INVISIBLE
                    grade1.text.clear()
                    ch1.text.clear()
                    fail1.isChecked = false
                    oldGrade1.text.clear()
                    oldGrade1.isEnabled = false

                }
                2 -> {
                    resetFun(course2, grade2, ch2, fail2, oldGrade2)
                    count = 1
                }
                3 -> {
                    resetFun(course3, grade3, ch3, fail3, oldGrade3)
                    count = 2
                }
                4 -> {
                    resetFun(course4, grade4, ch4, fail4, oldGrade4)
                    count = 3
                }
                5 -> {
                    resetFun(course5, grade5, ch5, fail5, oldGrade5)
                    count = 4
                }
                6 -> {
                    resetFun(course6, grade6, ch6, fail6, oldGrade6)
                    count = 5
                }
                7 -> {
                    resetFun(course7, grade7, ch7, fail7, oldGrade7)
                    count = 6
                }
                8 -> {
                    resetFun(course8, grade8, ch8, fail8, oldGrade8)
                    count = 7
                }
                9 -> {
                    resetFun(course9, grade9, ch9, fail9, oldGrade9)
                    count = 8
                }
                else -> {
                    Toast.makeText(applicationContext, "Minimum number of courses is 1!", Toast.LENGTH_SHORT)
                        .show()
                }

            }

        }
    }
    //variables to save final results in
    private var newSem = ""
    private var newCumulative = ""
    private var newChs = ""

    private fun calcGpa (id: Int, grade : Int, ch: Int ) {



        val current = etCurrentGpa.text.toString().toDouble()
        val passed = etPassedCh.text.toString().toInt()


        if (grade in 35..100) {
            grades[id] = grade
            gxc[id] = grade * ch
        } else {
            Toast.makeText(applicationContext, "Error: Grade range is wrong", Toast.LENGTH_LONG).show()
        }

        if (ch in 0..4) {
            if(grade in 50..100) {
                chs[id] = ch
            }
        } else {
            Toast.makeText(applicationContext, "Error: Credit Hours from 0 to 4", Toast.LENGTH_LONG).show()
        }
        val sumOfGXC = gxc.sum()
        semGpa = sumOfGXC.toDouble() / chs.sum().toDouble()
        val sem = String.format("%.1f", semGpa)

        if (current in 0.0..100.0 && etCurrentGpa.text != null) {
            if (passed in 0..180 && etPassedCh.text != null) {

                totalChsPassed = passed + chs.sum()

                cumulativeGpa =
                    ((passed.toDouble() * current) + gxc.sum()) / totalChsPassed.toDouble()
                val cumGpa = String.format("%.1f", cumulativeGpa)
                saveResult(totalChsPassed.toString(), sem, cumGpa)
            }else{
                Toast.makeText(applicationContext, "Passed Credit Hours cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(applicationContext, "Current GPA cannot be empty", Toast.LENGTH_SHORT).show()
        }
    }



    //new gpa after remark
    private fun ifRemark(oldGrade: Int, creditHours: Int): Double{

        val current = etCurrentGpa.text.toString().toDouble()
        val passed = etPassedCh.text.toString().toInt()

        return (oldGrade * creditHours) - (current * passed)
    }




    //save final result in strings
    private fun saveResult(tChs : String, semester : String, cumulative :String){

        newSem = semester
        newCumulative = cumulative
        newChs = tChs

    }
    //show final results in another activity
    private fun showResults(){
        val intent = Intent(this, CalculatorResults::class.java)
        intent.putExtra("SemesterGpa", newSem)
        intent.putExtra("CumulativeGpa", newCumulative)
        intent.putExtra("PassedChs", newChs)
        startActivity(intent)
    }
    //resets all courses
    private fun resetFun(course : TableRow, grade : EditText, chs : EditText, fail : CheckBox, oldGrade : EditText){

        course.visibility = View.GONE
        grade.text.clear()
        chs.text.clear()
        fail.isChecked = false
        oldGrade.text.clear()
        oldGrade.isEnabled = false

    }


}