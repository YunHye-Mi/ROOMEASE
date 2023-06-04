package com.example.capstonedesign2.data.remote

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class RoomResponse(
    @SerializedName("status") val  status: Int,
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: ArrayList<EstateInfo>?
)

data class EstateInfo(
    @SerializedName("id") val id: Int,
    @SerializedName("deposit") val deposit: Int,
    @SerializedName("imagesThumbnail") val images: String,
    @SerializedName("rent") val rent: Double,
    @SerializedName("sizeM2") val size: Double
)

data class MapZoomOut(
    @SerializedName("ids") val ids: ArrayList<String>,
    @SerializedName("lat") val lat: Double,
    @SerializedName("lng") val lng: Double,
    @SerializedName("minPrice") val minPrice: Double,
    @SerializedName("dongName") val dongName: String,
    @SerializedName("roomNum") val roomNum: Int
)

data class MapZoomIn(
    @SerializedName("id") val id: String,
    @SerializedName("lat") val lat: Double,
    @SerializedName("lng") val lng: Double,
    @SerializedName("price") val price: Double,
    @SerializedName("size") val size: Double
)

data class Room(
    @SerializedName("id") var id: String, // 매물 번호
    @SerializedName("deposit") var deposit: Int, // 보증금
    @SerializedName("address")var address: String, // 주소
    @SerializedName("floor")var floor: String, // 층수
    @SerializedName("images_thumbnail")var coverImg: String, // 이미지
    @SerializedName("rent")var rent: Float, // 월세 가격
    @SerializedName("manage_cost")var manage_cost: Float, // 관리비
    @SerializedName("service_type")var service_type: String, //
    @SerializedName("sales_type")var sales_type: String, //전월세 유형
    @SerializedName("size_m2")var size: Float, // 평수
    @SerializedName("latitude") var latitude: Double,
    @SerializedName("longitude") var longitude: Double,
    @SerializedName("amenities") val amenities: ArrayList<String>,
    @SerializedName("nearSubway") val nearSubway: ArrayList<NearSubway>,
    @SerializedName("explain") val explain: String,
    @SerializedName("environment") val environment: ArrayList<Environment>,
    @SerializedName("brokerInfo") val brokerInfo: ArrayList<BrokerInfo>
)

data class NearSubway(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String
)

data class Environment(
    @SerializedName("type") val type: String,
    @SerializedName("distance") val distance: Int,
    @SerializedName("time") val time: Int,
    @SerializedName("transport") val transport: String
)

data class BrokerInfo(
    @SerializedName("id") val id: Int,
    @SerializedName("user_id") val user_id: Int,
    @SerializedName("room_id") val room_id: Int,
    @SerializedName("create_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("email") val email: String,
    @SerializedName("name") val name: String
)