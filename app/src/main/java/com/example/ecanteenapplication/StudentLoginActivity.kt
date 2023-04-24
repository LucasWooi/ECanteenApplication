package com.example.ecanteenapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.ecanteenapplication.CustomerSide.HomePageActivity
import com.example.ecanteenapplication.CustomerSide.RegisterFromStudentActivity
import com.example.ecanteenapplication.CustomerSide.ResetPasswordActivity
import com.example.ecanteenapplication.databinding.ActivityRestaurantLoginBinding
import com.example.ecanteenapplication.databinding.ActivityStudLoginBinding

class StudentLoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStudLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stud_login)
        binding = ActivityStudLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginBtn.setOnClickListener {
            login()
        }

        binding.regisBtn.setOnClickListener {
            val intent = Intent(this, RegisterFromStudentActivity::class.java)
            startActivity(intent)
        }

        binding.forgotBtn.setOnClickListener {
            val intent = Intent(this, ResetPasswordActivity::class.java)
            startActivity(intent)
        }
    }

    fun login(){
        var loginUnsucess: Boolean = false
        for(student in Database.students){
            if(student.getID() == binding.enterStudID.text.toString() && student.getPassword() == binding.studPass.text.toString()){
                val studID = binding.enterStudID.text.toString()
                Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, HomePageActivity::class.java)
                intent.putExtra("getID",studID)
                startActivity(intent)
                loginUnsucess = true
            }
        }
        if(!loginUnsucess){
            Toast.makeText(this, "Student ID or Password Incorrect!!!", Toast.LENGTH_SHORT).show()
        }
    }
}