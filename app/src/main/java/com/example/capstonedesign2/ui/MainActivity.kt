package com.example.capstonedesign2.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.capstonedesign2.R
import com.example.capstonedesign2.databinding.ActivityMainBinding
import com.example.capstonedesign2.ui.like.LikeFragment
import com.example.capstonedesign2.ui.map.MapFragment
import com.example.capstonedesign2.ui.more.MoreFragment

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var spf = getSharedPreferences("filter", MODE_PRIVATE)
        var editor = spf.edit()
        editor.clear()
        editor.commit()

        initBottomNavigation()

    }

    private fun initBottomNavigation(){ //하단 네비게이션

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, MapFragment())
            .commitAllowingStateLoss()

        binding.mainBnv.setOnItemSelectedListener{ item ->
            when (item.itemId) {

                R.id.mapFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, MapFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

//                R.id.recommendFragment -> {
//                    supportFragmentManager.beginTransaction()
//                        .replace(R.id.main_frm, RecommendFragment())
//                        .commitAllowingStateLoss()
//                    return@setOnItemSelectedListener true
//                }

                R.id.likeFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LikeFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.moreFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, MoreFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }
}
