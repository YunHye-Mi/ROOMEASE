package com.example.capstonedesign2.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstonedesign2.R
import com.example.capstonedesign2.data.entities.Review
import com.example.capstonedesign2.data.local.EstateDatabase
import com.example.capstonedesign2.databinding.ActivityMyreviewBinding
import com.example.capstonedesign2.ui.more.MyReviewRVAdapter
import java.util.ArrayList

class MyReviewActivity : AppCompatActivity() {
    lateinit var binding : ActivityMyreviewBinding
    lateinit var estateDB : EstateDatabase
    var reviewData = ArrayList<Review>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyreviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        estateDB = EstateDatabase.getInstance(this)!!

        binding.backIv.setOnClickListener {
            onBackPressed()
        }

        var reviewRVAdapter = MyReviewRVAdapter(reviewData)
        binding.reviewRv.adapter = reviewRVAdapter
        binding.reviewRv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        reviewData.apply {
            add(Review("mm", 1,20230304, "4년 이내 거주", "대학생", "여성", 4, 3, 3, "집에서 곰팡이 냄새가 너무 나요."))
            add(Review("mm", 2,20230304, "4년 이내 거주", "대학생", "여성", 4, 3, 3, "집에서 곰팡이 냄새가 너무 나요."))
            add(Review("mm", 3,20230304, "4년 이내 거주", "대학생", "여성", 4, 3, 3, "집에서 곰팡이 냄새가 너무 나요."))
        }

        var checkedList = reviewRVAdapter.checkedList

        binding.moreIv.setOnClickListener {
            var popupMenu = PopupMenu(this, binding.moreIv)
            var menuInflater = popupMenu.menuInflater
            menuInflater.inflate(R.menu.my_review_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.selected_delete -> removeReview(checkedList)

                    else -> {reviewData.removeAll(reviewData.toSet())}
                }
            }
            popupMenu.show()
        }
    }

    fun removeReview(checkList: ArrayList<Int>): Boolean {
        for (i in checkList){
            reviewData.removeAt(i)
        }
        return true
    }
}
