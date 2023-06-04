package com.example.capstonedesign2.ui.chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstonedesign2.data.remote.Document
import com.example.capstonedesign2.databinding.ItemSearchBinding
import kotlin.collections.ArrayList

class PlaceRVAdapter(private val searchList: ArrayList<Document>) : RecyclerView.Adapter<PlaceRVAdapter.ViewHolder>() {

    interface MyItemClickListener{
        fun onItemClick(documentSearch: Document)
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
        fun bind(document: Document) {
            binding.addressTv.text = document.placeName
            binding.addressTv.textSize = 14.0f
            binding.detailAddressTv.text = document.road_address_name
            binding.detailAddressTv.textSize = 9.0f
        }
    }
}