package com.example.capstonedesign2.ui.map.search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstonedesign2.data.remote.SearchResponse
import com.example.capstonedesign2.data.remote.SearchService
import com.example.capstonedesign2.databinding.ActivitySearchBinding

class SearchActivity : AppCompatActivity(), SearchTextView {
    lateinit var binding : ActivitySearchBinding
    var filteredList =  ArrayList<SearchResponse>()
    var addressList = ArrayList<SearchResponse>()
    private lateinit var searchResultAdapter: SearchRVAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                if (newText.toString().isNullOrEmpty()) {
                    if (filteredList.isNotEmpty())
                        filteredList.clear()
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
            override fun onItemClick(searchResponse: SearchResponse) {
                intent.putExtra("search_dong", searchResponse.dong)
                intent.putExtra("search_full", searchResponse.fullAddress)
                startActivity(intent)
            }
        })

        binding.backIv.setOnClickListener {
            onBackPressed()
        }
    }

    private fun filterList(query: String?) {
        var searchService = SearchService()
        searchService.setSearchView(this)

        if (query?.isNotEmpty() == true) {
            searchService.getKeyword(query)
            for (i in addressList) {
                if (i.dong.contains(query)) {
                    filteredList.add(i)
                } else {
                    filteredList.remove(i)
                }
            }

            if (filteredList.isNotEmpty()) {
                searchResultAdapter.setFilteredList(filteredList)
            }
        }
    }

    override fun onSearchSuccess(response: SearchResponse) {
        if (!addressList.contains(response))
            addressList.add(response)
    }

    override fun onSearchFailure(message: String) {
        Log.d("Search/Failure", message)
    }
}