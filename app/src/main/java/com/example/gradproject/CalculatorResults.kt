package com.example.gradproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_calculator_results.*

class CalculatorResults : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator_results)

        val intent = intent
        val semester = intent.getStringExtra("SemesterGpa")
        val cumulative = intent.getStringExtra("CumulativeGpa")
        val passedChs = intent.getStringExtra("PassedChs")

        value_SemesterGpa.text = semester
        value_CumulativeGpa.text = cumulative
        value_PassedChs.text = passedChs

        doneBu.setOnClickListener {
            finish()
            startActivity(Intent(this, Calculator::class.java))
        }

    }
}