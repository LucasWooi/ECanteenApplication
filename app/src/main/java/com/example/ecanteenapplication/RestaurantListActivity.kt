package com.example.ecanteenapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.ecanteenapplication.databinding.ActivityRestaurantListBinding
import com.example.ecanteenapplication.databinding.ActivityStudentListBinding

class RestaurantListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRestaurantListBinding
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navigationCtrl = this.findNavController(R.id.myNavHostFragment)
        NavigationUI.setupActionBarWithNavController(this, navigationCtrl)

        drawerLayout = binding.drawerLayout
        NavigationUI.setupActionBarWithNavController(this, navigationCtrl, drawerLayout)
    }
}