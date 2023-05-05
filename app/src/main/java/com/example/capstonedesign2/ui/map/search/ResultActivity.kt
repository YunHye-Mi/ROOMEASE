package com.example.capstonedesign2.ui.map.search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstonedesign2.R
import com.example.capstonedesign2.data.entities.Estate
import com.example.capstonedesign2.databinding.ActivityResultBinding
import com.example.capstonedesign2.ui.detail.DetailActivity

class ResultActivity : AppCompatActivity() {
    lateinit var binding : ActivityResultBinding
    private val estateData = arrayListOf<Estate>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backIv.setOnClickListener {
            onBackPressed()
        }

        binding.searchTv.text = intent.getStringExtra("search_address")

        estateData.apply {
            add(Estate("집", "서울특별시 동작구 상도동", listOf(R.drawable.splash_image), "월세", 19.5, "2000", "50", 4))
            add(Estate("상도아파트", "서울특별시 동작구 상도 1동", listOf(R.drawable.splash_image),"전세", 20.3, "1억", "0", 7))
            add(Estate("집", "서울특별시 동작구 상도동", listOf(R.drawable.splash_image), "월세", 19.5, "2000", "50", 4))
            add(Estate("상도아파트", "서울특별시 동작구 상도 1동", listOf(R.drawable.splash_image),"전세", 20.3, "1억", "0", 7))
            add(Estate("집", "서울특별시 동작구 상도동", listOf(R.drawable.splash_image), "월세", 19.5, "2000", "50", 4))
            add(Estate("상도아파트", "서울특별시 동작구 상도 1동", listOf(R.drawable.splash_image),"전세", 20.3, "1억", "0", 7))
            add(Estate("집", "서울특별시 동작구 상도동", listOf(R.drawable.splash_image), "월세", 19.5, "2000", "50", 4))
            add(Estate("상도아파트", "서울특별시 동작구 상도 1동", listOf(R.drawable.splash_image),"전세", 20.3, "1억", "0", 7))
            add(Estate("집", "서울특별시 동작구 상도동", listOf(R.drawable.splash_image), "월세", 19.5, "2000", "50", 4))
            add(Estate("상도아파트", "서울특별시 동작구 상도 1동", listOf(R.drawable.splash_image),"전세", 20.3, "1억", "0", 7))
            add(Estate("집", "서울특별시 동작구 상도동", listOf(R.drawable.splash_image), "월세", 19.5, "2000", "50", 4))
            add(Estate("상도아파트", "서울특별시 동작구 상도 1동", listOf(R.drawable.splash_image),"전세", 20.3, "1억", "0", 7))
            add(Estate("집", "서울특별시 동작구 상도동", listOf(R.drawable.splash_image), "월세", 19.5, "2000", "50", 4))
            add(Estate("상도아파트", "서울특별시 동작구 상도 1동", listOf(R.drawable.splash_image),"전세", 20.3, "1억", "0", 7))
        }

        val spinner = binding.sortSpinner

        ArrayAdapter.createFromResource(
            this,
            R.array.sort_spinner,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        initRV()
    }

    fun initRV() {
        val resultRVAdapter = ResultRVAdapter(estateData)
        var intent = Intent(this, DetailActivity::class.java)

        binding.resultRv.adapter = resultRVAdapter
        binding.resultRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        resultRVAdapter.setMyItemClickListener(object : ResultRVAdapter.MyItemClickListner {
            override fun onItemClick(estate: Estate) {
                startActivity(intent)
            }
        } )
    }
}