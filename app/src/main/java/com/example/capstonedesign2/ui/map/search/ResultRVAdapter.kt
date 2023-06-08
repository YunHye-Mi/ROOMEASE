package com.example.capstonedesign2.ui.map.search

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.capstonedesign2.data.remote.ResultResponse
import com.example.capstonedesign2.databinding.ItemResultBinding

class ResultRVAdapter(val context: Context, private val resultResponseList: ArrayList<ResultResponse>) : RecyclerView.Adapter<ResultRVAdapter.ViewHolder>() {

    interface MyItemClickListener{
        fun onItemClick(resultResponse: ResultResponse)
    }

    private lateinit var mItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = ItemResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(resultResponseList[position])
        holder.itemView.setOnClickListener { mItemClickListener.onItemClick(resultResponseList[position]) }
    }

    override fun getItemCount(): Int = resultResponseList.size

    inner class ViewHolder(val binding: ItemResultBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(resultResponse: ResultResponse) {
            if (resultResponse.coverImg == "" || resultResponse.coverImg == null) {

            } else {
                Glide.with(context).load("${resultResponse.coverImg}?w=400&h=300").into(binding.roomIv)
            }
            if (resultResponse.sales_type == "전세") {
                binding.rentGbnTv.text = "전세"
                binding.rentPriceTv.text = resultResponse.deposit.toString()
            } else {
                binding.rentGbnTv.text = "월세"
                binding.rentPriceTv.text = resultResponse.deposit.toString() + "/" +  resultResponse.rent.toString()
            }
            binding.rentAreaTv.text = resultResponse.size.toString() + "(m²)"
        }
    }
}