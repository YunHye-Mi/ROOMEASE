package com.example.capstonedesign2.ui.login

interface RefreshView {
    fun onRefreshSuccess(code: Int, refreshToken: String)
    fun onRefreshFailure(code: Int, message: String)
}