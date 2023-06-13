package com.capstone.tesfirebase.data.api

import com.capstone.tesfirebase.data.response.HistoryResponse1
import com.capstone.tesfirebase.data.response.ScanAppleResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @Multipart
    @POST("/api/scan-apple")
    fun uploadImage(
        @Part image: MultipartBody.Part,
        @Part("email") email: RequestBody
    ): Call<ScanAppleResponse>

    @GET("/api/history")
    fun getHistory(
        @Query("email") email: String
    ):Call<HistoryResponse1>
}