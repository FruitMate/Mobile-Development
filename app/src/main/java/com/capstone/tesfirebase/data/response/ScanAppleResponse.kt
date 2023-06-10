package com.capstone.tesfirebase.data.response

import com.google.gson.annotations.SerializedName

data class ScanAppleResponse(

	@field:SerializedName("code")
	val code: Int? = null,

	@field:SerializedName("prediction")
	val prediction: String? = null,

	@field:SerializedName("message")
	val message: String? = null
)
