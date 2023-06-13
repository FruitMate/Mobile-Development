package com.capstone.tesfirebase.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class HistoryResponse1(
    @field:SerializedName("code")
    val code: Int? = null,

    @field:SerializedName("data")
    val data: List<HistoryItem>?
)

data class HistoryItem(
    @field:SerializedName("uid")
    val uid: String? = null,

    @field:SerializedName("classification_result")
    val classification_result: String? = null,

    @field:SerializedName("timestamp")
    val timestamp: String? = null,

    @field:SerializedName("image_url")
    val image_url: String? = null,
)