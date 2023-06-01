package com.capstone.tesfirebase.data.repository

import com.google.firebase.auth.FirebaseAuth

class Repository private constructor(
    private val auth: FirebaseAuth
) {
    fun login(email: String, password: String, callback: (Boolean) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                val success = task.isSuccessful
                callback(success)
            }
    }

    companion object {
        @Volatile
        private var instance: Repository? = null

        @JvmStatic
        fun getInstance(
            auth: FirebaseAuth,
        ) : Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(
                    auth,
                )
            }.also { instance = it }
    }
}