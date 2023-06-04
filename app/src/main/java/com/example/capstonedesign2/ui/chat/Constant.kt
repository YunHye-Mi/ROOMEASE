package com.example.capstonedesign2.ui.chat

object Constant {

    val MESSAGE_TYPE_ENTER: String = "ENTER"
    val MESSAGE_TYPE_TALK: String = "TALK"
    var SENDER: String = "DEFAULT"
    val URL: String = "ws://3.39.130.73:8080/ws-stomp/websocket"
    //ws://[도메인]/[엔드포인트]/websocket
    var CHATROOM_ID: String = "0"

    fun set(sender: String, chatRoomId: String){
        SENDER = sender
        CHATROOM_ID = chatRoomId
    }
}