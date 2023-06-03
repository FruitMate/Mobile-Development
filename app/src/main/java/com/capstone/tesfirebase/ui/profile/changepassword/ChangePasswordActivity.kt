package com.capstone.tesfirebase.ui.profile.changepassword

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.capstone.tesfirebase.databinding.ActivityChangePasswordBinding
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ChangePasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangePasswordBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        auth = Firebase.auth
        val user = auth.currentUser

        binding.apply {
            btnBack.setOnClickListener {
                onBackPressed()
            }
            btnSave.setOnClickListener {
                val oldPassword = binding.inputOldPassword.text.toString().trim()
                val newPassword = binding.inputNewPassword.text.toString().trim()
                val confirmNewPassword = binding.confirmNewPassword.text.toString().trim()

                if (oldPassword.isEmpty() || newPassword.isEmpty() || confirmNewPassword.isEmpty()) {
                    Toast.makeText(
                        baseContext,
                        "Mohon isi ketiga data.",
                        Toast.LENGTH_LONG).show()
                } else if (oldPassword.length < 8 || newPassword.length < 8 || confirmNewPassword.length < 8) {
                    Toast.makeText(
                        baseContext,
                        "Password kurang dari 8 karakter.",
                        Toast.LENGTH_LONG).show()
                } else if (oldPassword == newPassword) {
                    Toast.makeText(
                        baseContext,
                        "Password baru harus berbeda.",
                        Toast.LENGTH_LONG).show()
                } else if (newPassword != confirmNewPassword) {
                    Toast.makeText(
                        baseContext,
                        "Konfirmasi password berbeda.",
                        Toast.LENGTH_LONG).show()
                } else {
                    progressBar.visibility = View.VISIBLE
                    btnSave.isEnabled = false
                    if (user != null) {
                        val credential = EmailAuthProvider.getCredential(user.email!!, oldPassword)
                        user.reauthenticate(credential)
                            .addOnCompleteListener { reauthTask ->
                                if (reauthTask.isSuccessful) {
                                    // Update the password
                                    user.updatePassword(newPassword)
                                        .addOnCompleteListener { updateTask ->
                                            if (updateTask.isSuccessful) {
                                                // Password updated successfully
                                                Toast.makeText(
                                                    baseContext,
                                                    "Password berhasil diperbarui.",
                                                    Toast.LENGTH_SHORT).show()
                                                progressBar.visibility = View.GONE
                                                btnSave.isEnabled = true
                                                finish()
                                            } else {
                                                // Display error message if password update fails
                                                Log.d("Update password", "updatePasswordFail:${updateTask.exception}")
                                                Toast.makeText(
                                                    baseContext,
                                                    "Gagal memperbarui password.",
                                                    Toast.LENGTH_SHORT).show()
                                                progressBar.visibility = View.GONE
                                                btnSave.isEnabled = true
                                            }
                                        }
                                } else {
                                    // Display error message if re-authentication fails
                                    Log.d("Update password", "updatePasswordFail:${reauthTask.exception}")
                                    Toast.makeText(
                                        baseContext,
                                        "Password lama salah.",
                                        Toast.LENGTH_LONG).show()
                                    progressBar.visibility = View.GONE
                                    btnSave.isEnabled = true
                                }
                            }
                    }
                }
            }
        }
    }
}