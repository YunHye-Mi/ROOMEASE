package com.example.capstonedesign2.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstonedesign2.data.entities.Review
import com.example.capstonedesign2.databinding.ItemReviewBinding
import java.util.ArrayList

class ReviewRVAdapter(private val reviewList: ArrayList<Review>) : RecyclerView.Adapter<ReviewRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(reviewList[position])
    }

    override fun getItemCount(): Int = reviewList.size

    inner class ViewHolder(val binding: ItemReviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(review: Review) {
            binding.userNameTv.text = review.user
            binding.periodTv.text = review.period
            binding.rateTv.text = ((review.public_rate + review.ambient_rate + review.lived_rate) / 3 ).toString()
            binding.contentTv.text = review.content
        }
    }
}