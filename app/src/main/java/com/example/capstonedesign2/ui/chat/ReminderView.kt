package com.example.capstonedesign2.ui.chat

import com.example.capstonedesign2.data.remote.SeeReminder

interface ReminderView {
    fun onReminderSuccess(message: String)
    fun onReminderFailure(code: Int, message: String)

    fun onSeeReminderSuccess(reminder: SeeReminder?)
    fun onSeeReminderFailure(code: Int, message: String)
}