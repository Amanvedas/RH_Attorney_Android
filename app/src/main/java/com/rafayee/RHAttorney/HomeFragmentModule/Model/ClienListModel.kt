package com.rafayee.RHAttorney.HomeFragmentModule.Model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class ClienListModel {
    @SerializedName("response")
    @Expose
    private var response: Int? = null

    @SerializedName("message")
    @Expose
    private var message: String? = null

    @SerializedName("results")
    @Expose
    private var results: List<Result?>? = null

    @SerializedName("totalPages")
    @Expose
    private var totalPages: Int? = null

    fun getResponse(): Int? {
        return response
    }

    fun setResponse(response: Int?) {
        this.response = response
    }

    fun getMessage(): String? {
        return message
    }

    fun setMessage(message: String?) {
        this.message = message
    }

    fun getResults(): List<Result?>? {
        return results
    }

    fun setResults(results: List<Result?>?) {
        this.results = results
    }

    fun getTotalPages(): Int? {
        return totalPages
    }

    fun setTotalPages(totalPages: Int?) {
        this.totalPages = totalPages
    }
    public class Result {
        @SerializedName("firstName")
        @Expose
        var firstName: String? = null

        @SerializedName("lastName")
        @Expose
        var lastName: String? = null

        @SerializedName("SFID")
        @Expose
        var sfid: String? = null

        @SerializedName("profilePic")
        @Expose
        var profilePic: String? = null

        @SerializedName("verification_status")
        @Expose
        var verificationStatus: Boolean? = null

        @SerializedName("mailingStreet")
        @Expose
        var mailingStreet: String? = null

        @SerializedName("city")
        @Expose
        var city: String? = null

        @SerializedName("state")
        @Expose
        var state: String? = null

        @SerializedName("zipcode")
        @Expose
        var zipcode: String? = null

        @SerializedName("enableTouchID")
        @Expose
        var enableTouchID: Boolean? = null

        @SerializedName("enableFaceID")
        @Expose
        var enableFaceID: Boolean? = null

        @SerializedName("isProfileUpdate")
        @Expose
        var isProfileUpdate: Boolean? = null

        @SerializedName("isEmailVerified")
        @Expose
        var isEmailVerified: Boolean? = null

        @SerializedName("isInvite")
        @Expose
        var isInvite: Boolean? = null

        @SerializedName("isMobileVerified")
        @Expose
        var isMobileVerified: Boolean? = null

        @SerializedName("appUser")
        @Expose
        var appUser: Boolean? = null

        @SerializedName("isBooking")
        @Expose
        var isBooking: Boolean? = null

        @SerializedName("_id")
        @Expose
        var id: String? = null

        @SerializedName("clientID")
        @Expose
        var clientID: String? = null

        @SerializedName("password")
        @Expose
        var password: String? = null

        @SerializedName("phoneNumber")
        @Expose
        var phoneNumber: String? = null

        @SerializedName("emailID")
        @Expose
        var emailID: String? = null

        @SerializedName("loc")
        @Expose
        var loc: List<Double>? = null

        @SerializedName("otp")
        @Expose
        var otp: String? = null

        @SerializedName("register_time")
        @Expose
        var registerTime: String? = null

        @SerializedName("register_type")
        @Expose
        var registerType: String? = null

        @SerializedName("accountType")
        @Expose
        var accountType: String? = null

        @SerializedName("__v")
        @Expose
        var v: Int? = null

        @SerializedName("Google")
        @Expose
        var google: Google? = null

        @SerializedName("Facebook")
        @Expose
        var facebook: Facebook? = null

        @SerializedName("Apple")
        @Expose
        var apple: Apple? = null
    }
}

//=======================================
class Apple {
    @SerializedName("AppleID")
    @Expose
    private var appleID: String? = null

    @SerializedName("tokenId")
    @Expose
    private var tokenId: String? = null
    fun getAppleID(): String? {
        return appleID
    }

    fun setAppleID(appleID: String?) {
        this.appleID = appleID
    }

    fun getTokenId(): String? {
        return tokenId
    }

    fun setTokenId(tokenId: String?) {
        this.tokenId = tokenId
    }
}
//================================

class Facebook {
    @SerializedName("facebookID")
    @Expose
    var facebookID: String? = null

    @SerializedName("tokenId")
    @Expose
    var tokenId: String? = null
}
//=======================
class Google {
    @SerializedName("GoogleID")
    @Expose
    private var googleID: String? = null

    @SerializedName("tokenId")
    @Expose
    private var tokenId: String? = null
    fun getGoogleID(): String? {
        return googleID
    }

    fun setGoogleID(googleID: String?) {
        this.googleID = googleID
    }

    fun getTokenId(): String? {
        return tokenId
    }

    fun setTokenId(tokenId: String?) {
        this.tokenId = tokenId
    }
}
//=====================================

