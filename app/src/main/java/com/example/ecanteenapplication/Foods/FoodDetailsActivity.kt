package com.example.ecanteenapplication.Foods

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.ecanteenapplication.Database
import com.example.ecanteenapplication.R
import com.example.ecanteenapplication.databinding.ActivityFoodDetailsBinding
import com.example.ecanteenapplication.databinding.ActivityFoodListBinding
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class FoodDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFoodDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_details)
        binding = ActivityFoodDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val foodPosition = intent.getIntExtra("getIndex",0)

        binding.displayID.text = Database.foods[foodPosition].getID()
        binding.displayName.text = Database.foods[foodPosition].getName()
        binding.displayPrice.text = Database.foods[foodPosition].getPrice().toString()
        binding.displayRes.text = Database.foods[foodPosition].getResID()


        val foodID = binding.displayID.text.toString()
        Log.e("check food ID",foodID)
        val storageRef = FirebaseStorage.getInstance().reference.child("Food/$foodID.jpg")
        val localfile = File.createTempFile("tempImage","jpg")
        storageRef.getFile(localfile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
            binding.getFoodImage.setImageBitmap(bitmap)
        }.addOnFailureListener{
            binding.getFoodImage.setImageResource(R.drawable.white_background)
        }

        val returnID = binding.displayRes.text.toString()

        binding.confirmButton.setOnClickListener {
            val intent = Intent(this,FoodListActivity::class.java)
            intent.putExtra("getID",returnID)
            startActivity(intent)
        }

        binding.updateButton.setOnClickListener {
            val intent = Intent(this, UpdateFoodActivity::class.java)
            intent.putExtra("getFoodIndex",foodPosition)
            startActivity(intent)
        }

        binding.deleteButton.setOnClickListener {
            val deletedFood = binding.displayID.text.toString()
            Database.deleteFood(deletedFood)
            Toast.makeText(this,"Food deleted.", Toast.LENGTH_LONG).show()
            val intent = Intent(this,FoodListActivity::class.java)
            intent.putExtra("getID",returnID)
            startActivity(intent)
        }
    }
}

