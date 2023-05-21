package com.example.capstonedesign2.ui.map

import com.example.capstonedesign2.data.entities.Estate
import com.example.capstonedesign2.data.remote.MapZoomInResponse
import com.example.capstonedesign2.data.remote.MapZoomOutResponse
import com.example.capstonedesign2.data.remote.ResultResponse

interface RoomView {
    fun onAddRoomSuccess(code: Int)
    fun onAddRoomFailure(code: Int, message: String)

    fun onDetailSuccess(estate: Estate)
    fun onDetailFailure(code: Int, message: String)

}