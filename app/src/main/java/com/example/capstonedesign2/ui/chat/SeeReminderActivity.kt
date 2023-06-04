package com.example.capstonedesign2.ui.chat

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.capstonedesign2.data.entities.User
import com.example.capstonedesign2.data.remote.AuthService
import com.example.capstonedesign2.data.remote.Reminder
import com.example.capstonedesign2.data.remote.ReminderService
import com.example.capstonedesign2.data.remote.SeeReminder
import com.example.capstonedesign2.databinding.ActivitySeereminderBinding
import com.example.capstonedesign2.ui.login.RefreshView
import com.google.gson.Gson
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import net.daum.mf.map.api.MapView.MapViewEventListener


class SeeReminderActivity : AppCompatActivity(), MapViewEventListener {
    lateinit var binding: ActivitySeereminderBinding
    lateinit var accessToken: String
    lateinit var user: User
    private var gson = Gson()
    lateinit var spf: SharedPreferences
    lateinit var reminderJson: String
    lateinit var reminder: Reminder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeereminderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        spf = getSharedPreferences("reminder", MODE_PRIVATE)
        reminderJson = spf.getString("seeReminder","").toString()
        reminder = gson.fromJson(reminderJson, Reminder::class.java)
        binding.reminderSelectDateTv.text = reminder.date
        binding.reminderSelectTimeTv.text = reminder.time
        binding.reminderSelectPlaceTv.text = reminder.place

        var spf = getSharedPreferences("currentUser", MODE_PRIVATE)
        var userJson = spf.getString("User", "")
        user = gson.fromJson(userJson, User::class.java)
        accessToken = user.accessToken

        // 클릭 이벤트 발생 시
        onClickListener()

    }
    override fun onMapViewInitialized(p0: MapView?) {
        if (p0 != null) {
            var mapCenter = MapPoint.mapPointWithGeoCoord(reminder.latitude.toDouble(), reminder.longitude.toDouble())
            var marker = MapPOIItem()
            marker.apply {
                tag = reminder.roomId
                itemName = reminder.place
                markerType = MapPOIItem.MarkerType.RedPin
                isShowCalloutBalloonOnTouch = false
            }
            p0.setMapCenterPointAndZoomLevel(mapCenter, 1, false)
            p0.addPOIItem(marker)
            p0.zoomIn(false)
            p0.zoomOut(false)
        }
    }

    override fun onMapViewCenterPointMoved(p0: MapView?, p1: MapPoint?) {

    }

    override fun onMapViewZoomLevelChanged(p0: MapView?, p1: Int) {

    }

    override fun onMapViewSingleTapped(p0: MapView?, p1: MapPoint?) {

    }

    override fun onMapViewDoubleTapped(p0: MapView?, p1: MapPoint?) {

    }

    override fun onMapViewLongPressed(p0: MapView?, p1: MapPoint?) {

    }

    override fun onMapViewDragStarted(p0: MapView?, p1: MapPoint?) {

    }

    override fun onMapViewDragEnded(p0: MapView?, p1: MapPoint?) {

    }

    override fun onMapViewMoveFinished(p0: MapView?, p1: MapPoint?) {

    }

    private fun onClickListener(){
        // 뒤로 가기 누를 시
        binding.reminderBackIv.setOnClickListener {
            finish()
        }
    }
}