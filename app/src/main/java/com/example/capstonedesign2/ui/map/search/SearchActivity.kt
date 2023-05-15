package com.example.capstonedesign2.ui.map.search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstonedesign2.data.local.EstateDatabase
import com.example.capstonedesign2.data.entities.Address
import com.example.capstonedesign2.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity() {
    lateinit var binding : ActivitySearchBinding
    lateinit var estateDB : EstateDatabase
    var addressList = ArrayList<Address>()
    private lateinit var searchResultAdapter: SearchRVAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        estateDB = EstateDatabase.getInstance(this)!!

        // 화면 시작 시 키보드를 올리고 searchview에 focus를 맞춤.
        binding.searchSv.isFocusable = true
        binding.searchSv.requestFocus()
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0)
        binding.searchSv.isIconified = false
        binding.searchSv.isFocusableInTouchMode = true

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

        binding.backIv.setOnClickListener {
            onBackPressed()
        }
    }

    private fun filterList(query: String?) {
        if (query?.isNotEmpty() == true) {
            val filteredList = ArrayList<Address>()
            for (i in estateDB.addressDao().getAddresses()) {
                if (i.address.contains(query)) {
                }
                filteredList.add(i)
            }

            if (filteredList.isEmpty()) {

            } else {
                searchResultAdapter.setFilteredList(filteredList)
            }
        }
    }
}