package com.example.capstonedesign2.ui

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstonedesign2.R
import com.example.capstonedesign2.data.entities.Estate
import com.example.capstonedesign2.databinding.ItemResultBinding
import java.util.ArrayList

class ResultRVAdapter(private val estateList: ArrayList<Estate>) : RecyclerView.Adapter<ResultRVAdapter.ViewHolder>() {

    interface MyItemClickListner{
        fun onItemClick(estate: Estate)
    }

    private lateinit var mItemClickListener: MyItemClickListner
    fun setMyItemClickListener(itemClickListener: MyItemClickListner){
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = ItemResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(estateList[position])
        holder.itemView.setOnClickListener { mItemClickListener.onItemClick(estateList[position]) }
    }

    override fun getItemCount(): Int = estateList.size

    inner class ViewHolder(val binding: ItemResultBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(estate: Estate) {
            if (estate.coverImgList != null) binding.roomIv.setImageResource(estate.coverImgList!![0])
            binding.buildnameTv.text = estate.name
            if (estate.rent_gbn.toString() == "전세") {
                binding.rentGbnTv.text = "전세"
                binding.rentPriceTv.text = estate.rent_gtn.toString() + "만원"
            } else {
                binding.rentGbnTv.text = "월세"
                binding.rentPriceTv.text = estate.rent_gtn.toString() + "/" +  estate.rent_fee.toString() + "만원"
            }
            binding.rentAreaTv.text = estate.flr_no.toString() + "m\u00B2"
            binding.flrNoTv.text = estate.flr_no.toString() + " 층"
        }
    }
}