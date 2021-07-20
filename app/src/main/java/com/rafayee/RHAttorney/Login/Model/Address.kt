package com.rafayee.RHAttorney.Login.Model

import com.google.gson.annotations.SerializedName

   
data class Address (

   @SerializedName("street") var street : String,
   @SerializedName("state") var state : String,
   @SerializedName("city") var city : String,
   @SerializedName("country") var country : String,
   @SerializedName("postalCode") var postalCode : String

)