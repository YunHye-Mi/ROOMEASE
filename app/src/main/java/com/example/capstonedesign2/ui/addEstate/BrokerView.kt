package com.example.capstonedesign2.ui.addEstate

import com.example.capstonedesign2.data.remote.EstateInfo

interface BrokerView {

    fun onAddRoomSuccess(message: String)
    fun onAddRoomFailure(code: Int, message: String)
    fun onGetRoomSuccess(brokerList: ArrayList<EstateInfo>?)
    fun onGetRoomFailure(code: Int, message: String)
}