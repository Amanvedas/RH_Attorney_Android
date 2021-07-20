package com.rafayee.RHAttorney.Login.Model

class LoginResponseController {

    var loginResponseModel: LoginResponseModel?=null
    companion object {
        var myObj: LoginResponseController? = null
        val instance: LoginResponseController?
            get() {
                if (myObj == null) {
                    myObj = LoginResponseController()
                }
                return myObj
            }
    }

}