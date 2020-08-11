package com.example.gradproject

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_study_plan.*

@Suppress("DEPRECATION")
class StudyPlan : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_study_plan)

        val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
        val userEmail= mAuth.currentUser!!.email.toString()
        val user = mAuth.currentUser

        if (userEmail.toLowerCase() == "admin@admin.gju"){
            showUserEmailPlanTV.text = "Signed in as admin: $userEmail"

        }else if(user!!.isAnonymous){
            showUserEmailPlanTV.visibility = View.INVISIBLE
            signOutPlanBu.visibility = View.INVISIBLE

        }else{
            showUserEmailPlanTV.text = "Signed in Student: $userEmail"
        }

        signOutPlanBu.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent (this, Login::class.java)
            startActivity(intent)
            finish()
        }


        Acc_Bu.setOnClickListener {
            permissionCheck()
            startDownloading("https://www.docdroid.net/file/download/4yJrO5n/accouting-tree-plan-201819-pdf.pdf", "Accounting")
        }

        Bio_Bu.setOnClickListener {
            permissionCheck()
            startDownloading("https://www.docdroid.net/file/download/NkViVx6/biomedical-enginerring-tree-plan-2019-pdf.pdf","Biomedical")
        }
        Civil_Bu.setOnClickListener {
            permissionCheck()
            startDownloading("https://www.docdroid.net/file/download/zeqfRpa/civil-engineering-tree-plan-pdf.pdf","Civil")
        }
        CE_Bu.setOnClickListener {
            permissionCheck()
            startDownloading("https://www.docdroid.net/file/download/6A02mSe/computer-engineering-tree-plan-pdf.pdf", "CE")
        }
        CS_Bu.setOnClickListener {
            permissionCheck()
            startDownloading("https://www.docdroid.net/file/download/iBWWYGv/computer-science-tree-plan-pdf.pdf", "CS")
        }
        Indust_Bu.setOnClickListener {
            permissionCheck()
            startDownloading("https://www.docdroid.net/file/download/3Ii5Lr0/industrial-engineering-tree-plan-pdf.pdf", "Industrial")
        }
        Lgt_Bu.setOnClickListener {
            permissionCheck()
            startDownloading("https://www.docdroid.net/file/download/bLEnYnF/logistics-tree-plan-201819-pdf.pdf","Logistics")
        }
        Mng_Bu.setOnClickListener {
            permissionCheck()
            startDownloading("https://www.docdroid.net/file/download/Ax6QL8p/managment-tree-plan-2018-19-pdf.pdf","Management")
        }
        Pharma_Bu.setOnClickListener {
            permissionCheck()
            startDownloading("https://www.docdroid.net/file/download/qMEF60O/pharmaceutical-engineering-tree-plan2014-2018-pdf.pdf", "Pharmaceutical")
        }

        Archi_Bu.setOnClickListener {
            showNotAvailableToast()
        }
        Mech_Bu.setOnClickListener {
            showNotAvailableToast()
        }
        Visual_Bu.setOnClickListener {
            showNotAvailableToast()
        }

        GEBC_Bu.setOnClickListener {
            showNotAvailableToast()
        }
        Elect_Bu.setOnClickListener {
            showNotAvailableToast()
        }






    }


    private fun permissionCheck(){
        val STORAGE_PERMISSION_CODE: Int = 1000

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_DENIED
            ) {
                //Denied, request it again
                //show pop up for runtime request
                requestPermissions(
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    STORAGE_PERMISSION_CODE
                )
            }

        }
    }
    private fun startDownloading(spt_id: String , spt_name : String) {

        val request = DownloadManager.Request(Uri.parse(spt_id))
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
        request.setTitle(spt_name + " Study Plan Tree")
        request.setDescription("Downloading...")

        request.allowScanningByMediaScanner()
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(
            Environment.DIRECTORY_DOWNLOADS,
            "${System.currentTimeMillis()}"
        )
        val manager = baseContext?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        manager.enqueue(request)
        Toast.makeText(applicationContext, "Downloaded. Check notifications", Toast.LENGTH_SHORT).show()
    }


    private fun showNotAvailableToast(){
        Toast.makeText(this, "Study Plan Tree not available for now!", Toast.LENGTH_LONG).show()
    }

}