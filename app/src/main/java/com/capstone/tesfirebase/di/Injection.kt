package com.capstone.tesfirebase.di

import android.content.Context
import com.capstone.tesfirebase.data.api.ApiConfig
import com.capstone.tesfirebase.data.repository.Repository
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

object Injection {
    fun provideRepository(context: Context): Repository {
        val auth = Firebase.auth
        val apiService = ApiConfig.getApiService()
        return Repository.getInstance(
            auth,
            apiService
        )
    }
}