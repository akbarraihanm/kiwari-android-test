package com.example.testchataja.ui.chat

import com.example.testchataja.models.ChatData

interface ChatContract {
    fun whenSuccessGetChats(listChat: ArrayList<ChatData>)
    fun whenFailureGetChats(msg : String)
}