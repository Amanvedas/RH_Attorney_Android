package com.rafayee.RHAttorney.Login.Model

import com.google.gson.annotations.SerializedName

   
data class UserInfo (

   @SerializedName("designation") var designation : String,
   @SerializedName("practiceArea") var practiceArea : String,
   @SerializedName("teamLead") var teamLead : String,
   @SerializedName("dateLicenceInColorado") var dateLicenceInColorado : String,
   @SerializedName("primaryOfficeLocation") var primaryOfficeLocation : String,
   @SerializedName("biography") var biography : Biography,
   @SerializedName("nylasAccessToken") var nylasAccessToken : String,
   @SerializedName("rateForHour") var rateForHour : String,
   @SerializedName("experience") var experience : String,
   @SerializedName("legalIssue") var legalIssue : String,
   @SerializedName("availableForIntialConsultation") var availableForIntialConsultation : Boolean

)