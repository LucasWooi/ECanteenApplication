package com.example.ecanteenapplication.Student

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecanteenapplication.AdminLoginActivity
import com.example.ecanteenapplication.R
import com.example.ecanteenapplication.databinding.StudentListFragmentBinding

class StudentListFragment : Fragment() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<StudentListAdapter.ViewHolder>? = null
    private lateinit var binding: StudentListFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.student_list_fragment,container,false)

        layoutManager = LinearLayoutManager(context)

        binding.recycleViewStudents.layoutManager = layoutManager

        StudentListAdapter.setFragment(this)

        adapter = StudentListAdapter()
        binding.recycleViewStudents.adapter = adapter

        binding.addNewStudBtn.setOnClickListener {
            findNavController().navigate(R.id.action_studentListFragment_to_registerStudentFragment)
        }

        binding.backFromStudButton.setOnClickListener {
            findNavController().navigate(R.id.action_studentListFragment_to_adminHomeActivity)
        }

        return binding.root
    }

    fun toDetail(index: Int){
        val action = StudentListFragmentDirections.actionStudentListFragmentToStudentDetailsFragment(index)
        findNavController().navigate(action)
    }
}