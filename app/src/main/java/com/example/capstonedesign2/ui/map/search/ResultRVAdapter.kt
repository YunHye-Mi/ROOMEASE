package com.example.capstonedesign2.ui.map.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstonedesign2.data.remote.ResultResponse
import com.example.capstonedesign2.databinding.ItemResultBinding
import com.squareup.picasso.Picasso

class ResultRVAdapter(private val resultList: ArrayList<ResultResponse>) : RecyclerView.Adapter<ResultRVAdapter.ViewHolder>() {

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
        holder.bind(resultList[position])
        holder.itemView.setOnClickListener { mItemClickListener.onItemClick(resultList[position]) }
    }

    override fun getItemCount(): Int = resultList.size

    inner class ViewHolder(val binding: ItemResultBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(resultResponse: ResultResponse) {
            if (resultResponse.coverImg != null) {
                var imageUrl = resultResponse.coverImg
                Picasso.get().load(imageUrl).into(binding.roomIv)
            }
            if (resultResponse.sales_type == "전세") {
                binding.rentGbnTv.text = "전세"
                binding.rentPriceTv.text = resultResponse.deposit.toString() + "만원"
            } else {
                binding.rentGbnTv.text = "월세"
                binding.rentPriceTv.text = resultResponse.deposit.toString() + "/" +  resultResponse.rent + "만원"
            }
            binding.rentAreaTv.text = resultResponse.size.toString() + "m\u00B2"
            binding.flrNoTv.text = resultResponse.floor + " 층"
        }
    }
}