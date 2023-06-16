package com.example.github

import com.google.gson.annotations.SerializedName

 data class ResponseLogin(

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("email")
	val email: String
)

data class ResponseRegister(

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("confirmpassword")
	val confirmpassword: String
)
