package com.example.project.api

import com.google.gson.JsonObject
import com.rafayee.RHAttorney.HomeFragmentModule.Model.ClienListModel
import retrofit2.Call
import retrofit2.http.*


interface ApiList {


     @POST("clients/FetchAllClients")
    fun getClientList(@Body jsonObject: JsonObject): Call<ClienListModel>?



}
