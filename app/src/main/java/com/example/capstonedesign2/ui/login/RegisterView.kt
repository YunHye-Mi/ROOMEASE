package com.example.capstonedesign2.ui.login

interface RegisterView {
    fun onRegisterSuccess(message: String, data: Boolean)
    fun onRegisterFailure(code: Int, message: String)
}