package com.example.capstonedesign2.data.remote

import android.util.Log
import com.example.capstonedesign2.data.entities.Estate
import com.example.capstonedesign2.ui.map.MapView
import com.example.capstonedesign2.ui.map.RoomView
import com.example.capstonedesign2.utils.getRetrofit
import com.example.capstonedesign2.utils.getRetrofit2
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RoomService() {
    private lateinit var roomView: RoomView
    private lateinit var mapView: MapView

    fun setRoomView(roomView: RoomView) {
        this.roomView = roomView
    }

    fun setMapView(mapView: MapView) {
        this.mapView = mapView
    }

    fun addRoom(authorization: String, roomId: Int) {
        var roomService = getRetrofit().create(RoomRetrofitInterface::class.java)
        roomService.addRoom(authorization).enqueue(object : retrofit2.Callback<RoomResponse> {
            override fun onResponse(call: Call<RoomResponse>, response: Response<RoomResponse>) {
                if (response.isSuccessful) {
                    var resp: RoomResponse? = response.body()
                    if (resp != null) {
                        roomView.onAddRoomSuccess(resp.status)
                        Log.d("ADDROOM/SUCCESS", resp.message)
                    }
                }
            }

            override fun onFailure(call: Call<RoomResponse>, t: Throwable) {
                Log.d("ADDROOM/FAILURE", t.message.toString())
            }
        })
    }

    fun getZoomOut(
        centerlat: Double, centerlng: Double, minRent: Double?, maxRent: Double?, type: Int?,
        minSize: Double?, maxSize: Double?, minDeposit: Int?, maxDeposit: Int?, minManage: Double?,
        maxManage: Double?, lbLat: Double, lbLng: Double, rtLat: Double, rtLng: Double) {
        var roomService = getRetrofit2().create(RoomRetrofitInterface::class.java)
        roomService.getZoomOUt(centerlat, centerlng, minRent, maxRent, type, minSize, maxSize, minDeposit,
            maxDeposit, minManage, maxManage, lbLat, lbLng, rtLat, rtLng).enqueue(object : Callback<MapZoomOutResponse> {
            override fun onResponse(
                call: Call<MapZoomOutResponse>,
                response: Response<MapZoomOutResponse>
            ) {
                if (response.isSuccessful) {
                    var resp: MapZoomOutResponse? = response.body()
                    if (resp != null) {
                        mapView.onZoomOutSuccess(resp)
                        Log.d("ZOOMOUT/SUCCESS", response.message())
                    }
                }
            }

            override fun onFailure(call: Call<MapZoomOutResponse>, t: Throwable) {
                Log.d("ZOOMOUT/FAILURE", t.message.toString())
            }
        })
    }

    fun getZoomIn(centerlat: Double, centerlng: Double, minRent: Double?, maxRent: Double?, type: Int?,
                   minSize: Double?, maxSize: Double?, minDeposit: Int?, maxDeposit: Int?, minManage: Double?,
                   maxManage: Double?, lbLat: Double, lbLng: Double, rtLat: Double, rtLng: Double, minFloor: Int, maxFloor: Int) {
        var roomService = getRetrofit2().create(RoomRetrofitInterface::class.java)
        roomService.getZoomIn(centerlat, centerlng, minRent, maxRent, type, minSize, maxSize, minDeposit,
            maxDeposit, minManage, maxManage, lbLat, lbLng, rtLat, rtLng, minFloor, maxFloor).enqueue(object: Callback<MapZoomInResponse> {
            override fun onResponse(
                call: Call<MapZoomInResponse>,
                response: Response<MapZoomInResponse>
            ) {
                if (response.isSuccessful) {
                    var resp: MapZoomInResponse? = response.body()
                    if (resp != null) {
                        mapView.onZoomInSuccess(resp)
                        Log.d("ZOOMIN/SUCCESS", response.message())
                    }
                }
            }

            override fun onFailure(call: Call<MapZoomInResponse>, t: Throwable) {
                mapView.onZoomInFailure(t.message.toString())
                Log.d("ZOOMIN/FAILURE", t.message.toString())
            }
        })
    }

    fun getRoomDetail(roomId: String) {
        var roomService = getRetrofit2().create(RoomRetrofitInterface::class.java)
        roomService.getRoomDetail(roomId).enqueue(object : Callback<Estate> {
            override fun onResponse(call: Call<Estate>, response: Response<Estate>) {
                if (response.isSuccessful) {
                    var resp: Estate? = response.body()
                    if (resp != null) {
                        roomView.onDetailSuccess(resp)
                        Log.d("DETAIL/SUCCESS", response.message())
                    }
                }
            }

            override fun onFailure(call: Call<Estate>, t: Throwable) {
                Log.d("DETAIL/FAILURE", t.message.toString())
            }
        })
    }
}