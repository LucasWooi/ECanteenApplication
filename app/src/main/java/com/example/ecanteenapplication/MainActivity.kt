package com.example.ecanteenapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ecanteenapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(Database.students.size == 0) {
            Database.getAllDataFromFirestore()
        }

        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.studLoginBtn.setOnClickListener {
            val intent = Intent(this, StudentLoginActivity::class.java)

            startActivity(intent)
        }

        binding.restauLoginBtn.setOnClickListener {
            val intent = Intent(this, RestaurantLoginActivity::class.java)

            startActivity(intent)
        }

        binding.adminLoginBtn.setOnClickListener {
            val intent = Intent(this, AdminLoginActivity::class.java)

            startActivity(intent)
        }
    }
}