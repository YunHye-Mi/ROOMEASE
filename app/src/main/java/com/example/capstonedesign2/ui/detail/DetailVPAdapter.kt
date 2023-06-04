package com.example.capstonedesign2.ui.detail

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.capstonedesign2.data.remote.Room
import com.example.capstonedesign2.ui.detail.ambient.AmbientFragment
import com.example.capstonedesign2.ui.detail.review.ReviewFragment
import com.example.capstonedesign2.ui.detail.transportation.PublicFragment
import com.google.gson.Gson

class DetailVPAdapter(fragmentActivity: FragmentActivity, val room: Room) : FragmentStateAdapter(fragmentActivity) {

    private var gson = Gson()
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {

        return when (position) {
            0-> PublicFragment(room)
            1-> AmbientFragment(room)
            else-> ReviewFragment()
        }
    }
}
