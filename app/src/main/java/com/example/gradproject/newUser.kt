package com.example.gradproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_new_user.*

class newUser : AppCompatActivity() {

    val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_user)

        cancelBu.setOnClickListener {
            finish()
        }



        createBu.setOnClickListener {

            if (studentEmail.text.toString().isNotEmpty() && studentPass.text.toString().isNotEmpty()) {

                mAuth.createUserWithEmailAndPassword(studentEmail.text.toString(), studentPass.text.toString()).addOnCompleteListener { task ->
                    if (task.isSuccessful) {

                        Toast.makeText(this, "Added to database!", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this, task.exception.toString(), Toast.LENGTH_LONG).show()
                    }
                }
            }else{
                Toast.makeText(this, "Fields cannot be empty!", Toast.LENGTH_SHORT).show()
            }
        }

    }
}