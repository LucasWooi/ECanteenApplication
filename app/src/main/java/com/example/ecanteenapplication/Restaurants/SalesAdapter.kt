package com.example.ecanteenapplication.Restaurants

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ecanteenapplication.CustomerSide.OrderHistoryActivity
import com.example.ecanteenapplication.CustomerSide.OrderHistoryAdapter
import com.example.ecanteenapplication.Database
import com.example.ecanteenapplication.R
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class SalesAdapter: RecyclerView.Adapter<SalesAdapter.ViewHolder>() {
    companion object{
        private var getResID : String = ""
        private lateinit var activityActi : SalesActivity
        fun setFragment(getID: String,ActivityActi: SalesActivity){
            getResID = getID
            Companion.activityActi = ActivityActi
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_layout_sales, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        if(getResID == Database.orders[position].getResID()) {
            val id = Database.orders[position].getFoodID()
            val storageRef = FirebaseStorage.getInstance().reference.child("Food/$id.jpg")
            val localfile = File.createTempFile("tempImage", "jpeg")
            storageRef.getFile(localfile).addOnSuccessListener {
                val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                holder.img.setImageBitmap(bitmap)
            }
            holder.name.text = Database.orders[position].getName()
            holder.price.text = "RM " + Database.orders[position].getPrice().toString()
            holder.qty.text = "Quantity: " + Database.orders[position].getQty().toString()
            holder.payAmo.text = "RM " + Database.orders[position].getPayment().toString()
        }
    }

    override fun getItemCount(): Int {
        return Database.orders.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var img: ImageView
        var name: TextView
        var price: TextView
        var qty: TextView
        var payAmo: TextView

        init {
            img = itemView.findViewById(R.id.getFoodImg)
            name = itemView.findViewById(R.id.foodName)
            price = itemView.findViewById(R.id.foodPrice)
            qty = itemView.findViewById(R.id.orderQty)
            payAmo = itemView.findViewById(R.id.showAmount)
        }
    }
}