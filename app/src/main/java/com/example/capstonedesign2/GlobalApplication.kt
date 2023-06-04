package com.example.capstonedesign2

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, 	"534c304e8d235c4f010e28930e8b8b73")
    }
}