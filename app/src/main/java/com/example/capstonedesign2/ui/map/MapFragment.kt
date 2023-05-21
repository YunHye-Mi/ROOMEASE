package com.example.capstonedesign2.ui.map

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.capstonedesign2.R
import com.example.capstonedesign2.data.entities.Estate
import com.example.capstonedesign2.data.remote.*
import com.example.capstonedesign2.databinding.FragmentMapBinding
import com.example.capstonedesign2.ui.detail.DetailActivity
import com.example.capstonedesign2.ui.map.search.ResultActivity
import com.example.capstonedesign2.ui.map.search.SearchActivity
import com.google.gson.Gson
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import net.daum.mf.map.api.MapView.MapViewEventListener
import net.daum.mf.map.api.MapView.POIItemEventListener

class MapFragment() : Fragment(), MapViewEventListener, com.example.capstonedesign2.ui.map.MapView, KaKaoView {
    lateinit var binding: FragmentMapBinding
    lateinit var spf: SharedPreferences
    var rent: String = ""
    var area_min: Int? = 0
    var area_max: Int? = 0
    var price_min_d: Int? = 0
    var price_max_d: Int? = 0
    var price_min_a: Int? = 0
    var price_max_a: Int? = 0
    var price_min_m: Int? = 0
    var price_max_m: Int? = 0
    var floor: String =""
    private var infraList = ArrayList<Place>()
    private var markerList = ArrayList<MapPOIItem>()
    private var kakaoService = KakaoService()
    private var mapView = RoomService()
    private var zoomOutList = ArrayList<MapZoomOutResponse>()
    private var zoomInList = ArrayList<MapZoomInResponse>()
    private var gson = Gson()
    private var infraSubway = false
    private var infraBus = false
    private var infraBicycle = false
    private var infraRestaurant = false
    private var infraCafe = false
    private var infraStore = false
    private var infraConvenience = false
    private var infraFitness = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapBinding.inflate(inflater, container, false)

        kakaoService.setKaKaoView(this)
        mapView.setMapView(this)

        spf = this.requireContext().getSharedPreferences("infra", AppCompatActivity.MODE_PRIVATE)
        rent = spf?.getString("rent", "").toString()
        area_min = spf?.getInt("area_min", 0)
        area_max = spf?.getInt("area_max", 0)
        price_min_d = spf?.getInt("price_min_d", 0)
        price_max_d = spf?.getInt("price_max_d", 0)
        price_min_m = spf?.getInt("price_min_m", 0)
        price_max_m = spf?.getInt("price_max_m", 0)
        price_min_a = spf?.getInt("price_min_a", 0)
        price_max_a = spf?.getInt("price_max_a", 0)
        floor = spf?.getString("floor", "").toString()
        markerList = ArrayList<MapPOIItem>()
        infraList = arrayListOf<Place>()
        kakaoService = KakaoService()
        mapView = RoomService()
        gson = Gson()

        var resultIntent = Intent(this.context, ResultActivity::class.java)
        var detailIntent = Intent(this.context, DetailActivity::class.java)

        binding.mapView.setMapViewEventListener(this)
//        binding.mapView.setPOIItemEventListener(object : POIItemEventListener {
//            override fun onPOIItemSelected(p0: MapView?, p1: MapPOIItem?) {
//
//            }
//
//            override fun onCalloutBalloonOfPOIItemTouched(p0: MapView?, p1: MapPOIItem?) {
//                TODO("Not yet implemented")
//            }
//
//
//            override fun onCalloutBalloonOfPOIItemTouched(p0: MapView?, p1: MapPOIItem?, p2: MapPOIItem.CalloutBalloonButtonType?) {
//
//            }
//
//            override fun onDraggablePOIItemMoved(p0: MapView?, p1: MapPOIItem?, p2: MapPoint?) {
//
//            }
//        })

