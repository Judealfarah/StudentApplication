package com.example.gradproject


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add_book.*


class addBook : AppCompatActivity() {

    private var myRef= FirebaseDatabase.getInstance().getReference("UsedBooks")

    val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val userEmail= mAuth.currentUser!!.email.toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_book)

        usedBookCPEmailET.text = userEmail


        addBookBu.setOnClickListener {

                if (usedBookNameET.text.toString().isNotEmpty() && usedBookPriceET.text.toString()
                        .isNotEmpty() && usedBookCPEmailET.text.toString().isNotEmpty()
                ) {


                    val cpPhone = if(usedBookCpPhoneET.text.isEmpty()){
                        ""
                    }else{
                        usedBookCpPhoneET.text.toString()
                    }


                    val bookId = myRef.push().key.toString()
                    val book = Books(bookId,usedBookNameET.text.toString(),
                        usedBookDescET.text.toString(),
                        usedBookPriceET.text.toString(),
                        usedBookCPEmailET.text.toString(),
                        cpPhone)

                    myRef.child(bookId).setValue(book).addOnCompleteListener {
                        Toast.makeText(this, "Added!", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                } else {
                    Toast.makeText(this, "Cannot be empty!", Toast.LENGTH_LONG).show()
                }


            }


        cancelBu.setOnClickListener {
            finish()
        }
    }

}