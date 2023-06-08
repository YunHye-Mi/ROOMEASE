package com.example.capstonedesign2.ui.map.search

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.size
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstonedesign2.R
import com.example.capstonedesign2.data.remote.ResultResponse
import com.example.capstonedesign2.data.remote.SearchService
import com.example.capstonedesign2.databinding.ActivityResultBinding
import com.example.capstonedesign2.ui.detail.DetailActivity

class ResultActivity : AppCompatActivity(), ResultView {
    lateinit var binding : ActivityResultBinding
    private var resultResponseList = ArrayList<ResultResponse>()
    private var searchService = SearchService()
    lateinit var resultRVAdapter: ResultRVAdapter
    private var dong = ""
    private var full = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dong = intent.getStringExtra("search_dong").toString()
        full = intent.getStringExtra("search_full").toString()

        binding.backIv.setOnClickListener {
            onBackPressed()
        }

        val spinner = binding.sortSpinner

        ArrayAdapter.createFromResource(
            this,
            R.array.sort_spinner,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        searchService.setResultView(this)

        if (full != null) {
            searchService.getResultList(full, null, 1)
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // 선택된 항목에 따라 원하는 작업을 수행합니다.
                when (position) {
                    0 -> {
                        searchService.getResultList(full, null, 1)
                    }
                    1 -> {
                        searchService.getResultList(full, null, 2)
                    }
                    else -> {
                        searchService.getResultList(full, null, 3)
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // 아무 항목도 선택되지 않았을 때의 동작을 설정합니다.
            }
        }

        initRV()

        Log.d("spinner 개수", spinner.size.toString())
    }

    fun initRV() {
        resultRVAdapter = ResultRVAdapter(this, resultResponseList)
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

    @SuppressLint("NotifyDataSetChanged")
    override fun onResultSuccess(response: ArrayList<ResultResponse>) {
        if (resultResponseList.isNotEmpty()) {
            resultResponseList.clear()
        }
        resultRVAdapter.notifyDataSetChanged()
        for (i in response) {
            resultResponseList.add(i)
        }
        resultRVAdapter.notifyDataSetChanged()
        Log.d("RESULT/SUCCESS", "검색 결과 리스트 로드 완료")

        if (response.size > 999) {
            binding.searchTv.text = "\"$dong\"검색 결과(999+)"
        } else {
            binding.searchTv.text = "\"$dong\"검색 결과(${response.size})"
        }
    }

    override fun onResultFailure(message: String) {
        Log.d("RESULT/FAILURE", message)
    }
}