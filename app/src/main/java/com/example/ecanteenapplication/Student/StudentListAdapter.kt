package com.example.ecanteenapplication.Student

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ecanteenapplication.Database
import com.example.ecanteenapplication.R

class StudentListAdapter: RecyclerView.Adapter<StudentListAdapter.ViewHolder>() {
    companion object{
        private lateinit var fragmentActi : StudentListFragment
        fun setFragment(fragmentActi: StudentListFragment){
            Companion.fragmentActi = fragmentActi
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_layout_stud, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.id.text = Database.students[position].getID()
        holder.name.text = Database.students[position].getName()
        holder.phoneNo.text = Database.students[position].getPhoneNumber()
        holder.email.text = Database.students[position].getEmail()
        holder.balance.text = Database.students[position].getWalletBalance().toString()
    }

    override fun getItemCount(): Int {
        return Database.students.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var id: TextView
        var name: TextView
        var phoneNo: TextView
        var email: TextView
        var balance: TextView

        init {
            id = itemView.findViewById(R.id.card_ID)
            name = itemView.findViewById(R.id.card_name)
            phoneNo = itemView.findViewById(R.id.card_phone)
            email = itemView.findViewById(R.id.card_email)
            balance = itemView.findViewById(R.id.card_balance)

            itemView.setOnClickListener{
                val position: Int = absoluteAdapterPosition
                toDetail(fragmentActi,position)
            }
        }
    }

    fun toDetail(fragmentAct: StudentListFragment, index: Int){
        fragmentAct.toDetail(index)
    }
}