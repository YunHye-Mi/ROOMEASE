package com.example.capstonedesign2.data.remote

import android.util.Log
import com.example.capstonedesign2.ui.login.LoginView
import com.example.capstonedesign2.ui.login.RefreshView
import com.example.capstonedesign2.ui.login.RegisterView
import com.example.capstonedesign2.utils.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

class AuthService() {
    private lateinit var loginView: LoginView
    private lateinit var registerView: RegisterView
    private lateinit var refreshView: RefreshView

    fun setLoginView(loginView: LoginView) {
        this.loginView = loginView
    }

    fun setRegisterView(registerView: RegisterView) {
        this.registerView = registerView
    }

    fun setRefreshView(refreshView: RefreshView) {
        this.refreshView = refreshView
    }

    fun login(authRequest: AuthRequest) {
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
        authService.login(authRequest).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                val resp: AuthResponse? = response.body()
                if (resp != null) {
                    if (resp.success) {
                        loginView.onLoginSuccess(resp.accessToken, resp.refreshToken)
                    } else {
                        when (resp.status) {
                            401 -> loginView.onLoginFailure(resp.status, resp.message)
                            403 -> loginView.onLoginFailure(resp.status, resp.message)

                        }
                    }
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("Login/Failure", t.message.toString())
            }
        })
    }

    fun refresh(refreshRequest: RefreshRequest) {
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
        authService.refresh(refreshRequest).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                if (response.isSuccessful) {
                    var resp: AuthResponse? = response.body()
                    if (resp != null) {
                        if (resp.success) {
                            refreshView.onRefreshSuccess(
                                resp.accessToken,
                                resp.refreshToken
                            )
                        }
                    }
                }
                else {
                    refreshView.onRefreshFailure(response.code(), response.message())
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("Refresh/Failure", t.message.toString())
            }
        })
    }

    fun register(accessToken: String, registerRequest: RegisterRequest) {
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)

        authService.register("Bearer $accessToken", registerRequest).enqueue(object: Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                if (response.isSuccessful) {
                    var resp: RegisterResponse? = response.body()
                    if (resp != null) {
                        if (resp.success) {
                            registerView.onRegisterSuccess(resp.message, resp.data.isBroker)
                        }
                    }
                }
                else {
                    registerView.onRegisterFailure(response.code(), response.message())
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.d("Register/Failure", t.message.toString())
            }
        })
    }
}