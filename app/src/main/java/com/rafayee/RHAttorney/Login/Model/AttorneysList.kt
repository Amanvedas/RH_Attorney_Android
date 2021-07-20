package com.rafayee.RHAttorney.Login.Model

import com.google.gson.annotations.SerializedName

   
data class AttorneysList (

   @SerializedName("_id") var Id : String,
   @SerializedName("profilePic") var profilePic : String,
   @SerializedName("userInfo") var userInfo : UserInfo,
   /*@SerializedName("userRatings") var userRatings : List<String>,*/
   @SerializedName("userID") var userID : String,
   @SerializedName("firstName") var firstName : String,
   @SerializedName("lastName") var lastName : String,
   @SerializedName("password") var password : String,
   @SerializedName("phoneNumber") var phoneNumber : String,
   @SerializedName("emailID") var emailID : String,
   @SerializedName("otp") var otp : String,
   @SerializedName("gender") var gender : String,
   @SerializedName("calenderEmailID") var calenderEmailID : String,
   @SerializedName("isActive") var isActive : Boolean,
   @SerializedName("register_time") var registerTime : String,
   @SerializedName("address") var address : Address,
   @SerializedName("profileName") var profileName : String,
   @SerializedName("__v") var _v : Int,
   @SerializedName("otp_time") var otpTime : String,
   @SerializedName("time_slots") var timeSlots : List<TimeSlots>

)