package com.example.ecanteenapplication.Foods

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecanteenapplication.R
import com.example.ecanteenapplication.RestaurantHomeActivity
import com.example.ecanteenapplication.databinding.ActivityFoodListBinding

class FoodListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFoodListBinding
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<FoodListAdapter.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_list)
        binding = ActivityFoodListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val getLoginID = intent.getStringExtra("getID")

        layoutManager = LinearLayoutManager(this)

        binding.recycleViewFoods.layoutManager = layoutManager

        FoodListAdapter.setFragment(getLoginID.toString(),this)

        adapter = FoodListAdapter()
        binding.recycleViewFoods.adapter = adapter

        binding.addNewFoodBtn.setOnClickListener {
            val intent = Intent(this, AddNewFoodActivity::class.java)
            intent.putExtra("getLogID",getLoginID.toString())
            startActivity(intent)
        }

        binding.backFromFoodButton.setOnClickListener {
            val intent = Intent(this,RestaurantHomeActivity::class.java)
            intent.putExtra("getResID",getLoginID.toString())
            startActivity(intent)
        }

    }

    fun toDetail(index: Int){
        val intent = Intent(this, FoodDetailsActivity::class.java)
        intent.putExtra("getIndex",index)
        startActivity(intent)
    }
}