package com.capstone.tesfirebase.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.capstone.tesfirebase.databinding.ActivityRegisterBinding
import com.capstone.tesfirebase.ui.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        auth = Firebase.auth

        binding.tvLogin.setOnClickListener {
            onBackPressed()
        }

        binding.btnRegister.setOnClickListener {
            val name = binding.inputName.text.toString()
            val email = binding.inputEmail.text.toString()
            val password = binding.inputPassword.text.toString()
            if (binding.inputEmail.error == null && binding.inputPassword.error == null
                    && name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                binding.btnRegister.isEnabled = false
                binding.tvLogin.isEnabled = false
                binding.progressBar.visibility = View.VISIBLE
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign up success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success")
                            val user = auth.currentUser
                            val userPassword = user?.providerData?.get(1)?.email // Get the password from the user object
                            Log.d(TAG, "User password: $password")
                            val profileUpdates = UserProfileChangeRequest.Builder()
                                .setDisplayName(name)
                                .build()
                            user?.updateProfile(profileUpdates)
                                ?.addOnCompleteListener(this) { updateProfileTask ->
                                    if (updateProfileTask.isSuccessful) {
                                        Toast.makeText(
                                            baseContext,
                                            "Authentication success. Welcome ${user?.displayName}",
                                            Toast.LENGTH_SHORT,
                                        ).show()
                                    } else {
                                        Toast.makeText(
                                            baseContext,
                                            "Failed to update profile.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                    binding.btnRegister.isEnabled = true
                                    binding.tvLogin.isEnabled = true
                                    binding.progressBar.visibility = View.GONE
                                }
                            updateUI(user)
                        } else {
                            // If sign up fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(
                                baseContext,
                                "Authentication failed.",
                                Toast.LENGTH_SHORT,
                            ).show()

                            binding.btnRegister.isEnabled = true
                            binding.tvLogin.isEnabled = true
                            binding.progressBar.visibility = View.GONE
                            updateUI(null)
                        }
                    }
            }
        }
    }

    /*
    public override fun onStart() {
        super.onStart()

        val firebaseUser = auth.currentUser
        // Check if user is signed in (non-null) and update UI accordingly.
        if (firebaseUser == null) {
            // Not signed in, launch the Login activity
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }
    }
     */

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            val intent = Intent(this@RegisterActivity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }

    companion object {
        private const val TAG = "RegisterActivity"
    }
}