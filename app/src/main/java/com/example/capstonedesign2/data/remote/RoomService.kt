package com.example.capstonedesign2.data.remote

import android.util.Log
import com.example.capstonedesign2.ui.map.MapDataView
import com.example.capstonedesign2.ui.detail.RoomView
import com.example.capstonedesign2.utils.getRetrofit
import com.example.capstonedesign2.utils.getRetrofit2
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RoomService() {
    private lateinit var roomView: RoomView
    private lateinit var mapDataView: MapDataView

    fun setRoomView(roomView: RoomView) {
        this.roomView = roomView
    }

    fun setMapDataView(mapDataView: MapDataView) {
        this.mapDataView = mapDataView
    }

    fun addRoom(authorization: String, roomId: Int) {
        var roomService = getRetrofit().create(RoomRetrofitInterface::class.java)
        roomService.addRoom("Bearer $authorization", roomId).enqueue(object : Callback<RoomResponse> {
            override fun onResponse(call: Call<RoomResponse>, response: Response<RoomResponse>) {
                if (response.isSuccessful) {
                    var resp: RoomResponse? = response.body()
                    if (resp != null) {
                        if (resp.success) {
                            roomView.onAddRoomSuccess(resp.message)
                        } else {
                            when (resp.status) {
                                401 -> roomView.onAddRoomFailure(resp.status, resp.message)
                                403 -> roomView.onAddRoomFailure(resp.status, resp.message)
                            }
                        }
                    }
                }
            }

            override fun onFailure(call: Call<RoomResponse>, t: Throwable) {
                Log.d("ADDROOM/FAILURE", t.message.toString())
            }
        })
    }

    fun getZoomOut(
        lbLat: Double, lbLng: Double, rtLat: Double,
        rtLng: Double, zoomin: Int, minRent: Double, maxRent: Double, type: Int, minSize: Double, maxSize: Double,
        minDeposit: Int, maxDeposit: Int, minManage: Double, maxManage: Double, minFloor: Int, maxFloor: Int) {
        var roomService = getRetrofit2().create(RoomRetrofitInterface::class.java)
        roomService.getZoomOUt(lbLat, lbLng, rtLat, rtLng, zoomin, minRent, maxRent, type, minSize, maxSize, minDeposit,
            maxDeposit, minManage, maxManage, minFloor, maxFloor).enqueue(object : Callback<ArrayList<MapZoomOut>> {
            override fun onResponse(
                call: Call<ArrayList<MapZoomOut>>,
                response: Response<ArrayList<MapZoomOut>>
            ) {
                if (response.isSuccessful) {
                    val resp: ArrayList<MapZoomOut>? = response.body()
                    if (resp != null) {
                        if (response.code() == 200) {
                            mapDataView.onZoomOutSuccess(resp)
                        } else {
                            mapDataView.onZoomOutFailure(response.code(), response.message())
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<MapZoomOut>>, t: Throwable) {
                Log.d("ZOOMOUTLink/FAILURE", t.message.toString())
            }
        })
    }

    fun getZoomIn(lbLat: Double, lbLng: Double, rtLat: Double,
                  rtLng: Double, zoomin: Int, minRent: Double, maxRent: Double, type: Int, minSize: Double, maxSize: Double,
                  minDeposit: Int, maxDeposit: Int, minManage: Double, maxManage: Double, minFloor: Int, maxFloor: Int) {
        var roomService = getRetrofit2().create(RoomRetrofitInterface::class.java)
        roomService.getZoomIn(lbLat, lbLng, rtLat, rtLng, zoomin, minRent, maxRent, type, minSize, maxSize, minDeposit,
            maxDeposit, minManage, maxManage, minFloor, maxFloor).enqueue(object: Callback<ArrayList<MapZoomIn>> {
            override fun onResponse(
                call: Call<ArrayList<MapZoomIn>>,
                response: Response<ArrayList<MapZoomIn>>
            ) {
                if (response.isSuccessful) {
                    var resp: ArrayList<MapZoomIn>? = response.body()
                    if (resp != null) {
                        if (response.code() == 200) {
                            mapDataView.onZoomInSuccess(resp)
                        } else {
                            mapDataView.onZoomInFailure(response.code(), response.message())
                        }
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<MapZoomIn>>, t: Throwable) {
                Log.d("ZOOMINLink/FAILURE", t.message.toString())
            }
        })
    }

    fun getRoomDetail(roomId: String) {
        var roomService = getRetrofit2().create(RoomRetrofitInterface::class.java)
        roomService.getRoomDetail(roomId).enqueue(object : Callback<List<Room>> {
            override fun onResponse(call: Call<List<Room>>, response: Response<List<Room>>) {
                if (response.isSuccessful) {
                    var resp: List<Room>? = response.body()
                    if (resp != null) {
                        roomView.onDetailSuccess(resp as ArrayList<Room>)
                        Log.d("DETAIL/SUCCESS", response.message())
                    }
                }
            }

            override fun onFailure(call: Call<List<Room>>, t: Throwable) {
                Log.d("DETAIL/FAILURE", t.message.toString())
            }
        })
    }
}