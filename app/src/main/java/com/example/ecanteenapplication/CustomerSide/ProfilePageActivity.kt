package com.example.ecanteenapplication.CustomerSide

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.recreate
import com.example.ecanteenapplication.Database
import com.example.ecanteenapplication.MainActivity
import com.example.ecanteenapplication.R
import com.example.ecanteenapplication.databinding.ActivityProfilePageBinding
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class ProfilePageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfilePageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_page)
        binding = ActivityProfilePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val studID = intent.getStringExtra("getID")

        for(studentInfo in Database.students){
            if(studID == studentInfo.getID()){
                val storageRef = FirebaseStorage.getInstance().reference.child("Student/$studID.jpg")
                val localfile = File.createTempFile("tempImage","jpeg")
                storageRef.getFile(localfile).addOnSuccessListener {
                    val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                    binding.imageView.setImageBitmap(bitmap)
                }
                binding.displayID.text = studentInfo.getID()
                binding.displayName.text = studentInfo.getName()
                binding.displayEmail.text = studentInfo.getEmail()
                binding.displayPhone.text = studentInfo.getPhoneNumber()
                binding.displayBalance.text = "RM " + studentInfo.getWalletBalance().toString()
            }
        }

        binding.updateButton.setOnClickListener {
            val intent = Intent(this,UpdateFromStudentActivity::class.java)
            intent.putExtra("getID",studID)
            startActivity(intent)
        }

        binding.logoutButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.topupButton.setOnClickListener {
            val intent = Intent(this,TopupActivity::class.java)
            intent.putExtra("getID",studID)
            startActivity(intent)
        }

        val homePage = HomePageActivity()
        val orderHistoryPage = OrderHistoryActivity()
        val profilePage = ProfilePageActivity()


        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.page_1->setNewPage(homePage)
                R.id.page_2->setNewPage(orderHistoryPage)
                R.id.page_3->setNewPage(profilePage)
            }
            true
        }

    }
    fun setNewPage(activity: Activity){
        val loginID = intent.getStringExtra("getID")
        val intent = Intent(this,activity::class.java)
        intent.putExtra("getID",loginID)
        startActivity(intent)
    }
}