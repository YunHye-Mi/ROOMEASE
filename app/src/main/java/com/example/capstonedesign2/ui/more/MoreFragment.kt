package com.example.capstonedesign2.ui.more

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.capstonedesign2.R
import com.example.capstonedesign2.databinding.FragmentMoreBinding
import com.example.capstonedesign2.ui.MainActivity
import com.example.capstonedesign2.ui.login.LoginActivity
import com.kakao.sdk.user.UserApi
import com.kakao.sdk.user.UserApiClient

class MoreFragment() : Fragment() {
    lateinit var binding: FragmentMoreBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoreBinding.inflate(inflater, container, false)

        binding.nicknameTv.text

        binding.signOutTv.setOnClickListener {
            UserApiClient.instance.logout {
                if (it != null) {
                    Log.e("Hello", "로그아웃 실패. SDK에서 토큰 삭제됨", it)
                } else {
                    Log.i("Hello", "로그아웃 성공. SDK에서 토큰 삭제됨")
                }
            }
            var intent = Intent(this.context, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        binding.withdrawalTv.setOnClickListener {
            UserApiClient.instance.unlink {
                if (it != null) {
                    Log.e("Hello", "연결 끊기 실패", it)
                } else {
                    Log.i("Hello", "연결 끊기 성공. SDK에서 토큰 삭제 됨")
                }
            }
            (activity as MainActivity).finish()
        }

        return inflater.inflate(R.layout.fragment_more, container, false)
    }
}