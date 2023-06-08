package com.example.capstonedesign2.ui.login

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.capstonedesign2.data.entities.User
import com.example.capstonedesign2.data.remote.AuthService
import com.example.capstonedesign2.data.remote.RefreshRequest
import com.example.capstonedesign2.data.remote.RegisterRequest
import com.example.capstonedesign2.databinding.ActivityGeneralBinding
import com.example.capstonedesign2.ui.MainActivity
import com.google.gson.Gson

class GeneralActivity : AppCompatActivity(), RegisterView, RefreshView {
    lateinit var binding : ActivityGeneralBinding
    private var accessToken = ""
    private var refreshToken = ""
    var authService = AuthService()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGeneralBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authService.setRegisterView(this)
        authService.setRefreshView(this)

        val userSpf = getSharedPreferences("currentUser", MODE_PRIVATE)
        val userJson = userSpf.getString("User", "")
        val gson = Gson()
        val user = gson.fromJson(userJson, User::class.java)

        if (user != null) {
            if (user.accessToken != "" && user.refreshToken != "" && user.nickname.isNotEmpty()) {
                var intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }

        // 화면이 시작되었을 때 닉네임 입력창에 포커스를 맞춰 키보드가 올라오도록 함.
        binding.nickEt.clearFocus()
        binding.nickEt.requestFocus()
        var inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(binding.nickEt, InputMethodManager.SHOW_IMPLICIT)


        // 닉네임 입력 후 엔터 키를 누르면 키보드가 내려감.
        binding.nickEt.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        binding.nickEt.setOnEditorActionListener { view, i, event ->
            if (event != null && (event.action == KeyEvent.KEYCODE_ENTER || i == EditorInfo.IME_ACTION_DONE)) {
                val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(binding.nickEt.windowToken, 0)
                true
            } else {
                false
            }
        }


        // 유저에 등록하기 위해 access token과 access token을 갱신하기 위해 refresh token을 가져옴.
        accessToken = intent.getStringExtra("accessToken").toString()
        refreshToken = intent.getStringExtra("refreshToken").toString()

        writeView()
    }

    private fun writeView(){
        binding.addInfoActivity.viewTreeObserver.addOnGlobalLayoutListener {
            if (intent.getStringExtra("setName").equals("setName")) {
                binding.nickTv.text = "변경하기"
                if (binding.nickEt.text.toString().isNotEmpty()) {
                    binding.startTv.setTextColor(Color.parseColor("#754C24"))
                    binding.startTv.setBackgroundColor(Color.parseColor("#FDC536"))

                    binding.startTv.setOnClickListener {
                        finish()
                        var intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                } else {
                    binding.startTv.setTextColor(Color.parseColor("#666666"))
                    binding.startTv.setBackgroundColor(Color.parseColor("#C8C8C8"))
                    binding.startTv.setOnClickListener {
                        Toast.makeText(this, "닉네임 입력은 필수 입니다", Toast.LENGTH_LONG).show()
                    }
                }
            } else {
                if(binding.nickEt.text.toString().isNotEmpty()) {
                    binding.startTv.setTextColor(Color.parseColor("#754C24"))
                    binding.startTv.setBackgroundColor(Color.parseColor("#FDC536"))

                    binding.startTv.setOnClickListener {

                        var registerRequest = RegisterRequest(binding.nickEt.text.toString(), null)
                        authService.register(accessToken, registerRequest)

                        finish()
                    }
                }
                else {
                    binding.startTv.setTextColor(Color.parseColor("#666666"))
                    binding.startTv.setBackgroundColor(Color.parseColor("#C8C8C8"))
                    binding.startTv.setOnClickListener {
                        Toast.makeText(this,"닉네임 입력은 필수 입니다", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    // edittext 이외 영역 클릭 시 키보드를 숨기도록 재정의
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        var view = currentFocus
        if (view != null && (ev.action == MotionEvent.ACTION_UP || ev.action == MotionEvent.ACTION_MOVE) && view is EditText && !view.javaClass.name.startsWith("android.webkit.")) {
            var scrcoords = IntArray(2)
            view.getLocationOnScreen(scrcoords)
            val x = ev.rawX + view.getLeft() - scrcoords[0]
            val y = ev.rawY + view.getTop() - scrcoords[1]
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom()) {
                hideKeyBoard()
            }
            binding.nickEt.clearFocus()
        }
        return super.dispatchTouchEvent(ev)
    }

    // 키보드 숨기기
    private fun hideKeyBoard(){
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    override fun onRegisterSuccess(message: String, data: Boolean) {
       Toast.makeText(this, "등록 완료", Toast.LENGTH_LONG).show()
        val intent = Intent(this, MainActivity::class.java)
        finish()
        val name = binding.nickEt.text.toString()
        var role  = ""
        role = if (data) {
            "Broker"
        } else {
            "General"
        }
        val user = User(accessToken, refreshToken, name, null, role)
        val gson = Gson()
        val userJson = gson.toJson(user)
        val userSpf = getSharedPreferences("currentUser", MODE_PRIVATE)
        val editor = userSpf.edit()
        editor.apply {
            putString("User", userJson)
        }

        startActivity(intent)

        editor.apply()

        Log.d("REGISTER/SUCCESS", "${user.nickname}/${user.role}/${user.accessToken}")
    }

    override fun onRegisterFailure(code: Int, message: String) {
        when (code) {
            401 -> {
                Log.d("Register/Failure", "$code/$message")
                authService.refresh(RefreshRequest(refreshToken))
            }
            403 -> Log.d("Register/Failure", "$code/$message")
        }
    }

    override fun onRefreshSuccess(accessToken: String, refreshToken: String) {
        val updateUser = User(accessToken, refreshToken, binding.nickEt.text.toString(), null, "General")
        val gson = Gson()
        val userJson = gson.toJson(updateUser)
        val userSpf = getSharedPreferences("currentUser", MODE_PRIVATE)
        val editor = userSpf.edit()
        editor.apply {
            putString("User", userJson)
        }

        startActivity(intent)

        editor.apply()

        authService.register(accessToken, RegisterRequest(binding.nickEt.text.toString(), null))

        Log.d("ReRegister/Success", "${updateUser.nickname}/${updateUser.role}")
    }

    override fun onRefreshFailure(code: Int, message: String) {
        when (code) {
            401 -> {
                Log.d("Refresh/Failure", "$code/$message")
                authService.refresh(RefreshRequest(refreshToken))
            }
            403 -> Log.d("Refresh/Failure", "$code/$message")
        }
    }
}