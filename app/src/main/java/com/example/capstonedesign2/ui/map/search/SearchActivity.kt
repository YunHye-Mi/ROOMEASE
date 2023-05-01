package com.example.capstonedesign2.ui.map.search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.SearchView
import com.example.capstonedesign2.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {
    lateinit var binding : ActivitySearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchTv.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.searchTv.requestFocusFromTouch()
                val searchEditText = binding.searchTv.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(searchEditText, InputMethodManager.SHOW_IMPLICIT)
            }
        }

        binding.searchTv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // 검색어 제출 시 동작하는 코드 작성
                val intent = Intent(this@SearchActivity, ResultActivity::class.java)
                intent.putExtra("search", query)
                startActivity(intent)

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // 검색어 변경 시 동작하는 코드 작성
                return false
            }
        })

        binding.backIv.setOnClickListener {
            onBackPressed()
        }
    }
}