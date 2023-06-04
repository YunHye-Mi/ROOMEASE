package com.example.capstonedesign2.ui.detail.review

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.capstonedesign2.R
import com.example.capstonedesign2.data.entities.User
import com.example.capstonedesign2.data.remote.AuthService
import com.example.capstonedesign2.data.remote.RefreshRequest
import com.example.capstonedesign2.data.remote.Review
import com.example.capstonedesign2.data.remote.ReviewService
import com.example.capstonedesign2.databinding.ItemReviewBinding
import com.example.capstonedesign2.ui.detail.ReviewView
import com.example.capstonedesign2.ui.login.RefreshView
import com.google.gson.Gson
import kotlin.collections.ArrayList

class ReviewRVAdapter(val context: Context, private val reviewList: ArrayList<Review>) : RecyclerView.Adapter<ReviewRVAdapter.ViewHolder>() {

    interface MyItemClickListener{
        fun onItemClick(review: Review, imageView: ImageView)
    }

    private lateinit var mItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(reviewList[position])
        holder.binding.moreIv.setOnClickListener { mItemClickListener.onItemClick(reviewList[position], holder.binding.moreIv) }
    }

    override fun getItemCount(): Int = reviewList.size

    inner class ViewHolder(val binding: ItemReviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(review: Review) {
            binding.userNameTv.text = review.name
            binding.periodTv.text = "${review.period}년 이내 거주"
            binding.rateTv.text = review.score.toString()
            if (review.freeComments == "") {
                binding.contentTv.visibility = View.GONE
            } else {
                binding.contentTv.text = review.freeComments
            }
            if (review.myReview) {
                binding.moreIv.visibility = View.VISIBLE
            } else {
                binding.moreIv.visibility = View.GONE
            }
        }
    }
}