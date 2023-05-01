package com.example.capstonedesign2.ui.login

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.capstonedesign2.R
import com.example.capstonedesign2.databinding.ActivityLoginBinding
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.common.model.KakaoSdkError
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.model.AccessTokenInfo

class LoginActivity : AppCompatActivity() {
    lateinit var binding : ActivityLoginBinding
    //lateinit var dialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onClickListener()
    }

    private fun onClickListener() {
        binding.loginIv.setOnClickListener {
            kakaoLogin()
//            dialog.show()
//            var intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//            finish()
        }
    }

//    private fun customDialog() {
//        dialog = Dialog(this)
//        dialog.setContentView(R.layout.login_dialog)
//        dialog.show()
//
//        var intermediary = dialog.findViewById<TextView>(R.id.dg_intermediary_tv)
//        var customer = dialog.findViewById<TextView>(R.id.dg_customer_tv)
//
//        intermediary.setOnClickListener {
//
//        }
//
//        customer.setOnClickListener {
//
//        }
//    }

    private fun kakaoLogin() {
        if (AuthApiClient.instance.hasToken()) {
            UserApiClient.instance.accessTokenInfo { accessTokenInfo: AccessTokenInfo?, throwable: Throwable? ->
                if (throwable != null) {
                    if (throwable is KakaoSdkError && throwable.isInvalidTokenError() == true) {

                    } else {

                    }
                } else {

                }
            }

        }
    }
}