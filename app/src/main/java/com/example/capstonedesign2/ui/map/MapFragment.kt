package com.example.capstonedesign2.ui.map

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.capstonedesign2.R
import com.example.capstonedesign2.data.entities.Filter
import com.example.capstonedesign2.data.remote.*
import com.example.capstonedesign2.databinding.FragmentMapBinding
import com.example.capstonedesign2.ui.detail.DetailActivity
import com.example.capstonedesign2.ui.map.search.ResultActivity
import com.example.capstonedesign2.ui.map.search.SearchActivity
import com.google.gson.Gson
import net.daum.mf.map.api.MapView
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView.MapViewEventListener
import net.daum.mf.map.api.MapView.POIItemEventListener

class MapFragment() : Fragment() , MapViewEventListener, MapDataView, KaKaoView, POIItemEventListener {
    lateinit var binding: FragmentMapBinding
    lateinit var spf: SharedPreferences
    private var markerList = ArrayList<MapPOIItem>()
    private var kakaoView = KaKaoService()
    private var mapDataView = RoomService()
    private var gson = Gson()
    private var infraSubway = false
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

        spf = this.requireContext().getSharedPreferences("filter", AppCompatActivity.MODE_PRIVATE)
        gson = Gson()

        mapDataView = RoomService()
        kakaoView = KaKaoService()
        mapDataView.setMapDataView(this)
        kakaoView.setKaKaoView(this)

        binding.mapView.setPOIItemEventListener(this)

