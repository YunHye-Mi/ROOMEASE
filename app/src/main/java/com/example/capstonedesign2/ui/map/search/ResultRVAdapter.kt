package com.example.capstonedesign2.ui.map.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstonedesign2.data.entities.Estate
import com.example.capstonedesign2.databinding.ItemResultBinding
import com.squareup.picasso.Picasso
import java.util.ArrayList

class ResultRVAdapter(private val estateList: ArrayList<Estate>) : RecyclerView.Adapter<ResultRVAdapter.ViewHolder>() {

    interface MyItemClickListener{
        fun onItemClick(estate: Estate)
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
        holder.bind(estateList[position])
        holder.itemView.setOnClickListener { mItemClickListener.onItemClick(estateList[position]) }
    }

    override fun getItemCount(): Int = estateList.size

    inner class ViewHolder(val binding: ItemResultBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(estate: Estate) {
            if (estate.coverImg != null) {
                var imageUrl = estate.coverImg
                Picasso.get().load(imageUrl).into(binding.roomIv)
            }
            if (estate.sales_type == "전세") {
                binding.rentGbnTv.text = "전세"
                binding.rentPriceTv.text = estate.deposit + "만원"
            } else {
                binding.rentGbnTv.text = "월세"
                binding.rentPriceTv.text = estate.deposit + "/" +  estate.rent + "만원"
            }
            binding.rentAreaTv.text = estate.size + "m\u00B2"
            binding.flrNoTv.text = estate.floor + " 층"
        }
    }
}