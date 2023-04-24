package com.example.ecanteenapplication

import android.R
import android.content.Context
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ecanteenapplication.databinding.ActivityAdminLoginBinding


class  AdminLoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_item)
        binding = ActivityAdminLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginBtnAdmin.setOnClickListener {
            login()
        }
    }

    fun login(){
        var loginUnsucess: Boolean = false
        for(admin in Database.admins){
            if(admin.getID() == binding.enterAdminID.text.toString() && admin.getPassword() == binding.enterAdminPass.text.toString()){
                Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, AdminHomeActivity::class.java)
                startActivity(intent)
                loginUnsucess = true
            }
        }
        if(!loginUnsucess){
            Toast.makeText(this, "Admin ID or Password Incorrect!!!", Toast.LENGTH_SHORT).show()
        }
    }
}