        onClickListener()

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        setFilter()
        binding.mapView.setMapViewEventListener(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            for (i in binding.mapView.poiItems) {
                binding.mapView.removePOIItem(i)
            }

            Log.d("MapRect", "${binding.mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude},${binding.mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude}" +
                    ",${binding.mapView.mapPointBounds.topRight.mapPointGeoCoord.longitude},${binding.mapView.mapPointBounds.topRight.mapPointGeoCoord.latitude}")

            val filter: Filter = setFilter()
            val minRent = filter.minRent
            val maxRent = filter.maxRent
            val type = filter.type
            val minSize = filter.minSize
            val maxSize = filter.maxSize
            val minDeposit = filter.minDeposit
            val maxDeposit = filter.maxDeposit
            val minManage = filter.minManage
            val maxManage = filter.maxManage
            val minFloor = filter.minFloor
            val maxFloor = filter.maxFloor

            Log.d("Filter", "minRent: $minRent, maxRent: $maxRent, type: $type, minSize: $minSize, maxSize: $maxSize," +
                    "minDeposit: $minDeposit, maxDeposit: $maxDeposit, minManage: $minManage, maxManage: $maxManage, minFloor: $minFloor, maxFloor: $maxFloor")

            if (minRent != null && maxRent != null && minSize != null && maxSize != null && minDeposit != null
                && maxDeposit != null && minManage != null && maxManage != null

            ) {
                mapDataView.getZoomOut(
                    binding.mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude,
                    binding.mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude,
                    binding.mapView.mapPointBounds.topRight.mapPointGeoCoord.latitude,
                    binding.mapView.mapPointBounds.topRight.mapPointGeoCoord.longitude,
                    zoomin = 0,
                    minRent,
                    maxRent,
                    type,
                    minSize,
                    maxSize,
                    minDeposit,
                    maxDeposit,
                    minManage,
                    maxManage,
                    minFloor,
                    maxFloor
                )
            }
        }
    }

    override fun onMapViewInitialized(p0: MapView?) {
        if (p0 != null) {
            Log.d("MapDataView", "Called MapDataView")
            var mapPoint = MapPoint.mapPointWithGeoCoord(37.566352778, 126.977952778)
            p0.setMapCenterPointAndZoomLevel(mapPoint, 4, false)

            Log.d("MapRect", "${p0.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude},${p0.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude}" +
                    ",${p0.mapPointBounds.topRight.mapPointGeoCoord.longitude},${p0.mapPointBounds.topRight.mapPointGeoCoord.latitude}")

            val filter: Filter = setFilter()
            val minRent = filter.minRent
            val maxRent = filter.maxRent
            val type = filter.type
            val minSize = filter.minSize
            val maxSize = filter.maxSize
            val minDeposit = filter.minDeposit
            val maxDeposit = filter.maxDeposit
            val minManage = filter.minManage
            val maxManage = filter.maxManage
            val minFloor = filter.minFloor
            val maxFloor = filter.maxFloor

            Log.d("Filter", "minRent: $minRent, maxRent: $maxRent, type: $type, minSize: $minSize, maxSize: $maxSize," +
                    "minDeposit: $minDeposit, maxDeposit: $maxDeposit, minManage: $minManage, maxManage: $maxManage, minFloor: $minFloor, maxFloor: $maxFloor")

            if (minRent != null && maxRent != null && minSize != null && maxSize != null && minDeposit != null
                && maxDeposit != null && minManage != null && maxManage != null

            ) {
                mapDataView.getZoomOut(
                    p0.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude,
                    p0.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude,
                    p0.mapPointBounds.topRight.mapPointGeoCoord.latitude,
                    p0.mapPointBounds.topRight.mapPointGeoCoord.longitude,
                    zoomin = 0,
                    minRent,
                    maxRent,
                    type,
                    minSize,
                    maxSize,
                    minDeposit,
                    maxDeposit,
                    minManage,
                    maxManage,
                    minFloor,
                    maxFloor
                )
            }
        }
    }

    override fun onMapViewCenterPointMoved(p0: MapView?, p1: MapPoint?) {
        if (p0 != null) {
            Log.d("MapZoomLevel", "${p0.zoomLevel}")

            if (infraFitness) {
                for (i in p0.poiItems) {
                    if (p0.mapPointBounds.contains(i.mapPoint)) {

                    } else {
                        p0.removePOIItem(i)
                    }
                }
                kakaoView.getSearchKeyword(
                    "헬스클럽",
                    "${p0.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude},${p0.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude}" +
                            ",${p0.mapPointBounds.topRight.mapPointGeoCoord.longitude},${p0.mapPointBounds.topRight.mapPointGeoCoord.latitude}"
                )
            } else if (infraSubway) {
                for (i in p0.poiItems) {
                    if (p0.mapPointBounds.contains(i.mapPoint)) {

                    } else {
                        p0.removePOIItem(i)
                    }
                }
                kakaoView.searchPlacesByCategory(
                    "SW8",
                    "${p0.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude},${p0.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude}" +
                            ",${p0.mapPointBounds.topRight.mapPointGeoCoord.longitude},${p0.mapPointBounds.topRight.mapPointGeoCoord.latitude}"
                )
            } else if (infraBicycle) {
                for (i in p0.poiItems) {
                    if (p0.mapPointBounds.contains(i.mapPoint)) {

                    } else {
                        p0.removePOIItem(i)
                    }
                }
                kakaoView.getSearchKeyword(
                    "자전거대여소",
                    "${p0.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude},${p0.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude}" +
                            ",${p0.mapPointBounds.topRight.mapPointGeoCoord.longitude},${p0.mapPointBounds.topRight.mapPointGeoCoord.latitude}"
                )
            } else if (infraStore) {
                for (i in p0.poiItems) {
                    if (p0.mapPointBounds.contains(i.mapPoint)) {

                    } else {
                        p0.removePOIItem(i)
                    }
                }
                kakaoView.searchPlacesByCategory(
                    "MT1",
                    "${p0.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude},${p0.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude}" +
                            ",${p0.mapPointBounds.topRight.mapPointGeoCoord.longitude},${p0.mapPointBounds.topRight.mapPointGeoCoord.latitude}"
                )
            } else if (infraConvenience) {
                for (i in p0.poiItems) {
                    if (p0.mapPointBounds.contains(i.mapPoint)) {

                    } else {
                        p0.removePOIItem(i)
                    }
                }
                kakaoView.searchPlacesByCategory(
                    "CS2",
                    "${p0.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude},${p0.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude}" +
                            ",${p0.mapPointBounds.topRight.mapPointGeoCoord.longitude},${p0.mapPointBounds.topRight.mapPointGeoCoord.latitude}"
                )
            } else if (infraRestaurant) {
                for (i in p0.poiItems) {
                    if (p0.mapPointBounds.contains(i.mapPoint)) {

                    } else {
                        p0.removePOIItem(i)
                    }
                }
                kakaoView.searchPlacesByCategory(
                    "FD6",
                    "${p0.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude},${p0.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude}" +
                            ",${p0.mapPointBounds.topRight.mapPointGeoCoord.longitude},${p0.mapPointBounds.topRight.mapPointGeoCoord.latitude}"
                )
            } else if (infraCafe) {
                for (i in p0.poiItems) {
                    if (p0.mapPointBounds.contains(i.mapPoint)) {

                    } else {
                        p0.removePOIItem(i)
                    }
                }
                kakaoView.searchPlacesByCategory(
                    "CE7",
                    "${p0.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude},${p0.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude}" +
                            ",${p0.mapPointBounds.topRight.mapPointGeoCoord.longitude},${p0.mapPointBounds.topRight.mapPointGeoCoord.latitude}"
                )
            }else {
                val filter = setFilter()
                val minRent = filter.minRent
                val maxRent = filter.maxRent
                val type = filter.type
                val minSize = filter.minSize
                val maxSize = filter.maxSize
                val minDeposit = filter.minDeposit
                val maxDeposit = filter.maxDeposit
                val minManage = filter.minManage
                val maxManage = filter.maxManage
                val minFloor = filter.minFloor
                val maxFloor = filter.maxFloor

                Log.d("Filter", "minRent: $minRent, maxRent: $maxRent, type: $type, minSize: $minSize, maxSize: $maxSize," +
                        "minDeposit: $minDeposit, maxDeposit: $maxDeposit, minManage: $minManage, maxManage: $maxManage, minFloor: $minFloor, maxFloor: $maxFloor")

                if (p0.zoomLevel in 3..4) {
                    p0.removeAllPOIItems()

                    if (minRent != null && maxRent != null && minSize != null && maxSize != null && minDeposit != null
                        && maxDeposit != null && minManage != null && maxManage != null

                    ) {
                        mapDataView.getZoomOut(
                            p0.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude,
                            p0.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude,
                            p0.mapPointBounds.topRight.mapPointGeoCoord.latitude,
                            p0.mapPointBounds.topRight.mapPointGeoCoord.longitude,
                            zoomin = 0,
                            minRent,
                            maxRent,
                            type,
                            minSize,
                            maxSize,
                            minDeposit,
                            maxDeposit,
                            minManage,
                            maxManage,
                            minFloor,
                            maxFloor
                        )
                    }
                }
                // Zoom Level이 1~2인 경우
                else if (p0.zoomLevel <= 2) {
                    p0.removeAllPOIItems()

                    if (minRent != null && maxRent != null && minSize != null && maxSize != null && minDeposit != null
                        && maxDeposit != null && minManage != null && maxManage != null
                    ) {

                        mapDataView.getZoomIn(
                            p0.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude,
                            p0.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude,
                            p0.mapPointBounds.topRight.mapPointGeoCoord.latitude,
                            p0.mapPointBounds.topRight.mapPointGeoCoord.longitude,
                            zoomin = 1,
                            minRent,
                            maxRent,
                            type,
                            minSize,
                            maxSize,
                            minDeposit,
                            maxDeposit,
                            minManage,
                            maxManage,
                            minFloor,
                            maxFloor
                        )

                    }
                } else {
                    p0.removeAllPOIItems()
                }
            }
        }
    }

    override fun onMapViewZoomLevelChanged(p0: MapView?, p1: Int) {
        if (p0 != null) {

            if (infraFitness) {
                for (i in p0.poiItems) {
                    if (p0.mapPointBounds.contains(i.mapPoint)) {

                    } else {
                        p0.removePOIItem(i)
                    }
                }
                kakaoView.getSearchKeyword(
                    "헬스클럽",
                    "${p0.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude},${p0.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude}" +
                            ",${p0.mapPointBounds.topRight.mapPointGeoCoord.longitude},${p0.mapPointBounds.topRight.mapPointGeoCoord.latitude}"
                )
            } else if (infraSubway) {
                for (i in p0.poiItems) {
                    if (p0.mapPointBounds.contains(i.mapPoint)) {

                    } else {
                        p0.removePOIItem(i)
                    }
                }
                kakaoView.searchPlacesByCategory(
                    "SW8",
                    "${p0.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude},${p0.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude}" +
                            ",${p0.mapPointBounds.topRight.mapPointGeoCoord.longitude},${p0.mapPointBounds.topRight.mapPointGeoCoord.latitude}"
                )
            } else if (infraBicycle) {
                for (i in p0.poiItems) {
                    if (p0.mapPointBounds.contains(i.mapPoint)) {

                    } else {
                        p0.removePOIItem(i)
                    }
                }
                kakaoView.getSearchKeyword(
                    "자전거대여소",
                    "${p0.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude},${p0.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude}" +
                            ",${p0.mapPointBounds.topRight.mapPointGeoCoord.longitude},${p0.mapPointBounds.topRight.mapPointGeoCoord.latitude}"
                )
            } else if (infraStore) {
                for (i in p0.poiItems) {
                    if (p0.mapPointBounds.contains(i.mapPoint)) {

                    } else {
                        p0.removePOIItem(i)
                    }
                }
                kakaoView.searchPlacesByCategory(
                    "MT1",
                    "${p0.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude},${p0.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude}" +
                            ",${p0.mapPointBounds.topRight.mapPointGeoCoord.longitude},${p0.mapPointBounds.topRight.mapPointGeoCoord.latitude}"
                )
            } else if (infraConvenience) {
                for (i in p0.poiItems) {
                    if (p0.mapPointBounds.contains(i.mapPoint)) {

                    } else {
                        p0.removePOIItem(i)
                    }
                }
                kakaoView.searchPlacesByCategory(
                    "CS2",
                    "${p0.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude},${p0.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude}" +
                            ",${p0.mapPointBounds.topRight.mapPointGeoCoord.longitude},${p0.mapPointBounds.topRight.mapPointGeoCoord.latitude}"
                )
            } else if (infraRestaurant) {
                for (i in p0.poiItems) {
                    if (p0.mapPointBounds.contains(i.mapPoint)) {

                    } else {
                        p0.removePOIItem(i)
                    }
                }
                kakaoView.searchPlacesByCategory(
                    "FD6",
                    "${p0.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude},${p0.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude}" +
                            ",${p0.mapPointBounds.topRight.mapPointGeoCoord.longitude},${p0.mapPointBounds.topRight.mapPointGeoCoord.latitude}"
                )
            } else if (infraCafe) {
                for (i in p0.poiItems) {
                    if (p0.mapPointBounds.contains(i.mapPoint)) {

                    } else {
                        p0.removePOIItem(i)
                    }
                }
                kakaoView.searchPlacesByCategory(
                    "CE7",
                    "${p0.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude},${p0.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude}" +
                            ",${p0.mapPointBounds.topRight.mapPointGeoCoord.longitude},${p0.mapPointBounds.topRight.mapPointGeoCoord.latitude}"
                )
            } else {

                val filter = setFilter()
                val minRent = filter.minRent
                val maxRent = filter.maxRent
                val type = filter.type
                val minSize = filter.minSize
                val maxSize = filter.maxSize
                val minDeposit = filter.minDeposit
                val maxDeposit = filter.maxDeposit
                val minManage = filter.minManage
                val maxManage = filter.maxManage
                val minFloor = filter.minFloor
                val maxFloor = filter.maxFloor

                Log.d("Filter", "minRent: $minRent, maxRent: $maxRent, type: $type, minSize: $minSize, maxSize: $maxSize," +
                        "minDeposit: $minDeposit, maxDeposit: $maxDeposit, minManage: $minManage, maxManage: $maxManage, minFloor: $minFloor, maxFloor: $maxFloor")


                if (p1 in 3..4) {
                    p0.removeAllPOIItems()


                    if (minRent != null && maxRent != null && minSize != null && maxSize != null && minDeposit != null
                        && maxDeposit != null && minManage != null && maxManage != null

                    ) {

                        mapDataView.getZoomOut(
                            p0.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude,
                            p0.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude,
                            p0.mapPointBounds.topRight.mapPointGeoCoord.latitude,
                            p0.mapPointBounds.topRight.mapPointGeoCoord.longitude,
                            zoomin = 0,
                            minRent,
                            maxRent,
                            type,
                            minSize,
                            maxSize,
                            minDeposit,
                            maxDeposit,
                            minManage,
                            maxManage,
                            minFloor,
                            maxFloor
                        )
                    }

                }
                // Zoom Level이 1~2인 경우
                else if (p1 <= 2) {
                    p0.removeAllPOIItems()

                    if (minRent != null && maxRent != null && minSize != null && maxSize != null && minDeposit != null
                        && maxDeposit != null && minManage != null && maxManage != null
                    ) {

                        mapDataView.getZoomIn(
                            p0.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude,
                            p0.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude,
                            p0.mapPointBounds.topRight.mapPointGeoCoord.latitude,
                            p0.mapPointBounds.topRight.mapPointGeoCoord.longitude,
                            zoomin = 1,
                            minRent,
                            maxRent,
                            type,
                            minSize,
                            maxSize,
                            minDeposit,
                            maxDeposit,
                            minManage,
                            maxManage,
                            minFloor,
                            maxFloor
                        )

                    }
                } else {
                    p0.removeAllPOIItems()
                }
            }
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
                binding.mapView.removeAllPOIItems()

                kakaoView.searchPlacesByCategory("SW8", "${binding.mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude}" +
                        ",${binding.mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude}" +
                        ",${binding.mapView.mapPointBounds.topRight.mapPointGeoCoord.longitude}" +
                        ",${binding.mapView.mapPointBounds.topRight.mapPointGeoCoord.latitude}")

                infraSubway = true
                infraBicycle = false
                infraStore = false
                infraConvenience = false
                infraCafe = false
                infraFitness = false
                infraRestaurant = false
                infraClick = false
            } else if (infraSubway) {
                binding.mapView.removeAllPOIItems()
                binding.infraList.visibility = View.GONE
                val filter = setFilter()
                val minRent = filter.minRent
                val maxRent = filter.maxRent
                val type = filter.type
                val minSize = filter.minSize
                val maxSize = filter.maxSize
                val minDeposit = filter.minDeposit
                val maxDeposit = filter.maxDeposit
                val minManage = filter.minManage
                val maxManage = filter.maxManage
                val minFloor = filter.minFloor
                val maxFloor = filter.maxFloor

                if (binding.mapView.zoomLevel in 3..4) {

                    if (minRent != null && maxRent != null && minSize != null && maxSize != null && minDeposit != null
                        && maxDeposit != null && minManage != null && maxManage != null

                    ) {
                        mapDataView.getZoomOut(
                            binding.mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude,
                            binding.mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude,
                            binding.mapView.mapPointBounds.topRight.mapPointGeoCoord.latitude,
                            binding.mapView.mapPointBounds.topRight.mapPointGeoCoord.longitude,
                            zoomin = 0,
                            minRent,
                            maxRent,
                            type,
                            minSize,
                            maxSize,
                            minDeposit,
                            maxDeposit,
                            minManage,
                            maxManage,
                            minFloor,
                            maxFloor
                        )
                    }
                }
                // Zoom Level이 1~2인 경우
                else if (binding.mapView.zoomLevel <= 2) {

                    if (minRent != null && maxRent != null && minSize != null && maxSize != null && minDeposit != null
                        && maxDeposit != null && minManage != null && maxManage != null
                    ) {
                        mapDataView.getZoomIn(
                            binding.mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude,
                            binding.mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude,
                            binding.mapView.mapPointBounds.topRight.mapPointGeoCoord.latitude,
                            binding.mapView.mapPointBounds.topRight.mapPointGeoCoord.longitude,
                            zoomin = 1,
                            minRent,
                            maxRent,
                            type,
                            minSize,
                            maxSize,
                            minDeposit,
                            maxDeposit,
                            minManage,
                            maxManage,
                            minFloor,
                            maxFloor
                        )

                    }
                }
                infraSubway = false
                infraClick = false
            }
            Log.d("infra_subway", infraSubway.toString())
        }

        binding.bicycleBt.setOnClickListener {
            if (!infraBicycle) {
                markerList.addAll(binding.mapView.poiItems)
                binding.mapView.removeAllPOIItems()
                binding.infraList.visibility = View.GONE
                kakaoView.getSearchKeyword("자전거대여소", "${binding.mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude}" +
                        ",${binding.mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude}" +
                        ",${binding.mapView.mapPointBounds.topRight.mapPointGeoCoord.longitude}" +
                        ",${binding.mapView.mapPointBounds.topRight.mapPointGeoCoord.latitude}")
                infraSubway = false
                infraBicycle = true
                infraStore = false
                infraConvenience = false
                infraCafe = false
                infraFitness = false
                infraRestaurant = false
                infraClick = false
            } else if (infraBicycle) {
                binding.mapView.removeAllPOIItems()
                binding.infraList.visibility = View.GONE
                val filter = setFilter()
                val minRent = filter.minRent
                val maxRent = filter.maxRent
                val type = filter.type
                val minSize = filter.minSize
                val maxSize = filter.maxSize
                val minDeposit = filter.minDeposit
                val maxDeposit = filter.maxDeposit
                val minManage = filter.minManage
                val maxManage = filter.maxManage
                val minFloor = filter.minFloor
                val maxFloor = filter.maxFloor

                if (binding.mapView.zoomLevel in 3..4) {

                    if (minRent != null && maxRent != null && minSize != null && maxSize != null && minDeposit != null
                        && maxDeposit != null && minManage != null && maxManage != null

                    ) {
                        mapDataView.getZoomOut(
                            binding.mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude,
                            binding.mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude,
                            binding.mapView.mapPointBounds.topRight.mapPointGeoCoord.latitude,
                            binding.mapView.mapPointBounds.topRight.mapPointGeoCoord.longitude,
                            zoomin = 0,
                            minRent,
                            maxRent,
                            type,
                            minSize,
                            maxSize,
                            minDeposit,
                            maxDeposit,
                            minManage,
                            maxManage,
                            minFloor,
                            maxFloor
                        )
                    }
                }
                // Zoom Level이 1~2인 경우
                else if (binding.mapView.zoomLevel <= 2) {

                    if (minRent != null && maxRent != null && minSize != null && maxSize != null && minDeposit != null
                        && maxDeposit != null && minManage != null && maxManage != null
                    ) {
                        mapDataView.getZoomIn(
                            binding.mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude,
                            binding.mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude,
                            binding.mapView.mapPointBounds.topRight.mapPointGeoCoord.latitude,
                            binding.mapView.mapPointBounds.topRight.mapPointGeoCoord.longitude,
                            zoomin = 1,
                            minRent,
                            maxRent,
                            type,
                            minSize,
                            maxSize,
                            minDeposit,
                            maxDeposit,
                            minManage,
                            maxManage,
                            minFloor,
                            maxFloor
                        )

                    }
                }
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
                kakaoView.searchPlacesByCategory("FD6", "${binding.mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude}" +
                        ",${binding.mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude}" +
                        ",${binding.mapView.mapPointBounds.topRight.mapPointGeoCoord.longitude}" +
                        ",${binding.mapView.mapPointBounds.topRight.mapPointGeoCoord.latitude}")
                infraSubway = false
                infraBicycle = false
                infraStore = false
                infraConvenience = false
                infraCafe = false
                infraFitness = false
                infraRestaurant = true
                infraClick = false
            } else if (infraRestaurant) {
                binding.mapView.removeAllPOIItems()
                binding.infraList.visibility = View.GONE
                val filter = setFilter()
                val minRent = filter.minRent
                val maxRent = filter.maxRent
                val type = filter.type
                val minSize = filter.minSize
                val maxSize = filter.maxSize
                val minDeposit = filter.minDeposit
                val maxDeposit = filter.maxDeposit
                val minManage = filter.minManage
                val maxManage = filter.maxManage
                val minFloor = filter.minFloor
                val maxFloor = filter.maxFloor

                if (binding.mapView.zoomLevel in 3..4) {

                    if (minRent != null && maxRent != null && minSize != null && maxSize != null && minDeposit != null
                        && maxDeposit != null && minManage != null && maxManage != null

                    ) {
                        mapDataView.getZoomOut(
                            binding.mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude,
                            binding.mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude,
                            binding.mapView.mapPointBounds.topRight.mapPointGeoCoord.latitude,
                            binding.mapView.mapPointBounds.topRight.mapPointGeoCoord.longitude,
                            zoomin = 0,
                            minRent,
                            maxRent,
                            type,
                            minSize,
                            maxSize,
                            minDeposit,
                            maxDeposit,
                            minManage,
                            maxManage,
                            minFloor,
                            maxFloor
                        )
                    }
                }
                // Zoom Level이 1~2인 경우
                else if (binding.mapView.zoomLevel <= 2) {

                    if (minRent != null && maxRent != null && minSize != null && maxSize != null && minDeposit != null
                        && maxDeposit != null && minManage != null && maxManage != null
                    ) {
                        mapDataView.getZoomIn(
                            binding.mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude,
                            binding.mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude,
                            binding.mapView.mapPointBounds.topRight.mapPointGeoCoord.latitude,
                            binding.mapView.mapPointBounds.topRight.mapPointGeoCoord.longitude,
                            zoomin = 1,
                            minRent,
                            maxRent,
                            type,
                            minSize,
                            maxSize,
                            minDeposit,
                            maxDeposit,
                            minManage,
                            maxManage,
                            minFloor,
                            maxFloor
                        )

                    }
                }
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
                kakaoView.searchPlacesByCategory("CE7", "${binding.mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude}" +
                        ",${binding.mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude}" +
                        ",${binding.mapView.mapPointBounds.topRight.mapPointGeoCoord.longitude}" +
                        ",${binding.mapView.mapPointBounds.topRight.mapPointGeoCoord.latitude}")
                infraSubway = false
                infraBicycle = false
                infraStore = false
                infraConvenience = false
                infraCafe = true
                infraFitness = false
                infraRestaurant = false
                infraClick = false
            } else if (infraCafe) {
                binding.mapView.removeAllPOIItems()
                binding.infraList.visibility = View.GONE
                val filter = setFilter()
                val minRent = filter.minRent
                val maxRent = filter.maxRent
                val type = filter.type
                val minSize = filter.minSize
                val maxSize = filter.maxSize
                val minDeposit = filter.minDeposit
                val maxDeposit = filter.maxDeposit
                val minManage = filter.minManage
                val maxManage = filter.maxManage
                val minFloor = filter.minFloor
                val maxFloor = filter.maxFloor

                if (binding.mapView.zoomLevel in 3..4) {

                    if (minRent != null && maxRent != null && minSize != null && maxSize != null && minDeposit != null
                        && maxDeposit != null && minManage != null && maxManage != null

                    ) {
                        mapDataView.getZoomOut(
                            binding.mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude,
                            binding.mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude,
                            binding.mapView.mapPointBounds.topRight.mapPointGeoCoord.latitude,
                            binding.mapView.mapPointBounds.topRight.mapPointGeoCoord.longitude,
                            zoomin = 0,
                            minRent,
                            maxRent,
                            type,
                            minSize,
                            maxSize,
                            minDeposit,
                            maxDeposit,
                            minManage,
                            maxManage,
                            minFloor,
                            maxFloor
                        )
                    }
                }
                // Zoom Level이 1~2인 경우
                else if (binding.mapView.zoomLevel <= 2) {

                    if (minRent != null && maxRent != null && minSize != null && maxSize != null && minDeposit != null
                        && maxDeposit != null && minManage != null && maxManage != null
                    ) {
                        mapDataView.getZoomIn(
                            binding.mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude,
                            binding.mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude,
                            binding.mapView.mapPointBounds.topRight.mapPointGeoCoord.latitude,
                            binding.mapView.mapPointBounds.topRight.mapPointGeoCoord.longitude,
                            zoomin = 1,
                            minRent,
                            maxRent,
                            type,
                            minSize,
                            maxSize,
                            minDeposit,
                            maxDeposit,
                            minManage,
                            maxManage,
                            minFloor,
                            maxFloor
                        )

                    }
                }
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
                kakaoView.searchPlacesByCategory("MT1", "${binding.mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude}" +
                        ",${binding.mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude}" +
                        ",${binding.mapView.mapPointBounds.topRight.mapPointGeoCoord.longitude}" +
                        ",${binding.mapView.mapPointBounds.topRight.mapPointGeoCoord.latitude}")
                infraSubway = false
                infraBicycle = false
                infraStore = true
                infraConvenience = false
                infraCafe = false
                infraFitness = false
                infraRestaurant = false
                infraClick = false
            } else if (infraStore) {
                binding.mapView.removeAllPOIItems()
                binding.infraList.visibility = View.GONE
                val filter = setFilter()
                val minRent = filter.minRent
                val maxRent = filter.maxRent
                val type = filter.type
                val minSize = filter.minSize
                val maxSize = filter.maxSize
                val minDeposit = filter.minDeposit
                val maxDeposit = filter.maxDeposit
                val minManage = filter.minManage
                val maxManage = filter.maxManage
                val minFloor = filter.minFloor
                val maxFloor = filter.maxFloor

                if (binding.mapView.zoomLevel in 3..4) {

                    if (minRent != null && maxRent != null && minSize != null && maxSize != null && minDeposit != null
                        && maxDeposit != null && minManage != null && maxManage != null

                    ) {
                        mapDataView.getZoomOut(
                            binding.mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude,
                            binding.mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude,
                            binding.mapView.mapPointBounds.topRight.mapPointGeoCoord.latitude,
                            binding.mapView.mapPointBounds.topRight.mapPointGeoCoord.longitude,
                            zoomin = 0,
                            minRent,
                            maxRent,
                            type,
                            minSize,
                            maxSize,
                            minDeposit,
                            maxDeposit,
                            minManage,
                            maxManage,
                            minFloor,
                            maxFloor
                        )
                    }
                }
                // Zoom Level이 1~2인 경우
                else if (binding.mapView.zoomLevel <= 2) {

                    if (minRent != null && maxRent != null && minSize != null && maxSize != null && minDeposit != null
                        && maxDeposit != null && minManage != null && maxManage != null
                    ) {
                        mapDataView.getZoomIn(
                            binding.mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude,
                            binding.mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude,
                            binding.mapView.mapPointBounds.topRight.mapPointGeoCoord.latitude,
                            binding.mapView.mapPointBounds.topRight.mapPointGeoCoord.longitude,
                            zoomin = 1,
                            minRent,
                            maxRent,
                            type,
                            minSize,
                            maxSize,
                            minDeposit,
                            maxDeposit,
                            minManage,
                            maxManage,
                            minFloor,
                            maxFloor
                        )

                    }
                }
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
                kakaoView.searchPlacesByCategory("CS2", "${binding.mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude}" +
                        ",${binding.mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude}" +
                        ",${binding.mapView.mapPointBounds.topRight.mapPointGeoCoord.longitude}" +
                        ",${binding.mapView.mapPointBounds.topRight.mapPointGeoCoord.latitude}")
                infraSubway = false
                infraBicycle = false
                infraStore = false
                infraConvenience = true
                infraCafe = false
                infraFitness = false
                infraRestaurant = false
                infraClick = false
            } else if (infraConvenience) {
                binding.mapView.removeAllPOIItems()
                binding.infraList.visibility = View.GONE
                val filter = setFilter()
                val minRent = filter.minRent
                val maxRent = filter.maxRent
                val type = filter.type
                val minSize = filter.minSize
                val maxSize = filter.maxSize
                val minDeposit = filter.minDeposit
                val maxDeposit = filter.maxDeposit
                val minManage = filter.minManage
                val maxManage = filter.maxManage
                val minFloor = filter.minFloor
                val maxFloor = filter.maxFloor

                if (binding.mapView.zoomLevel in 3..4) {

                    if (minRent != null && maxRent != null && minSize != null && maxSize != null && minDeposit != null
                        && maxDeposit != null && minManage != null && maxManage != null

                    ) {
                        mapDataView.getZoomOut(
                            binding.mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude,
                            binding.mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude,
                            binding.mapView.mapPointBounds.topRight.mapPointGeoCoord.latitude,
                            binding.mapView.mapPointBounds.topRight.mapPointGeoCoord.longitude,
                            zoomin = 0,
                            minRent,
                            maxRent,
                            type,
                            minSize,
                            maxSize,
                            minDeposit,
                            maxDeposit,
                            minManage,
                            maxManage,
                            minFloor,
                            maxFloor
                        )
                    }
                }
                // Zoom Level이 1~2인 경우
                else if (binding.mapView.zoomLevel <= 2) {

                    if (minRent != null && maxRent != null && minSize != null && maxSize != null && minDeposit != null
                        && maxDeposit != null && minManage != null && maxManage != null
                    ) {
                        mapDataView.getZoomIn(
                            binding.mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude,
                            binding.mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude,
                            binding.mapView.mapPointBounds.topRight.mapPointGeoCoord.latitude,
                            binding.mapView.mapPointBounds.topRight.mapPointGeoCoord.longitude,
                            zoomin = 1,
                            minRent,
                            maxRent,
                            type,
                            minSize,
                            maxSize,
                            minDeposit,
                            maxDeposit,
                            minManage,
                            maxManage,
                            minFloor,
                            maxFloor
                        )

                    }
                }
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
                kakaoView.getSearchKeyword("헬스클럽", "${binding.mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude}" +
                        ",${binding.mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude}" +
                        ",${binding.mapView.mapPointBounds.topRight.mapPointGeoCoord.longitude}" +
                        ",${binding.mapView.mapPointBounds.topRight.mapPointGeoCoord.latitude}")
                infraSubway = false
                infraBicycle = false
                infraStore = false
                infraConvenience = false
                infraCafe = false
                infraFitness = true
                infraRestaurant = false
                infraClick = false
            } else if (infraFitness) {
                binding.mapView.removeAllPOIItems()
                binding.infraList.visibility = View.GONE
                val filter = setFilter()
                val minRent = filter.minRent
                val maxRent = filter.maxRent
                val type = filter.type
                val minSize = filter.minSize
                val maxSize = filter.maxSize
                val minDeposit = filter.minDeposit
                val maxDeposit = filter.maxDeposit
                val minManage = filter.minManage
                val maxManage = filter.maxManage
                val minFloor = filter.minFloor
                val maxFloor = filter.maxFloor

                if (binding.mapView.zoomLevel in 3..4) {

                    if (minRent != null && maxRent != null && minSize != null && maxSize != null && minDeposit != null
                        && maxDeposit != null && minManage != null && maxManage != null

                    ) {
                        mapDataView.getZoomOut(
                            binding.mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude,
                            binding.mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude,
                            binding.mapView.mapPointBounds.topRight.mapPointGeoCoord.latitude,
                            binding.mapView.mapPointBounds.topRight.mapPointGeoCoord.longitude,
                            zoomin = 0,
                            minRent,
                            maxRent,
                            type,
                            minSize,
                            maxSize,
                            minDeposit,
                            maxDeposit,
                            minManage,
                            maxManage,
                            minFloor,
                            maxFloor
                        )
                    }
                }
                // Zoom Level이 1~2인 경우
                else if (binding.mapView.zoomLevel <= 2) {

                    if (minRent != null && maxRent != null && minSize != null && maxSize != null && minDeposit != null
                        && maxDeposit != null && minManage != null && maxManage != null
                    ) {
                        mapDataView.getZoomIn(
                            binding.mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.latitude,
                            binding.mapView.mapPointBounds.bottomLeft.mapPointGeoCoord.longitude,
                            binding.mapView.mapPointBounds.topRight.mapPointGeoCoord.latitude,
                            binding.mapView.mapPointBounds.topRight.mapPointGeoCoord.longitude,
                            zoomin = 1,
                            minRent,
                            maxRent,
                            type,
                            minSize,
                            maxSize,
                            minDeposit,
                            maxDeposit,
                            minManage,
                            maxManage,
                            minFloor,
                            maxFloor
                        )

                    }
                }
                infraFitness = false
                infraClick = false
            }
            Log.d("infra_fitness", infraFitness.toString())
        }
    }

    private fun setFilter(): Filter {
        val rent = spf.getString("rent", "").toString()
        val area_min = spf.getInt("area_min", 0).toDouble()
        val area_max = spf.getInt("area_max", 0).toDouble()
        val price_min_d = spf.getInt("price_min_d", 0)
        val price_max_d = spf.getInt("price_max_d", 0)
        val price_min_m = spf.getInt("price_min_m", 0).toDouble()
        val price_max_m = spf.getInt("price_max_m", 0).toDouble()
        val price_min_a = spf.getInt("price_min_a", 0).toDouble()
        val price_max_a = spf.getInt("price_max_a", 0).toDouble()
        val floor = spf.getString("floor", "").toString()

        var rentType = -1
        if (rent != null) {
            if (rent.isNotEmpty()) {
                binding.rentTypeBt.text = rent
                binding.rentTypeBt.setTextColor(Color.parseColor("#C69C6D"))
                binding.rentTypeBt.setBackgroundResource(R.drawable.select_brown_round_shape)
                rentType = if (rent == "전세") {
                    0
                } else {
                    1
                }
            } else {
                binding.rentTypeBt.text = "전월세"
                binding.rentTypeBt.setTextColor(Color.WHITE)
                binding.rentTypeBt.setBackgroundResource(R.drawable.brown_round_shape)
            }
        }

        var minSize: Double = 0.0
        var maxSize: Double = 30961.0
        if (area_min != null && area_max != null){
            if (area_min != 0.0 && area_max != 0.0) {
                binding.areaBt.setTextColor(Color.parseColor("#C69C6D"))
                binding.areaBt.setBackgroundResource(R.drawable.select_brown_round_shape)
                minSize = area_min as Double
                maxSize = area_max as Double
            } else {
                binding.areaBt.setTextColor(Color.WHITE)
                binding.areaBt.setBackgroundResource(R.drawable.brown_round_shape)
            }
        }

        var minDeposit = 0
        var maxDeposit = 350000
        var minRent = 0.0
        var maxRent = 400000.0
        var minManage = 0.0
        var maxManage = 9999.0

        if (price_min_d != null && price_max_d != null && price_min_m != null && price_max_m != null && price_min_a != null && price_max_a != null){
            if (rent == "전세") {
                if (price_min_d != 0 && price_max_d != 0) {
                    binding.priceBt.setTextColor(Color.parseColor("#C69C6D"))
                    binding.priceBt.setBackgroundResource(R.drawable.select_brown_round_shape)
                    minDeposit = price_min_d as Int
                    maxDeposit = price_max_d as Int
                }
            } else {
                if (price_min_d != 0 && price_max_d != 0
                    && price_min_m != 0.0 && price_max_m != 0.0
                    && price_min_a != 0.0 && price_max_a != 0.0) {
                    binding.priceBt.setTextColor(Color.parseColor("#C69C6D"))
                    binding.priceBt.setBackgroundResource(R.drawable.select_brown_round_shape)
                    minDeposit = price_min_d as Int
                    maxDeposit = price_max_d as Int
                    minRent = price_min_a as Double
                    maxRent = price_max_a as Double
                    minManage = price_min_m as Double
                    maxManage = price_max_m as Double
                }
                else if (price_min_m != 0.0 && price_max_m != 0.0) {
                    binding.priceBt.setTextColor(Color.parseColor("#C69C6D"))
                    binding.priceBt.setBackgroundResource(R.drawable.select_brown_round_shape)
                    minManage = price_min_m as Double
                    maxManage = price_max_m as Double
                }
                else if (price_min_a != 0.0 && price_max_a != 0.0) {
                    binding.priceBt.setTextColor(Color.parseColor("#C69C6D"))
                    binding.priceBt.setBackgroundResource(R.drawable.select_brown_round_shape)
                    minRent = price_min_a as Double
                    maxRent = price_max_a as Double
                }
                else if (price_min_d != 0 && price_max_d != 0) {
                    binding.priceBt.setTextColor(Color.parseColor("#C69C6D"))
                    binding.priceBt.setBackgroundResource(R.drawable.select_brown_round_shape)
                    minDeposit = price_min_d as Int
                    maxDeposit = price_max_d as Int
                } else {
                    binding.priceBt.setTextColor(Color.WHITE)
                    binding.priceBt.setBackgroundResource(R.drawable.brown_round_shape)
                }
            }
        }

        var minFloor = -999
        var maxFloor = 999

        if (floor != null) {
            if (floor.isNotEmpty()) {
                binding.floorBt.text = floor
                binding.floorBt.setTextColor(Color.parseColor("#C69C6D"))
                binding.floorBt.setBackgroundResource(R.drawable.select_brown_round_shape)
                if (floor == "반지하") {
                    minFloor = -999
                    maxFloor = 0
                } else {
                    minFloor = 1
                    maxFloor = 999
                }
            } else {
                binding.floorBt.text = "층"
                binding.floorBt.setTextColor(Color.WHITE)
                binding.floorBt.setBackgroundResource(R.drawable.brown_round_shape)
            }
        }

        return Filter(minRent, maxRent, rentType, minSize, maxSize, minDeposit, maxDeposit, minManage, maxManage, minFloor, maxFloor)
    }

    private fun viewConvertToBitmapZoomIn(price: Double, size: Double): Bitmap {
        val zoom_in_view = layoutInflater.inflate(R.layout.zoomin_marker_layout, null)
        val displayMetrics = DisplayMetrics()
        val area = zoom_in_view.findViewById<TextView>(R.id.zoom_in_area_tv)
        val minprice = zoom_in_view.findViewById<TextView>(R.id.zoom_in_max_tv)
        area.text = size.toString()
        minprice.text = price.toString()
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
        minprice.text = "최저가\n$minPrice"
        count.text = "$roomNum 건"
        zoom_out_view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        zoom_out_view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels)
        zoom_out_view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels)
        val bitmap = Bitmap.createBitmap(zoom_out_view.measuredWidth, zoom_out_view.measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        zoom_out_view.draw(canvas)
        return bitmap
    }

    override fun onZoomOutSuccess(zoomOutList: ArrayList<MapZoomOut>) {
        for (i in zoomOutList) {
            var mapMarker = MapPOIItem()
            mapMarker.apply {
                itemName = i.dongName
                tag = i.ids.last().toInt()
                mapPoint = MapPoint.mapPointWithGeoCoord(i.lat, i.lng)
                markerType = MapPOIItem.MarkerType.CustomImage
                customImageBitmap = Bitmap.createBitmap(viewConvertToBitmapZoomOut(i.dongName, i.minPrice, i.roomNum))
                isShowCalloutBalloonOnTouch = false
            }
            binding.mapView.addPOIItem(mapMarker)

            Log.d("ZoomOUTData", i.dongName)
        }
        binding.mapView.invalidate()
        Log.d("ZOOMOUT/SUCCESS", "MapZoomOutResponse succeed")
    }

    override fun onZoomOutFailure(code: Int, message: String) {
        Log.d("ZOOMOUT/FAILURE", "$code/$message")
    }

    override fun onZoomInSuccess(zoomInList: ArrayList<MapZoomIn>) {
        for (i in zoomInList) {
            var mapMarker = MapPOIItem()
            mapMarker.apply {
                itemName = i.id
                tag = i.id.toInt()
                mapPoint = MapPoint.mapPointWithGeoCoord(i.lat, i.lng)
                markerType = MapPOIItem.MarkerType.CustomImage
                customImageBitmap = Bitmap.createBitmap(viewConvertToBitmapZoomIn(i.size, i.price))
                isShowCalloutBalloonOnTouch = false
            }
            binding.mapView.addPOIItem(mapMarker)

            Log.d("ZoomINData", i.id)
        }
        binding.mapView.invalidate()
        Log.d("ZOOMIN/SUCCESS", "MapZoomInResponse succeed")
    }

    override fun onZoomInFailure(code: Int, message: String) {
        Log.d("ZOOMIN/FAILURE", "$code/$message")
    }

    override fun onCategorySuccess(message: String, document: ArrayList<Document>) {
        for (i in document) {
            var marker = MapPOIItem()
            marker.apply {
                tag = i.id.toInt()
                itemName = i.placeName
                mapPoint = MapPoint.mapPointWithGeoCoord(i.y.toDouble(), i.x.toDouble())
                markerType = MapPOIItem.MarkerType.YellowPin
            }
            binding.mapView.addPOIItem(marker)
            Log.d("KAKAOINFRA/SUCCESS", i.category_group_name)
        }
        binding.mapView.invalidate()
    }

    override fun onCategoryFailure(message: String) {
        Log.d("KAKAOPLACE/FAILURE", "인프라를 가져올 수 없습니다./$message")
    }

    override fun onKeyWordSuccess(resultSearchKeyword: ResultSearchKeyword, message: String) {
        for (i in resultSearchKeyword.documents) {
            var marker = MapPOIItem()
            marker.apply {
                tag = i.id.toInt()
                itemName = i.placeName
                mapPoint = MapPoint.mapPointWithGeoCoord(i.y.toDouble(), i.x.toDouble())
                markerType = MapPOIItem.MarkerType.YellowPin
            }
            binding.mapView.addPOIItem(marker)
            Log.d("KAKAOINFRA/SUCCESS", i.category_group_name)
        }
        binding.mapView.invalidate()
    }

    override fun onKeyWordFailure(message: String) {
        Log.d("KAKAOPLACE/FAILURE", "인프라를 가져올 수 없습니다./$message")
    }

    override fun onPOIItemSelected(p0: MapView?, p1: MapPOIItem?) {
        val zoomOutIntent = Intent(this.context, ResultActivity::class.java)
        val zoomInIntent = Intent(this.context, DetailActivity::class.java)
        if (p0 != null && p1 !== null) {
            if (p0.zoomLevel in 3..4) {
//                zoomOutIntent.putExtra("search_full", p1.itemName)
//                startActivity(zoomOutIntent)
            } else if (p0.zoomLevel <= 2) {
                zoomInIntent.putExtra("roomId", p1.tag.toString())
                Log.d("PoiItemTag", p1.tag.toString())
                startActivity(zoomInIntent)
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onCalloutBalloonOfPOIItemTouched(p0: MapView?, p1: MapPOIItem?) {

    }

    override fun onCalloutBalloonOfPOIItemTouched(
        p0: MapView?,
        p1: MapPOIItem?,
        p2: MapPOIItem.CalloutBalloonButtonType?
    ) {

    }

    override fun onDraggablePOIItemMoved(p0: MapView?, p1: MapPOIItem?, p2: MapPoint?) {

    }
}