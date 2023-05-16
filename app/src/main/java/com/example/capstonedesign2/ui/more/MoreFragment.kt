package com.example.capstonedesign2.ui.more

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.capstonedesign2.data.entities.User
import com.example.capstonedesign2.databinding.FragmentMoreBinding
import com.example.capstonedesign2.ui.MainActivity
import com.example.capstonedesign2.ui.MyReviewActivity
import com.example.capstonedesign2.ui.login.GeneralActivity
import com.example.capstonedesign2.ui.login.IntermediaryActivity
import com.example.capstonedesign2.ui.login.LoginActivity
import com.google.gson.Gson
import com.kakao.sdk.user.UserApiClient

class MoreFragment() : Fragment() {
    lateinit var binding: FragmentMoreBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var spfUser = activity?.getSharedPreferences("currentUser", AppCompatActivity.MODE_PRIVATE)
        var gson = Gson()
        var userJson = spfUser?.getString("User", "")
        var user = gson.fromJson(userJson, User::class.java)
        var role = user.role

        binding = FragmentMoreBinding.inflate(inflater, container, false)

        binding.nicknameTv.text = "이름: " + user.nickname

        if (role == "Intermediary") {
            binding.registerIntermediary.visibility = View.GONE
            binding.myreviewTv.visibility = View.GONE
            binding.nicknameTv.isClickable = false
        } else {
            binding.registerIntermediary.visibility = View.VISIBLE
            binding.myreviewTv.visibility = View.VISIBLE

            binding.nicknameTv.setOnClickListener {
                var intent = Intent(this.context, GeneralActivity::class.java)
                intent.putExtra("setName", "setName")
                startActivity(intent)
            }

            binding.registerIntermediary.setOnClickListener {
                var intent = Intent(this.context, IntermediaryActivity::class.java)
                intent.putExtra("IntermediaryRegister", "register")
                startActivity(intent)
            }

            binding.myreviewTv.setOnClickListener {
                var intent = Intent(this.context, MyReviewActivity::class.java)
                startActivity(intent)
            }
        }

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
            (activity as MainActivity).finishAndRemoveTask()
        }

        return binding.root
    }
}