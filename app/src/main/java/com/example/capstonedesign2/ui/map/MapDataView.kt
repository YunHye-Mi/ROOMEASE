package com.example.capstonedesign2.ui.map

import com.example.capstonedesign2.data.remote.MapZoomIn
import com.example.capstonedesign2.data.remote.MapZoomOut

interface MapDataView {
    fun onZoomOutSuccess(zoomOutList: ArrayList<MapZoomOut>)
    fun onZoomOutFailure(code: Int, message: String)

    fun onZoomInSuccess(zoomInList: ArrayList<MapZoomIn>)
    fun onZoomInFailure(code: Int, message: String)
}