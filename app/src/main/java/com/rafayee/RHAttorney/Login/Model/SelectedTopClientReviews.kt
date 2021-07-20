package com.rafayee.RHAttorney.Login.Model

import com.google.gson.annotations.SerializedName

   
data class SelectedTopClientReviews (

   @SerializedName("reviewID") var reviewID : String,
   @SerializedName("slno") var slno : String

)