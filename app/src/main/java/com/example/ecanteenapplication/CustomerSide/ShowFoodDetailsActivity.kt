package com.example.ecanteenapplication.CustomerSide

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.ecanteenapplication.Database
import com.example.ecanteenapplication.R
import com.example.ecanteenapplication.databinding.ActivityShowFoodDetailsBinding
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class ShowFoodDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShowFoodDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_food_details)
        binding = ActivityShowFoodDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val foodPosition = intent.getIntExtra("getIndex",0)
        val studID = intent.getStringExtra("getID")
        val fid = Database.foods[foodPosition].getID()
        val rid = Database.foods[foodPosition].getResID()

        val storageRef = FirebaseStorage.getInstance().reference.child("Food/$fid.jpg")
        val localfile = File.createTempFile("tempImage","jpg")
        storageRef.getFile(localfile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
            binding.getFoodImage.setImageBitmap(bitmap)
        }.addOnFailureListener{
            binding.getFoodImage.setImageResource(R.drawable.white_background)
        }

        binding.displayName.text = Database.foods[foodPosition].getName()
        binding.displayPrice.text = "RM " + Database.foods[foodPosition].getPrice().toString()

        binding.confirmButton.setOnClickListener {
            if(binding.orderQty.text.toString() == ""){
                Toast.makeText(this,"Please enter how many you want order.",Toast.LENGTH_LONG).show()
                binding.orderQty.requestFocus()
            }else {
                val orderQty = Integer.parseInt(binding.orderQty.text.toString())

                val intent = Intent(this, SummaryOrderActivity::class.java)
                intent.putExtra("getFID", fid)
                intent.putExtra("getSID", studID)
                intent.putExtra("getRID", rid)
                intent.putExtra("getQty", orderQty)
                startActivity(intent)
            }
        }
        binding.cancelButton.setOnClickListener {
            val intent = Intent(this,ResFoodListActivity::class.java)
            intent.putExtra("getSID", studID)
            startActivity(intent)
        }
    }
}