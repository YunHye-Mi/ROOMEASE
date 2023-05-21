package com.example.capstonedesign2.data.remote

import com.example.capstonedesign2.ui.map.KaKaoView
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class KakaoService() {
    private lateinit var KaKaoView: KaKaoView
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://dapi.kakao.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val apiKey = "dd9ec86e2259c879e5d263419bda2967"

    fun setKaKaoView(KaKaoView: KaKaoView) {
        this.KaKaoView = KaKaoView
    }

    fun searchPlacesByCategory(categoryCode: String, rect: String) {
        val kakaoApi = retrofit.create(KaKaoApiInterface::class.java)
        kakaoApi.searchPlacesByCategory("KakaoAK $apiKey" ,categoryCode, rect).enqueue(object : Callback<PlaceResponse> {
            override fun onResponse(call: Call<PlaceResponse>, response: Response<PlaceResponse>) {
                if (response.isSuccessful) {
                    var resp: PlaceResponse? = response.body()
                    if (resp != null) {
                        KaKaoView.onCategorySuccess(resp.places)
                    }
                }
            }

            override fun onFailure(call: Call<PlaceResponse>, t: Throwable) {
                KaKaoView.onCategoryFailure(400, "네트워크 오류가 발생했습니다.")
            }
        })
    }

    fun getSearchKeyword(query: String, page: Int) {
        var kakaoService = retrofit.create(KaKaoApiInterface::class.java)
        kakaoService.getSearchKeyword("KakaoAK $apiKey", query, page).enqueue(object :
            Callback<ResultSearchKeyword> {
            override fun onResponse(call: Call<ResultSearchKeyword>, response: Response<ResultSearchKeyword>) {
                if (response.isSuccessful) {
                    var resp: ResultSearchKeyword? = response.body()
                    if (resp != null) {
                        KaKaoView.onKeyWordSuccess(resp)
                    }
                }
            }

            override fun onFailure(call: Call<ResultSearchKeyword>, t: Throwable) {
                KaKaoView.onKeyWordFailure(400, "네트워크 오류가 발생했습니다.")
            }
        })
    }
}