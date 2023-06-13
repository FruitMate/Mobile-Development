package com.capstone.tesfirebase.data.repository

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class HistoryResponse(
    @field:SerializedName("code")
    val code: Int,

    @field:SerializedName("data")
    val data: List<HistoryItem>

) : Parcelable

@Parcelize
@Entity(tableName = "history")
data class HistoryItem(
    @PrimaryKey
    @field:SerializedName("uid")
    val uid: String,

    @field:SerializedName("classification_result")
    val classification_result: String,

    @field:SerializedName("timestamp")
    val timestamp: String,

    @field:SerializedName("image_url")
    val image_url: String
) : Parcelable