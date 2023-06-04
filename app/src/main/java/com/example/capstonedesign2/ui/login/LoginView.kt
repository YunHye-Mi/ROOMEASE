package com.example.capstonedesign2.ui.login

interface LoginView {
    fun onLoginSuccess(accessToken: String, refreshToken: String)
    fun onLoginFailure(code: Int, message: String)
}