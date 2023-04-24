package com.example.ecanteenapplication.CustomerSide

import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import com.example.ecanteenapplication.Database
import com.example.ecanteenapplication.R
import com.example.ecanteenapplication.databinding.ActivityUpdateFromStudentBinding
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.lang.Double

class UpdateFromStudentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateFromStudentBinding
    private lateinit var ImageUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_from_student)
        binding = ActivityUpdateFromStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val studID = intent.getStringExtra("getID")
        var position = 0
        var getPosition = 0

        for(studentInfo in Database.students){
            position++
            if(studID == studentInfo.getID()){
                val storageRef = FirebaseStorage.getInstance().reference.child("Student/$studID.jpg")
                val localfile = File.createTempFile("tempImage","jpeg")
                storageRef.getFile(localfile).addOnSuccessListener {
                    val bitmap = BitmapFactory.decodeFile(localfile.absolutePath)
                    binding.imageView.setImageBitmap(bitmap)
                }
                binding.displayID.text = studentInfo.getID()
                binding.EditName.setText(studentInfo.getName())
                binding.EditEmail.setText(studentInfo.getEmail())
                binding.EditPhone.setText(studentInfo.getPhoneNumber())
                binding.displayBalance.text = studentInfo.getWalletBalance().toString()
                binding.EditPass.setText(studentInfo.getPassword())
                binding.EditConfirmPass.setText(studentInfo.getConfirmPass())
                getPosition = position - 1
            }
        }

        Log.e("Check Position",getPosition.toString())

        binding.imageButton5.isEnabled = false

        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
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
            val studID = binding.displayID.text.toString()
            val studName = binding.EditName.text.toString()
            val studEmail = binding.EditEmail.text.toString()
            val studPhone = binding.EditPhone.text.toString()
            val studBalance = Double.parseDouble(binding.displayBalance.text.toString())
            val studPass = binding.EditPass.text.toString()
            val studConfirmPass = binding.EditConfirmPass.text.toString()
            val fileName = "$studID.jpg"
            val storageReference = FirebaseStorage.getInstance().getReference("Student/$fileName")

            storageReference.putFile(ImageUri)
                .addOnSuccessListener {
                    binding.imageView.setImageURI(null)
                }
                .addOnFailureListener{
                    Toast.makeText(this,"Failed to update image",Toast.LENGTH_LONG).show()
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
                Database.updateStudent(studID,studName,studEmail,studPhone,studBalance,studPass,studConfirmPass,getPosition)
                Toast.makeText(this,"Update Successful!", Toast.LENGTH_LONG).show()
                val intent = Intent(this, ProfilePageActivity::class.java)
                intent.putExtra("getID",studID)
                startActivity(intent)
            }
        }

        binding.cancelButton.setOnClickListener {
            clearText()
            Toast.makeText(this,"Update Cancelled.", Toast.LENGTH_LONG).show()
            val intent = Intent(this, ProfilePageActivity::class.java)
            intent.putExtra("getID",studID)
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