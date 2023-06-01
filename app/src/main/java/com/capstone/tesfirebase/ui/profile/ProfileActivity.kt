package com.capstone.tesfirebase.ui.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.capstone.tesfirebase.AboutAppActivity
import com.capstone.tesfirebase.EditProfileActivity
import com.capstone.tesfirebase.databinding.ActivityProfileBinding
import com.capstone.tesfirebase.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.apply {
            btnBack.setOnClickListener{
               onBackPressed()
            }

            btnEditProfile.setOnClickListener{
                val intent = Intent(this@ProfileActivity, EditProfileActivity::class.java )
                startActivity(intent)
            }

            btnAboutApp.setOnClickListener {
                val intent = Intent(this@ProfileActivity, AboutAppActivity::class.java )
                startActivity(intent)
            }

            btnLogOut.setOnClickListener {
                val auth = Firebase.auth

                val alertDialogBuilder = AlertDialog.Builder(this@ProfileActivity)
                alertDialogBuilder.setTitle("Log Out")
                alertDialogBuilder.setMessage("Are you sure you want to log out?")
                alertDialogBuilder.setPositiveButton("Yes") { dialog, which ->
                    auth.signOut()
                    val intent = Intent(this@ProfileActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                alertDialogBuilder.setNegativeButton("No") { dialog, which ->
                    // Do nothing or any other action you want
                }

                val alertDialog = alertDialogBuilder.create()
                alertDialog.show()
            }
        }
    }
}