        onClickListener()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setFilter()
    }

    private var cafeMarkers: MutableList<MapPOIItem> = mutableListOf() // 카페 마커들을 담을 리스트
    private var previousMarkers: MutableList<MapPOIItem> = mutableListOf() // 이전에 있던 마커들을 담을 리스트
    private var isCafeMarkerVisible = false // 카페 마커 표시 여부

    // 카페 이미지 뷰 클릭 이벤트 핸들러
    private val cafeImageViewClickListener = View.OnClickListener {
        if (isCafeMarkerVisible) { // 카페 마커가 표시되어 있는 경우
            binding.mapView.removeAllPOIItems() // 모든 마커 제거

            // 이전에 있던 마커들을 다시 추가
            for (marker in previousMarkers) {
                binding.mapView.addPOIItem(marker)
            }

            isCafeMarkerVisible = false
        } else { // 카페 마커가 표시되어 있지 않은 경우
            binding.mapView.removeAllPOIItems() // 모든 마커 제거

            // 카페 마커들을 추가
            for (cafeMarker in cafeMarkers) {
                binding.mapView.addPOIItem(cafeMarker)
            }

            isCafeMarkerVisible = true
        }
    }

    // 카페 마커 추가
//    private fun addCafeMarkers() {
//        // 카페 마커들을 생성하고 cafeMarkers 리스트에 추가하는 로직을 구현합니다.
//        // ...
//    }
//
//    // 이전에 있던 마커들을 저장
//    private fun savePreviousMarkers() {
//        previousMarkers.clear()
//        previousMarkers.addAll(binding.mapView.poiItems)
//    }
//
//    // 지도 초기화 시 동작하는 코드
//    private fun initializeMapView() {
//        // ...
//
//        // 카페 이미지 뷰 클릭 이벤트 핸들러 등록
//        binding.cafeBt.setOnClickListener(cafeImageViewClickListener)
//
//        // 카페 마커 추가
//        addCafeMarkers()
//
//        // 이전에 있던 마커들 저장
//        savePreviousMarkers()
//    }


    private fun onClickListener() {
        var infraClick = false

        binding.searchTv.setOnClickListener {
            var intent = Intent(this.context, SearchActivity::class.java)
            startActivity(intent)
        }

        binding.rentTypeBt.setOnClickListener {
            var intent = Intent(this.context, FilterActivity::class.java)
            startActivity(intent)
        }

        binding.areaBt.setOnClickListener {
            var intent = Intent(this.context, FilterActivity::class.java)
            startActivity(intent)
        }

        binding.priceBt.setOnClickListener {
            var intent = Intent(this.context, FilterActivity::class.java)
            startActivity(intent)
        }

        binding.floorBt.setOnClickListener {
            var intent = Intent(this.context, FilterActivity::class.java)
            startActivity(intent)
        }

        binding.infraMakerFilterBt.setOnClickListener {
            if (!infraClick) {
                binding.infraList.visibility = View.VISIBLE
                infraClick = true
            } else {
                binding.infraList.visibility = View.GONE
                infraClick = false
            }
        }

        binding.subwayBt.setOnClickListener {
            if (!infraSubway) {
                binding.infraList.visibility = View.GONE
                kakaoService.searchPlacesByCategory("SW8", binding.mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude.toString()
                        + ", " + binding.mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude.toString()
                        + ", " + binding.mapView.mapPointBounds.topRight.mapPointGeoCoord.latitude.toString() + ", "
                        + binding.mapView.mapPointBounds.topRight.mapPointGeoCoord.longitude.toString())
                if (infraList != null) {
                    for (i in infraList) {
                        var marker = MapPOIItem()
                        marker.apply {
                            tag = i.id.toInt()
                            itemName = i.placeName
                            mapPoint = MapPoint.mapPointWithGeoCoord(i.x.toDouble(), i.y.toDouble())
                            markerType = MapPOIItem.MarkerType.YellowPin
                        }
                        binding.mapView.addPOIItem(marker)
                    }
                }
                infraSubway = true
                infraClick = false
            } else if (infraSubway) {
                binding.mapView.removeAllPOIItems()
                for (i in markerList) {
                    binding.mapView.addPOIItem(i)
                }
                markerList.clear()
                binding.infraList.visibility = View.GONE
                infraSubway = false
                infraClick = false
            }
            Log.d("infra_subway", infraSubway.toString())
        }

        binding.busBt.setOnClickListener {
            if (!infraBus) {
                markerList.addAll(binding.mapView.poiItems)
                binding.mapView.removeAllPOIItems()
                binding.infraList.visibility = View.GONE
                infraBus = true
                infraClick = false
            } else if (infraBus) {
                binding.infraList.visibility = View.GONE
                infraBus = false
                infraClick = false
            }
            Log.d("infra_bus", infraBus.toString())

        }

        binding.bicycleBt.setOnClickListener {
            if (!infraBicycle) {
                markerList.addAll(binding.mapView.poiItems)
                binding.mapView.removeAllPOIItems()
                binding.infraList.visibility = View.GONE
                infraBicycle = true
                infraClick = false
            } else if (infraBicycle) {
                binding.mapView.removeAllPOIItems()
                for (i in markerList) {
                    binding.mapView.addPOIItem(i)
                }
                markerList.clear()
                binding.infraList.visibility = View.GONE
                infraBicycle = false
                infraClick = false
            }
            Log.d("infra_bicycle", infraBicycle.toString())
        }

        binding.restaurantBt.setOnClickListener {
            if (!infraRestaurant) {
                markerList.addAll(binding.mapView.poiItems)
                binding.mapView.removeAllPOIItems()
                binding.infraList.visibility = View.GONE
                kakaoService.searchPlacesByCategory("FD6", binding.mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude.toString()
                        + ", " + binding.mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude.toString()
                        + ", " + binding.mapView.mapPointBounds.topRight.mapPointGeoCoord.latitude.toString() + ", "
                        + binding.mapView.mapPointBounds.topRight.mapPointGeoCoord.longitude.toString())
                infraRestaurant = true
                infraClick = false
            } else if (infraRestaurant) {
                binding.mapView.removeAllPOIItems()
                for (i in markerList) {
                    binding.mapView.addPOIItem(i)
                }
                markerList.clear()
                binding.infraList.visibility = View.GONE
                infraRestaurant = false
                infraClick = false
            }
            Log.d("infra_restaurant", infraRestaurant.toString())
        }

        binding.cafeBt.setOnClickListener {
            if (!infraCafe) {
                markerList.addAll(binding.mapView.poiItems)
                binding.mapView.removeAllPOIItems()
                binding.infraList.visibility = View.GONE
                kakaoService.searchPlacesByCategory("CE7", binding.mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude.toString()
                        + ", " + binding.mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude.toString()
                        + ", " + binding.mapView.mapPointBounds.topRight.mapPointGeoCoord.latitude.toString() + ", "
                        + binding.mapView.mapPointBounds.topRight.mapPointGeoCoord.longitude.toString())
                infraCafe = true
                infraClick = false
            } else if (infraCafe) {
                binding.mapView.removeAllPOIItems()
                for (i in markerList) {
                    binding.mapView.addPOIItem(i)
                }
                markerList.clear()
                binding.infraList.visibility = View.GONE
                infraCafe = false
                infraClick = false
            }
            Log.d("infra_cafe", infraCafe.toString())
        }

        binding.shoppingStreetBt.setOnClickListener {
            if (!infraStore) {
                markerList.addAll(binding.mapView.poiItems)
                binding.mapView.removeAllPOIItems()
                binding.infraList.visibility = View.GONE
                kakaoService.searchPlacesByCategory("MT1", binding.mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude.toString()
                        + ", " + binding.mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude.toString()
                        + ", " + binding.mapView.mapPointBounds.topRight.mapPointGeoCoord.latitude.toString() + ", "
                        + binding.mapView.mapPointBounds.topRight.mapPointGeoCoord.longitude.toString())
                infraStore = true
                infraClick = false
            } else if (infraStore) {
                binding.mapView.removeAllPOIItems()
                for (i in markerList) {
                    binding.mapView.addPOIItem(i)
                }
                markerList.clear()
                binding.infraList.visibility = View.GONE
                infraStore = false
                infraClick = false
            }
            Log.d("infra_store", infraStore.toString())
        }

        binding.convenienceBt.setOnClickListener {
            if (!infraConvenience) {
                markerList.addAll(binding.mapView.poiItems)
                binding.mapView.removeAllPOIItems()
                binding.infraList.visibility = View.GONE
                kakaoService.searchPlacesByCategory("CS2", binding.mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude.toString()
                        + ", " + binding.mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude.toString()
                        + ", " + binding.mapView.mapPointBounds.topRight.mapPointGeoCoord.latitude.toString() + ", "
                        + binding.mapView.mapPointBounds.topRight.mapPointGeoCoord.longitude.toString())
                infraConvenience = true
                infraClick = false
            } else if (infraConvenience) {
                binding.mapView.removeAllPOIItems()
                for (i in markerList) {
                    binding.mapView.addPOIItem(i)
                }
                markerList.clear()
                binding.infraList.visibility = View.GONE
                infraConvenience = false
                infraClick = false
            }
            Log.d("infra_convenience", infraConvenience.toString())
        }

        binding.fitnessBt.setOnClickListener {
            if (!infraFitness) {
                markerList.addAll(binding.mapView.poiItems)
                binding.mapView.removeAllPOIItems()
                binding.infraList.visibility = View.GONE
                infraFitness = true
                infraClick = false
            } else if (infraFitness) {
                binding.mapView.removeAllPOIItems()
                binding.infraList.visibility = View.GONE
                infraFitness = false
                infraClick = false
                for (i in markerList)
                    binding.mapView.addPOIItem(i)
                markerList.clear()
            }
            Log.d("infra_fitness", infraFitness.toString())
        }
    }

    fun setFilter() {
        val spf = activity?.getSharedPreferences("filter", AppCompatActivity.MODE_PRIVATE)

        if (rent != null) {
            if (rent.isNotEmpty()) {
                binding.rentTypeBt.text = rent
                binding.rentTypeBt.setTextColor(Color.parseColor("#C69C6D"))
                binding.rentTypeBt.setBackgroundResource(R.drawable.select_brown_round_shape)
            } else {
                binding.rentTypeBt.text = "전월세"
                binding.rentTypeBt.setTextColor(Color.WHITE)
                binding.rentTypeBt.setBackgroundResource(R.drawable.brown_round_shape)
            }
        }

        if (area_min != null && area_max != null){
            if (area_min != 0 && area_max != 0) {
                binding.areaBt.setTextColor(Color.parseColor("#C69C6D"))
                binding.areaBt.setBackgroundResource(R.drawable.select_brown_round_shape)
            } else {
                binding.areaBt.setTextColor(Color.WHITE)
                binding.areaBt.setBackgroundResource(R.drawable.brown_round_shape)
            }
        }

        if (price_min_d != null && price_max_d != null && price_min_m != null && price_max_m != null && price_min_a != null && price_max_a != null){
            if (rent == "전세") {
                if (price_min_d != 0 && price_max_d != 0) {
                    binding.priceBt.setTextColor(Color.parseColor("#C69C6D"))
                    binding.priceBt.setBackgroundResource(R.drawable.select_brown_round_shape)
                }
            } else {
                if (price_min_d != 0 && price_max_d != 0
                    && price_min_m != 0 && price_max_m != 0
                    && price_min_a != 0 && price_max_a != 0) {
                    binding.priceBt.setTextColor(Color.parseColor("#C69C6D"))
                    binding.priceBt.setBackgroundResource(R.drawable.select_brown_round_shape)
                }
                else if (price_min_m != 0 && price_max_m != 0) {
                    binding.priceBt.setTextColor(Color.parseColor("#C69C6D"))
                    binding.priceBt.setBackgroundResource(R.drawable.select_brown_round_shape)
                }
                else if (price_min_a != 0 && price_max_a != 0) {
                    binding.priceBt.setTextColor(Color.parseColor("#C69C6D"))
                    binding.priceBt.setBackgroundResource(R.drawable.select_brown_round_shape)
                }
                else if (price_min_d != 0 && price_max_d != 0) {
                    binding.priceBt.setTextColor(Color.parseColor("#C69C6D"))
                    binding.priceBt.setBackgroundResource(R.drawable.select_brown_round_shape)
                } else {
                    binding.priceBt.setTextColor(Color.WHITE)
                    binding.priceBt.setBackgroundResource(R.drawable.brown_round_shape)
                }
            }
        }

        if (floor != null) {
            if (floor.isNotEmpty()) {
                binding.floorBt.text = floor
                binding.floorBt.setTextColor(Color.parseColor("#C69C6D"))
                binding.floorBt.setBackgroundResource(R.drawable.select_brown_round_shape)
            } else {
                binding.floorBt.text = "층"
                binding.floorBt.setTextColor(Color.WHITE)
                binding.floorBt.setBackgroundResource(R.drawable.brown_round_shape)
            }
        }
    }


    private fun viewConvertToBitmapZoomIn(price: Double, size: Double): Bitmap {
        val zoom_in_view = layoutInflater.inflate(R.layout.zoomin_marker_layout, null)
        val displayMetrics = DisplayMetrics()
        val area = zoom_in_view.findViewById<TextView>(R.id.zoom_in_area_tv)
        val price = zoom_in_view.findViewById<TextView>(R.id.zoom_in_max_tv)
        area.text = size.toString()
        price.text = price.toString()
        zoom_in_view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        zoom_in_view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels)
        zoom_in_view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels)
        val bitmap = Bitmap.createBitmap(zoom_in_view.measuredWidth, zoom_in_view.measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        zoom_in_view.draw(canvas)
        return bitmap
    }

    private fun viewConvertToBitmapZoomOut(dongName: String, minPrice: Double, roomNum: Int): Bitmap {
        val zoom_out_view = layoutInflater.inflate(R.layout.zoomout_marker_layout, null)
        val displayMetrics = DisplayMetrics()
        val location = zoom_out_view.findViewById<TextView>(R.id.location_tv)
        val minprice = zoom_out_view.findViewById<TextView>(R.id.zoom_out_min_price_tv)
        val count = zoom_out_view.findViewById<TextView>(R.id.count_tv)
        location.text = dongName
        minprice.text = minPrice.toString()
        count.text = roomNum.toString() + "건"
        zoom_out_view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        zoom_out_view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels)
        zoom_out_view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels)
        val bitmap = Bitmap.createBitmap(zoom_out_view.measuredWidth, zoom_out_view.measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        zoom_out_view.draw(canvas)
        return bitmap
    }

    override fun onMapViewInitialized(p0: MapView?) {
        Log.d("MapView", "Called MapView")
        var mapPoint = MapPoint.mapPointWithGeoCoord(37.566352778, 126.977952778)
        p0!!.setMapCenterPointAndZoomLevel(mapPoint, 3,false)

//        initializeMapView()

//        var latitude = p0.mapCenterPoint.mapPointGeoCoord.latitude
//        var longitude = p0.mapCenterPoint.mapPointGeoCoord.longitude
//        var lbLatitude = p0.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude
//        var lbLongitude = p0.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude
//        var rtLatitude = p0.mapPointBounds.topRight.mapPointGeoCoord.latitude
//        var rtLongitude = p0.mapPointBounds.topRight.mapPointGeoCoord.longitude
//
//        mapView.getZoomOut(latitude, longitude, null, null, null, null, null, null, null, null, null,  lbLatitude, lbLongitude, rtLatitude, rtLongitude)
//
//        for (i in zoomOutList) {
//            val markerMapPoint = MapPoint.mapPointWithGeoCoord(i.lat, i.lng)
//            if (p0.mapPointBounds.contains(markerMapPoint)) {
//                var mapMarker = MapPOIItem()
//                mapMarker.apply {
//                    itemName = i.dongName
//                    tag = i.roomNum
//                    mapPoint = markerMapPoint
//                    markerType = MapPOIItem.MarkerType.CustomImage
//                    customImageBitmap = Bitmap.createBitmap(viewConvertToBitmapZoomOut(i.dongName, i.minPrice, i.roomNum))
//                    isShowCalloutBalloonOnTouch = false
//                }
//                p0.addPOIItem(mapMarker)
//            }
//        }
        Log.d("MapPointBounds", p0.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude.toString() + ", "
                + p0.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude.toString() + ", "
                + p0.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude.toString() + ", "
                + p0.mapPointBounds.topRight.mapPointGeoCoord.longitude.toString())
    }

    override fun onMapViewCenterPointMoved(p0: MapView?, p1: MapPoint?) {
        if (p0 != null) {
//            val zoomInMarker = mutableListOf<MapPOIItem>()
//            var zoomOutMarker = mutableListOf<MapPOIItem>()
//
//            // Zoom Level이 3~4인 경우
//            if (p0.zoomLevel in 3..4) {
//                // 기존 Zoom In 마커 제거
//                for (marker in p0.poiItems) {
//                    p0.removePOIItem(marker)
//                }
//                zoomInMarker.clear()
//
//                // Zoom Out 마커 추가
//                for (i in zoomOutList) {
//                    var markerMapPoint = MapPoint.mapPointWithGeoCoord(i.lat, i.lng)
//                    var mapMarker = MapPOIItem()
//                    mapMarker.apply {
//                        itemName = i.dongName
//                        tag = i.roomNum
//                        mapPoint = markerMapPoint
//                        markerType = MapPOIItem.MarkerType.CustomImage
//                        customImageBitmap = Bitmap.createBitmap(viewConvertToBitmapZoomOut(i.dongName, i.minPrice, i.roomNum))
//                        isShowCalloutBalloonOnTouch = false
//                    }
//                    if (p0.mapPointBounds.contains(markerMapPoint)) {
//                        p0.addPOIItem(mapMarker)
//                        zoomOutMarker.add(mapMarker)
//                    } else
//                        p0.removePOIItem(mapMarker)
//                }
//            }
//
//            // Zoom Level이 1~2인 경우
//            else if (p0.zoomLevel in 1..2) {
//                // 기존 Zoom Out 마커 제거
//                for (marker in p0.poiItems) {
//                    p0.removePOIItem(marker)
//                }
//                zoomOutMarker.clear()
//
//                // Zoom In 마커 추가
//                for (i in zoomInList) {
//                    val markerMapPoint = MapPoint.mapPointWithGeoCoord(i.lat, i.lng)
//                    var mapMarker = MapPOIItem()
//                    mapMarker.apply {
//                        itemName = i.id
//                        tag = i.id.toInt()
//                        mapPoint = markerMapPoint
//                        markerType = MapPOIItem.MarkerType.CustomImage
//                        customImageBitmap = Bitmap.createBitmap(viewConvertToBitmapZoomIn(i.size, i.price))
//                        isShowCalloutBalloonOnTouch = false
//                    }
//                    if (p0.mapPointBounds.contains(markerMapPoint)) {
//                        p0.addPOIItem(mapMarker)
//                        zoomInMarker.add(mapMarker)
//                    } else
//                        p0.removePOIItem(mapMarker)
//                }
//            }
            Log.d("MapCenterMoved", "Map Moved ${p0.mapCenterPoint.mapPointGeoCoord.latitude}/${p0.mapCenterPoint.mapPointGeoCoord.longitude}")
        }
    }

    override fun onMapViewZoomLevelChanged(p0: MapView?, p1: Int) {
        if (p0 != null) {
//            val zoomInMarker = mutableListOf<MapPOIItem>()
//            var zoomOutMarker = mutableListOf<MapPOIItem>()
//
//            // Zoom Level이 3~4인 경우
//            if (p0.zoomLevel in 3..4) {
//                // 기존 Zoom In 마커 제거
//                for (marker in p0.poiItems) {
//                    p0.removePOIItem(marker)
//                }
//                zoomInMarker.clear()
//
//                // Zoom Out 마커 추가
//                for (i in zoomOutList) {
//                    var markerMapPoint = MapPoint.mapPointWithGeoCoord(i.lat, i.lng)
//                    var mapMarker = MapPOIItem()
//                    mapMarker.apply {
//                        itemName = i.dongName
//                        tag = i.roomNum
//                        mapPoint = markerMapPoint
//                        markerType = MapPOIItem.MarkerType.CustomImage
//                        customImageBitmap = Bitmap.createBitmap(viewConvertToBitmapZoomOut(i.dongName, i.minPrice, i.roomNum))
//                        isShowCalloutBalloonOnTouch = false
//                    }
//                    if (p0.mapPointBounds.contains(markerMapPoint)) {
//                        p0.addPOIItem(mapMarker)
//                        zoomOutMarker.add(mapMarker)
//                    } else
//                        p0.removePOIItem(mapMarker)
//                }
//            }
//
//            // Zoom Level이 1~2인 경우
//            else if (p0.zoomLevel in 1..2) {
//                // 기존 Zoom Out 마커 제거
//                for (marker in p0.poiItems) {
//                    p0.removePOIItem(marker)
//                }
//                zoomOutMarker.clear()
//
//                // Zoom In 마커 추가
//                for (i in zoomInList) {
//                    val markerMapPoint = MapPoint.mapPointWithGeoCoord(i.lat, i.lng)
//                    var mapMarker = MapPOIItem()
//                    mapMarker.apply {
//                        itemName = i.id
//                        tag = i.id.toInt()
//                        mapPoint = markerMapPoint
//                        markerType = MapPOIItem.MarkerType.CustomImage
//                        customImageBitmap = Bitmap.createBitmap(viewConvertToBitmapZoomIn(i.size, i.price))
//                        isShowCalloutBalloonOnTouch = false
//                    }
//                    if (p0.mapPointBounds.contains(markerMapPoint)) {
//                        p0.addPOIItem(mapMarker)
//                        zoomInMarker.add(mapMarker)
//                    } else
//                        p0.removePOIItem(mapMarker)
//                }
//            }

            Log.d("MapZoom", "MapZoom changed $p1")
        }
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
    override fun onZoomOutSuccess(response: MapZoomOutResponse) {
        zoomOutList.add(response)
        Log.d("ZoomOut/SUCCESS", "MapZoomOutResponse succeed")
    }

    override fun onZoomOutFailure(message: String) {
        Log.d("ZoomOut/FAILURE", message)
    }

    override fun onZoomInSuccess(response: MapZoomInResponse) {
        zoomInList.add(response)
        Log.d("ZoomIn/SUCCESS", "MapZoomInResponse succeed")
    }

    override fun onZoomInFailure(message: String) {
        Log.d("ZoomIn/FAILURE", message)
    }

    override fun onCategorySuccess(place: Place) {
        infraList.add(place)
    }

    override fun onCategoryFailure(code: Int, message: String) {
        Log.d("KAKAO/FAILURE", "인프라를 가져올 수 없습니다.")
    }

    override fun onKeyWordSuccess(resultSearchKeyword: ResultSearchKeyword) {
        TODO("Not yet implemented")
    }

    override fun onKeyWordFailure(code: Int, message: String) {
        TODO("Not yet implemented")
    }
}