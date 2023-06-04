package com.example.capstonedesign2.ui.bookmark

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.capstonedesign2.data.remote.EstateInfo
import com.example.capstonedesign2.databinding.ItemBookmarkBinding
import kotlin.collections.ArrayList

class BookmarkRVAdapter(val context: Context, private val bookmarkList: ArrayList<EstateInfo>) : RecyclerView.Adapter<BookmarkRVAdapter.ViewHolder>() {

    interface MyItemClickListener{
        fun onItemClick(estateInfo: EstateInfo)
    }

    private lateinit var mItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = ItemBookmarkBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(bookmarkList[position])
        holder.itemView.setOnClickListener { mItemClickListener.onItemClick(bookmarkList[position]) }
    }

    override fun getItemCount(): Int = bookmarkList.size

    inner class ViewHolder(val binding: ItemBookmarkBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(estateInfo: EstateInfo) {
            Glide.with(context).load("${estateInfo.images}?w=400&h=300").into(binding.roomIv)
            binding.rentAreaTv.text = estateInfo.size.toString()+"(m²)"
            binding.rentPriceTv.text = "${estateInfo.deposit}/${estateInfo.rent}"
        }
    }
}