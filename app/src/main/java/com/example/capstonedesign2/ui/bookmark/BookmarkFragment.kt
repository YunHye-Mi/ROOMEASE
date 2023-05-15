package com.example.capstonedesign2.ui.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.capstonedesign2.R
import com.example.capstonedesign2.databinding.FragmentBookmarkBinding

class BookmarkFragment() : Fragment() {
    lateinit var binding: FragmentBookmarkBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBookmarkBinding.inflate(inflater, container, false)

        val spinner = binding.sortSpinner

        ArrayAdapter.createFromResource(
            this.requireContext(),
            R.array.sort_spinner,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        return inflater.inflate(R.layout.fragment_bookmark, container, false)
    }
}