package com.capstone.tesfirebase.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.capstone.tesfirebase.data.api.ApiService
import com.capstone.tesfirebase.data.response.HistoryItem
import com.capstone.tesfirebase.data.response.HistoryResponse1
import com.capstone.tesfirebase.data.response.ScanAppleResponse
import com.google.firebase.auth.FirebaseAuth
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository private constructor(
    private val auth: FirebaseAuth,
    private val apiService: ApiService,
) {
    fun login(email: String, password: String, callback: (Boolean) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                val success = task.isSuccessful
                callback(success)
            }
    }

    fun getHistory() : LiveData<List<HistoryItem>> {
        val email = auth.currentUser!!.email
        val historyResponseStatus = MutableLiveData<List<HistoryItem>>()
        val client = apiService.getHistory(email!!)
        client.enqueue(object : Callback<HistoryResponse1> {
            override fun onResponse(
                call: Call<HistoryResponse1>,
                response: Response<HistoryResponse1>
            ) {
                if (response.isSuccessful) {
                    val listHistory = response.body()?.data
                    historyResponseStatus.postValue(listHistory!!)
                }
            }
            override fun onFailure(call: Call<HistoryResponse1>, t: Throwable) {
                Log.d("Get History", "Gagal mendapat History")
            }
        })
        return historyResponseStatus
    }

    fun uploadImage(
        image: MultipartBody.Part,
        email: RequestBody
    ): LiveData<ScanAppleResponse> {
        val uploadResponseStatus = MutableLiveData<ScanAppleResponse>()
        val client = apiService.uploadImage(image, email)
        client.enqueue(object : Callback<ScanAppleResponse> {
            override fun onResponse(
                call: Call<ScanAppleResponse>,
                response: Response<ScanAppleResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && responseBody.code == 200) {
                        uploadResponseStatus.postValue(responseBody!!)
                    } else {
                        uploadResponseStatus.postValue(
                            ScanAppleResponse(404, "YNTKTS", "Terjadi Error")
                        )
                    }
                } else {
                    uploadResponseStatus.postValue(
                        ScanAppleResponse(404, "YNTKTS", "Terjadi Error ${response.message()}")
                    )
                }
            }

            override fun onFailure(call: Call<ScanAppleResponse>, t: Throwable) {
                uploadResponseStatus.postValue(
                    ScanAppleResponse(404, "YNTKTS", "Terjadi Error $t")
                )
            }
        })
        return uploadResponseStatus
    }


    companion object {
        @Volatile
        private var instance: Repository? = null

        @JvmStatic
        fun getInstance(
            auth: FirebaseAuth,
            apiService: ApiService,
        ) : Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(
                    auth,
                    apiService
                )
            }.also { instance = it }
    }
}