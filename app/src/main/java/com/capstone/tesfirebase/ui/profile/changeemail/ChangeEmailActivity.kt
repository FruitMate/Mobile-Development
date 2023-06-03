package com.capstone.tesfirebase.ui.profile.changeemail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.capstone.tesfirebase.databinding.ActivityChangeEmailBinding
import com.capstone.tesfirebase.ui.profile.editprofile.EditProfileActivity
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ChangeEmailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangeEmailBinding
    private lateinit var auth: FirebaseAuth
    private var password: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        auth = Firebase.auth
        val user = auth.currentUser

        binding.apply {
            btnBack.setOnClickListener {
                onBackPressed()
            }
            inputEmail.setText(user?.email)
            btnReauthenticate.setOnClickListener {
                val enteredPassword = inputPassword.text.toString().trim()
                if (enteredPassword.isEmpty()) {
                    Toast.makeText(
                        baseContext,
                        "Mohon masukkan password.",
                        Toast.LENGTH_LONG
                    ).show()
                } else if (enteredPassword.length < 8) {
                    Toast.makeText(
                        baseContext,
                        "Password harus memiliki setidaknya 8 karakter.",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    progressBar.visibility = View.VISIBLE
                    btnReauthenticate.isEnabled = false
                    val credential = EmailAuthProvider.getCredential(user?.email!!, enteredPassword)
                    user.reauthenticate(credential)
                        .addOnCompleteListener { reauthTask ->
                            if (reauthTask.isSuccessful) {
                                password = enteredPassword
                                progressBar.visibility = View.GONE
                                passwordLayout.visibility = View.GONE
                                btnReauthenticate.visibility = View.GONE
                                emailLayout.visibility = View.VISIBLE
                                btnSave.visibility = View.VISIBLE
                            } else {
                                Toast.makeText(
                                    baseContext,
                                    "Autentikasi gagal, cek kembali password.",
                                    Toast.LENGTH_LONG
                                ).show()
                                progressBar.visibility = View.GONE
                                btnReauthenticate.isEnabled = true
                            }
                        }
                }
            }
            btnSave.setOnClickListener {
                val enteredEmail = inputEmail.text.toString().trim()
                if (enteredEmail.isEmpty()) {
                    Toast.makeText(
                        baseContext,
                        "Mohon masukkan email.",
                        Toast.LENGTH_LONG
                    ).show()
                }  else if (enteredEmail == user?.email) {
                    Toast.makeText(
                        baseContext,
                        "Email baru harus berbeda.",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    progressBar.visibility = View.VISIBLE
                    btnSave.isEnabled = false
                    if (user != null) {
                        val credential = EmailAuthProvider.getCredential(user.email!!, password)
                        user.reauthenticate(credential)
                            .addOnCompleteListener { reauthTask ->
                                if (reauthTask.isSuccessful) {
                                    user.updateEmail(enteredEmail)
                                        .addOnCompleteListener { updateTask ->
                                            if (updateTask.isSuccessful) {
                                                Toast.makeText(
                                                    baseContext,
                                                    "Email berhasil diperbarui.",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                                progressBar.visibility = View.GONE
                                                btnSave.isEnabled = true
                                                val intent = Intent(this@ChangeEmailActivity, EditProfileActivity::class.java)
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                                                startActivity(intent)
                                                finish()
                                            } else {
                                                Log.d("Update email", "updateEmailFail:${updateTask.exception}")
                                                Toast.makeText(
                                                    baseContext,
                                                    "Gagal memperbarui email.",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                                progressBar.visibility = View.GONE
                                                btnSave.isEnabled = true
                                            }
                                        }
                                } else {
                                    Log.d("Reauthentication Failed", "reauthenticationFail:${reauthTask.exception}")
                                    Toast.makeText(
                                        baseContext,
                                        "Reautentikasi gagal.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                    }
                }
            }
        }
    }
}

