package com.capstone.tesfirebase.ui.profile.changenumber

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.capstone.tesfirebase.databinding.ActivityChangeNumberBinding
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ChangeNumberActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangeNumberBinding
    private lateinit var auth: FirebaseAuth
    private var password: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeNumberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        auth = Firebase.auth
        val user = auth.currentUser

        binding.apply {
            btnBack.setOnClickListener {
                onBackPressed()
            }
            if (user?.phoneNumber != null) {
                inputNumber.setText(user.phoneNumber)
            } else {
                inputNumber.setText("")
            }
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
                                numberLayout.visibility = View.VISIBLE
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
                val enteredNumber = inputNumber.text.toString().trim()
                if (enteredNumber.isEmpty()) {
                    Toast.makeText(
                        baseContext,
                        "Mohon masukkan nomor telepon.",
                        Toast.LENGTH_LONG
                    ).show()
                } else if (enteredNumber == user?.phoneNumber) {
                    Toast.makeText(
                        baseContext,
                        "Nomor baru harus berbeda.",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    progressBar.visibility = View.VISIBLE
                    btnSave.isEnabled = false
                    val credential = EmailAuthProvider.getCredential(user?.email!!, password)
                    user.reauthenticate(credential)
                        .addOnCompleteListener { reauthTask ->
                            if (reauthTask.isSuccessful) {

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