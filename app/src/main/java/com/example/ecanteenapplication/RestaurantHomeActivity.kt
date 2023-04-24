package com.example.ecanteenapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ecanteenapplication.Foods.FoodListActivity
import com.example.ecanteenapplication.Restaurants.SalesActivity
//import com.example.ecanteenapplication.Foods.FoodListFragment
import com.example.ecanteenapplication.databinding.ActivityRestaurantHomeBinding

class RestaurantHomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRestaurantHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_home)
        binding = ActivityRestaurantHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val getRestaurantID = intent.getStringExtra("getResID")

        binding.viewSalesBtn.setOnClickListener {
            val intent = Intent(this, SalesActivity::class.java)
            intent.putExtra("getID",getRestaurantID.toString())
            startActivity(intent)
        }

        binding.viewFoodBtn.setOnClickListener {
            val intent = Intent(this, FoodListActivity::class.java)
            intent.putExtra("getID",getRestaurantID.toString())
            startActivity(intent)
        }

        binding.restaurantLogoutBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}