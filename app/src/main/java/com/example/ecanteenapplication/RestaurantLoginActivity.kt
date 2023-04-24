package com.example.ecanteenapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.ecanteenapplication.databinding.ActivityAdminLoginBinding
import com.example.ecanteenapplication.databinding.ActivityRestaurantLoginBinding

class RestaurantLoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRestaurantLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_login)
        binding = ActivityRestaurantLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginBtnRes.setOnClickListener {
            login()
        }
    }

    fun login(){
        var loginUnsucess: Boolean = false
        for(restaurant in Database.restaurants){
            if(restaurant.getID() == binding.enterResID.text.toString() && restaurant.getPassword() == binding.resPass.text.toString()){
                Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, RestaurantHomeActivity::class.java)
                intent.putExtra("getResID",restaurant.getID())
                startActivity(intent)
                loginUnsucess = true
            }
        }
        if(!loginUnsucess){
            Toast.makeText(this, "Restaurant ID or Password Incorrect!!!", Toast.LENGTH_SHORT).show()
        }
    }
}