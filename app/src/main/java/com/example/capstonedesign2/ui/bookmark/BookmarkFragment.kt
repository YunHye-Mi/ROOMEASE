package com.example.capstonedesign2.ui.bookmark

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstonedesign2.data.entities.Bookmark
import com.example.capstonedesign2.data.entities.User
import com.example.capstonedesign2.data.remote.BookmarkListResponse
import com.example.capstonedesign2.data.remote.BookmarkService
import com.example.capstonedesign2.databinding.FragmentBookmarkBinding
import com.google.gson.Gson

class BookmarkFragment() : Fragment(), BookmarkView {
    lateinit var binding: FragmentBookmarkBinding
    lateinit var bookmarkView: BookmarkService
    lateinit var bookmarkRVAdapter: BookmarkRVAdapter
    private var bookmarkList = ArrayList<Bookmark>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBookmarkBinding.inflate(inflater, container, false)

        var spf = this.requireContext().getSharedPreferences("currentUser", AppCompatActivity.MODE_PRIVATE)
        var gson = Gson()
        var userJson = spf.getString("User", "")
        var user = gson.fromJson(userJson, User::class.java)

        bookmarkView.setBookmarkView(this)
        bookmarkView.getBookmark(user.token)

        bookmarkRVAdapter = BookmarkRVAdapter(bookmarkList)
        binding.bookmarkRv.adapter = bookmarkRVAdapter
        binding.bookmarkRv.layoutManager = LinearLayoutManager(this.requireContext(), LinearLayoutManager.VERTICAL, false)

        return binding.root
    }

    override fun onBookmarkSuccess(code: Int, message: String) {
        TODO("Not yet implemented")
    }

    override fun onBookmarkFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onBMListSuccess(bookmarkListResponse: BookmarkListResponse) {
        bookmarkList.add(bookmarkListResponse.bookmark)
        bookmarkRVAdapter.notifyDataSetChanged()
    }

    override fun onBMListFailure(message: String) {
        Log.d("BOOKMARK/FAILURE", message)
    }
}