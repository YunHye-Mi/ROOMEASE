package com.example.capstonedesign2.ui.bookmark

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstonedesign2.data.entities.Estate
import com.example.capstonedesign2.databinding.ItemResultBinding
import java.util.ArrayList

class AddEstateRVAdapter(private val estateList: ArrayList<Estate>) : RecyclerView.Adapter<AddEstateRVAdapter.ViewHolder>() {

    interface MyItemClickListener{
        fun onItemClick(estate: Estate)
        fun deleteItem(estate: Estate)
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

        }
    }
}