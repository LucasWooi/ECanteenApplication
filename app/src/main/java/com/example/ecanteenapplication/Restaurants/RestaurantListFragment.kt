package com.example.ecanteenapplication.Restaurants

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecanteenapplication.R
import com.example.ecanteenapplication.databinding.RestaurantListFragmentBinding

class RestaurantListFragment : Fragment() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RestaurantListAdapter.ViewHolder>? = null
    private lateinit var binding: RestaurantListFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,
            R.layout.restaurant_list_fragment,container,false)

        layoutManager = LinearLayoutManager(context)

        binding.recycleViewRestaurants.layoutManager = layoutManager

        RestaurantListAdapter.setFragment(this)

        adapter = RestaurantListAdapter()
        binding.recycleViewRestaurants.adapter = adapter

        binding.addNewResBtn.setOnClickListener {
            findNavController().navigate(R.id.action_restaurantListFragment2_to_registerRestaurantFragment)
        }

        binding.backFromResButton.setOnClickListener {
            findNavController().navigate(R.id.action_restaurantListFragment2_to_adminHomeActivity3)
        }

        return binding.root
    }

    fun toDetail(index: Int){
        val action = RestaurantListFragmentDirections.actionRestaurantListFragment2ToRestaurantDetailsFragment(index)
        findNavController().navigate(action)
    }
}