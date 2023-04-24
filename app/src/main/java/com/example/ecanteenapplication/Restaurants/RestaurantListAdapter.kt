package com.example.ecanteenapplication.Restaurants

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ecanteenapplication.Database
import com.example.ecanteenapplication.R

class RestaurantListAdapter: RecyclerView.Adapter<RestaurantListAdapter.ViewHolder>() {
    companion object{
        private lateinit var fragmentActi : RestaurantListFragment
        fun setFragment(fragmentActi: RestaurantListFragment){
            Companion.fragmentActi = fragmentActi
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_layout_res, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.id.text = Database.restaurants[position].getID()
        holder.name.text = Database.restaurants[position].getName()
        holder.ownName.text = Database.restaurants[position].getOwnerName()
        holder.email.text = Database.restaurants[position].getEmail()
        holder.phoneNo.text = Database.restaurants[position].getPhoneNumber()
        holder.balance.text = Database.restaurants[position].getWalletBalance().toString()
    }

    override fun getItemCount(): Int {
        return Database.restaurants.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var id: TextView
        var name: TextView
        var ownName: TextView
        var phoneNo: TextView
        var email: TextView
        var balance: TextView

        init {
            id = itemView.findViewById(R.id.card_ID)
            name = itemView.findViewById(R.id.card_name)
            ownName = itemView.findViewById(R.id.card_ownName)
            email = itemView.findViewById(R.id.card_email)
            phoneNo = itemView.findViewById(R.id.card_phone)
            balance = itemView.findViewById(R.id.card_balance)

            itemView.setOnClickListener{
                val position: Int = absoluteAdapterPosition
                toDetail(fragmentActi,position)
            }
        }
    }
    fun toDetail(fragmentAct: RestaurantListFragment, index: Int){
        fragmentAct.toDetail(index)
    }

}