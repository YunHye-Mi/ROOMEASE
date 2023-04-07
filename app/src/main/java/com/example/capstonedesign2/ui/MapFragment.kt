package com.example.capstonedesign2.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.capstonedesign2.R
import com.example.capstonedesign2.databinding.*
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

        val mapPoint = MapPoint.mapPointWithGeoCoord(37.566352778, 126.977952778)

        val marker = MapPOIItem()
        marker.tag = 1
        marker.mapPoint = mapPoint
        marker.markerType = MapPOIItem.MarkerType.CustomImage
        marker.customImageResourceId = R.layout.zoomout_marker_layout
        marker.isCustomImageAutoscale = false
        marker.setCustomImageAnchor(1.0f, 1.0f)
        binding.mapView.addPOIItem(marker)

        onClickListener()

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        setFilter()
    }

    override fun onPause() {
        super.onPause()

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
                Log.d("평형", area_min.toString() + " / " + area_max.toString())
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
                    Log.d("전세", price_min_d.toString() + " / " + price_max_d.toString())
                }
            } else {
                if (price_min_d != 0 && price_max_d != 0
                    && price_min_m != 0 && price_max_m != 0
                    && price_min_a != 0 && price_max_a != 0) {
                    binding.priceBt.setTextColor(Color.parseColor("#C69C6D"))
                    binding.priceBt.setBackgroundResource(R.drawable.select_brown_round_shape)
                    Log.d("월세 보증금", price_min_d.toString() + " / " + price_max_d.toString())
                    Log.d("월세 월세", price_min_m.toString() + " / " + price_max_m.toString())
                    Log.d("월세 관리비", price_min_a.toString() + " / " + price_max_a.toString())
                }
                else if (price_min_m != 0 && price_max_m != 0) {
                    binding.priceBt.setTextColor(Color.parseColor("#C69C6D"))
                    binding.priceBt.setBackgroundResource(R.drawable.select_brown_round_shape)
                    Log.d("월세 월세", price_min_m.toString() + " / " + price_max_m.toString())
                }
                else if (price_min_a != 0 && price_max_a != 0) {
                    binding.priceBt.setTextColor(Color.parseColor("#C69C6D"))
                    binding.priceBt.setBackgroundResource(R.drawable.select_brown_round_shape)
                    Log.d("월세 관리비", price_min_a.toString() + " / " + price_max_a.toString())
                }
                else if (price_min_d != 0 && price_max_d != 0) {
                    binding.priceBt.setTextColor(Color.parseColor("#C69C6D"))
                    binding.priceBt.setBackgroundResource(R.drawable.select_brown_round_shape)
                    Log.d("월세 보증금", price_min_d.toString() + " / " + price_max_d.toString())
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
}