package com.example.capstonedesign2.ui.login

import android.app.Dialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.capstonedesign2.data.entities.User
import com.example.capstonedesign2.data.remote.AuthRequest
import com.example.capstonedesign2.data.remote.AuthService
import com.example.capstonedesign2.databinding.DialogLoginBinding
import com.google.gson.Gson
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient

class LoginDialog(context: Context) : Dialog(context), LoginView {
    lateinit var binding: DialogLoginBinding
    private var role = ""
    val authService = AuthService()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authService.setLoginView(this)

        binding.dgCustomerTv.setOnClickListener {
            dismiss()
            //역할 정보 전송 구현 부분
            role = "general"
            loginWithKaKao()
        }

        binding.dgIntermediaryTv.setOnClickListener {
            dismiss()
            //역할 정보 전송 구현 부분
            role = "broker"
            loginWithKaKao()
        }
    }

    private fun loginWithKaKao() {
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.d(ContentValues.TAG, "로그인 실패", error)
            }
            else if (token != null) {
                Log.i("LOGIN", "카카오로 로그인 성공 ${token.accessToken}")
                val authRequest = AuthRequest("kakao", token.accessToken)
                Log.d("KAKAOToken", token.accessToken)
                authService.login(authRequest)
            }
        }

        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    Log.e("LOGIN", "카카오톡으로 로그인 실패", error)
                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
                } else if (token != null) {
                    Log.i("LOGIN", "카카오톡으로 로그인 성공 ${token.accessToken}")
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(this@LoginDialog.context, callback = callback)
        }
    }

    override fun onLoginSuccess(accessToken: String, refreshToken: String) {
        val gson = Gson()
        val userSpf = context.getSharedPreferences("currentUser", AppCompatActivity.MODE_PRIVATE)
        val userJson = userSpf.getString("User", "")
        val user = gson.fromJson(userJson, User::class.java)
        val userEditor = userSpf.edit()

        if (role == "general") {
            if (user != null && user.role == "General") {
                userEditor.clear()
                userEditor.apply()
            }
            val intent = Intent(context, GeneralActivity::class.java)
            intent.putExtra("accessToken", accessToken)
            intent.putExtra("refreshToken", refreshToken)
            context.startActivity(intent)
        } else {
            if (user != null &&user.role == "Broker") {
                userEditor.clear()
                userEditor.apply()
            }
            val intent = Intent(context, BrokerActivity::class.java)
            intent.putExtra("accessToken", accessToken)
            intent.putExtra("refreshToken", refreshToken)
            context.startActivity(intent)
        }

        Log.d("LOGIN/SUCCESS", "$accessToken/$refreshToken")
        Log.d("LOGIN/POINT", role)
    }

    override fun onLoginFailure(code: Int, message: String) {
        Log.d("LOGIN/FAILURE", message)
    }
}