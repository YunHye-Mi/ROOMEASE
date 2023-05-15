package com.example.capstonedesign2.ui.map.search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstonedesign2.R
import com.example.capstonedesign2.data.local.EstateDatabase
import com.example.capstonedesign2.data.entities.Estate
import com.example.capstonedesign2.databinding.ActivityResultBinding
import com.example.capstonedesign2.ui.detail.DetailActivity
import com.google.gson.Gson
import java.util.ArrayList

class ResultActivity : AppCompatActivity() {
    lateinit var binding : ActivityResultBinding
    lateinit var estateDB: EstateDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        estateDB = EstateDatabase.getInstance(this)!!

        binding.backIv.setOnClickListener {
            onBackPressed()
        }

        binding.searchTv.text = intent.getStringExtra("search_address")

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
        var detail = intent.getStringExtra("search_detail")
        val resultRVAdapter = ResultRVAdapter(estateDB.estateDao().getEstate(detail!!) as ArrayList<Estate>)
        var intent = Intent(this, DetailActivity::class.java)

        binding.resultRv.adapter = resultRVAdapter
        binding.resultRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        resultRVAdapter.setMyItemClickListener(object : ResultRVAdapter.MyItemClickListener {
            override fun onItemClick(estate: Estate) {
                val gson = Gson()
                val estateJson = gson.toJson(estate)
                intent.putExtra("estate", estateJson)
                startActivity(intent)
            }
        } )
    }
}