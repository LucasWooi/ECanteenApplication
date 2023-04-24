package com.example.ecanteenapplication.Foods

import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ecanteenapplication.Database
import com.example.ecanteenapplication.Database.Companion.foods
import com.example.ecanteenapplication.R

class FoodListAdapter: RecyclerView.Adapter<FoodListAdapter.ViewHolder>() {

    companion object{
        private var getLoginID : String = ""
        private lateinit var ActivityActi : FoodListActivity
        fun setFragment(getID: String,ActivityActi: FoodListActivity){
            getLoginID = getID
            Companion.ActivityActi = ActivityActi
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodListAdapter.ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_layout_food, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodListAdapter.ViewHolder, position: Int) {
        Log.e("Check ID", getLoginID)

        if(getLoginID == Database.foods[position].getResID()) {
            holder.id.text = Database.foods[position].getID()
            holder.name.text = Database.foods[position].getName()
            holder.price.text = Database.foods[position].getPrice().toString()
            holder.resID.text = Database.foods[position].getResID()
        }
    }

    override fun getItemCount(): Int {
        return Database.foods.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var id: TextView
        var name: TextView
        var price: TextView
        var resID: TextView

        init {
            id = itemView.findViewById(R.id.card_displayID)
            name = itemView.findViewById(R.id.card_displayName)
            price = itemView.findViewById(R.id.card_displayPrice)
            resID = itemView.findViewById(R.id.card_displayRes)

            itemView.setOnClickListener{
                val position: Int = absoluteAdapterPosition
                toDetail(ActivityActi,position)
            }
        }
    }

    fun toDetail(ActivityAct: FoodListActivity, index: Int){
        ActivityAct.toDetail(index)
    }


}