package com.example.capstonedesign2.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstonedesign2.R
import com.example.capstonedesign2.data.remote.Review
import com.example.capstonedesign2.databinding.ActivityMyreviewBinding
import com.example.capstonedesign2.ui.more.MyReviewRVAdapter
import kotlin.collections.ArrayList

class MyReviewActivity : AppCompatActivity() {
    lateinit var binding : ActivityMyreviewBinding
    lateinit var reviewRVAdapter: MyReviewRVAdapter
    var reviewData = ArrayList<Review>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyreviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backIv.setOnClickListener {
            onBackPressed()
        }

        reviewRVAdapter = MyReviewRVAdapter(reviewData)
        binding.reviewRv.adapter = reviewRVAdapter
        binding.reviewRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        var checkedList = reviewRVAdapter.checkedList

        binding.moreIv.setOnClickListener {
            var popupMenu = PopupMenu(this, binding.moreIv)
            var menuInflater = popupMenu.menuInflater
            menuInflater.inflate(R.menu.my_review_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.selected_delete -> {
                        removeReview(checkedList)
                        Toast.makeText(this, "해당 리뷰 삭제", Toast.LENGTH_SHORT)
                        return@setOnMenuItemClickListener true
                    }

                    else -> {
                        reviewData.removeAll(reviewData.toSet())
                        Toast.makeText(this, "리뷰 전체 삭제", Toast.LENGTH_SHORT)
                        reviewRVAdapter.notifyDataSetChanged()
                        return@setOnMenuItemClickListener true
                    }
                }
            }
            popupMenu.show()
        }
    }

    private fun removeReview(checkList: ArrayList<Int>): Boolean {
        for (i in checkList){
            reviewData.removeAt(i)
            reviewRVAdapter.notifyDataSetChanged()
        }
        checkList.clear()
        return true
    }
}
