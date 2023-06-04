package com.example.capstonedesign2.ui.chat

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.text.InputType
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
import com.example.capstonedesign2.data.entities.User
import com.example.capstonedesign2.data.remote.*
import com.example.capstonedesign2.databinding.ActivityReminderBinding
import com.example.capstonedesign2.ui.login.RefreshView
import com.example.capstonedesign2.ui.map.KaKaoView
import com.google.gson.Gson
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ReminderActivity : AppCompatActivity(), KaKaoView, ReminderView, RefreshView {
    lateinit var binding: ActivityReminderBinding
    private var searchList = ArrayList<Document>()
    private var gson = Gson()
    lateinit var user: User
    lateinit var spf: SharedPreferences
    private var kakaoView = KaKaoService()
    private var placeRVAdapter = PlaceRVAdapter(searchList)
    lateinit var view: View
    lateinit var mapView: MapView
    private val reminderService = ReminderService()
    private val authService = AuthService()
    private var roomId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReminderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        kakaoView.setKaKaoView(this)
        reminderService.setReminderView(this)
        authService.setRefreshView(this)

        spf = getSharedPreferences("reminder", MODE_PRIVATE)

        var spf = getSharedPreferences("currentUser", MODE_PRIVATE)
        var userJson = spf.getString("User", "")
        user = gson.fromJson(userJson, User::class.java)
        roomId = intent.getIntExtra("chatRoomId", roomId)

        // 클릭 이벤트 발생 시
        onClickListener()

        //모두 다 작성하였을 때
        writeAllView()
    }

    // 모든 TextView를 작성
    private fun writeAllView(){
        binding.uploadReminderActivity.viewTreeObserver.addOnGlobalLayoutListener {
            if(binding.reminderSelectDateTv.text.toString().isNotEmpty() && binding.reminderSelectTimeTv.text.toString().isNotEmpty()
                && binding.reminderSelectPlaceTv.text.toString().isNotEmpty()) {
                binding.reminderUploadTv.setTextColor(Color.WHITE)
                binding.reminderUploadTv.setBackgroundResource(R.drawable.filter_apply_button)

                binding.reminderUploadTv.setOnClickListener {
                    Toast.makeText(this,"일정 등록 완료", Toast.LENGTH_LONG).show()
                    reminderService.addReminder(user.accessToken, getReminder())
                    finish()
                }
            }
            else {
                binding.reminderUploadTv.setTextColor(Color.parseColor("#EBEBEB"))
                binding.reminderUploadTv.setBackgroundResource(R.drawable.filter_button)
                binding.reminderUploadTv.setOnClickListener {
                    Toast.makeText(this,"아직 작성 되지 않은 부분이 있어요😅", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    // 클릭 이벤트 함수
    private fun onClickListener(){

        // 날짜 선택시
        binding.reminderSelectDateIv.setOnClickListener {
            datePicker(binding.reminderSelectDateTv,this)
        }

        // 시간 선택시
        binding.reminderSelectTimeIv.setOnClickListener {
            timePicker(binding.reminderSelectTimeTv,this)
        }

        // 인원 수 선택시
        binding.reminderSelectPlaceIv.setOnClickListener {
            placePicker(binding.reminderSelectPlaceTv, this)
        }

        // 뒤로 가기 누를 시
        binding.reminderBackIv.setOnClickListener {
            finish()
        }
    }

    // 장소 선택 대화창
    private fun placePicker(textView: TextView, context: Context) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        view = this.layoutInflater.inflate(R.layout.dialog_place, null)
        builder.setView(view)

        mapView = view.findViewById(R.id.dialog_mapView)
        val search = view.findViewById<EditText>(R.id.dialog_search_et)
        val button = view.findViewById<TextView>(R.id.dialog_search_tv)
        val placeRV = view.findViewById<RecyclerView>(R.id.dialog_place_rv)
        var selectedPlace = ""
        var centerPoint = MapPoint.mapPointWithGeoCoord(37.566352778, 126.977952778)

        mapView.setMapCenterPointAndZoomLevel(centerPoint, 1, true)

        placeRV.adapter = placeRVAdapter
        placeRV.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        button.setOnClickListener {
            hideKeyBoard()
            val search = view.findViewById<EditText>(R.id.dialog_search_et).text.toString()
            kakaoView.getSearchKeyword(search, null)
        }

        search.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        search.setOnEditorActionListener { view, i, event ->
            if (event != null && (event.action == KeyEvent.KEYCODE_ENTER || i == EditorInfo.IME_ACTION_DONE)) {
                val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(search.windowToken, 0)
                search.clearFocus()
                true
            } else {
                false
            }
        }

        var editor = spf.edit()

        placeRVAdapter.setMyItemClickListener(object : PlaceRVAdapter.MyItemClickListener {
            override fun onItemClick(document: Document) {
                if (mapView.poiItems.isNotEmpty())
                    mapView.removeAllPOIItems()

                val markerPoint = MapPoint.mapPointWithGeoCoord(document.y.toDouble(), document.x.toDouble())
                val marker = MapPOIItem()
                marker.apply {
                    itemName = document.placeName
                    tag = document.id.toInt()
                    mapPoint = markerPoint
                    markerType = MapPOIItem.MarkerType.BluePin
                    isShowCalloutBalloonOnTouch = false
                }

                mapView.addPOIItem(marker)
                mapView.setMapCenterPointAndZoomLevel(markerPoint, 1, true)

                editor.apply {
                    putString("reminderPlace", document.placeName)
                    putString("reminderPlaceLng", document.x)
                    putString("reminderPlaceLat", document.y)
                }

                selectedPlace = document.placeName
            }
        })


        builder.setPositiveButton(R.string.ok) { dialog, id ->
            textView.text = selectedPlace
            editor.commit()
        }
            .setNegativeButton(R.string.cancel) { dialog, id ->

            }
        builder.create().show()
    }

    // 날짜 선택 대화창
    private fun datePicker(textView: TextView, context: Context){
        val cal = Calendar.getInstance()

        val datePickerDialog = DatePickerDialog(context, { datePicker, year, month, day ->
            cal.set(Calendar.YEAR, year)
            cal.set(Calendar.MONTH, month)
            cal.set(Calendar.DAY_OF_MONTH, day)

            textView.text = SimpleDateFormat("yyyy년 M월 d일").format(cal.timeInMillis)
            Log.d("Date", textView.text.toString())
        },cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH))
        datePickerDialog.show()
    }

    // 시간 선택 대화창
    private fun timePicker(textView: TextView, context: Context){
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        val view: View = this.layoutInflater.inflate(R.layout.dialog_time, null)
        builder.setView(view)

        val hour = view.findViewById<View>(R.id.dialog_hour_picker) as NumberPicker
        val min = view.findViewById<View>(R.id.dialog_minute_picker) as NumberPicker
        val am_pm = view.findViewById<View>(R.id.dialog_am_pm_picker) as NumberPicker

        hour.minValue = 1
        hour.maxValue = 12
        hour.wrapSelectorWheel = true

        min.minValue = 0
        min.maxValue = 5
        min.displayedValues = arrayOf("00", "10", "20", "30", "40", "50")
        min.wrapSelectorWheel = true

        am_pm.minValue = 0
        am_pm.maxValue = 1
        am_pm.displayedValues = arrayOf("오전", "오후")
        am_pm.wrapSelectorWheel = false

        builder.setPositiveButton(R.string.ok) { dialog, id ->
            when (am_pm.value) {
                0 -> {
                    when (min.value) {
                        0 -> textView.text = "오전 " + hour.value.toString() + "시"
                        1 -> textView.text = "오전 " + hour.value.toString() + "시 " + "10분"
                        2 -> textView.text = "오전 " + hour.value.toString() + "시 " + "20분"
                        3 -> textView.text = "오전 " + hour.value.toString() + "시 " + "30분"
                        4 -> textView.text = "오전 " + hour.value.toString() + "시 " + "40분"
                        else -> textView.text = "오전 " + hour.value.toString() + "시 " + "50분"
                    }
                }
                else -> {
                    when (min.value) {
                        0 -> textView.text = "오후 " + hour.value.toString() + "시"
                        1 -> textView.text = "오후 " + hour.value.toString() + "시 " + "10분"
                        2 -> textView.text = "오후 " + hour.value.toString() + "시 " + "20분"
                        3 -> textView.text = "오후 " + hour.value.toString() + "시 " + "30분"
                        4 -> textView.text = "오후 " + hour.value.toString() + "시 " + "40분"
                        else -> textView.text = "오후 " + hour.value.toString() + "시 " + "50분"
                    }
                }
            }
            Log.d("Time", textView.text.toString())
        }
            .setNegativeButton(R.string.cancel) { dialog, id ->
            }
        builder.create().show()
    }

    private fun getReminder(): Reminder {
        val date: String = binding.reminderSelectDateTv.text.toString()
        val time: String = binding.reminderSelectTimeTv.text.toString()
        val place: String = binding.reminderSelectPlaceTv.text.toString()
        val placeLat: String = spf.getString("reminderPlaceLat", "")!!.toString()
        val placeLng: String = spf.getString("reminderPlaceLng", "")!!.toString()

        return Reminder(roomId, date, time, place, placeLat, placeLng)
    }

    override fun onCategorySuccess(document1: String, document: ArrayList<Document>) {
        TODO("Not yet implemented")
    }

    override fun onCategoryFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onKeyWordSuccess(resultSearchKeyword: ResultSearchKeyword, message: String) {
        if (!resultSearchKeyword.documents.isNullOrEmpty()) {
            if (searchList.isNotEmpty()) {
                searchList.clear()
            }

            for (document in resultSearchKeyword.documents) {
                var documentSearch = Document(document.id, document.placeName, document.category_name, document.category_group_code,document.category_group_name, document.phone, document.addressName, document.road_address_name, document.x, document.y, document.place_url, document.distance)
                searchList.add(documentSearch)
            }
            placeRVAdapter.notifyDataSetChanged()
        }
        Log.d("KAKAO/SUCCESS", resultSearchKeyword.meta.total_count.toString())
    }

    override fun onKeyWordFailure(message: String) {
        Toast.makeText(this, "검색 결과가 없습니다", Toast.LENGTH_LONG).show()
        Log.d("KAKAO/FAILURE", message)
    }

    // 키보드 숨기기
    private fun hideKeyBoard(){
        var inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    override fun onReminderSuccess(message: String) {
        Log.d("AddReminder/Success", message)
    }

    override fun onReminderFailure(code: Int, message: String) {
        when (code) {
            401 -> {
                Log.d("AddReminder/Failure", "$code/$message")
                authService.refresh(user.accessToken, RefreshRequest(user.refreshToken))
            }
            403 -> Log.d("AddReminder/Failure", "$code/$message")
        }
    }

    override fun onSeeReminderSuccess(reminder: SeeReminder?) {
        TODO("Not yet implemented")
    }

    override fun onSeeReminderFailure(code: Int, message: String) {
        TODO("Not yet implemented")
    }

    override fun onRefreshSuccess(accessToken: String, refreshToken: String) {
        val updateUser = User(accessToken, refreshToken, user.nickname, null, "General")
        val gson = Gson()
        val userJson = gson.toJson(updateUser)
        val userSpf = getSharedPreferences("currentUser", MODE_PRIVATE)
        val editor = userSpf.edit()
        editor.apply {
            putString("User", userJson)
        }

        editor.commit()

        reminderService.addReminder(accessToken, getReminder())

        Log.d("ReAddReminder", "${updateUser.accessToken}/$roomId")
    }

    override fun onRefreshFailure(code: Int, message: String) {
        when (code) {
            401 -> {
                Log.d("Refresh/Failure", "$code/$message")
                authService.refresh(user.accessToken, RefreshRequest(user.refreshToken))
            }
            403 -> Log.d("Refresh/Failure", "$code/$message")
        }
    }
}