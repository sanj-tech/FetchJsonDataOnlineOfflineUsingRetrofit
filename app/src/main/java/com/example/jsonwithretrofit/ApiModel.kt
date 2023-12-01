package com.example.jsonwithretrofit

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class SubCategory(
    @SerializedName("id")
    @PrimaryKey val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("banner_image")
    val banner_image: String?
)

data class AppCenter(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("sub_category")
    val sub_category: List<SubCategory>
)

data class ApiModel(
    @SerializedName("status")
    val status: Int,

    @SerializedName("message")
    val message: String,

    @SerializedName("app_center")
    val app_center: List<AppCenter>
)
