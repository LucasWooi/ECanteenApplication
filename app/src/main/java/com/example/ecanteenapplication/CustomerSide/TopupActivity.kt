package com.example.ecanteenapplication.CustomerSide

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.ecanteenapplication.Database
import com.example.ecanteenapplication.R
import com.example.ecanteenapplication.databinding.ActivityTopupBinding
import java.lang.Double

class TopupActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTopupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_topup)
        binding = ActivityTopupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra("getID")

        binding.topupButton.setOnClickListener {
            val password = binding.enterPass.text.toString()
            val amount = Double.parseDouble(binding.enterAmount.text.toString())

            if (binding.enterAmount.text.toString() == "") {
                Toast.makeText(this, "Please Enter Amount.", Toast.LENGTH_SHORT).show()
                binding.enterAmount.requestFocus()
            } else if (password == "") {
                Toast.makeText(this, "Please Enter Password.", Toast.LENGTH_SHORT).show()
                binding.enterPass.requestFocus()
            } else {
                for (stud in Database.students) {
                    if (id == stud.getID()) {
                        if(password != stud.getPassword()){
                            Toast.makeText(this, "Password Incorrect.", Toast.LENGTH_SHORT).show()
                            binding.enterPass.requestFocus()
                        }else{
                            val oldBalance = stud.getWalletBalance()
                            val newBalance = amount + oldBalance
                            val updateStudentData = mapOf(
                                "WalletBalance" to newBalance
                            )

                            Database.db.collection("Student").document(id)
                                .update(updateStudentData)
                                .addOnSuccessListener {
                                    Log.d(ContentValues.TAG, "DocumentSnapshot updated successfully!")
                                }
                                .addOnFailureListener {
                                    Log.d(ContentValues.TAG,"Error update data",it)
                                }

                            Toast.makeText(this,"Top up Successful!", Toast.LENGTH_LONG).show()
                            val intent = Intent(this, ProfilePageActivity::class.java)
                            intent.putExtra("getID",id)
                            startActivity(intent)
                        }
                    }
                }
            }
        }
        binding.cancelButton.setOnClickListener {
            Toast.makeText(this,"Top up cancelled!", Toast.LENGTH_LONG).show()
            val intent = Intent(this, ProfilePageActivity::class.java)
            intent.putExtra("getID",id)
            startActivity(intent)
        }
    }
}