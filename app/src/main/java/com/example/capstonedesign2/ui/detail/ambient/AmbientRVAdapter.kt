package com.example.capstonedesign2.ui.detail.ambient

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstonedesign2.R
import com.example.capstonedesign2.data.remote.Environment
import com.example.capstonedesign2.databinding.ItemDetailBinding
import java.util.ArrayList

class AmbientRVAdapter(private val environmentList: ArrayList<Environment>) : RecyclerView.Adapter<AmbientRVAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when(environmentList[position].type) {
            "세탁소" -> {
                holder.binding.itemIv.setImageResource(R.drawable.outline_local_laundry_service_24)
                holder.binding.itemTv.text = environmentList[position].type + "까지 " +
                        environmentList[position].transport + "로 "+environmentList[position].time +"분"
            }
            "카페" -> {
                holder.binding.itemIv.setImageResource(R.drawable.outline_local_cafe_24)
                holder.binding.itemTv.text = environmentList[position].type + "까지 " +
                        environmentList[position].transport + "로 "+environmentList[position].time +"분"
            }
            "약국" -> {
                holder.binding.itemIv.setImageResource(R.drawable.outline_medication_24)
                holder.binding.itemTv.text = environmentList[position].type + "까지 " +
                        environmentList[position].transport + "로 "+environmentList[position].time +"분"
            }
            "대형마트" -> {
                holder.binding.itemIv.setImageResource(R.drawable.outline_store_24)
                holder.binding.itemTv.text = environmentList[position].type + "까지 " +
                        environmentList[position].transport + "로 "+environmentList[position].time +"분"
            }
            "편의점" -> {
                holder.binding.itemIv.setImageResource(R.drawable.outline_local_convenience_store_24)
                holder.binding.itemTv.text = environmentList[position].type + "까지 " +
                        environmentList[position].transport + "로 "+environmentList[position].time +"분"
            }
            else -> {
                holder.itemView.visibility = View.GONE
            }

        }
    }

    override fun getItemCount(): Int = environmentList.size

    inner class ViewHolder(val binding: ItemDetailBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}