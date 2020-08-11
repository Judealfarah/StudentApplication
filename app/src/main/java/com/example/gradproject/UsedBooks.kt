package com.example.gradproject

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
import kotlinx.android.synthetic.main.activity_used_books.*
import kotlinx.android.synthetic.main.usedbooksticket.view.*

class UsedBooks : AppCompatActivity() {

    var listBooksAdapter = ArrayList<Books>()
    val mAuth : FirebaseAuth = FirebaseAuth.getInstance()
    val userEmail = mAuth.currentUser!!.email.toString()
    val myRef = FirebaseDatabase.getInstance().getReference("UsedBooks")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_used_books)

        if (userEmail.toLowerCase() == "admin@admin.gju"){
            showUserEmailUsedBooksTV.text = "Signed in as admin: $userEmail"
        }else{
            showUserEmailUsedBooksTV.text = "Signed in Student: $userEmail"
        }

        loadList()

        signOutUsedBooksBu.setOnClickListener {
            mAuth.signOut()
            startActivity(Intent(this, Login::class.java))
            finish()
        }

        addNewBookBu.setOnClickListener {
            startActivity(Intent(this, addBook::class.java))
        }


    }

    override fun onStart() {
        super.onStart()
        loadList()
    }

    override fun onResume() {
        super.onResume()
        loadList()
    }


    private fun loadList(){
        myRef.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {

                    listBooksAdapter.clear()
                    for(ds in snapshot.children){
                        val book = ds.getValue(Books::class.java)
                        if (book != null){
                            listBooksAdapter.add(book)
                        }
                    }
                    val booksAdapter = BooksAdapter(this@UsedBooks, listBooksAdapter)
                    lvBooks.adapter = booksAdapter


            }

        })
    }

    inner class BooksAdapter(context: Context, listBooks: ArrayList<Books>): BaseAdapter(){

        var listBooksAdapter = ArrayList<Books>()
        private val mContext:Context

        init {
            listBooksAdapter = listBooks
            mContext = context
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
           val layoutInflater = LayoutInflater.from(mContext)
            val myView = layoutInflater.inflate(R.layout.usedbooksticket, parent, false)

            val books = listBooksAdapter[position]
            myView.usedBookNameTV.text = books.bookName
            myView.usedBookDescTV.text = books.bookDesc
            myView.usedBookPriceTV.text = books.bookPrice + " JDs"
            myView.usedBookCPEmailTV.text = books.cpEmail
            myView.usedBookCPNumTV.text = books.cpNum

            if (userEmail == "admin@admin.gju" || userEmail == books.cpEmail){
                myView.deleteBookBu.visibility = View.VISIBLE
            }

            myView.deleteBookBu.setOnClickListener {

                myRef.child(books.bookId).removeValue()

            }

            return  myView

        }

        override fun getItem(position: Int): Any {
            return listBooksAdapter[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return listBooksAdapter.size
        }


    }
}