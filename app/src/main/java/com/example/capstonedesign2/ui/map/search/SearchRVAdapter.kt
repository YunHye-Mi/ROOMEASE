package com.example.capstonedesign2.ui.map.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstonedesign2.data.entities.Address
import com.example.capstonedesign2.data.entities.Estate
import com.example.capstonedesign2.databinding.ItemResultBinding
import com.example.capstonedesign2.databinding.ItemSearchBinding
import java.util.ArrayList

class SearchRVAdapter(private var addressList: ArrayList<Address>) : RecyclerView.Adapter<SearchRVAdapter.ViewHolder>() {

    interface MyItemClickListener{
        fun onItemClick(address: Address)
    }

    private lateinit var mItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener = itemClickListener
    }

    fun setFilteredList(addressList: ArrayList<Address>) {
        this.addressList = addressList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(addressList[position])
        holder.itemView.setOnClickListener { mItemClickListener.onItemClick(addressList[position]) }
    }

    override fun getItemCount(): Int = addressList.size

    inner class ViewHolder(val binding: ItemSearchBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(address: Address) {
            binding.addressTv.text = address.address
            binding.detailAddressTv.text = address.detail
        }
    }
}