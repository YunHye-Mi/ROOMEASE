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
        holder.bind(nearSubways[position])
    }

    override fun getItemCount(): Int = nearSubways.size

    inner class ViewHolder(val binding: ItemDetailBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(nearSubway: NearSubway) {
            binding.itemIv.setImageResource(R.drawable.outline_subway_24)
            binding.itemTv.text = nearSubway.name + nearSubway.description
            binding.distanceTv.text = "${nearSubway.distance}M/${nearSubway.time}분 소요"
            binding.goToWorkTv.text = nearSubway.crowding[0].toString()
            binding.afternoonTv.text = nearSubway.crowding[1].toString()
            binding.leaveWorkTv.text = nearSubway.crowding[2].toString()
            binding.midnightTv.text = nearSubway.crowding[3].toString()
        }
    }
}