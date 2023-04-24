package com.example.ecanteenapplication.Restaurants

import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.ecanteenapplication.Database
import com.example.ecanteenapplication.R
import com.example.ecanteenapplication.Student.StudentDetailsFragmentArgs
import com.example.ecanteenapplication.databinding.RestaurantDetailsFragmentBinding
import com.example.ecanteenapplication.databinding.StudentDetailsFragmentBinding
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class RestaurantDetailsFragment : Fragment() {
    private lateinit var binding: RestaurantDetailsFragmentBinding
    private val args: RestaurantDetailsFragmentArgs by navArgs<RestaurantDetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.restaurant_details_fragment,container,false)

        val restaurantPosition = args.resIndex

        binding.displayID.text = Database.restaurants[restaurantPosition].getID()
        binding.displayName.text = Database.restaurants[restaurantPosition].getName()
        binding.displayOwner.text = Database.restaurants[restaurantPosition].getOwnerName()
        binding.displayEmail.text = Database.restaurants[restaurantPosition].getEmail()
        binding.displayPhone.text = Database.restaurants[restaurantPosition].getPhoneNumber()
        binding.displayBalance.text = Database.restaurants[restaurantPosition].getWalletBalance().toString()

        val resID = binding.displayID.text
        val storageRef = FirebaseStorage.getInstance().reference.child("res/$resID.jpg")
        val localfile = File.createTempFile("tempImage","jpeg")
        storageRef.getFile(localfile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
            binding.getResLogo.setImageBitmap(bitmap)
        }

        binding.confirmButton.setOnClickListener {
            findNavController().navigate(R.id.action_restaurantDetailsFragment_to_restaurantListFragment2)
        }

        binding.updateButton.setOnClickListener {
            findNavController().navigate(R.id.action_restaurantDetailsFragment_to_updateRestaurantFragment,Bundle().apply {
                putInt("getRestaurantIndex",restaurantPosition)
            })
        }

        binding.deleteButton.setOnClickListener {
            val restaurantDelete = binding.displayID.text.toString()
            Database.deleteRestaurant(restaurantDelete)
            Toast.makeText(context,"Restaurant deleted.", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_restaurantDetailsFragment_to_restaurantListFragment2)
        }

        return binding.root
    }
}