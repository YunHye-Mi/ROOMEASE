package com.example.capstonedesign2.ui.bookmark

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstonedesign2.data.entities.Bookmark
import com.example.capstonedesign2.databinding.ItemResultBinding
import kotlin.collections.ArrayList

class BookmarkRVAdapter(private val bookmarkList: ArrayList<Bookmark>) : RecyclerView.Adapter<BookmarkRVAdapter.ViewHolder>() {

    interface MyItemClickListener{
        fun onItemClick(bookmark: Bookmark)
        fun deleteItem(bookmark: Bookmark)
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
        holder.bind(bookmarkList[position])
        holder.itemView.setOnClickListener { mItemClickListener.onItemClick(bookmarkList[position]) }
    }

    override fun getItemCount(): Int = bookmarkList.size

    inner class ViewHolder(val binding: ItemResultBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(bookmark: Bookmark) {

        }
    }
}