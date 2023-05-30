package com.capstone.tesfirebase.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import com.capstone.tesfirebase.databinding.ActivityLoginBinding
import com.capstone.tesfirebase.ui.main.MainActivity
import com.capstone.tesfirebase.ui.register.RegisterActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        supportActionBar?.hide()

        auth = Firebase.auth

        binding.tvRegister.setOnClickListener {
            val optionsCompat: ActivityOptionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this,
                    Pair(binding.inputEmail, "inputemail"),
                    Pair(binding.inputPassword, "inputpassword"),
                )
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent, optionsCompat.toBundle())
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.inputEmail.text.toString()
            val password = binding.inputPassword.text.toString()

            if (binding.inputEmail.errorText != null) {
                Toast.makeText(
                    baseContext,
                    "Silahkan masukkan email yang valid.",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (binding.inputPassword.errorText != null) {
                Toast.makeText(
                    baseContext,
                    "Silahkan masukkan password yang valid.",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (email.isEmpty()) {
                Toast.makeText(
                    baseContext,
                    "Silahkan masukkan email anda.",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (password.isEmpty()) {
                Toast.makeText(
                    baseContext,
                    "Silahkan masukkan password anda.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                binding.btnLogin.isEnabled = false
                binding.tvRegister.isEnabled = false
                binding.progressBar.visibility = View.VISIBLE
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmailAndPassword:success")
                            val user = auth.currentUser
                            binding.btnLogin.isEnabled = true
                            binding.tvRegister.isEnabled = true
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(
                                baseContext,
                                "Selamat datang kembali, ${user?.displayName}",
                                Toast.LENGTH_SHORT
                            ).show()
                            updateUI(user)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.exception)
                            Toast.makeText(
                                baseContext,
                                "Authentication failed.",
                                Toast.LENGTH_SHORT
                            ).show()
                            binding.btnLogin.isEnabled = true
                            binding.tvRegister.isEnabled = true
                            binding.progressBar.visibility = View.GONE
                            updateUI(null)
                        }
                    }
            }
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            updateUI(currentUser)
        }

    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }

    companion object {
        private const val TAG = "LoginActivity"
    }
}