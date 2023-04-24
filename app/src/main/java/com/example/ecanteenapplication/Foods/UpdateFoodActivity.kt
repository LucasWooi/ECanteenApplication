package com.example.ecanteenapplication.Foods

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.ecanteenapplication.Database
import com.example.ecanteenapplication.R
import com.example.ecanteenapplication.databinding.ActivityFoodDetailsBinding
import com.example.ecanteenapplication.databinding.ActivityFoodListBinding
import com.example.ecanteenapplication.databinding.ActivityUpdateFoodBinding
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.lang.Double

class UpdateFoodActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateFoodBinding
    private lateinit var ImageUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_food)
        binding = ActivityUpdateFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val getFoodIndex = intent.getIntExtra("getFoodIndex",0)

        binding.displayID.text = Database.foods[getFoodIndex].getID()
        binding.enterName.setText(Database.foods[getFoodIndex].getName())
        binding.enterPrice.setText(Database.foods[getFoodIndex].getPrice().toString())
        binding.displayRes.text = Database.foods[getFoodIndex].getResID()
        val foodID = binding.displayID.text.toString()
        val storageRef = FirebaseStorage.getInstance().reference.child("Food/$foodID.jpg")
        val localfile = File.createTempFile("tempImage","jpg")
        storageRef.getFile(localfile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
            binding.getFoodImage.setImageBitmap(bitmap)
        }.addOnFailureListener{
            binding.getFoodImage.setImageResource(R.drawable.white_background)
        }

        binding.editImage.setOnClickListener {
            val intent = Intent()
            intent.type = "image/"
            intent.action = Intent.ACTION_GET_CONTENT

            startActivityForResult(intent,100)
        }

        binding.updateButton.setOnClickListener {
            val foodID = binding.displayID.text.toString()
            val foodName = binding.enterName.text.toString()
            val foodPrice = Double.parseDouble(binding.enterPrice.text.toString())
            val resID = binding.displayRes.text.toString()

            val fileName = "$foodID.jpg"
            val storageReference = FirebaseStorage.getInstance().getReference("Food/$fileName")

            storageReference.putFile(ImageUri)
                .addOnSuccessListener {
                    binding.getFoodImage.setImageURI(null)
                }
                .addOnFailureListener{
                    Toast.makeText(this,"Failed to update image",Toast.LENGTH_LONG).show()
                }

            if(foodName == ""){
                Toast.makeText(this,"Please Enter Food Name.", Toast.LENGTH_SHORT).show()
                binding.enterName.requestFocus()
            }else if(binding.enterPrice.text.toString() == ""){
                Toast.makeText(this,"Please Enter Food Price.", Toast.LENGTH_SHORT).show()
                binding.enterPrice.requestFocus()
            }else{
                Database.updateFood(foodID,foodName,foodPrice,resID)
                Toast.makeText(this,"Update Successful!", Toast.LENGTH_LONG).show()
                val intent = Intent(this,FoodListActivity::class.java)
                intent.putExtra("getID",resID)
                startActivity(intent)
            }
        }
        binding.BackButton.setOnClickListener {
            clearText()
            Toast.makeText(this,"Update Cancelled!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this,FoodListActivity::class.java)
            intent.putExtra("getID",binding.displayRes.text.toString())
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