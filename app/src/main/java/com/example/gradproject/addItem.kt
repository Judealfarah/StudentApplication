package com.example.gradproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add_item.*
import kotlinx.android.synthetic.main.activity_add_item.cancelBu

class addItem : AppCompatActivity() {

    val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val userEmail= mAuth.currentUser!!.email.toString()
    private var myRef= FirebaseDatabase.getInstance().getReference("FoundItems")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)


        if (userEmail.toLowerCase() != "admin@admin.gju"){
                 lostItemCpEmail.text = userEmail

        }



        addLostBu.setOnClickListener {


            if (lostItemName.text.toString().isNotEmpty() && lostItemPlace.text.toString().isNotEmpty()){


                val cpPhone = if(lostItemCpPhone.text.isEmpty()){
                    ""
                }else{
                    lostItemCpPhone.text.toString()
                }

                val itemId = myRef.push().key.toString()
                val item = Items(itemId,
                    lostItemName.text.toString(),
                    lostItemDesc.text.toString(),
                    lostItemPlace.text.toString(),
                    lostItemCpEmail.text.toString(),
                    cpPhone)


                myRef.child(itemId).setValue(item).addOnCompleteListener {
                    Toast.makeText(this, "Added!", Toast.LENGTH_SHORT).show()
                    finish()
                }

            }else{
                Toast.makeText(this, "Error: Field with * cannot be empty!", Toast.LENGTH_LONG).show()
            }


        }

        cancelBu.setOnClickListener {
            finish()
        }
    }

}