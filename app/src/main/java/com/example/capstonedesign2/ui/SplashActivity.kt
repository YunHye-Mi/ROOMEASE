package com.example.capstonedesign2.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.capstonedesign2.R
import com.example.capstonedesign2.data.entities.User
import com.example.capstonedesign2.data.remote.AuthService
import com.example.capstonedesign2.data.remote.RefreshRequest
import com.example.capstonedesign2.ui.login.LoginActivity
import com.example.capstonedesign2.ui.login.RefreshView
import com.google.gson.Gson

class SplashActivity : AppCompatActivity() {
    lateinit var user: User
    lateinit var userSpf: SharedPreferences
    private var gson = Gson()
    private var accessToken = ""
    private var refreshToken = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // 앱 실행 시 자동 로그인을 체크
        userSpf = getSharedPreferences("currentUser", Context.MODE_PRIVATE)
        var editor = userSpf.edit()
        val userJson = userSpf.getString("User", "").toString()
        if (!userJson.isEmpty()) {
            user = gson.fromJson(userJson, User::class.java)
            if (user.accessToken != "" && user.refreshToken != "" && user.role != "") {
                accessToken = user.accessToken
                refreshToken = user.refreshToken

                Handler(Looper.getMainLooper()).postDelayed({
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }, 500)

                Log.d("AccessToken", accessToken)
                Log.d("RefreshToken", refreshToken)
            }
            else {
                // 저장된 액세스 토큰이 없으면 로그인 화면으로 이동
                Handler(Looper.getMainLooper()).postDelayed({
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }, 500)
            }
        }else {
            // 저장된 액세스 토큰이 없으면 로그인 화면으로 이동
            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }, 500)
        }
    }
}