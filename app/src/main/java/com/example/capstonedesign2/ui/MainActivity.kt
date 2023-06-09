package com.example.capstonedesign2.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.capstonedesign2.R
import com.example.capstonedesign2.data.entities.User
import com.example.capstonedesign2.databinding.ActivityMainBinding
import com.example.capstonedesign2.ui.chat.ChatFragment
import com.example.capstonedesign2.ui.bookmark.AddEstateFragment
import com.example.capstonedesign2.ui.bookmark.BookmarkFragment
import com.example.capstonedesign2.ui.map.MapFragment
import com.example.capstonedesign2.ui.more.MoreFragment
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    private var gson = Gson()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val spf = getSharedPreferences("filter", MODE_PRIVATE)
        val editor = spf.edit()
        editor.clear()
        editor.apply()

        val userSpf = getSharedPreferences("currentUser", MODE_PRIVATE)
        val userJson = userSpf.getString("User", "")

        if (userJson != null) {
            if (!userJson.isEmpty()) {
                var user = gson.fromJson(userJson, User::class.java)
                initBottomNavigation(user.role)
                Log.d("accessToken", user.accessToken)
            } else {
                initBottomNavigation("General")
            }
        }
    }

    private fun initBottomNavigation(role: String) { //BottomNavigationView init
        if (role == "General") {
            binding.mainBnv.menu.findItem(R.menu.bottom_nav_general_menu)
            binding.mainBnv.inflateMenu(R.menu.bottom_nav_general_menu)

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

                    R.id.chatFragment -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, ChatFragment())
                            .commitAllowingStateLoss()
                        return@setOnItemSelectedListener true
                    }

                    R.id.likeFragment -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, BookmarkFragment())
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
        } else {
            binding.mainBnv.menu.findItem(R.menu.bottom_nav_intermediary_menu)
            binding.mainBnv.inflateMenu(R.menu.bottom_nav_intermediary_menu)

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

                    R.id.chatFragment -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, ChatFragment())
                            .commitAllowingStateLoss()
                        return@setOnItemSelectedListener true
                    }

                    R.id.addEstateFragment -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, AddEstateFragment())
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
}
