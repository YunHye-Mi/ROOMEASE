package com.example.capstonedesign2.data.remote

import android.util.Log
import com.example.capstonedesign2.ui.login.LoginView
import com.example.capstonedesign2.ui.login.RegisterView
import com.example.capstonedesign2.utils.getRetrofit
import retrofit2.Call
import retrofit2.Response

class AuthService() {
    private lateinit var loginView: LoginView
    private lateinit var registerView: RegisterView

    fun setLoginView(loginView: LoginView) {
        this.loginView = loginView
    }

    fun setRegisterView(registerView: RegisterView) {
        this.registerView = registerView
    }

    fun login(authRequest: AuthRequest) {
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
        authService.login(authRequest).enqueue(object : retrofit2.Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                Log.d("Login/SUCCESS", response.message().toString())
                val resp: AuthResponse = response.body()!!
                when (resp.status) {
//                    1000 -> loginView.onLoginSuccess(resp.status, resp.accessToken)
                    else -> loginView.onLoginFailure(resp.status, resp.message)
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("Login/Failure", t.message.toString())
            }
        })
        Log.d("Login", "Hello")
    }

    fun register(accessToken: String, registerRequest: RegisterRequest) {
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)

        authService.register(accessToken, registerRequest).enqueue(object :
            retrofit2.Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                Log.d("Register/SUCCESS", response.message().toString())
                val resp: RegisterResponse = response.body()!!
                when (resp.status) {
                    else -> registerView.onRegisterFailure(resp.status, resp.message)
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.d("Register/Failure", t.message.toString())
            }
        })
    }
}