package com.capstone.tesfirebase.data.api

import com.capstone.tesfirebase.data.response.ScanAppleResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @Multipart
    @POST("/api/scan-apple")
    fun uploadImage(
        @Part image: MultipartBody.Part,
        @Part email: RequestBody,
    ): Call<ScanAppleResponse>
}