package com.example.ecanteenapplication.Student

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.ecanteenapplication.Database
import com.example.ecanteenapplication.R
import com.example.ecanteenapplication.databinding.StudentDetailsFragmentBinding
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class StudentDetailsFragment : Fragment() {

    private lateinit var binding: StudentDetailsFragmentBinding
    private val args: StudentDetailsFragmentArgs by navArgs<StudentDetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.student_details_fragment,container,false)

        val studentPosition = args.studIndex

        binding.displayID.text = Database.students[studentPosition].getID()
        binding.displayName.text = Database.students[studentPosition].getName()
        binding.displayEmail.text = Database.students[studentPosition].getEmail()
        binding.displayPhone.text = Database.students[studentPosition].getPhoneNumber()
        binding.displayBalance.text = "RM " + Database.students[studentPosition].getWalletBalance().toString()

        val studID = binding.displayID.text
        val storageRef = FirebaseStorage.getInstance().reference.child("Student/$studID.jpg")
        val localfile = File.createTempFile("tempImage","jpeg")
        storageRef.getFile(localfile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
            binding.getAvatar.setImageBitmap(bitmap)
        }

        binding.confirmButton.setOnClickListener {
            findNavController().navigate(R.id.action_studentDetailsFragment_to_studentListFragment)
        }

        binding.updateButton.setOnClickListener {
            findNavController().navigate(R.id.action_studentDetailsFragment_to_updateStudentFragment,Bundle().apply {
                putInt("getStudentIndex",studentPosition)
            })
        }

        binding.deleteButton.setOnClickListener {
            val studentDetele = binding.displayID.text.toString()
            Database.deleteStudent(studentDetele)
            Toast.makeText(context,"Student deleted.",Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_studentDetailsFragment_to_studentListFragment)
        }

        return binding.root
    }
}