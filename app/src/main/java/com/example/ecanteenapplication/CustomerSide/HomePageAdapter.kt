package com.example.ecanteenapplication.CustomerSide

import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ecanteenapplication.Database
import com.example.ecanteenapplication.Foods.FoodListActivity
import com.example.ecanteenapplication.R
import com.example.ecanteenapplication.Restaurants.RestaurantListAdapter
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class HomePageAdapter: RecyclerView.Adapter<HomePageAdapter.ViewHolder>() {
    companion object{
        private lateinit var activityActi : HomePageActivity
        fun setFragment(activityActi: HomePageActivity){
            Companion.activityActi = activityActi
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_layout_showcust, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val id = Database.restaurants[position].getID()
        holder.name.text = Database.restaurants[position].getName()
        Log.e("Check get ID",id)
        val storageRef = FirebaseStorage.getInstance().reference.child("res/$id.jpg")
        val localfile = File.createTempFile("tempImage","jpeg")
        storageRef.getFile(localfile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
            holder.img.setImageBitmap(bitmap)
        }
    }

    override fun getItemCount(): Int {
        return Database.restaurants.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var img: ImageView
        var name: TextView

        init {
            img = itemView.findViewById(R.id.getResImg)
            name = itemView.findViewById(R.id.resName)

            itemView.setOnClickListener{
                val position: Int = absoluteAdapterPosition
                toDetail(HomePageAdapter.activityActi,position)
            }
        }
    }
    fun toDetail(ActivityAct: HomePageActivity, index: Int){
        ActivityAct.toDetail(index)
    }
}