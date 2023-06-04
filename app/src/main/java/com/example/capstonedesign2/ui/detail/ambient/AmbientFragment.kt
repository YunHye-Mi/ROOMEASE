package com.example.capstonedesign2.ui.detail.ambient

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
import com.example.capstonedesign2.databinding.FragmentAmbientBinding
import com.google.gson.Gson

class AmbientFragment(val room: Room) : Fragment() {
    lateinit var binding: FragmentAmbientBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAmbientBinding.inflate(inflater, container, false)

        val envList = ArrayList<Environment>()

        for (i in room.environment) {
            if (i.type == "지하철역" || i.type == "버스정류장") {
            } else {
                envList.add(i)
            }
        }

        val ambientRVAdapter = AmbientRVAdapter(envList)
        binding.ambientRv.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        binding.ambientRv.adapter = ambientRVAdapter

        binding.root.requestLayout()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }
}