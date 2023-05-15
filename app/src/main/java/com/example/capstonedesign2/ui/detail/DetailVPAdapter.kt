package com.example.capstonedesign2.ui.detail

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.capstonedesign2.data.entities.Estate
import com.google.gson.Gson

class DetailVPAdapter(fragmentActivity: FragmentActivity, estate : Estate) : FragmentStateAdapter(fragmentActivity) {

    val gson = Gson()
    val estateJson = gson.toJson(estate)
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {

        return when (position) {
            0-> PublicFragment()
            1-> AmbientFragment()
//            2-> ResidenceFragment()
            else-> ReviewFragment.newInstance(estateJson)
        }
    }
}
