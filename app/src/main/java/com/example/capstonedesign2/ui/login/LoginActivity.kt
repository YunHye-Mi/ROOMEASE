package com.example.capstonedesign2.ui.login

import android.app.Dialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.capstonedesign2.R
import com.example.capstonedesign2.databinding.ActivityLoginBinding
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.KakaoSdkError
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.model.AccessTokenInfo

class LoginActivity : AppCompatActivity() {
    lateinit var binding : ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        KakaoSdk.init(this, "534c304e8d235c4f010e28930e8b8b73")

        onClickListener()
    }

    private fun onClickListener() {
        binding.loginIv.setOnClickListener {
//            loginWithKakao()
            var intent = Intent(this, AddInfoActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginWithKakao() {
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.d(TAG, "로그인 실패", error)
            }
            else if (token != null) {
                Log.i(TAG, "로그인 성공 ${token.accessToken}")
                val sharedPreferences = getSharedPreferences("token", MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                with(editor) {
                    putString("access", token.accessToken)
                    putString("refresh", token.refreshToken)
                }
            }
        }

        UserApiClient.instance.run {
            loginWithKakaoAccount(this@LoginActivity, callback = callback)
        }
    }
}