package com.example.capstonedesign2.ui.login

interface RefreshView {
    fun onRefreshSuccess(accessToken: String, refreshToken: String)
    fun onRefreshFailure(code: Int, message: String)
}