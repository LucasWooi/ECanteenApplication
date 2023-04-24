package com.example.ecanteenapplication.CustomerSide

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecanteenapplication.R
import com.example.ecanteenapplication.databinding.ActivityOrderHistoryBinding

class OrderHistoryActivity : AppCompatActivity() {
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder>? = null
    private lateinit var binding: ActivityOrderHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_history)
        binding = ActivityOrderHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        layoutManager = LinearLayoutManager(this)

        binding.recycleViewOrders.layoutManager = layoutManager

        val id = intent.getStringExtra("getID")

        OrderHistoryAdapter.setFragment(id.toString(),this)

        adapter = OrderHistoryAdapter()

        binding.recycleViewOrders.adapter = adapter

        val homePage = HomePageActivity()
        val orderHistoryPage = OrderHistoryActivity()
        val profilePage = ProfilePageActivity()


        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.page_1->setNewPage(homePage)
                R.id.page_2->setNewPage(orderHistoryPage)
                R.id.page_3->setNewPage(profilePage)
            }
            true
        }
    }
    fun setNewPage(activity: Activity){
        val loginID = intent.getStringExtra("getID")
        val intent = Intent(this,activity::class.java)
        intent.putExtra("getID",loginID)
        startActivity(intent)
    }
}