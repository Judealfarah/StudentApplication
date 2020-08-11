package com.example.gradproject


import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add_post.*
import kotlinx.android.synthetic.main.activity_add_post.cancelBu
import java.text.DateFormat
import java.util.*

class addPost : AppCompatActivity() {

    val PICK_IMAGE_REQUEST = 1

    var imageUrl : Uri = Uri.parse("@drawable/defaultimage.jpg")

    private var myRef= FirebaseDatabase.getInstance().getReference("Posts")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_post)

        val calendar : Calendar = Calendar.getInstance()
        val date = DateFormat.getDateInstance().format(calendar.time)

        cancelBu.setOnClickListener {
            finish()
        }



        addPostBu.setOnClickListener {

            if(announcementTitle.text.isNotEmpty() && announcementDesc.text.isNotEmpty()){

                val postId = myRef.push().key.toString()

                val post = Posts(postId,announcementTitle.text.toString(), announcementDesc.text.toString(), date)
                myRef.child(postId).setValue(post).addOnCompleteListener {
                    Toast.makeText(this, "Added!", Toast.LENGTH_SHORT).show()

                    finish()
                }

            }else{
                Toast.makeText(this, "Fields cannot be empty!", Toast.LENGTH_SHORT).show()
            }

        }

    }


}