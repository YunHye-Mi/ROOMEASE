package com.example.capstonedesign2.ui.map

import com.example.capstonedesign2.data.remote.MapZoomInResponse
import com.example.capstonedesign2.data.remote.MapZoomOutResponse

interface MapView {
    fun onZoomOutSuccess(response: MapZoomOutResponse)
    fun onZoomOutFailure(message: String)

    fun onZoomInSuccess(response: MapZoomInResponse)
    fun onZoomInFailure(message: String)
}