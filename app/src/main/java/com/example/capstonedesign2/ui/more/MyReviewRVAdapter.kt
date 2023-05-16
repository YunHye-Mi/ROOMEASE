package com.example.capstonedesign2.ui.more

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstonedesign2.data.entities.Review
import com.example.capstonedesign2.databinding.ItemMyreviewBinding
import java.util.ArrayList

class MyReviewRVAdapter(private val reviewList: ArrayList<Review>) : RecyclerView.Adapter<MyReviewRVAdapter.ViewHolder>() {

    val checkedList = ArrayList<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = ItemMyreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(reviewList[position])
        holder.binding.deleteCb.setOnCheckedChangeListener { compoundButton, b ->
            if (compoundButton.isChecked) {
                checkedList.add(position)
            } else {
                checkedList.remove(position)
            }
        }
    }

    override fun getItemCount(): Int = reviewList.size

    inner class ViewHolder(val binding: ItemMyreviewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(review: Review) {
            binding.estateNameTv.text = review.estateId.toString()
            binding.myPeriodTv.text = review.period
            binding.rateTv.text = ((review.public_rate.toDouble() + review.ambient_rate.toDouble() + review.lived_rate.toDouble()) / 3.0 ).toDouble().toString()
            binding.contentTv.text = review.content
            binding.deleteCb.isChecked = false
        }
    }
}