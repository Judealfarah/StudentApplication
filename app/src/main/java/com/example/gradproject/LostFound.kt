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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_lost_found.*
import kotlinx.android.synthetic.main.foundticket.view.*

class LostFound : AppCompatActivity() {

    var listItemsAdapter = ArrayList<Items>()
    val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val userEmail= mAuth.currentUser!!.email.toString()
    private var myRef= FirebaseDatabase.getInstance().getReference("FoundItems")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lost_found)


        if (userEmail.toLowerCase() == "admin@admin.gju"){
            showUserEmaillostTV.text = "Signed in as admin: $userEmail"

        }else {
            showUserEmaillostTV.text = "Signed in Student: $userEmail"
        }

        loadList()

        signOutlostBu.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent (this, Login::class.java)
            startActivity(intent)
            finish()
        }


        lostAddBu.setOnClickListener {
            startActivity(Intent(this, addItem::class.java))
        }
    }
    override  fun onResume() {
        super.onResume()
        loadList()
    }

    override fun onStart() {
        super.onStart()
        loadList()
    }

    private fun loadList(){
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {

                    listItemsAdapter.clear()
                    for(ds in snapshot.children) {
                        val item = ds.getValue(Items::class.java)
                        if (item != null) {
                            listItemsAdapter.add(item)
                        }
                    }
                    val itemAdapter = itemsAdapter(this@LostFound, listItemsAdapter)
                    lost_listView.adapter = itemAdapter
            }
        })
    }

    inner class itemsAdapter(context: Context, listItems: ArrayList<Items>): BaseAdapter(){


        var listItemsAdapter = ArrayList<Items>()
        private val mContext:Context

        init {

            listItemsAdapter =listItems
            mContext = context
        }


        @SuppressLint("ViewHolder")
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val layoutInflater = LayoutInflater.from(mContext)
            val myView = layoutInflater.inflate(R.layout.foundticket,parent,false)

            val item = listItemsAdapter[position]
            myView.lostItemNameTV.text = item.itemName
            myView.lostItemDescTV.text = item.itemDesc
            myView.itemPlaceFoundTV.text = item.itemPlace
            myView.studentEmailTV.text = item.cpEmail
            myView.studentNumTV.text = item.cpNum

            if (userEmail == "admin@admin.gju" || userEmail == item.cpEmail){
                myView.deleteBu.visibility = View.VISIBLE
            }

            myView.deleteBu.setOnClickListener {
                myRef.child(item.itemId).removeValue()
            }

            return myView

        }

        override fun getItem(position: Int): Any {

            return listItemsAdapter[position]
        }

        override fun getItemId(position: Int): Long {

            return position.toLong()
        }

        override fun getCount(): Int {

            return listItemsAdapter.size
        }


    }





}