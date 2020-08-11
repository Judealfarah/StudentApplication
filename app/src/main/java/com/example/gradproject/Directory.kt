package com.example.gradproject

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_directory.*
import kotlinx.android.synthetic.main.directoryticket.view.*
import kotlin.collections.ArrayList


class Directory : AppCompatActivity() {

    var listProf = ArrayList<Professor>()
    private var ref1= FirebaseDatabase.getInstance().getReference("Professors")

    val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val userEmail= mAuth.currentUser!!.email.toString()

    val storeRef = FirebaseStorage.getInstance().getReference("Professors/")



    @SuppressLint("SetTextI18n", "DefaultLocale")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_directory)


        if (userEmail.toLowerCase() == "admin@admin.gju") {
            showUserEmailTV.text = "Signed in as admin: $userEmail"
        }else {
            showUserEmailTV.text = "Signed in Student: $userEmail"
        }


        loadList()

        signOutBu.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent (this, Login::class.java)
            startActivity(intent)
            finish()
        }

        searchSubmit.setOnClickListener {
            loadList()
        }


    }

    override fun onStart(){
        super.onStart()
        loadList()
    }

    override fun onResume() {
        super.onResume()
        loadList()
    }
    


    inner class ProfAdapter(context: Context, listProfs: ArrayList<Professor>): BaseAdapter(){


        private var listProfsAdapter = ArrayList<Professor>()
        private val mContext:Context

        init {

            listProfsAdapter =listProfs
            mContext = context
        }


        @SuppressLint("ViewHolder")
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val layoutInflater = LayoutInflater.from(mContext)
            val myView = layoutInflater.inflate(R.layout.directoryticket,parent,false)

            val prof = listProfsAdapter[position]


                storeRef.child((prof.professorName)+" Image").downloadUrl.addOnSuccessListener {Uri ->

                    val imageUrl = Uri.toString()
                    Picasso.with(this@Directory).load(imageUrl).into(myView.profImage)

            }


            myView.profName.text = prof.professorName
            myView.profTitle.text = prof.professorTitle
            myView.profEmail.text = prof.professorEmail
            myView.profOffice.text = prof.professorOffice
            myView.DepartmentTV.text = prof.department

            if (userEmail == "admin@admin.gju"){
                myView.deleteBu.visibility = View.VISIBLE
            }
            myView.deleteBu.setOnClickListener {
                ref1.child(prof.professorName).removeValue()
               storeRef.child((prof.professorName)+" Image").delete()

            }


            return myView

        }

        override fun getItem(position: Int): Any {

            return listProfsAdapter[position]
        }

        override fun getItemId(position: Int): Long {

            return position.toLong()
        }

        override fun getCount(): Int {

            return listProfsAdapter.size
        }


    }

    private fun loadList(){

        if(searchProfessor.text.isNotEmpty()) {

            val text = searchProfessor.text.toString()


            ref1.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                }

                override fun onDataChange(snapshot: DataSnapshot) {


                    listProf.clear()
                    for (ds in snapshot.children) {
                        val prof = ds.getValue(Professor::class.java)
                        if (prof != null && prof.professorName == text) {
                            listProf.add(prof)
                        }
                    }
                    val profsAdapter = ProfAdapter(this@Directory, listProf)
                    profsAdapter.notifyDataSetChanged()
                    lvDirectory.adapter = profsAdapter



                }
            })
        }else if (searchProfessor.text.isEmpty()) {
            ref1.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                }

                override fun onDataChange(snapshot: DataSnapshot) {

                    listProf.clear()
                    for (ds in snapshot.children) {
                        val prof = ds.getValue(Professor::class.java)
                        if (prof != null) {
                            listProf.add(prof)
                        }
                    }
                    val profsAdapter = ProfAdapter(this@Directory, listProf)
                    lvDirectory.adapter = profsAdapter


                }

            })
        }
    }






}


