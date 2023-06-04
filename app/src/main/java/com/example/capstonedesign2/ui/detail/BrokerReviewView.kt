package com.example.capstonedesign2.ui.detail

import com.example.capstonedesign2.data.remote.BrokerScore

interface BrokerReviewView {

    fun onAddBrokerReviewSuccess(message: String)
    fun onAddBrokerReviewFailure(code: Int, message: String)

    fun onBrokerReviewSuccess(brokerScore: BrokerScore?)
    fun onBrokerReviewFailure(code: Int, message: String)
}