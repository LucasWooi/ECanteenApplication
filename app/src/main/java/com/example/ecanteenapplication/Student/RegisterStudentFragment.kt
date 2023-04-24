package com.example.ecanteenapplication.Student

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
import com.example.ecanteenapplication.databinding.RegisterStudentFragmentBinding
import com.google.firebase.storage.FirebaseStorage

class RegisterStudentFragment : Fragment() {
    private lateinit var binding: RegisterStudentFragmentBinding
    private lateinit var ImageUri: Uri

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.register_student_fragment, container, false)

        binding.editImage.setOnClickListener {
            val intent = Intent()
            intent.type = "image/"
            intent.action = Intent.ACTION_GET_CONTENT

            startActivityForResult(intent,100)
        }

        binding.registerButton.setOnClickListener {
            val studentCount: Int = Database.students.size
            val newID: Int = studentCount+1

            val studID = "ST000" + newID
            val studName = binding.enterName.text.toString()
            val studEmail = binding.enterEmail.text.toString()
            val studPhone = binding.enterPhone.text.toString()
            val studBalance = 0.0
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
                Toast.makeText(context,"Please Enter Student Name.",Toast.LENGTH_SHORT).show()
                binding.enterName.requestFocus()
            }else if(studEmail == ""){
                Toast.makeText(context,"Please Enter Student Email.",Toast.LENGTH_SHORT).show()
                binding.enterEmail.requestFocus()
            }else if(studPhone == ""){
                Toast.makeText(context,"Please Enter Student Phone Number.",Toast.LENGTH_SHORT).show()
                binding.enterPhone.requestFocus()
            }else if(studPass == ""){
                Toast.makeText(context,"Please Enter Password.",Toast.LENGTH_SHORT).show()
                binding.enterPass.requestFocus()
            }else if(studConfirmPass == ""){
                Toast.makeText(context,"Please Enter Confirm Password.",Toast.LENGTH_SHORT).show()
                binding.enterConfirmPass.requestFocus()
            }else if(studPass != studConfirmPass){
                Toast.makeText(context,"Confirm password and password must be same.",Toast.LENGTH_SHORT).show()
                binding.enterConfirmPass.requestFocus()
            }else{
                Database.addStudent(studID,studName,studEmail,studPhone,studBalance,studPass,studConfirmPass)
                Toast.makeText(context,"Register Successful!",Toast.LENGTH_LONG).show()
                it.findNavController().navigate(R.id.action_registerStudentFragment_to_studentListFragment)
            }
        }

        binding.BackButton.setOnClickListener {
            clearText()
            Toast.makeText(context,"Register Cancelled!",Toast.LENGTH_SHORT).show()
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