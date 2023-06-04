package com.example.capstonedesign2.data.remote

import android.util.Log
import com.example.capstonedesign2.ui.map.KaKaoView
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class KaKaoService() {
    private lateinit var KaKaoView: KaKaoView

    fun setKaKaoView(KaKaoView: KaKaoView) {
        this.KaKaoView = KaKaoView
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://dapi.kakao.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val apiKey = "dd9ec86e2259c879e5d263419bda2967"

    fun searchPlacesByCategory(categoryCode: String, rect: String) {
        val kakaoApi = retrofit.create(KaKaoRetrofitInterface::class.java)
        kakaoApi.searchPlacesByCategory("KakaoAK $apiKey" ,categoryCode, rect).enqueue(object : Callback<ResultSearchKeyword> {
            override fun onResponse(call: Call<ResultSearchKeyword>, response: Response<ResultSearchKeyword>) {
                if (response.isSuccessful) {
                    var resp: ResultSearchKeyword? = response.body()
                    if (resp != null) {
                        when (response.code()) {
                            200 -> KaKaoView.onCategorySuccess(
                                response.message(),
                                resp.documents as ArrayList<Document>
                            )
                            else -> Log.d("KAKAOLOCAL/FAILURE", response.message())
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ResultSearchKeyword>, t: Throwable) {
                KaKaoView.onCategoryFailure("네트워크 오류가 발생했습니다.")
            }
        })
    }

    fun getSearchKeyword(query: String, rect: String?) {
        var kakaoService = retrofit.create(KaKaoRetrofitInterface::class.java)
        kakaoService.getSearchKeyword("KakaoAK $apiKey", query, rect).enqueue(object : Callback<ResultSearchKeyword> {
            override fun onResponse(call: Call<ResultSearchKeyword>, response: Response<ResultSearchKeyword>) {
                if (response.isSuccessful) {
                    var resp: ResultSearchKeyword? = response.body()
                    if (resp != null) {
                        when (response.code()) {
                            200 -> KaKaoView.onKeyWordSuccess(resp, response.message())
                            else -> Log.d("KAKAOLOCAL/FAILURE", response.message())
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ResultSearchKeyword>, t: Throwable) {
                KaKaoView.onKeyWordFailure("네트워크 오류가 발생했습니다.")
            }
        })
    }
}