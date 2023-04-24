package com.example.ecanteenapplication.Restaurants

import android.content.Intent
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
import com.example.ecanteenapplication.databinding.RegisterRestaurantFragmentBinding
import com.google.firebase.storage.FirebaseStorage

class RegisterRestaurantFragment : Fragment() {
    private lateinit var binding: RegisterRestaurantFragmentBinding
    private lateinit var ImageUri: Uri

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.register_restaurant_fragment, container, false)

        binding.editImage.setOnClickListener {
            val intent = Intent()
            intent.type = "image/"
            intent.action = Intent.ACTION_GET_CONTENT

            startActivityForResult(intent,100)
        }

        binding.registerButton.setOnClickListener {
            val restaurantCount: Int = Database.restaurants.size
            val newID: Int = restaurantCount+1

            val resID = "RES000" + newID
            val resName = binding.enterName.text.toString()
            val resOwner = binding.enterOwner.text.toString()
            val resEmail = binding.enterEmail.text.toString()
            val resPhone = binding.enterPhone.text.toString()
            val resBalance = 0.0
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
                binding.enterEmail.requestFocus()
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
                Database.addRestaurant(resID,resName,resOwner,resEmail,resPhone,resBalance,resPass,resConfirmPass)
                Toast.makeText(context,"Register Successful!", Toast.LENGTH_LONG).show()
                it.findNavController().navigate(R.id.action_registerRestaurantFragment_to_restaurantListFragment2)
            }
        }

        binding.BackButton.setOnClickListener {
            clearText()
            Toast.makeText(context,"Register Cancelled!", Toast.LENGTH_SHORT).show()
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