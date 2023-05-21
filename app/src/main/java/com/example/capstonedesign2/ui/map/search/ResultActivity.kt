package com.example.capstonedesign2.ui.map.search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstonedesign2.R
import com.example.capstonedesign2.data.remote.ResultResponse
import com.example.capstonedesign2.data.remote.SearchResponse
import com.example.capstonedesign2.data.remote.SearchService
import com.example.capstonedesign2.databinding.ActivityResultBinding
import com.example.capstonedesign2.ui.detail.DetailActivity
import com.google.gson.Gson

class ResultActivity : AppCompatActivity(), ResultView {
    lateinit var binding : ActivityResultBinding
    private var resultList = ArrayList<ResultResponse>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var dong = intent.getStringExtra("search_dong")
        var full = intent.getStringExtra("search_full")

        var searchService = SearchService()
        searchService.setResultView(this)
        if (full != null) {
            searchService.getResultList(full, 100, 1)
        }

        binding.backIv.setOnClickListener {
            onBackPressed()
        }

        binding.searchTv.text = dong

        val spinner = binding.sortSpinner

        ArrayAdapter.createFromResource(
            this,
            R.array.sort_spinner,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // 선택된 항목에 따라 원하는 작업을 수행합니다.
                when (position) {
                    0 -> {
                        if (full != null) {
                            resultList.clear()
                            searchService.getResultList(full, 100, 1)
                        }
                    }
                    1 -> {
                        if (full != null) {
                            resultList.clear()
                            searchService.getResultList(full, 100, 2)
                        }
                    }
                    else -> {
                        if (full != null) {
                            resultList.clear()
                            searchService.getResultList(full, 100, 3)
                        }
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // 아무 항목도 선택되지 않았을 때의 동작을 설정합니다.
            }
        }

        initRV()
    }

    fun initRV() {
        val resultRVAdapter = ResultRVAdapter(resultList)
        var intent = Intent(this, DetailActivity::class.java)

        binding.resultRv.adapter = resultRVAdapter
        binding.resultRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        resultRVAdapter.setMyItemClickListener(object : ResultRVAdapter.MyItemClickListener {
            override fun onItemClick(resultResponse: ResultResponse) {
                intent.putExtra("roomId", resultResponse.id)
                startActivity(intent)
            }
        } )
    }

    override fun onResultSuccess(response: ResultResponse) {
        resultList.add(response)
    }
}