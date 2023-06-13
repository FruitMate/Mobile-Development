package com.capstone.tesfirebase.ui.main.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.tesfirebase.data.repository.Repository
import com.capstone.tesfirebase.data.response.HistoryItem

class HistoryViewModel(private val repository: Repository) : ViewModel() {
    fun getHistory() : LiveData<List<HistoryItem>> {
        return repository.getHistory()
    }
    private val _text = MutableLiveData<String>().apply {
        value = "This is history Fragment"
    }
    val text: LiveData<String> = _text
}