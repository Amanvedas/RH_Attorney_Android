package com.rafayee.RHAttorney.Login.Model

import com.google.gson.annotations.SerializedName

   
data class Biography (

   @SerializedName("profileVideoUrl") var profileVideoUrl : String,
   @SerializedName("educationBarAdmitions") var educationBarAdmitions : List<String>,
   @SerializedName("awards") var awards : List<String>,
   @SerializedName("selectedTopClientReviews") var selectedTopClientReviews : List<SelectedTopClientReviews>

)