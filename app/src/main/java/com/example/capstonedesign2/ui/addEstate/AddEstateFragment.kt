package com.example.capstonedesign2.ui.bookmark

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstonedesign2.R
import com.example.capstonedesign2.data.entities.Estate
import com.example.capstonedesign2.data.entities.Review
import com.example.capstonedesign2.databinding.FragmentAddestateBinding
import com.example.capstonedesign2.ui.more.MyReviewRVAdapter

class AddEstateFragment() : Fragment() {
    lateinit var binding: FragmentAddestateBinding

    private var estateList = ArrayList<Estate>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddestateBinding.inflate(inflater, container, false)

        var addEstateRVAdapter = AddEstateRVAdapter(estateList)
        binding.estateRv.adapter = addEstateRVAdapter
        binding.estateRv.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)



//        binding.optionIv.setOnClickListener {
//            var popupMenu = PopupMenu(this.context, binding.optionIv)
//            var menuInflater = popupMenu.menuInflater
//            menuInflater.inflate(R.menu.my_review_menu, popupMenu.menu)
//            popupMenu.setOnMenuItemClickListener { menuItem ->
//                when (menuItem.itemId) {
//                    else -> {}
//                }
//            }
//            popupMenu.show()
//        }

        return binding.root
    }
}