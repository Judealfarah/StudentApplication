package com.example.gradproject


import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_add_prof.*

class addProf : AppCompatActivity() {
    
    val PICK_IMAGE_REQUEST = 1

    var imageUrl : Uri = Uri.parse("@drawable/defaultimage.jpg")

    private var myRef= FirebaseDatabase.getInstance().getReference("Professors")

    lateinit var getDepartmentString : String

    private val storeRef = FirebaseStorage.getInstance().getReference("Professors")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_prof)

        val department = resources.getStringArray(R.array.Department)

        if (departmentSpinner != null){
            val adapter = ArrayAdapter(this, R.layout.spinner_directory, department)
            departmentSpinner.adapter = adapter
            departmentSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    getDepartmentString = department[position].toString()
                }
                override fun onNothingSelected(parent: AdapterView<*>) {
                   getDepartmentString = ""
                }
            }
        }

        cancelBu.setOnClickListener {
            finish()
        }

        noPhotoBu.setOnCheckedChangeListener { buttonView, isChecked ->
            uploadPhotoBu.isEnabled = !isChecked
        }


        uploadPhotoBu.setOnClickListener{
            openFileChooser()
        }



        saveBu.setOnClickListener {

            if(et_profName.text.isNotEmpty() && et_profTitle.text.isNotEmpty() && et_profEmail.text.isNotEmpty() && et_profOffice.text.isNotEmpty()){

                val profName = et_profName.text.toString()

                val prof = Professor(profName,et_profTitle.text.toString(),et_profEmail.text.toString(),et_profOffice.text.toString(),getDepartmentString)
                uploadFile()
                myRef.child(profName).setValue(prof).addOnCompleteListener {
                    Toast.makeText(this, "Added!", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }else{
                Toast.makeText(this, "Fields cannot be empty!", Toast.LENGTH_LONG).show()
            }

        }
    }

    private fun openFileChooser(){
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null){


            imageUrl = data.data!!
            Picasso.with(this)
                    .load(imageUrl)
                    .resize(500,600)
                    .centerInside()
                    .into(uploadPhotoBu)

        }

    }



    private fun uploadFile(){



        val fileRef = storeRef.child(et_profName.text.toString()+" Image")
         fileRef.putFile(imageUrl).addOnSuccessListener {
             Toast.makeText(this, "Uploaded to Storage", Toast.LENGTH_SHORT).show()
         }
             .addOnFailureListener {
                 Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
             }

    }



}

