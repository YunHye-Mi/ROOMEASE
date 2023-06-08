package com.example.capstonedesign2.ui.detail.transportation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstonedesign2.data.remote.Environment
import com.example.capstonedesign2.data.remote.Room
import com.example.capstonedesign2.databinding.FragmentPublicBinding
import com.google.gson.Gson

class PublicFragment(val room: Room) : Fragment() {
    lateinit var binding: FragmentPublicBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPublicBinding.inflate(inflater, container, false)

        val subwayRVAdapter = SubwayRVAdapter(room.nearSubway)
        binding.subwayRv.adapter = subwayRVAdapter
        binding.subwayRv.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)

        binding.root.requestLayout()

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        binding.root.requestLayout()
    }
}