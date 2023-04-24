package com.example.ecanteenapplication.Restaurants

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.ecanteenapplication.Database
import com.example.ecanteenapplication.R
import com.example.ecanteenapplication.databinding.UpdateRestaurantFragmentBinding
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.lang.Double

class UpdateRestaurantFragment : Fragment() {

    private lateinit var binding: UpdateRestaurantFragmentBinding
    private lateinit var ImageUri: Uri

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.update_restaurant_fragment, container, false)

        val restaurantIndex = requireArguments().getInt("getRestaurantIndex")

        binding.displayID.text = Database.restaurants[restaurantIndex].getID()
        binding.enterName.setText(Database.restaurants[restaurantIndex].getName())
        binding.enterOwner.setText(Database.restaurants[restaurantIndex].getOwnerName())
        binding.enterEmail.setText(Database.restaurants[restaurantIndex].getEmail())
        binding.enterPhone.setText(Database.restaurants[restaurantIndex].getPhoneNumber())
        binding.displayBalance.text = Database.restaurants[restaurantIndex].getWalletBalance().toString()
        binding.enterPass.setText(Database.restaurants[restaurantIndex].getPassword())
        binding.enterConfirmPass.setText(Database.restaurants[restaurantIndex].getConfirmPass())

        val resID = binding.displayID.text
        val storageRef = FirebaseStorage.getInstance().reference.child("res/$resID.jpg")
        val localfile = File.createTempFile("tempImage","jpeg")
        storageRef.getFile(localfile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
            binding.getRestaurantLogo.setImageBitmap(bitmap)
        }

        binding.editImage.setOnClickListener {
            val intent = Intent()
            intent.type = "image/"
            intent.action = Intent.ACTION_GET_CONTENT

            startActivityForResult(intent,100)
        }

        binding.updateButton.setOnClickListener {
            val resID = binding.displayID.text.toString()
            val resName = binding.enterName.text.toString()
            val resOwner = binding.enterOwner.text.toString()
            val resEmail = binding.enterEmail.text.toString()
            val resPhone = binding.enterPhone.text.toString()
            val resBalance = Double.parseDouble(binding.displayBalance.text.toString())
            val resPass = binding.enterPass.text.toString()
            val resConfirmPass = binding.enterConfirmPass.text.toString()

            val fileName = "$resID.jpg"
            val storageReference = FirebaseStorage.getInstance().getReference("res/$fileName")

            storageReference.putFile(ImageUri)
                .addOnSuccessListener {
                    binding.getRestaurantLogo.setImageURI(null)
                }
                .addOnFailureListener{
                    Toast.makeText(context,"Failed to update image",Toast.LENGTH_LONG).show()
                }

            if(resName == ""){
                Toast.makeText(context,"Please Enter Restaurant Name.", Toast.LENGTH_SHORT).show()
                binding.enterName.requestFocus()
            }else if(resOwner == ""){
                Toast.makeText(context,"Please Enter Owner Name.", Toast.LENGTH_SHORT).show()
                binding.enterOwner.requestFocus()
            }else if(resEmail == ""){
                Toast.makeText(context,"Please Enter Owner Email.", Toast.LENGTH_SHORT).show()
                binding.enterEmail.requestFocus()
            }else if(resPhone == ""){
                Toast.makeText(context,"Please Enter Owner Phone Number.", Toast.LENGTH_SHORT).show()
                binding.enterPhone.requestFocus()
            }else if(resPass == ""){
                Toast.makeText(context,"Please Enter Password.", Toast.LENGTH_SHORT).show()
                binding.enterPass.requestFocus()
            }else if(resConfirmPass == ""){
                Toast.makeText(context,"Please Enter Confirm Password.", Toast.LENGTH_SHORT).show()
                binding.enterConfirmPass.requestFocus()
            }else if(resPass != resConfirmPass){
                Toast.makeText(context,"Confirm password and password must be same.", Toast.LENGTH_SHORT).show()
                binding.enterConfirmPass.requestFocus()
            }else{
                Database.updateRestaurant(resID,resName,resOwner,resEmail,resPhone,resBalance,resPass,resConfirmPass)
                Toast.makeText(context,"Update Successful!", Toast.LENGTH_LONG).show()
                it.findNavController().popBackStack()
            }
        }

        binding.BackButton.setOnClickListener {
            clearText()
            Toast.makeText(context,"Update Cancelled!", Toast.LENGTH_SHORT).show()
            it.findNavController().popBackStack()
        }

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 100 && resultCode == AppCompatActivity.RESULT_OK){
            ImageUri = data?.data!!
            binding.getRestaurantLogo.setImageURI(ImageUri)
        }
    }

    fun clearText(){
        binding.enterName.setText("")
        binding.enterOwner.setText("")
        binding.enterEmail.setText("")
        binding.enterPhone.setText("")
        binding.enterPass.setText("")
        binding.enterConfirmPass.setText("")
    }

}