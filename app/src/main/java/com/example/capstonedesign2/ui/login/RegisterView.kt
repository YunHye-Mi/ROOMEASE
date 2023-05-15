package com.example.capstonedesign2.ui.login

interface RegisterView {
    fun onRegisterSuccess(code: Int)
    fun onRegisterFailure(code: Int, message: String)
}