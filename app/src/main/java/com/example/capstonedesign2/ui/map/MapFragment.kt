package com.example.capstonedesign2.ui.map

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.capstonedesign2.R
import com.example.capstonedesign2.databinding.FragmentMapBinding
import com.example.capstonedesign2.ui.map.search.SearchActivity
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint

class MapFragment() : Fragment() {
    lateinit var binding: FragmentMapBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapBinding.inflate(inflater, container, false)

        var currentPoint = MapPoint.mapPointWithGeoCoord(37.566352778, 126.977952778)

        var mapMarker = MapPOIItem()
        mapMarker.apply {
            itemName = "zoom-out marker"
            tag = 0
            mapPoint = currentPoint
            markerType = MapPOIItem.MarkerType.CustomImage
            customImageBitmap = Bitmap.createBitmap(viewConvertToBitmap())
            isShowCalloutBalloonOnTouch = false
        }

        binding.mapView.addPOIItem(mapMarker)

        onClickListener()

        return binding.root
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

        binding.subwayBt.setOnClickListener {
            binding.infraList.visibility = View.GONE
        }

        binding.busBt.setOnClickListener {
            binding.infraList.visibility = View.GONE
        }

        binding.bicycleBt.setOnClickListener {
            binding.infraList.visibility = View.GONE
        }

        binding.restaurantBt.setOnClickListener {
            binding.infraList.visibility = View.GONE
        }

        binding.cafeBt.setOnClickListener {
            binding.infraList.visibility = View.GONE
        }

        binding.shoppingStreetBt.setOnClickListener {
            binding.infraList.visibility = View.GONE
        }

        binding.convenienceBt.setOnClickListener {
            binding.infraList.visibility = View.GONE
        }

        binding.fitnessBt.setOnClickListener {
            binding.infraList.visibility = View.GONE
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

    private fun viewConvertToBitmap(): Bitmap {
        val view = layoutInflater.inflate(R.layout.zoomout_marker_layout, null)
        val displayMetrics = DisplayMetrics()
        val location = view.findViewById<TextView>(R.id.location_tv)
        val maxprice = view.findViewById<TextView>(R.id.max_price_tv)
        val count = view.findViewById<TextView>(R.id.count_tv)
        location.text = "회현동"
        maxprice.text = "2억7천"
        count.text = "7건"
        view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels)
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels)
        val bitmap = Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }
}
