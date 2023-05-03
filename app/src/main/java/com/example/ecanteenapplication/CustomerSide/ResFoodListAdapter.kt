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
import com.example.ecanteenapplication.Food
import com.example.ecanteenapplication.R
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.lang.Double

class ResFoodListAdapter: RecyclerView.Adapter<ResFoodListAdapter.ViewHolder>() {

    companion object{
        private var getResID : String = ""
        private lateinit var ActivityActi : ResFoodListActivity
        fun setFragment(getID: String,ActivityActi: ResFoodListActivity){
            getResID = getID
            Companion.ActivityActi = ActivityActi
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResFoodListAdapter.ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_layout_showfood, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ResFoodListAdapter.ViewHolder, position: Int) {
        Log.e("Check ID", getResID)

        if(getResID == Database.foods[position].getResID()) {
            val id = Database.foods[position].getID()
            val storageRef = FirebaseStorage.getInstance().reference.child("Food/$id.jpg")
            val localfile = File.createTempFile("tempImage","jpeg")
            storageRef.getFile(localfile).addOnSuccessListener {
                val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                holder.fdImg.setImageBitmap(bitmap)
            }
            holder.name.text = Database.foods[position].getName()
            holder.price.text = "RM " + Database.foods[position].getPrice().toString()
        }
        else {
            // No orders to display for this ResID
            holder.name.text = "empty"
            holder.price.text = ""

            // Set visibility of card view to GONE
            holder.itemView.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return Database.foods.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var fdImg: ImageView
        var name: TextView
        var price: TextView

        init {
            fdImg = itemView.findViewById(R.id.getFoodImg)
            name = itemView.findViewById(R.id.fdName)
            price = itemView.findViewById(R.id.fdPrice)

            itemView.setOnClickListener{
                val position: Int = absoluteAdapterPosition
                toDetail(ActivityActi,position)
            }
        }
    }

    fun toDetail(ActivityAct: ResFoodListActivity, index: Int){
        ActivityAct.toDetail(index)
    }
}