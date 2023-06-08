package com.example.capstonedesign2.ui.detail

import com.example.capstonedesign2.data.remote.Room


interface RoomView {
    fun onDetailSuccess(room: ArrayList<Room>)
    fun onDetailFailure(message: String)
}