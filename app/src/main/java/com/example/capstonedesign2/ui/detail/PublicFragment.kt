package com.example.capstonedesign2.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.capstonedesign2.R
import com.example.capstonedesign2.databinding.FragmentPublicBinding

class PublicFragment() : Fragment() {
    lateinit var binding: FragmentPublicBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPublicBinding.inflate(inflater, container, false)


        return binding.root
    }
}