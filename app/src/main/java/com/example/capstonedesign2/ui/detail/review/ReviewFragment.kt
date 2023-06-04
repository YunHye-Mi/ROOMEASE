package com.example.capstonedesign2.ui.detail.review

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstonedesign2.R
import com.example.capstonedesign2.data.entities.User
import com.example.capstonedesign2.data.remote.*
import com.example.capstonedesign2.databinding.FragmentReviewBinding
import com.example.capstonedesign2.ui.detail.ReviewView
import com.example.capstonedesign2.ui.login.RefreshView
import com.google.gson.Gson

class ReviewFragment() : Fragment(), ReviewView, RefreshView {
    lateinit var binding: FragmentReviewBinding
    lateinit var spf: SharedPreferences
    private var reviewlist = ArrayList<Review>()
    private var reviewView = ReviewService()
    lateinit var accessToken: String
    lateinit var refreshToken: String
    lateinit var user: User
    lateinit var reviewRVAdapter: ReviewRVAdapter
    private var gson = Gson()
    private val authService = AuthService()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReviewBinding.inflate(inflater, container, false)

        reviewView.setReviewView(this)
        authService.setRefreshView(this)

        spf = this.requireContext().getSharedPreferences("roomId", AppCompatActivity.MODE_PRIVATE)
        var roomId = spf.getInt("roomId", 0 )
        var userSpf = this.requireContext().getSharedPreferences("currentUser", AppCompatActivity.MODE_PRIVATE)
        var userJson = userSpf.getString("User", "")
        user = gson.fromJson(userJson, User::class.java)
        accessToken = user.accessToken
        refreshToken = user.refreshToken

        binding.reviewTv.setOnClickListener {
            var intent = Intent(this.context, ReviewActivity::class.java)
            intent.putExtra("room", roomId)
            startActivity(intent)
        }

        reviewRVAdapter = ReviewRVAdapter(this.requireContext(), reviewlist)
        binding.reviewRv.adapter = reviewRVAdapter
        binding.reviewRv.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)

        reviewRVAdapter.setMyItemClickListener(object : ReviewRVAdapter.MyItemClickListener {
            override fun onItemClick(review: Review, imageView: ImageView) {
                Log.d("reviewId", review.id.toString())
                var popupMenu = PopupMenu(context, imageView)
                var menuInflater = popupMenu.menuInflater
                menuInflater.inflate(R.menu.review_menu, popupMenu.menu)
                popupMenu.setOnMenuItemClickListener { menuItem ->
                    if (menuItem.itemId == R.id.delete) {
                        reviewView.deleteReview(user.accessToken, review.id)
                        reviewlist.remove(review)
                    }
                    return@setOnMenuItemClickListener true
                }
                popupMenu.show()
            }

        })

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        var roomId = spf.getInt("roomId", 0 )
        reviewView.getReviews(accessToken, roomId)
    }

    override fun onResume() {
        super.onResume()

        var roomId = spf.getInt("roomId", 0 )
        reviewView.getReviews(accessToken, roomId)
    }

    override fun onDestroy() {
        super.onDestroy()
        reviewlist.clear()
    }

    override fun onAddReviewSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onAddReviewFailure(code: Int, message: String) {
        TODO("Not yet implemented")
    }

    override fun onReviewSuccess(response: ArrayList<Review>) {
        if (reviewlist.isNotEmpty()) {
            reviewlist.clear()
        }
        if (response != null) {
            for (i in response) {
                reviewlist.add(i)
            }
            reviewRVAdapter.notifyDataSetChanged()
            Log.d("GETREVIEW/SUCCESS", "리뷰 가져오기 성공")
        }
        if (reviewlist.isNotEmpty()) {
            binding.noneTv.visibility = View.GONE
        } else {
            binding.noneTv.visibility = View.VISIBLE
        }
        binding.reviewCountTv.text = "전체 리뷰 ${response.size} 개"
    }

    override fun onReviewFailure(code: Int, message: String) {
        when (code) {
            401 -> {
                Log.d("GetReview/FAILURE", "$code/$message")
                authService.refresh(accessToken, RefreshRequest(refreshToken))
            }
            403 -> Log.d("GetReview/FAILURE", "$code/$message")
        }
    }

    override fun onDeleteReviewSuccess(message: String) {
        Toast.makeText(context, "해당 리뷰 삭제 완료", Toast.LENGTH_LONG).show()
        reviewRVAdapter.notifyDataSetChanged()
        binding.reviewTv.text = "전체 리뷰 ${reviewlist.size} 개"
        Log.d("DeleteReview/Success", message)
    }

    override fun onDeleteReviewFailure(code: Int, message: String) {
        when (code) {
            401 -> {
                Log.d("DeleteReview/Failure", "$code/$message")
                authService.refresh(user.accessToken, RefreshRequest(user.refreshToken))
            }
            403 -> Log.d("DeleteReview/Failure", "$code/$message")
        }
    }

    override fun onRefreshSuccess(accessToken: String, refreshToken: String) {
        val updateUser = User(accessToken, refreshToken, user.nickname, null, "General")
        val gson = Gson()
        val userJson = gson.toJson(updateUser)
        val userSpf = this.requireContext().getSharedPreferences("currentUser", AppCompatActivity.MODE_PRIVATE)
        val editor = userSpf.edit()
        editor.apply {
            putString("User", userJson)
        }

        editor.commit()

        var roomId = spf.getInt("roomId", 0 )
        reviewView.getReviews(accessToken, roomId)

        Log.d("ReGetReview", "${updateUser.accessToken}/${updateUser.role}")
    }

    override fun onRefreshFailure(code: Int, message: String) {
        when (code) {
            401 -> {
                Log.d("Refresh/Failure", "$code/$message")
                authService.refresh(accessToken, RefreshRequest(user.refreshToken))
            }
            403 -> Log.d("Refresh/Failure", "$code/$message")
        }
    }
}