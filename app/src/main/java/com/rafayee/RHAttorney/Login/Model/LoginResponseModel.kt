package com.rafayee.RHAttorney.Login.Model

import com.google.gson.annotations.SerializedName

   
data class LoginResponseModel (

   @SerializedName("response") var response : Int,
   @SerializedName("message") var message : String,
   @SerializedName("attorneysList") var attorneysList : List<AttorneysList>

)