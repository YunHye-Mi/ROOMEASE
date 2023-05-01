package com.example.capstonedesign2.ui.like

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstonedesign2.data.entities.Estate
import com.example.capstonedesign2.data.entities.Like
import com.example.capstonedesign2.databinding.ItemResultBinding
import java.util.ArrayList

class LikeRVAdapter(private val likeList: ArrayList<Like>) : RecyclerView.Adapter<LikeRVAdapter.ViewHolder>() {

    interface MyItemClickListener{
        fun onItemClick(like: Like)
        fun deleteItem(like: Like)
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
        holder.bind(likeList[position])
        holder.itemView.setOnClickListener { mItemClickListener.onItemClick(likeList[position]) }
    }

    override fun getItemCount(): Int = likeList.size

    inner class ViewHolder(val binding: ItemResultBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(like: Like) {

        }
    }
}