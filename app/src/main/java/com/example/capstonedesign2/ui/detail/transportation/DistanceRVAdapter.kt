package com.example.capstonedesign2.ui.detail.transportation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstonedesign2.R
import com.example.capstonedesign2.data.remote.Environment
import com.example.capstonedesign2.databinding.ItemDetailBinding

class DistanceRVAdapter(private val environments: ArrayList<Environment>) : RecyclerView.Adapter<DistanceRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged", "SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (environments[position].type) {
            "지하철역" -> {
                holder.img.setImageResource(R.drawable.outline_subway_24)
                holder.distance.text = environments[position].type+"까지 "+environments[position].transport+"로 "+environments[position].time+"분"
            }
            "버스정류장" -> {
                holder.img.setImageResource(R.drawable.outline_subway_24)
                holder.distance.text =
                    environments[position].type + "까지 " + environments[position].transport + "로 " + environments[position].time + "분"
            }
            else -> {
                holder.itemView.visibility = View.GONE
            }
        }
    }

    override fun getItemCount(): Int = environments.size

    inner class ViewHolder(val binding: ItemDetailBinding) : RecyclerView.ViewHolder(binding.root) {
        val img = binding.itemIv
        val distance = binding.itemTv
    }
}