package com.example.project.api

import retrofit2.Retrofit

object ApiService {


    private const val BASE_URL = "http://dev-api.robinsonandhenry.com/api/"
    fun setApiCall() = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ApiWorker.gsonConverter)
            .client(ApiWorker.client)
            .build()
            .create(ApiList::class.java)!!


}
