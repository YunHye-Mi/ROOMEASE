package com.example.capstonedesign2.ui.map

import android.content.Intent
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
import com.example.capstonedesign2.data.local.EstateDatabase
import com.example.capstonedesign2.data.entities.Address
import com.example.capstonedesign2.data.entities.Estate
import com.example.capstonedesign2.databinding.FragmentMapBinding
import com.example.capstonedesign2.ui.map.search.SearchActivity
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import net.daum.mf.map.api.MapView.MapType

class MapFragment() : Fragment() {
    lateinit var binding: FragmentMapBinding
    lateinit var estateDB: EstateDatabase
    var markerList = ArrayList<MapPOIItem>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapBinding.inflate(inflater, container, false)

        estateDB = EstateDatabase.getInstance(this.requireContext())!!

        var mapPoint = MapPoint.mapPointWithGeoCoord(37.566352778, 126.977952778)

        initAddress()

        binding.mapView.setMapViewEventListener(object : MapView.MapViewEventListener {
            override fun onMapViewInitialized(p0: MapView?) {
                p0!!.mapType = MapType.Satellite
                p0!!.setMapCenterPointAndZoomLevel(mapPoint, 4,true)
                for (i in estateDB.addressDao().getAddresses() as ArrayList<Address>) {
                    p0.addPOIItem(makeZoomOutMarker(i))
                }
            }

            override fun onMapViewCenterPointMoved(p0: MapView?, p1: MapPoint?) {
                p0?.removeAllPOIItems()
                for (i in estateDB.addressDao().getAddresses() as ArrayList<Address>) {
                    val latitude = i.latitude
                    val longitude = i.longitude
                    p0?.addPOIItem(makeZoomOutMarker(i))
                }
            }

            override fun onMapViewZoomLevelChanged(p0: MapView?, p1: Int) {
                p0?.removeAllPOIItems()
                val currentZoomLevel = p0?.zoomLevel ?: 0
                if (currentZoomLevel in 3..6) {
                    for (i in estateDB.addressDao().getAddresses() as ArrayList<Address>) {
                        p0!!.addPOIItem(makeZoomOutMarker(i))
                    }
                }
                else if (currentZoomLevel in 1..3) {
                    for (i in estateDB.estateDao().getEstates() as ArrayList<Estate>) {
                        p0!!.addPOIItem(makeZoomInMarker(i))
                    }
                }

                Log.d("ZoomChange", p0!!.zoomLevel.toString())
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
                p0?.setMapCenterPoint(p1, true)
            }

            override fun onMapViewMoveFinished(p0: MapView?, p1: MapPoint?) {

            }
        })

        onClickListener()

