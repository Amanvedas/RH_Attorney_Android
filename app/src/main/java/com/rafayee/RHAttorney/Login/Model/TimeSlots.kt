package com.rafayee.RHAttorney.Login.Model

import com.google.gson.annotations.SerializedName

   
data class TimeSlots (

   @SerializedName("end") var end : Int,
   @SerializedName("object") var time_object : String,
   @SerializedName("start") var start : Int,
   @SerializedName("status") var status : String

)