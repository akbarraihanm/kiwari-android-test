package com.example.testchataja.ui.login

import com.example.testchataja.utils.ConstString

class LoginPresenter(private val loginContract: LoginContract) {
    fun doLogin(email : String,password : String) {
        if(email == ConstString().email1) {
            if(password == ConstString().password1) {
                loginContract.whenSuccessLogin(ConstString().userName1)
            } else {
                loginContract.whenPasswordWrong("Password is incorrect")
            }
        } else if (email == ConstString().email2) {
            if(password == ConstString().password2) {
                loginContract.whenSuccessLogin(ConstString().userName2)
            } else {
                loginContract.whenPasswordWrong("Password is incorrect")
            }
        } else {
            loginContract.whenEmailWrong("Email not found")
        }
    }
}