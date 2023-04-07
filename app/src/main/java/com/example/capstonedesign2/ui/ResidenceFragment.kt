package com.example.capstonedesign2.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.capstonedesign2.R
import com.example.capstonedesign2.databinding.FragmentPublicBinding
import com.example.capstonedesign2.databinding.FragmentResidenceBinding

class ResidenceFragment() : Fragment() {
    lateinit var binding: FragmentResidenceBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResidenceBinding.inflate(inflater, container, false)


        return binding.root
    }
}