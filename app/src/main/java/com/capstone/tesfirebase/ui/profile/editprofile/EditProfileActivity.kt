package com.capstone.tesfirebase.ui.profile.editprofile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.capstone.tesfirebase.databinding.ActivityEditProfileBinding
import com.capstone.tesfirebase.ui.login.LoginActivity
import com.capstone.tesfirebase.ui.profile.ProfileActivity
import com.capstone.tesfirebase.ui.profile.changeemail.ChangeEmailActivity
import com.capstone.tesfirebase.ui.profile.changenumber.ChangeNumberActivity
import com.capstone.tesfirebase.ui.profile.changepassword.ChangePasswordActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase

class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        auth = Firebase.auth
        val user = auth.currentUser

        binding.apply {
            val initialName = user?.displayName

            inputName.setText(initialName)

            btnBack.setOnClickListener {
                onBackPressed()
            }
            btnSave.setOnClickListener {
                val newName = inputName.text.toString().trim()
                val isNameChanged = newName != initialName

                if (isNameChanged) {
                    btnSave.visibility = View.GONE
                    progressBar.visibility = View.VISIBLE
                    val profileUpdates = userProfileChangeRequest {
                        displayName = newName
                    }
                    user!!.updateProfile(profileUpdates)
                        .addOnCompleteListener { taskProfile ->
                            if (taskProfile.isSuccessful) {
                                Toast.makeText(
                                    baseContext,
                                    "Data berhasil disimpan",
                                    Toast.LENGTH_SHORT
                                ).show()
                                btnSave.visibility = View.VISIBLE
                                progressBar.visibility = View.GONE
                                val intent = Intent(this@EditProfileActivity, ProfileActivity::class.java)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(
                                    baseContext,
                                    "Data gagal disimpan.",
                                    Toast.LENGTH_SHORT
                                ).show()
                                Log.d("Update Profile", "updateProfileFailed: ${taskProfile.exception}")
                                btnSave.visibility = View.VISIBLE
                                progressBar.visibility = View.GONE
                            }
                        }
                } else {
                    Toast.makeText(
                        baseContext,
                        "Error: Mohon ubah data terlebih dahulu",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            emailLayout.setOnClickListener {
                val intent = Intent(this@EditProfileActivity, ChangeEmailActivity::class.java)
                startActivity(intent)
            }
            numberLayout.setOnClickListener {
                val intent = Intent(this@EditProfileActivity, ChangeNumberActivity::class.java)
                startActivity(intent)
            }
            passwordLayout.setOnClickListener {
                val intent = Intent(this@EditProfileActivity, ChangePasswordActivity::class.java)
                startActivity(intent)
            }
        }
    }
}