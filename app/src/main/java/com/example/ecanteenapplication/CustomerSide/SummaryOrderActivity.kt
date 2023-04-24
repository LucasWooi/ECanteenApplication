package com.example.ecanteenapplication.CustomerSide


import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.ecanteenapplication.Database
import com.example.ecanteenapplication.R
import com.example.ecanteenapplication.databinding.ActivitySummaryOrderBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.lang.Double

class SummaryOrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySummaryOrderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_summary_order)
        binding = ActivitySummaryOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val getFID = intent.getStringExtra("getFID")
        val getSID = intent.getStringExtra("getSID")
        val getRID = intent.getStringExtra("getRID")
        val getQty = intent.getIntExtra("getQty",0)
        var amount = 0.0
        var oldBalance = 0.0
        var newBalance = 0.0
        var displayBalance = ""
        var resOldBalance = 0.0
        var resNewBalance = 0.0
        var fName = ""
        var fPrice = 0.0
        var OQty = 0

        for(food in Database.foods){
            if(getFID == food.getID()){
                binding.showName.text = "   " + food.getName()
                binding.showPrice.text = "   RM " + food.getPrice().toString()
                binding.showQty.text = "    " + getQty.toString()
                binding.showPay.text = "    RM " + (food.getPrice() * getQty).toString()
                fName = food.getName()
                fPrice = food.getPrice()
                OQty = getQty
                amount = food.getPrice() * getQty
            }
        }

        binding.payButton.setOnClickListener {
            for(student in Database.students){
                if(getSID == student.getID()){
                    oldBalance = student.getWalletBalance()
                    if(oldBalance >= amount) {
                        newBalance = oldBalance - amount

                        displayBalance = "RM " + newBalance
                        val updateStudentData = mapOf(
                            "WalletBalance" to newBalance
                        )

                        Database.db.collection("Student").document(getSID)
                            .update(updateStudentData)
                            .addOnSuccessListener {
                                Log.d(ContentValues.TAG, "DocumentSnapshot updated successfully!")
                            }
                            .addOnFailureListener {
                                Log.d(ContentValues.TAG, "Error update data", it)
                            }

                        for(res in Database.restaurants){
                            if(getRID == res.getID()){
                                resOldBalance = res.getWalletBalance()
                                resNewBalance = resOldBalance + amount

                                val updateResData = mapOf(
                                    "WalletBalance" to resNewBalance
                                )

                                Database.db.collection("Restaurant").document(getRID)
                                    .update(updateResData)
                                    .addOnSuccessListener {
                                        Log.d(ContentValues.TAG, "DocumentSnapshot updated successfully!")
                                    }
                                    .addOnFailureListener {
                                        Log.d(ContentValues.TAG,"Error update data",it)
                                    }
                            }
                        }

                        val orderSize = Database.orders.size + 1

                        val orderID = "ORD" + orderSize
                        Database.addOrder(orderID, getFID.toString(), fName, fPrice, OQty, getRID.toString(), getSID.toString(), amount)

                        Toast.makeText(this,"Payment Successful! Your balance is $newBalance",Toast.LENGTH_LONG).show()
                        val intent = Intent(this,HomePageActivity::class.java)
                        intent.putExtra("getID",getSID)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this, "Failed payment! Balance is not enough!",Toast.LENGTH_LONG).show()
                        val intent = Intent(this,HomePageActivity::class.java)
                        intent.putExtra("getID",getSID)
                        startActivity(intent)
                    }
                }
            }
        }
    }
}