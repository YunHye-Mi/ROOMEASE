package com.example.capstonedesign2.ui.map.search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstonedesign2.data.entities.Address
import com.example.capstonedesign2.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {
    lateinit var binding : ActivitySearchBinding
    var addressList = ArrayList<Address>()
    private lateinit var searchResultAdapter: SearchRVAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addressList.apply {
            add(Address("상도동", "서울특별시 동작구 상도동", 37.4987679,126.9440654))
            add(Address("흑석동", "서울특별시 동작구 흑석동", 37.5055226,126.9623716))
            add(Address("연희동", "서울특별시 서대문구 연희동", 37.5715546,126.930927))
            add(Address("사당동", "서울특별시 동작구 사당동", 37.4856409,126.9723271))
            add(Address("신대방동", "서울특별시 동작구 신대방동", 37.4926959,126.9171638))
            add(Address("신당동", "서울특별시 중구 신당동", 37.5579703,127.0136667))
        }

        binding.searchSv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // 검색어 제출 시 동작하는 코드 작성
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // 검색어 변경 시 동작하는 코드 작성
                if (newText.isNullOrEmpty()) {
                    binding.searchRv.visibility = View.GONE
                } else {
                    filterList(newText)
                    binding.searchRv.visibility = View.VISIBLE
                }

                return true
            }
        })

        searchResultAdapter = SearchRVAdapter(addressList)
        binding.searchRv.adapter = searchResultAdapter
        binding.searchRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        var intent = Intent(this, ResultActivity::class.java)

        searchResultAdapter.setMyItemClickListener(object : SearchRVAdapter.MyItemClickListener {
            override fun onItemClick(address: Address) {
                intent.putExtra("search_address", address.address)
                intent.putExtra("search_detail", address.detail)
                startActivity(intent)
            }

        })


        binding.searchSv.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                binding.searchSv.requestFocusFromTouch()
                val searchEditText = binding.searchSv.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(searchEditText, InputMethodManager.SHOW_IMPLICIT)
            }
        }

        binding.backIv.setOnClickListener {
            onBackPressed()
        }
    }

    private fun filterList(query: String?) {
        if (!query.isNullOrEmpty()) {
            val filteredList = ArrayList<Address>()
            for (i in addressList) {
                if (i.address.contains(query))
                    filteredList.add(i)
            }

            if (filteredList.isEmpty()) {
                Toast.makeText(this, "No Data found", Toast.LENGTH_LONG).show()
            } else {
                searchResultAdapter.setFilteredList(filteredList)
            }
        }
    }
}