package com.example.capstonedesign2.ui.login

interface LoginView {
    fun onLoginSuccess(code: Int, accessToken: String)
    fun onLoginFailure(code: Int, message: String)
}