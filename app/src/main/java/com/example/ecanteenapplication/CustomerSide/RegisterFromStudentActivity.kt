package com.example.ecanteenapplication.CustomerSide

import android.Manifest.permission.CAMERA
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.ecanteenapplication.Database
import com.example.ecanteenapplication.R
import com.example.ecanteenapplication.StudentLoginActivity
import com.example.ecanteenapplication.databinding.ActivityRegisterFromStudentBinding
import com.google.firebase.storage.FirebaseStorage
import java.lang.Double

class RegisterFromStudentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterFromStudentBinding
    private lateinit var ImageUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_from_student)
        binding = ActivityRegisterFromStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageButton5.isEnabled = false

        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA),111)
        }else{
            binding.imageButton5.isEnabled = true
        }

        binding.imageButton5.setOnClickListener{
            val values = ContentValues()
            values.put(MediaStore.Images.Media.TITLE, "New Avatar")
            values.put(MediaStore.Images.Media.DESCRIPTION,"From Camera")
            ImageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values)!!
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT,ImageUri)
            startActivityForResult(intent, 101)
        }

        binding.editImage.setOnClickListener {
            val intent = Intent()
            intent.type = "image/"
            intent.action = Intent.ACTION_GET_CONTENT

            startActivityForResult(intent,100)
        }

        binding.confirmButton.setOnClickListener {
            val studentCount: Int = Database.students.size
            val newID: Int = studentCount+1

            val studID = "ST000" + newID
            val studName = binding.EditName.text.toString()
            val studEmail = binding.EditEmail.text.toString()
            val studPhone = binding.EditPhone.text.toString()
            val studBalance = 0.0
            val studPass = binding.EditPass.text.toString()
            val studConfirmPass = binding.EditConfirmPass.text.toString()

            val fileName = "$studID.jpg"
            val storageReference = FirebaseStorage.getInstance().getReference("Student/$fileName")

            storageReference.putFile(ImageUri)
                .addOnSuccessListener {
                    binding.imageView.setImageURI(null)
                }
                .addOnFailureListener{
                    Toast.makeText(this,"Failed to upload image",Toast.LENGTH_LONG).show()
                }

            if(studName == ""){
                Toast.makeText(this,"Please Enter Student Name.", Toast.LENGTH_SHORT).show()
                binding.EditName.requestFocus()
            }else if(studEmail == ""){
                Toast.makeText(this,"Please Enter Student Email.", Toast.LENGTH_SHORT).show()
                binding.EditEmail.requestFocus()
            }else if(studPhone == ""){
                Toast.makeText(this,"Please Enter Student Phone Number.", Toast.LENGTH_SHORT).show()
                binding.EditPhone.requestFocus()
            }else if(studPass == ""){
                Toast.makeText(this,"Please Enter Password.", Toast.LENGTH_SHORT).show()
                binding.EditPass.requestFocus()
            }else if(studConfirmPass == ""){
                Toast.makeText(this,"Please Enter Confirm Password.", Toast.LENGTH_SHORT).show()
                binding.EditConfirmPass.requestFocus()
            }else if(studPass != studConfirmPass){
                Toast.makeText(this,"Confirm password and password must be same.", Toast.LENGTH_SHORT).show()
                binding.EditConfirmPass.requestFocus()
            }else{
                Database.addStudent(studID,studName,studEmail,studPhone,studBalance,studPass,studConfirmPass)
                Toast.makeText(this,"Register Successful!", Toast.LENGTH_LONG).show()
                val intent = Intent(this, StudentLoginActivity::class.java)
                startActivity(intent)
            }
        }

        binding.cancelButton.setOnClickListener {
            clearText()
            Toast.makeText(this,"Update Cancelled.", Toast.LENGTH_LONG).show()
            val intent = Intent(this, StudentLoginActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 100 && resultCode == AppCompatActivity.RESULT_OK){
            ImageUri = data?.data!!
            binding.imageView.setImageURI(ImageUri)
        }else if(requestCode == 101 && resultCode == AppCompatActivity.RESULT_OK){
            binding.imageView.setImageURI(ImageUri)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 111 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            binding.imageButton5.isEnabled = true
        }
    }

    fun clearText(){
        binding.EditName.setText("")
        binding.EditEmail.setText("")
        binding.EditPhone.setText("")
        binding.EditPass.setText("")
        binding.EditConfirmPass.setText("")
    }
}