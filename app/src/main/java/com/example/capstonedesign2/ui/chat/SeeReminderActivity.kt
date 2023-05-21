package com.example.capstonedesign2.ui.chat

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.capstonedesign2.R
import com.example.capstonedesign2.data.entities.Reminder
import com.example.capstonedesign2.data.remote.KakaoService
import com.example.capstonedesign2.data.remote.Place
import com.example.capstonedesign2.data.remote.PlaceSearch
import com.example.capstonedesign2.data.remote.ResultSearchKeyword
import com.example.capstonedesign2.databinding.ActivityReminderBinding
import com.example.capstonedesign2.databinding.ActivitySeereminderBinding
import com.example.capstonedesign2.ui.map.KaKaoView
import com.google.gson.Gson
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import java.text.SimpleDateFormat
import java.util.*
import javax.microedition.khronos.opengles.GL10
import kotlin.collections.ArrayList


class SeeReminderActivity : AppCompatActivity() {
    lateinit var binding: ActivitySeereminderBinding
    private var gson = Gson()
    lateinit var spf: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeereminderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        spf = getSharedPreferences("reminder", MODE_PRIVATE)
        if (spf != null) {
            var reminderJson = spf.getString("saveReminder","")
            var reminder = gson.fromJson(reminderJson, Reminder::class.java)
            binding.reminderSelectDateTv.text = reminder.date
            binding.reminderSelectTimeTv.text = reminder.time
            binding.reminderSelectPlaceTv.text = reminder.place
            var mapCenter = MapPoint.mapPointWithGeoCoord(reminder.placeLat, reminder.placeLng)
            var marker = MapPOIItem()
            marker.apply {
                tag = reminder.roomId
                itemName = reminder.place
                markerType = MapPOIItem.MarkerType.RedPin
                isShowCalloutBalloonOnTouch = false
            }
            binding.reminderMapview.setMapCenterPointAndZoomLevel(mapCenter, 1, false)
            binding.reminderMapview.addPOIItem(marker)
        }

        // 클릭 이벤트 발생 시
        onClickListener()

    }
    // 클릭 이벤트 함수
    private fun onClickListener(){
        // 뒤로 가기 누를 시
        binding.reminderBackIv.setOnClickListener {
            finish()
        }
    }

    private fun getReminder(): Reminder {
        val date: String = binding.reminderSelectDateTv.text.toString()
        val time: String = binding.reminderSelectTimeTv.text.toString()
        val place: String = binding.reminderSelectPlaceTv.text.toString()
        val placeLat: Double = spf.getString("reminderPlaceLat", "")!!.toDouble()
        val placeLng: Double = spf.getString("reminderPlaceLng", "")!!.toDouble()

        return Reminder(0, date, time, place, placeLat, placeLng)
    }
}