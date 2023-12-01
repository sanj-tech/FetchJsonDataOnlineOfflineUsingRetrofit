package com.example.jsonwithretrofit

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("AdvertiseNewApplications/17/com.hd.camera.apps.high.quality")
    fun getApplications(): Call<ApiModel>

}