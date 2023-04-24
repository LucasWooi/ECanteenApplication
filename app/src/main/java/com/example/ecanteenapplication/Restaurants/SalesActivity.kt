package com.example.ecanteenapplication.Restaurants

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecanteenapplication.CustomerSide.OrderHistoryAdapter
import com.example.ecanteenapplication.Database
import com.example.ecanteenapplication.R
import com.example.ecanteenapplication.RestaurantHomeActivity
import com.example.ecanteenapplication.databinding.ActivityOrderHistoryBinding
import com.example.ecanteenapplication.databinding.ActivitySalesBinding

class SalesActivity : AppCompatActivity() {
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<SalesAdapter.ViewHolder>? = null
    private lateinit var binding: ActivitySalesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sales)
        binding = ActivitySalesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        layoutManager = LinearLayoutManager(this)

        val id = intent.getStringExtra("getID")

        binding.recycleViewSales.layoutManager = layoutManager

        SalesAdapter.setFragment(id.toString(),this)

        adapter = SalesAdapter()

        binding.recycleViewSales.adapter = adapter

        var totalIncome = 0.0

        for(order in Database.orders){
            if(id == order.getResID()){
                totalIncome += order.getPayment()
            }
        }

        binding.textView.text = "Total Income : RM " + totalIncome

        binding.backFromSalesButton.setOnClickListener {
            val intent = Intent(this,RestaurantHomeActivity::class.java)
            intent.putExtra("getResID",id)
            startActivity(intent)
        }
    }
}