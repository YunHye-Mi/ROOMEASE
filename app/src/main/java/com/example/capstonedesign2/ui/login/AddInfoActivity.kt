package com.example.capstonedesign2.ui.login

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.capstonedesign2.databinding.ActivityAddinfoBinding
import com.example.capstonedesign2.ui.MainActivity
import com.kakao.sdk.user.model.User

class AddInfoActivity : AppCompatActivity() {
    lateinit var binding : ActivityAddinfoBinding
    var access = ""
    var refresh = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddinfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.nickEt.requestFocus()

        val spf = getSharedPreferences("token", MODE_PRIVATE)
        access = spf.getString("access", "").toString()
        refresh = spf.getString("refresh", "").toString()

        writeView()

        binding.startTv.setOnClickListener {

        }
    }

    private fun writeView(){
        binding.addInfoActivity.viewTreeObserver.addOnGlobalLayoutListener {
            if(binding.nickEt.text.toString().isNotEmpty()) {
                binding.startTv.setTextColor(Color.parseColor("#754C24"))
                binding.startTv.setBackgroundColor(Color.parseColor("#FDC536"))

                binding.startTv.setOnClickListener {
                    finish()

                    var intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            }
            else {
                binding.startTv.setTextColor(Color.parseColor("#666666"))
                binding.startTv.setBackgroundColor(Color.parseColor("#C8C8C8"))
                binding.startTv.setOnClickListener {
                    Toast.makeText(this,"닉네임 입력은 필수 입니다", Toast.LENGTH_LONG).show()
                }
            }

            if (binding.intermediaryEt.text.isNotEmpty()) {

            }
        }
    }

    // edittext 이외 영역 클릭 시 키보드를 숨기도록 재정의
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        var view = currentFocus
        if (view != null && (ev.action == MotionEvent.ACTION_UP || ev.action == MotionEvent.ACTION_MOVE) && view is EditText && !view.javaClass.name.startsWith(
                "android.webkit."
            )
        ) {
            var scrcoords = IntArray(2)
            view.getLocationOnScreen(scrcoords)
            val x = ev.rawX + view.getLeft() - scrcoords[0]
            val y = ev.rawY + view.getTop() - scrcoords[1]
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom()) {
                hideKeyBoard()
            }
            binding.nickEt.clearFocus()
            binding.intermediaryEt.clearFocus()
        }
        return super.dispatchTouchEvent(ev)
    }

    // 키보드 숨기기
    private fun hideKeyBoard(){
        var inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    private fun saveUser() {

    }
}