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
import kotlinx.android.synthetic.main.activity_announcements.*
import kotlinx.android.synthetic.main.activity_announcements.addPostBu
import kotlinx.android.synthetic.main.announcementsticket.view.*

class Announcements : AppCompatActivity() {

    val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val userEmail= mAuth.currentUser!!.email.toString()
    val user = mAuth.currentUser

    var listPostAdapter = ArrayList<Posts>()
    private var myRef= FirebaseDatabase.getInstance().getReference("Posts")




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_announcements)

        loadList()



        if (userEmail.toLowerCase() == "admin@admin.gju"){
            showUserEmailAnnounceTV.text = "Signed in as admin: $userEmail"

        }else if(user!!.isAnonymous){
            showUserEmailAnnounceTV.visibility = View.INVISIBLE
            signOutAnnounceBu.visibility = View.INVISIBLE
            addPostBu.visibility = View.GONE
        }else{
            showUserEmailAnnounceTV.text = "Signed in Student: $userEmail"
            addPostBu.visibility = View.GONE
        }

        signOutAnnounceBu.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent (this, Login::class.java)
            startActivity(intent)
            finish()
        }



        addPostBu.setOnClickListener {
            startActivity(Intent(this, addPost::class.java))
        }

    }

    override  fun onResume() {
        super.onResume()
        loadList()    }

    override fun onStart() {
        super.onStart()
        loadList()
    }

    inner class postAdapter(context: Context, listPosts: ArrayList<Posts>): BaseAdapter(){


        var listPostAdapter = ArrayList<Posts>()
        private val mContext:Context

        init {

            listPostAdapter =listPosts
            mContext = context
        }


        @SuppressLint("ViewHolder")
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val layoutInflater = LayoutInflater.from(mContext)
            val myView = layoutInflater.inflate(R.layout.announcementsticket,parent,false)

            val post = listPostAdapter[position]




            myView.date.text = post.postDate
            myView.announcementId.text = post.postId
            myView.announcementTitle.text = post.postTitle
            myView.announcementDesc.text =post.postDesc


            if (userEmail == "admin@admin.gju"){
                myView.deleteBu.visibility = View.VISIBLE
            }

            myView.deleteBu.setOnClickListener {
                myRef.child(post.postId).removeValue()

            }



            return myView

        }

        override fun getItem(position: Int): Any {

            return listPostAdapter[position]
        }

        override fun getItemId(position: Int): Long {

            return position.toLong()
        }

        override fun getCount(): Int {

            return listPostAdapter.size
        }


    }

    private fun loadList(){
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){

                    listPostAdapter.clear()
                    for(ds in snapshot.children) {
                        val post = ds.getValue(Posts::class.java)
                        if (post != null) {
                            listPostAdapter.add(post)
                        }

                    }
                    val postAdapter = postAdapter(this@Announcements, listPostAdapter)
                    lvPosts.adapter = postAdapter

                }
            }
        })


    }



    override fun onBackPressed() {
        super.onBackPressed()

        val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
        val userEmail= mAuth.currentUser!!.email.toString()
        val user = mAuth.currentUser

        if(user!!.isAnonymous){
            startActivity(Intent(this, guestWelcome::class.java))
            finish()
        }else{
            startActivity(Intent(this, Feed::class.java))
            finish()
        }

    }




    /*private fun showNotification(title : String){

        lateinit var notificationChannel : NotificationChannel
        lateinit var builder : Notification.Builder
        val channelId = "com.example.gradproject"
        val description = "Post Notifications"

        val notificationManager : NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        lateinit var intent : Intent


        val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
        val user = mAuth.currentUser
        if (user != null) {
            intent = Intent(this, Announcements::class.java)
        } else {
            intent = Intent(this, Login::class.java)
        }

        val notifytag = "New Post"
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        notificationChannel = NotificationChannel(channelId,description, NotificationManager.IMPORTANCE_DEFAULT)
        notificationChannel.enableLights(true)
        notificationChannel.enableVibration(true)
        notificationManager.createNotificationChannel(notificationChannel)

        builder = Notification.Builder(this, channelId)
            .setContentTitle("New Announcement!")
            .setContentText(title)
            .setSmallIcon(R.drawable.logo_no_border)
            .setContentIntent(pendingIntent)

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.ECLAIR) {
            notificationManager.notify(notifytag,0, builder.build())
        }else{
            notificationManager.notify(notifytag.hashCode(), builder.build())
        }

    }*/
}