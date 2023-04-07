package com.example.capstonedesign2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.capstonedesign2.R
import com.example.capstonedesign2.databinding.FragmentAmbientBinding
import com.example.capstonedesign2.databinding.FragmentPublicBinding

class AmbientFragment() : Fragment() {
    lateinit var binding: FragmentAmbientBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAmbientBinding.inflate(inflater, container, false)


        return binding.root
    }
}