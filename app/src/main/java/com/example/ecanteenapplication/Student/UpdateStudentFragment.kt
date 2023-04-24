package com.example.ecanteenapplication.Student

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
import com.example.ecanteenapplication.databinding.UpdateStudentFragmentBinding
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.lang.Double.parseDouble

class UpdateStudentFragment : Fragment() {
    private lateinit var binding: UpdateStudentFragmentBinding
    private lateinit var ImageUri: Uri

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.update_student_fragment, container, false)

        val studentIndex = requireArguments().getInt("getStudentIndex")

        binding.displayID.text = Database.students[studentIndex].getID()
        binding.enterName.setText(Database.students[studentIndex].getName())
        binding.enterEmail.setText(Database.students[studentIndex].getEmail())
        binding.enterPhone.setText(Database.students[studentIndex].getPhoneNumber())
        binding.displayBalance.text = Database.students[studentIndex].getWalletBalance().toString()
        binding.enterPass.setText(Database.students[studentIndex].getPassword())
        binding.enterConfirmPass.setText(Database.students[studentIndex].getConfirmPass())

        val studID = binding.displayID.text
        val storageRef = FirebaseStorage.getInstance().reference.child("Student/$studID.jpg")
        val localfile = File.createTempFile("tempImage","jpeg")
        storageRef.getFile(localfile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
            binding.getStudentImage.setImageBitmap(bitmap)
        }

        binding.editImage.setOnClickListener {
            val intent = Intent()
            intent.type = "image/"
            intent.action = Intent.ACTION_GET_CONTENT

            startActivityForResult(intent,100)
        }

        binding.updateButton.setOnClickListener {
            val studID = binding.displayID.text.toString()
            val studName = binding.enterName.text.toString()
            val studEmail = binding.enterEmail.text.toString()
            val studPhone = binding.enterPhone.text.toString()
            val studBalance = parseDouble(binding.displayBalance.text.toString())
            val studPass = binding.enterPass.text.toString()
            val studConfirmPass = binding.enterConfirmPass.text.toString()

            val fileName = "$studID.jpg"
            val storageReference = FirebaseStorage.getInstance().getReference("Student/$fileName")

            storageReference.putFile(ImageUri)
                .addOnSuccessListener {
                    binding.getStudentImage.setImageURI(null)
                }
                .addOnFailureListener{
                    Toast.makeText(context,"Failed to update image",Toast.LENGTH_LONG).show()
                }

            if(studName == ""){
                Toast.makeText(context,"Please Enter Student Name.", Toast.LENGTH_SHORT).show()
                binding.enterName.requestFocus()
            }else if(studEmail == ""){
                Toast.makeText(context,"Please Enter Student Email.", Toast.LENGTH_SHORT).show()
                binding.enterEmail.requestFocus()
            }else if(studPhone == ""){
                Toast.makeText(context,"Please Enter Student Phone Number.", Toast.LENGTH_SHORT).show()
                binding.enterPhone.requestFocus()
            }else if(studPass == ""){
                Toast.makeText(context,"Please Enter Password.", Toast.LENGTH_SHORT).show()
                binding.enterPass.requestFocus()
            }else if(studConfirmPass == ""){
                Toast.makeText(context,"Please Enter Confirm Password.", Toast.LENGTH_SHORT).show()
                binding.enterConfirmPass.requestFocus()
            }else if(studPass != studConfirmPass){
                Toast.makeText(context,"Confirm password and password must be same.", Toast.LENGTH_SHORT).show()
                binding.enterConfirmPass.requestFocus()
            }else {
                Database.updateStudent(
                    studID,
                    studName,
                    studEmail,
                    studPhone,
                    studBalance,
                    studPass,
                    studConfirmPass,
                    studentIndex
                )
                Toast.makeText(context, "Update Successful!", Toast.LENGTH_LONG).show()
                it.findNavController().popBackStack()
            }
        }

        binding.BackButton.setOnClickListener {
            clearText()
            Toast.makeText(context,"Update Cancelled!",Toast.LENGTH_SHORT).show()
            it.findNavController().popBackStack()
        }

        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 100 && resultCode == AppCompatActivity.RESULT_OK){
            ImageUri = data?.data!!
            binding.getStudentImage.setImageURI(ImageUri)
        }
    }

    fun clearText(){
        binding.enterName.setText("")
        binding.enterEmail.setText("")
        binding.enterPhone.setText("")
        binding.enterPass.setText("")
        binding.enterConfirmPass.setText("")
    }
}