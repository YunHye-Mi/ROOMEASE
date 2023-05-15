package com.example.capstonedesign2.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.capstonedesign2.databinding.ActivityLoginBinding
import com.kakao.sdk.common.KakaoSdk

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
            var loginDialog = LoginDialog(this)
            loginDialog.show()
        }
    }
}