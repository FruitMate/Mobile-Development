package com.capstone.tesfirebase.data.response


data class HistoryResponse(
    val uid: String,
    val classification_result: String,
    val timestamp: String,
    val image_url: String
)