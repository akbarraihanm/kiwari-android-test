package com.example.testchataja.ui.login

interface LoginContract {
    fun whenSuccessLogin(nameUser : String)
    fun whenPasswordWrong(msg : String)
    fun whenEmailWrong(msg : String)
}