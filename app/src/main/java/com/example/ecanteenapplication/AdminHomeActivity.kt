package com.example.ecanteenapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ecanteenapplication.databinding.ActivityAdminHomeBinding

class AdminHomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_home)
        binding = ActivityAdminHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.viewStudBtn.setOnClickListener {
            val intent = Intent(this, StudentListActivity::class.java)

            startActivity(intent)
        }

        binding.viewResBtn.setOnClickListener {
            val intent = Intent(this, RestaurantListActivity::class.java)

            startActivity(intent)
        }

        binding.adminLogoutBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)

            startActivity(intent)
        }
    }
}