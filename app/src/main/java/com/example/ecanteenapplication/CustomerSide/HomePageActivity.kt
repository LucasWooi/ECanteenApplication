package com.example.ecanteenapplication.CustomerSide

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecanteenapplication.R
import com.example.ecanteenapplication.databinding.ActivityHomePageBinding

class HomePageActivity : AppCompatActivity() {
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<HomePageAdapter.ViewHolder>? = null
    private lateinit var binding: ActivityHomePageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        layoutManager = LinearLayoutManager(this)

        binding.recycleViewRes.layoutManager = layoutManager

        HomePageAdapter.setFragment(this)

        adapter = HomePageAdapter()
        binding.recycleViewRes.adapter = adapter

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

    fun toDetail(index: Int){
        val loginID = intent.getStringExtra("getID")
        val intent = Intent(this, ResFoodListActivity::class.java)
        intent.putExtra("getIndex",index)
        intent.putExtra("getID",loginID)
        startActivity(intent)
    }

    fun setNewPage(activity: Activity){
        val loginID = intent.getStringExtra("getID")
        val intent = Intent(this,activity::class.java)
        intent.putExtra("getID",loginID)
        startActivity(intent)
    }
}