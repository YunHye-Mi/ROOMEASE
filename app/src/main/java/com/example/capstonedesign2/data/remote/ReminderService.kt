package com.example.capstonedesign2.data.remote

import android.util.Log
import com.example.capstonedesign2.ui.chat.ReminderView
import com.example.capstonedesign2.utils.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReminderService() {
    private lateinit var reminderView: ReminderView

    fun setReminderView(reminderView: ReminderView) {
        this.reminderView = reminderView
    }

    fun addReminder(authorization: String, reminder: Reminder) {
        val reminderService = getRetrofit().create(ReminderRetrofitInterface::class.java)
        reminderService.addReminder("Bearer $authorization", reminder).enqueue(object :
            Callback<ReminderResponse> {
            override fun onResponse(
                call: Call<ReminderResponse>,
                response: Response<ReminderResponse>
            ) {
                if (response.isSuccessful) {
                    val resp: ReminderResponse? = response.body()
                    if (resp != null) {
                        if (resp.success) {
                            reminderView.onReminderSuccess(resp.message)
                        } else {
                            when (resp.status) {
                                401 -> reminderView.onReminderFailure(resp.status, resp.message)
                                else -> reminderView.onReminderFailure(resp.status, resp.message)
                            }
                        }
                    }
                    Log.d("LinkReminderApi", "리마인더 api 연결 성공")
                }
            }

            override fun onFailure(call: Call<ReminderResponse>, t: Throwable) {
                Log.d("LinkReminder/Failure", t.message.toString())
            }
        })
    }

    fun getReminder(authorization: String, roomId: Long) {
        val reminderService = getRetrofit().create(ReminderRetrofitInterface::class.java)
        reminderService.getReminder("Bearer $authorization", roomId).enqueue(object :
            Callback<ReminderResponse> {
            override fun onResponse(
                call: Call<ReminderResponse>,
                response: Response<ReminderResponse>
            ) {
                if (response.isSuccessful) {
                    val resp: ReminderResponse? = response.body()
                    if (resp != null) {
                        if (resp.success) {
                            reminderView.onSeeReminderSuccess(resp.data)
                        } else {
                            when (resp.status) {
                                401 -> reminderView.onSeeReminderFailure(resp.status, resp.message)
                                else -> reminderView.onSeeReminderFailure(resp.status, resp.message)
                            }
                        }
                    }
                    Log.d("LinkGetReminderApi", "리마인더 api 연결 성공")
                }
            }

            override fun onFailure(call: Call<ReminderResponse>, t: Throwable) {
                Log.d("LinkGetReminder/Failure", t.message.toString())
            }
        })
    }
}