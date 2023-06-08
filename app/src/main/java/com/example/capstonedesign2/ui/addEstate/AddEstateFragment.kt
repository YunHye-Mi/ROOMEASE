package com.example.capstonedesign2.ui.bookmark

import android.annotation.SuppressLint
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
import com.example.capstonedesign2.databinding.FragmentAddestateBinding
import com.example.capstonedesign2.ui.addEstate.BrokerView
import com.example.capstonedesign2.ui.detail.DetailActivity
import com.example.capstonedesign2.ui.login.RefreshView
import com.google.gson.Gson

class AddEstateFragment() : Fragment(), BrokerView, RefreshView {
    lateinit var binding: FragmentAddestateBinding
    lateinit var addEstateRVAdapter: AddEstateRVAdapter
    lateinit var user: User
    private var gson = Gson()
    private var brokerView = BookmarkService()
    private var roomList = ArrayList<EstateInfo>()
    private val authService = AuthService()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddestateBinding.inflate(inflater, container, false)

        brokerView.setBrokerView(this)
        authService.setRefreshView(this)

        val spf = this.requireContext().getSharedPreferences("currentUser", AppCompatActivity.MODE_PRIVATE)
        val userJson = spf.getString("User", "")
        user = gson.fromJson(userJson, User::class.java)

        addEstateRVAdapter = AddEstateRVAdapter(this.requireContext(), roomList)
        binding.estateRv.adapter = addEstateRVAdapter
        binding.estateRv.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)

        var intent = Intent(this.context, DetailActivity::class.java)

        addEstateRVAdapter.setMyItemClickListener(object : AddEstateRVAdapter.MyItemClickListener {
            override fun onItemClick(estateInfo: EstateInfo) {
                intent.putExtra("roomId", estateInfo.id.toString())
                startActivity(intent)
            }
        })

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        brokerView.getBrokerEstates(user.accessToken)
    }

    override fun onAddRoomSuccess(message: String) {
        TODO("Not yet implemented")
    }

    override fun onAddRoomFailure(code: Int, message: String) {
        TODO("Not yet implemented")
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onGetRoomSuccess(brokerList: ArrayList<EstateInfo>?) {
        if (roomList.isNotEmpty()) {
            roomList.clear()
        }
        if (brokerList != null) {
            for (i in brokerList) {
                roomList.add(i)
            }
            addEstateRVAdapter.notifyDataSetChanged()
        }
    }

    override fun onGetRoomFailure(code: Int, message: String) {
        when (code) {
            401 -> {
                Log.d("Register/Failure", "$code/$message")
                authService.refresh(RefreshRequest(user.refreshToken))
            }
            403 -> Log.d("Register/Failure", "$code/$message")
        }
    }

    override fun onRefreshSuccess(accessToken: String, refreshToken: String) {
        if (!isAdded) {
            val updateUser = User(accessToken, refreshToken, user.nickname, user.registerNumber, "Broker")
            val gson = Gson()
            val userJson = gson.toJson(updateUser)
            val userSpf = this.requireContext().getSharedPreferences("currentUser", AppCompatActivity.MODE_PRIVATE)
            val editor = userSpf.edit()
            editor.apply {
                putString("User", userJson)
            }

            editor.apply()

            brokerView.getBrokerEstates(accessToken)

            Log.d("ReGetBrokerEstate", "${updateUser.accessToken}/${updateUser.refreshToken}")

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