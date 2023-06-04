package com.example.capstonedesign2.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.capstonedesign2.data.remote.Environment
import com.example.capstonedesign2.databinding.FragmentExplainBinding

class ExplainFragment() : Fragment() {
    lateinit var binding: FragmentExplainBinding

    companion object {
        fun newInstance(environment: Environment): ExplainFragment {
            val fragment = ExplainFragment()
            val args = Bundle()


            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExplainBinding.inflate(inflater, container, false)


        return binding.root
    }
}