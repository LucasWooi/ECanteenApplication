package com.example.ecanteenapplication.CustomerSide

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecanteenapplication.Database
import com.example.ecanteenapplication.Foods.FoodDetailsActivity
import com.example.ecanteenapplication.Foods.FoodListAdapter
import com.example.ecanteenapplication.R
import com.example.ecanteenapplication.databinding.ActivityHomePageBinding
import com.example.ecanteenapplication.databinding.ActivityResFoodListBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.lang.Double

class ResFoodListActivity : AppCompatActivity() {
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<ResFoodListAdapter.ViewHolder>? = null
    private lateinit var binding: ActivityResFoodListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_res_food_list)
        binding = ActivityResFoodListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val resPosition = intent.getIntExtra("getIndex",0)

        val resID = Database.restaurants[resPosition].getID()
        val studID = intent.getStringExtra("getID")

        val storageRef = FirebaseStorage.getInstance().reference.child("res/$resID.jpg")
        val localfile = File.createTempFile("tempImage","jpeg")
        storageRef.getFile(localfile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
            binding.getResImage.setImageBitmap(bitmap)
        }

        layoutManager = LinearLayoutManager(this)

        binding.recycleViewFoods.layoutManager = layoutManager

        ResFoodListAdapter.setFragment(resID,this)

        adapter = ResFoodListAdapter()
        binding.recycleViewFoods.adapter = adapter

        binding.backFromFoodButton.setOnClickListener {
            val intent = Intent(this,HomePageActivity::class.java)
            intent.putExtra("getID",studID)
            startActivity(intent)
        }
    }

    fun toDetail(index: Int){
        val studID = intent.getStringExtra("getID")
        val intent = Intent(this, ShowFoodDetailsActivity::class.java)
        intent.putExtra("getIndex",index)
        intent.putExtra("getID",studID)
        startActivity(intent)
    }
}