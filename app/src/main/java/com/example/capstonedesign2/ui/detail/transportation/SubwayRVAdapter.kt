package com.example.capstonedesign2.ui.detail.transportation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstonedesign2.R
import com.example.capstonedesign2.data.remote.NearSubway
import com.example.capstonedesign2.databinding.ItemDetailBinding

class SubwayRVAdapter(private val nearSubways: ArrayList<NearSubway>) : RecyclerView.Adapter<SubwayRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = ItemDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.img.setImageResource(R.drawable.outline_subway_24)
        holder.trans.text = nearSubways[position].name + nearSubways[position].description
    }

    override fun getItemCount(): Int = nearSubways.size

    inner class ViewHolder(val binding: ItemDetailBinding) : RecyclerView.ViewHolder(binding.root) {
        val img = binding.itemIv
        val trans = binding.itemTv
    }
}