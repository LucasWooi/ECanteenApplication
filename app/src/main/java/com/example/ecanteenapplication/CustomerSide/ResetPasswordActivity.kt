package com.example.ecanteenapplication.CustomerSide

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.ecanteenapplication.Database
import com.example.ecanteenapplication.R
import com.example.ecanteenapplication.StudentLoginActivity
import com.example.ecanteenapplication.Students
import com.example.ecanteenapplication.databinding.ActivityResetPasswordBinding
import java.util.*

class ResetPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResetPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val n = 8
        val newPassword = getRandPassword(n)

        binding.resetButton.setOnClickListener {
            if (binding.enterID.text.toString() == "") {
                Toast.makeText(this,"Please Enter Student ID.", Toast.LENGTH_SHORT).show()
                binding.enterID.requestFocus()
            } else if (binding.enterEmail.text.toString() == "") {
                Toast.makeText(this,"Please Enter Register Email.", Toast.LENGTH_SHORT).show()
                binding.enterEmail.requestFocus()
            } else {
                for (student in Database.students) {
                    if (student.getID() == binding.enterID.text.toString()) {
                        if(student.getEmail() == binding.enterEmail.text.toString()) {
                            val updateStudentData = mapOf(
                                "Password" to newPassword,
                                "ConfirmPass" to newPassword
                            )

                            Database.db.collection("Student").document(student.getID())
                                .update(updateStudentData)
                                .addOnSuccessListener {
                                    Log.d(ContentValues.TAG, "DocumentSnapshot reset successfully!")
                                }
                                .addOnFailureListener {
                                    Log.d(ContentValues.TAG, "Error update data", it)
                                }

                            binding.displayNewPass.text =
                                "Here is your new password: " + newPassword

                        } else {
                            Toast.makeText(
                                this,
                                "Student ID & Email is not matched. Please enter again.",
                                Toast.LENGTH_LONG
                            ).show()
                            binding.enterID.requestFocus()
                        }
                    }
                }
            }
        }

        binding.cancelButton.setOnClickListener {
            val intent = Intent(this, StudentLoginActivity::class.java)
            startActivity(intent)
        }
    }

    fun getRandPassword(n: Int): String
    {
        val characterSet = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"

        val random = Random(System.nanoTime())
        val password = StringBuilder()

        for (i in 0 until n)
        {
            val rIndex = random.nextInt(characterSet.length)
            password.append(characterSet[rIndex])
        }

        return password.toString()
    }
}