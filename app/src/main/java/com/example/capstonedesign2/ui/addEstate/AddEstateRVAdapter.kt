package com.example.capstonedesign2.ui.bookmark

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.capstonedesign2.data.remote.EstateInfo
import com.example.capstonedesign2.databinding.ItemBrokerestaeBinding
import kotlin.collections.ArrayList

class AddEstateRVAdapter(val context: Context, private val roomList: ArrayList<EstateInfo>) : RecyclerView.Adapter<AddEstateRVAdapter.ViewHolder>() {

    interface MyItemClickListener{
        fun onItemClick(estateInfo: EstateInfo)
    }

    private lateinit var mItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = ItemBrokerestaeBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(roomList[position])
        holder.itemView.setOnClickListener { mItemClickListener.onItemClick(roomList[position]) }
    }

    override fun getItemCount(): Int = roomList.size

    inner class ViewHolder(val binding: ItemBrokerestaeBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(estateInfo: EstateInfo) {
            Glide.with(context).load("${estateInfo.images}?w=400&h=300").into(binding.roomIv)
            binding.rentAreaTv.text = estateInfo.size.toString()+"(m²)"
            binding.rentPriceTv.text = "${estateInfo.deposit}/${estateInfo.rent} 만원"
        }
    }
}