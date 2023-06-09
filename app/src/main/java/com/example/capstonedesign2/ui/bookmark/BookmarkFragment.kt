package com.example.capstonedesign2.ui.bookmark

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstonedesign2.data.entities.User
import com.example.capstonedesign2.data.remote.*
import com.example.capstonedesign2.databinding.FragmentBookmarkBinding
import com.example.capstonedesign2.ui.detail.DetailActivity
import com.example.capstonedesign2.ui.login.RefreshView
import com.google.gson.Gson

class BookmarkFragment() : Fragment(), BookmarkView, RefreshView {
    lateinit var binding: FragmentBookmarkBinding
    lateinit var bookmarkView: BookmarkService
    lateinit var bookmarkRVAdapter: BookmarkRVAdapter
    lateinit var user: User
    private var authService = AuthService()
    private var bookmarkList = ArrayList<EstateInfo>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBookmarkBinding.inflate(inflater, container, false)

        var spf = this.requireContext().getSharedPreferences("currentUser", AppCompatActivity.MODE_PRIVATE)
        var gson = Gson()
        var userJson = spf.getString("User", "")
        user = gson.fromJson(userJson, User::class.java)

        bookmarkView = BookmarkService()

        Log.d("현재 북마크 개수", bookmarkList.size.toString())


        if (bookmarkList.isNotEmpty()) {
            bookmarkList.clear()
        }

        bookmarkRVAdapter = BookmarkRVAdapter(this.requireContext(), bookmarkList)
        binding.bookmarkRv.adapter = bookmarkRVAdapter
        binding.bookmarkRv.layoutManager = LinearLayoutManager(this.requireContext(), LinearLayoutManager.VERTICAL, false)

        var intent = Intent(this.context, DetailActivity::class.java)

        bookmarkRVAdapter.setMyItemClickListener(object : BookmarkRVAdapter.MyItemClickListener {
            override fun onItemClick(estateInfo: EstateInfo) {
                intent.putExtra("roomId", estateInfo.id.toString())
                intent.putExtra("bookmarkOn", true)
                startActivity(intent)
            }
        })

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        bookmarkView.setBookmarkView(this)
        bookmarkView.getBookmark(user.accessToken)
    }

    override fun onDestroy() {
        super.onDestroy()

        bookmarkList.clear()
    }

    override fun onBookmarkSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onBookmarkFailure(code: Int, message: String) {
        TODO("Not yet implemented")
    }

    override fun onBMListSuccess(bookmarklist: ArrayList<EstateInfo>?) {
        if (this.bookmarkList.isNotEmpty()) {
            this.bookmarkList.clear()
        }
        if (bookmarklist != null) {
            for (i in bookmarklist) {
                bookmarkList.add(i)
            }
            bookmarkRVAdapter.notifyDataSetChanged()
            Log.d("BOOKMARKLIST/SUCCESS", "북마크 리스트 가져오기 성공")
        }
    }

    override fun onBMListFailure(code: Int, message: String) {
        when (code) {
            401 -> {
                Log.d("Register/Failure", "$code/$message")
                authService.refresh(RefreshRequest(user.refreshToken))
            }
            403 -> Log.d("BookMarkList/FAILURE", "$code/$message")
        }
    }

    override fun onRefreshSuccess(accessToken: String, refreshToken: String) {
        val updateUser =
            User(accessToken, refreshToken, user.nickname, user.registerNumber, "Broker")
        this.onAttach(this.requireContext())
            .let {
                val gson = Gson()
                val userJson = gson.toJson(updateUser)
                val userSpf = this.requireContext()
                    .getSharedPreferences("currentUser", AppCompatActivity.MODE_PRIVATE)
                val editor = userSpf.edit()
                editor.apply {
                    putString("User", userJson)
                }

                editor.apply()

                bookmarkView.getBookmark(accessToken)

                Log.d("ReGetBookmarkList", "${updateUser.accessToken}/${updateUser.refreshToken}")

            }
    }

    override fun onRefreshFailure(code: Int, message: String) {
        when (code) {
            401 -> {
                Log.d("Refresh/Failure", "$code/$message")
                authService.refresh(RefreshRequest(user.refreshToken))
            }
            403 -> Log.d("Refresh/Failure", "$code/$message")
        }
    }
}