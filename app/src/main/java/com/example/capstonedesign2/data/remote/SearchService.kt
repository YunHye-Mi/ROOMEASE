package com.example.capstonedesign2.data.remote

import android.util.Log
import com.example.capstonedesign2.ui.map.search.ResultView
import com.example.capstonedesign2.ui.map.search.SearchTextView
import com.example.capstonedesign2.utils.getRetrofit2
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchService() {
    private lateinit var searchTextView: SearchTextView
    private lateinit var resultView: ResultView

    fun setSearchView(searchTextView: SearchTextView) {
        this.searchTextView = searchTextView
    }

    fun setResultView(resultView: ResultView) {
        this.resultView = resultView
    }

    fun getKeyword(query: String) {
        var searchService = getRetrofit2().create(SearchRetrofitInterface::class.java)
        searchService.getKeyword(query).enqueue(object : Callback<SearchResponse> {
            override fun onResponse(call: Call<SearchResponse>, response: Response<SearchResponse>) {
                if (response.isSuccessful) {
                    var resp: SearchResponse? = response.body()
                    if (resp != null) {
                        searchTextView.onSearchSuccess(resp)
                        Log.d("SEARCH/SUCCESS", response.message())
                    }
                }
            }

            override fun onFailure(call: Call<SearchResponse>, t: Throwable) {
                searchTextView.onSearchFailure(t.message.toString())
                Log.d("SEARCH/FAILURE", t.message.toString())
            }
        })
    }

    fun getResultList(address: String, count: Int, sort: Int) {
        var searchService = getRetrofit2().create(SearchRetrofitInterface::class.java)
        searchService.getResultList(address, count, sort).enqueue(object : Callback<ResultResponse> {
            override fun onResponse(call: Call<ResultResponse>, response: Response<ResultResponse>) {
                if (response.isSuccessful) {
                    var resp: ResultResponse? = response.body()
                    if (resp != null) {
                        resultView.onResultSuccess(resp)
                        Log.d("RESULT/SUCCESS", response.message())
                    }
                }
            }

            override fun onFailure(call: Call<ResultResponse>, t: Throwable) {
                Log.d("RESULT/FAILURE", t.message.toString())
            }

        })
    }
}