        return binding.root
    }

    private fun initAddress() {

        estateDB.addressDao().insert(Address("상도동", "서울특별시 동작구 상도동", 37.4987679,126.9440654))
        estateDB.addressDao().insert(Address("흑석동", "서울특별시 동작구 흑석동", 37.5055226,126.9623716))
        estateDB.addressDao().insert(Address("연희동", "서울특별시 서대문구 연희동", 37.5715546,126.930927))
        estateDB.addressDao().insert(Address("사당동", "서울특별시 동작구 사당동", 37.4856409,126.9723271))
        estateDB.addressDao().insert(Address("신대방동", "서울특별시 동작구 신대방동", 37.4926959,126.9171638))
        estateDB.addressDao().insert(Address("신당동", "서울특별시 중구 신당동", 37.5579703,127.0136667))

        estateDB.estateDao().insert(Estate("0", "2000", "서울특별시 서대문구 연희동", "4", null, "50", "8", "", "월세", "19.5"),)
        estateDB.estateDao().insert(Estate("1", "4000", "서울특별시 동작구 상도동", "2", null, "48", "9", "", "월세", "21.5"),)

    }

    override fun onResume() {
        super.onResume()
        setFilter()
    }

    private fun onClickListener() {
        var infra_click = false

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
            if (!infra_click) {
                binding.infraList.visibility = View.VISIBLE
                infra_click = true
            } else {
                binding.infraList.visibility = View.GONE
                infra_click = false
            }
        }

        var infra_subway = false
        var infra_bus = false
        var infra_bicycle = false
        var infra_restaurant = false
        var infra_cafe = false
        var infra_store = false
        var infra_convenience = false
        var infra_fitness =false

        binding.subwayBt.setOnClickListener {
            if (infra_subway == false) {
                markerList.addAll(binding.mapView.poiItems)
                binding.mapView.removeAllPOIItems()
                binding.infraList.visibility = View.GONE
                var marker = MapPOIItem()
                marker.apply {
                    itemName = "지하철"
                    mapPoint = binding.mapView.mapCenterPoint
                    markerType = MapPOIItem.MarkerType.YellowPin
                    tag = 0
                    isShowCalloutBalloonOnTouch = false
                }
                binding.mapView.addPOIItem(marker)
                infra_subway = true
                infra_click = false
            } else if (infra_subway == true) {
                binding.mapView.removeAllPOIItems()
                for (i in markerList) {
                    binding.mapView.addPOIItem(i)
                }
                markerList.clear()
                binding.infraList.visibility = View.GONE
                infra_subway = false
                infra_click = false
            }
            Log.d("infra_subway", infra_subway.toString())
        }

        binding.busBt.setOnClickListener {
            if (infra_bus == false) {
                markerList.addAll(binding.mapView.poiItems)
                binding.mapView.removeAllPOIItems()
                binding.infraList.visibility = View.GONE
                var marker = MapPOIItem()
                marker.apply {
                    itemName = "버스"
                    mapPoint = binding.mapView.mapCenterPoint
                    markerType = MapPOIItem.MarkerType.YellowPin
                    tag = 0
                    isShowCalloutBalloonOnTouch = false
                }
                binding.mapView.addPOIItem(marker)
                infra_bus = true
                infra_click = false
            } else if (infra_bus == true) {
                binding.mapView.removeAllPOIItems()
                for (i in markerList) {
                    binding.mapView.addPOIItem(i)
                }
                markerList.clear()
                binding.infraList.visibility = View.GONE
                infra_bus = false
                infra_click = false
            }
            Log.d("infra_bus", infra_bus.toString())
        }

        binding.bicycleBt.setOnClickListener {
            if (infra_bicycle == false) {
                markerList.addAll(binding.mapView.poiItems)
                binding.mapView.removeAllPOIItems()
                binding.infraList.visibility = View.GONE
                var marker = MapPOIItem()
                marker.apply {
                    itemName = "자전거"
                    mapPoint = binding.mapView.mapCenterPoint
                    markerType = MapPOIItem.MarkerType.YellowPin
                    tag = 0
                    isShowCalloutBalloonOnTouch = false
                }
                binding.mapView.addPOIItem(marker)
                infra_bicycle = true
                infra_click = false
            } else if (infra_bicycle == true) {
                binding.mapView.removeAllPOIItems()
                for (i in markerList) {
                    binding.mapView.addPOIItem(i)
                }
                markerList.clear()
                binding.infraList.visibility = View.GONE
                infra_bicycle = false
                infra_click = false
            }
            Log.d("infra_bicycle", infra_bicycle.toString())
        }

        binding.restaurantBt.setOnClickListener {
            if (infra_restaurant == false) {
                markerList.addAll(binding.mapView.poiItems)
                binding.mapView.removeAllPOIItems()
                binding.infraList.visibility = View.GONE
                var marker = MapPOIItem()
                marker.apply {
                    itemName = "식당"
                    mapPoint = binding.mapView.mapCenterPoint
                    markerType = MapPOIItem.MarkerType.YellowPin
                    tag = 0
                    isShowCalloutBalloonOnTouch = false
                }
                binding.mapView.addPOIItem(marker)
                infra_restaurant = true
                infra_click = false
            } else if (infra_restaurant == true) {
                binding.mapView.removeAllPOIItems()
                for (i in markerList) {
                    binding.mapView.addPOIItem(i)
                }
                markerList.clear()
                binding.infraList.visibility = View.GONE
                infra_restaurant = false
                infra_click = false
            }
            Log.d("infra_restaurant", infra_restaurant.toString())
        }

        binding.cafeBt.setOnClickListener {
            if (infra_cafe == false) {
                markerList.addAll(binding.mapView.poiItems)
                binding.mapView.removeAllPOIItems()
                binding.infraList.visibility = View.GONE
                var marker = MapPOIItem()
                marker.apply {
                    itemName = "카페"
                    mapPoint = binding.mapView.mapCenterPoint
                    markerType = MapPOIItem.MarkerType.YellowPin
                    tag = 0
                    isShowCalloutBalloonOnTouch = false
                }
                binding.mapView.addPOIItem(marker)
                infra_cafe = true
                infra_click = false
            } else if (infra_cafe == true) {
                binding.mapView.removeAllPOIItems()
                for (i in markerList) {
                    binding.mapView.addPOIItem(i)
                }
                markerList.clear()
                binding.infraList.visibility = View.GONE
                infra_cafe = false
                infra_click = false
            }
            Log.d("infra_cafe", infra_cafe.toString())
        }

        binding.shoppingStreetBt.setOnClickListener {
            if (infra_store == false) {
                markerList.addAll(binding.mapView.poiItems)
                binding.mapView.removeAllPOIItems()
                binding.infraList.visibility = View.GONE
                var marker = MapPOIItem()
                marker.apply {
                    itemName = "가게"
                    mapPoint = binding.mapView.mapCenterPoint
                    markerType = MapPOIItem.MarkerType.YellowPin
                    tag = 0
                    isShowCalloutBalloonOnTouch = false
                }
                binding.mapView.addPOIItem(marker)
                infra_store = true
                infra_click = false
            } else if (infra_store == true) {
                binding.mapView.removeAllPOIItems()
                for (i in markerList) {
                    binding.mapView.addPOIItem(i)
                }
                markerList.clear()
                binding.infraList.visibility = View.GONE
                infra_store = false
                infra_click = false
            }
            Log.d("infra_store", infra_store.toString())
        }

        binding.convenienceBt.setOnClickListener {
            if (infra_convenience == false) {
                markerList.addAll(binding.mapView.poiItems)
                binding.mapView.removeAllPOIItems()
                binding.infraList.visibility = View.GONE
                var marker = MapPOIItem()
                marker.apply {
                    itemName = "편의점"
                    mapPoint = binding.mapView.mapCenterPoint
                    markerType = MapPOIItem.MarkerType.YellowPin
                    tag = 0
                    isShowCalloutBalloonOnTouch = false
                }
                binding.mapView.addPOIItem(marker)
                infra_convenience = true
                infra_click = false
            } else if (infra_convenience == true) {
                binding.mapView.removeAllPOIItems()
                for (i in markerList) {
                    binding.mapView.addPOIItem(i)
                }
                markerList.clear()
                binding.infraList.visibility = View.GONE
                infra_convenience = false
                infra_click = false
            }
            Log.d("infra_convenience", infra_convenience.toString())
        }

        binding.fitnessBt.setOnClickListener {
            if (infra_fitness == false) {
                markerList.addAll(binding.mapView.poiItems)
                binding.mapView.removeAllPOIItems()
                binding.infraList.visibility = View.GONE
                var marker = MapPOIItem()
                marker.apply {
                    itemName = "헬스장"
                    mapPoint = binding.mapView.mapCenterPoint
                    markerType = MapPOIItem.MarkerType.YellowPin
                    tag = 0
                    isShowCalloutBalloonOnTouch = false
                }
                binding.mapView.addPOIItem(marker)
                infra_fitness = true
                infra_click = false
            } else if (infra_fitness == true) {
                binding.mapView.removeAllPOIItems()
                binding.infraList.visibility = View.GONE
                infra_fitness = false
                infra_click = false
                for (i in markerList)
                    binding.mapView.addPOIItem(i)
                markerList.clear()
            }
            Log.d("infra_fitness", infra_fitness.toString())
        }
    }

    fun setFilter() {
        val spf = activity?.getSharedPreferences("filter", AppCompatActivity.MODE_PRIVATE)

        val rent = spf?.getString("rent", "")
        val area_min = spf?.getInt("area_min", 0)
        val area_max = spf?.getInt("area_max", 0)
        val price_min_d = spf?.getInt("price_min_d", 0)
        val price_max_d = spf?.getInt("price_max_d", 0)
        val price_min_m = spf?.getInt("price_min_m", 0)
        val price_max_m = spf?.getInt("price_max_m", 0)
        val price_min_a = spf?.getInt("price_min_a", 0)
        val price_max_a = spf?.getInt("price_max_a", 0)
        val floor = spf?.getString("floor", "")

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

    private fun makeZoomOutMarker(address: Address): MapPOIItem? {
        var mapMarker = MapPOIItem()
        return mapMarker.apply {
            itemName = address.detail.toString()
            tag = 0
            mapPoint = MapPoint.mapPointWithGeoCoord(address.latitude, address.longitude)
            markerType = MapPOIItem.MarkerType.CustomImage
            customImageBitmap = Bitmap.createBitmap(viewConvertToBitmapZoomOut(address))
            isShowCalloutBalloonOnTouch = false
        }
    }

    private fun makeZoomInMarker(estate: Estate): MapPOIItem? {
        var mapMarker = MapPOIItem()
        return mapMarker.apply {
            itemName = estate.id.toString()
            tag = estate.id.toInt()
//            mapPoint = MapPoint.mapPointWithGeoCoord(estate.latitude, estate.longitude)
            markerType = MapPOIItem.MarkerType.CustomImage
            customImageBitmap = Bitmap.createBitmap(viewConvertToBitmapZoomIn(estate))
            isShowCalloutBalloonOnTouch = false
        }
    }


    private fun viewConvertToBitmapZoomIn(estate: Estate): Bitmap {
        val zoom_in_view = layoutInflater.inflate(R.layout.zoomin_marker_layout, null)
        val displayMetrics = DisplayMetrics()
        val area = zoom_in_view.findViewById<TextView>(R.id.zoom_in_area_tv)
        val price = zoom_in_view.findViewById<TextView>(R.id.zoom_in_max_tv)
        area.text = estate.size
        price.text = estate.deposit
        zoom_in_view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        zoom_in_view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels)
        zoom_in_view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels)
        val bitmap = Bitmap.createBitmap(zoom_in_view.measuredWidth, zoom_in_view.measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        zoom_in_view.draw(canvas)
        return bitmap
    }

    private fun viewConvertToBitmapZoomOut(address: Address): Bitmap {
        val zoom_out_view = layoutInflater.inflate(R.layout.zoomout_marker_layout, null)
        val displayMetrics = DisplayMetrics()
        val location = zoom_out_view.findViewById<TextView>(R.id.location_tv)
        val maxprice = zoom_out_view.findViewById<TextView>(R.id.max_price_tv)
        val count = zoom_out_view.findViewById<TextView>(R.id.count_tv)
        location.text = address.address
        maxprice.text = "2억7천"
        count.text = "7건"
        zoom_out_view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        zoom_out_view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels)
        zoom_out_view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels)
        val bitmap = Bitmap.createBitmap(zoom_out_view.measuredWidth, zoom_out_view.measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        zoom_out_view.draw(canvas)
        return bitmap
    }
}
