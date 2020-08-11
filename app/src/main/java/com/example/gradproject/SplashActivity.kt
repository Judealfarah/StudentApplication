package com.example.gradproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.lang.Exception

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        val background = object : Thread() {

            override fun run() {
                try {
                    Thread.sleep(4000)

                    val intent = Intent(baseContext, Login::class.java)
                    startActivity(intent)

                }catch (e: Exception){
                    e.printStackTrace()
                }
            }
        }
        background.start()


    }


}