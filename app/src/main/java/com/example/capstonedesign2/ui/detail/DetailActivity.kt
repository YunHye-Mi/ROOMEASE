package com.example.capstonedesign2.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.capstonedesign2.data.entities.Estate
import com.example.capstonedesign2.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {
    lateinit var binding : ActivityDetailBinding
    private val information = arrayListOf("대중교통", "주변환경", "리뷰")
    private var gson = Gson()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var estateJson = intent.getStringExtra("estate")
        var estate = gson.fromJson(estateJson, Estate::class.java)

        binding.addressTv.text = estate.address
        binding.priceTv.text = estate.deposit + "만원 / " + estate.rent + "만원"
        binding.manageTv.text = estate.manage_cost + "원"
        var imageUrl = estate.coverImg
        Picasso.get().load(imageUrl).into(binding.detailIv)

        binding.backIv.setOnClickListener {
            onBackPressed()
        }

        val detailAdapter = DetailVPAdapter(this, estate)
        binding.detailVp.adapter = detailAdapter
        TabLayoutMediator(binding.detailTb, binding.detailVp){
            tab, position ->
            tab.text = information[position]

        }.attach()

        binding.infoIntermediaryLl.setOnClickListener {
            var customDialog = IntermediaryDialog(this)
            customDialog.show()
            customDialog.binding.dialogNameTv.text = "공인중개사 이름을 쓸거야"
            customDialog.binding.dialogRateTv.text = "3.68"
        }

        binding.bookmarkOffIv.setOnClickListener {
            binding.bookmarkOffIv.visibility = View.GONE
            binding.bookmarkOnIv.visibility = View.VISIBLE


        }
    }
}