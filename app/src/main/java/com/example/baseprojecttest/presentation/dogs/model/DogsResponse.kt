package com.example.baseprojecttest.presentation.dogs.model

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json

data class DogsResponse(
    @Json(name = "message")
    val message:List<Breeds>?=null
)

data class Breeds(
    @SerializedName("message")
    val message: Any?,

    )
data class DogsBreedImage(
    @Json(name = "message")
    val message: String?=""
)
data class Dogs(
    val breed : String?,
    val imageUrl: String?
)
data class Name(
    val name:String?=null
)