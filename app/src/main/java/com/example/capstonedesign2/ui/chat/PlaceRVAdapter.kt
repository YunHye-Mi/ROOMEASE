package com.example.capstonedesign2.ui.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstonedesign2.data.entities.Bookmark
import com.example.capstonedesign2.data.remote.PlaceSearch
import com.example.capstonedesign2.databinding.ItemResultBinding
import com.example.capstonedesign2.databinding.ItemSearchBinding
import kotlin.collections.ArrayList

class PlaceRVAdapter(private val searchList: ArrayList<PlaceSearch>) : RecyclerView.Adapter<PlaceRVAdapter.ViewHolder>() {

    interface MyItemClickListener{
        fun onItemClick(placeSearch: PlaceSearch)
    }

    private lateinit var mItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(searchList[position])
        holder.itemView.setOnClickListener { mItemClickListener.onItemClick(searchList[position]) }
    }

    override fun getItemCount(): Int = searchList.size

    inner class ViewHolder(val binding: ItemSearchBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(placeSearch: PlaceSearch) {
            binding.addressTv.text = placeSearch.name
            binding.addressTv.textSize = 14.0f
            binding.detailAddressTv.text = placeSearch.road
            binding.detailAddressTv.textSize = 9.0f
        }
    }
}