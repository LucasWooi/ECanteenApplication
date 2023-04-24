package com.example.ecanteenapplication.Foods

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.ecanteenapplication.Database
import com.example.ecanteenapplication.Food
import com.example.ecanteenapplication.R
import com.example.ecanteenapplication.databinding.ActivityAddNewFoodBinding
import com.example.ecanteenapplication.databinding.ActivityUpdateFoodBinding
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.lang.Double

class AddNewFoodActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddNewFoodBinding
    private lateinit var ImageUri: Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_food)
        binding = ActivityAddNewFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val resID = intent.getStringExtra("getLogID")


        binding.selectImage.setOnClickListener {
            val intent = Intent()
            intent.type = "image/"
            intent.action = Intent.ACTION_GET_CONTENT

            startActivityForResult(intent,100)
        }

        binding.addNewFoodBtn.setOnClickListener {
            val foodCount: Int = Database.foods.size
            val newID: Int = foodCount+1

            val foodID = "FD" + newID
            val foodName = binding.enterName.text.toString()
            val foodPrice = Double.parseDouble(binding.enterPrice.text.toString())
            val fileName = "$foodID.jpg"
            val storageReference = FirebaseStorage.getInstance().getReference("Food/$fileName")

            storageReference.putFile(ImageUri)
                .addOnSuccessListener {
                    binding.getFoodImage.setImageURI(null)
                }
                .addOnFailureListener{
                    Toast.makeText(this,"Failed to upload image",Toast.LENGTH_LONG).show()
                }

            if(foodName == ""){
                Toast.makeText(this,"Please Enter Food Name.", Toast.LENGTH_SHORT).show()
                binding.enterName.requestFocus()
            }else if(binding.enterPrice.text.toString() == ""){
                Toast.makeText(this,"Please Enter Food Price.", Toast.LENGTH_SHORT).show()
                binding.enterPrice.requestFocus()
            }else{
                Database.addFood(foodID,foodName,foodPrice,resID.toString())
                Toast.makeText(this,"Add New Food Successful!", Toast.LENGTH_LONG).show()
                val intent = Intent(this,FoodListActivity::class.java)
                intent.putExtra("getID",resID.toString())
                startActivity(intent)
            }
        }

        binding.BackButton.setOnClickListener {
            clearText()
            Toast.makeText(this,"Add Food Cancelled!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this,FoodListActivity::class.java)
            intent.putExtra("getID",resID.toString())
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 100 && resultCode == RESULT_OK){
            ImageUri = data?.data!!
            binding.getFoodImage.setImageURI(ImageUri)
        }
    }

    fun clearText(){
        binding.enterName.setText("")
        binding.enterPrice.setText("")
    }
}