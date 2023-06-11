package com.capstone.tesfirebase.ui.main.scan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.tesfirebase.data.repository.Repository
import com.capstone.tesfirebase.data.response.ScanAppleResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ScanViewModel(private val repository: Repository) : ViewModel() {

    fun uploadImage(image: MultipartBody.Part, email: RequestBody): LiveData<ScanAppleResponse> {
        return repository.uploadImage(image, email)
    }
    private val _text = MutableLiveData<String>().apply {
        value = "This is Scan Fragment"
    }
    val text: LiveData<String> = _text
}