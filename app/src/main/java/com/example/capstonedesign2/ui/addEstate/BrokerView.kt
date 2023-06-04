package com.example.capstonedesign2.ui.addEstate

import com.example.capstonedesign2.data.remote.EstateInfo

interface BrokerView {
    fun onGetRoomSuccess(brokerList: ArrayList<EstateInfo>?)
    fun onGetRoomFailure(code: Int, message: String)
}