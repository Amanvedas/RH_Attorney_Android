package com.rafayee.RHAttorney.Model

import com.rafayee.RHAttorney.AttornyProfile.Model.AttorneysAvailableTimeList

class SingleAttorneyResponse {
    var response:Int = 0
    var message:String?=null
    var attorneysAvailableTimeList: AttorneysAvailableTimeList?=null
    companion object {
        var myObj: SingleAttorneyResponse? = null
        val instance: SingleAttorneyResponse?
            get() {
                if (myObj == null) {
                    myObj = SingleAttorneyResponse()
                }
                return myObj
            }
    }
}