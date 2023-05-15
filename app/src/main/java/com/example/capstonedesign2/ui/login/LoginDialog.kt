package com.example.capstonedesign2.ui.login

import android.app.Dialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.capstonedesign2.data.remote.AuthRequest
import com.example.capstonedesign2.data.remote.AuthService
import com.example.capstonedesign2.databinding.DialogIntermediaryBinding
import com.example.capstonedesign2.databinding.DialogLoginBinding
import com.example.capstonedesign2.ui.chat.ChatActivity
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import okhttp3.FormBody

class LoginDialog(context: Context) : Dialog(context), LoginView {
    lateinit var binding: DialogLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.dgCustomerTv.setOnClickListener {
            dismiss()
            //역할 정보 전송 구현 부분
//            loginWithKakao()
            var intent = Intent(context, GeneralActivity::class.java)
            context.startActivity(intent)
        }

        binding.dgIntermediaryTv.setOnClickListener {
            dismiss()
            //역할 정보 전송 구현 부분
//            loginWithKakao()
            var intent = Intent(context, IntermediaryActivity::class.java)
            context.startActivity(intent)
        }
    }

    private fun loginWithKakao() {
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.d(ContentValues.TAG, "로그인 실패", error)
            }
            else if (token != null) {
                val authRequest = AuthRequest("kakao", token.accessToken)
                val authService = AuthService()
                authService.setLoginView(this)
                authService.login(authRequest)
                Log.i(ContentValues.TAG, "로그인 성공 ${token.accessToken}")
            }
        }

        UserApiClient.instance.run {
            loginWithKakaoAccount(this@LoginDialog.context, callback = callback)
        }
    }

    override fun onLoginSuccess(code: Int, accessToken: String) {
        val sharedPreferences = context.getSharedPreferences("token", AppCompatActivity.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        with(editor) {
            putString("access", accessToken)
        }
        editor.commit()
    }

    override fun onLoginFailure(code: Int, message: String) {
        when (code) {

        }
    }
}