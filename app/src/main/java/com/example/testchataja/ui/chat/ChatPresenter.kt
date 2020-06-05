package com.example.testchataja.ui.chat

import com.example.testchataja.models.ChatData
import com.google.firebase.database.*

class ChatPresenter(private val chatContract: ChatContract) {
    private lateinit var ref : DatabaseReference
    fun getChat() {
        val listChat = arrayListOf<ChatData>()
        ref = FirebaseDatabase.getInstance().getReference("chats")
        ref.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                chatContract.whenFailureGetChats(p0.message)
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()){

                    listChat.clear()
                    for(i in p0.children) {
                        val chat = i.getValue(ChatData::class.java)
                        chat?.let { listChat.add(0, it) }
                    }

                    chatContract.whenSuccessGetChats(listChat)
                }
            }

        })
    }

    fun sendChat(name : String, message : String, date : String) {
        val chatId = ref.push().key
        val chat = ChatData(chatId,message,name,date)
        chatId?.let { ref.child(it).setValue(chat) }
    }
}