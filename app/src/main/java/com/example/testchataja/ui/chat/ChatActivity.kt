package com.example.testchataja.ui.chat

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.testchataja.R
import com.example.testchataja.models.ChatData
import com.example.testchataja.ui.login.MainActivity
import com.example.testchataja.utils.AppPreferences
import com.example.testchataja.utils.ConstString
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.custom_actionbar.*
import java.text.SimpleDateFormat
import java.util.*

class ChatActivity : AppCompatActivity(), ChatContract {

    private lateinit var pref : AppPreferences
    private lateinit var adapter : ChatAdapter
    private lateinit var listChat : ArrayList<ChatData>
    private lateinit var chatPresenter: ChatPresenter
    private lateinit var layoutManager : LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        pref = AppPreferences(this)
        pref.setPreferences()
        listChat = arrayListOf()
        layoutManager = LinearLayoutManager(this,RecyclerView.VERTICAL,true)
        rv_list_chat.layoutManager = layoutManager
        rv_list_chat.itemAnimator = DefaultItemAnimator()
        chatPresenter = ChatPresenter(this)
        chatPresenter.getChat()
        setTitleName()
        btn_send.setOnClickListener {
            val msg = "${et_message.text}"
            if(msg.isNotEmpty()) {
                pref.getNameUser()?.let { it1 -> chatPresenter.sendChat(it1,msg,setDateTime()) }
                et_message.text = null
            }
        }
    }

    private fun setDateTime() : String {
        val date = Date()
        val sfd = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())
        return sfd.format(date)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.sub_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.menu_logout -> {
                val intent = Intent(this,MainActivity::class.java)
                pref.setNameUser("")
                startActivity(intent)
                finish()
                true
            } else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setTitleName() {
        setSupportActionBar(custom_toolbar)
        supportActionBar?.title = null
        supportActionBar?.elevation = 2.5F
        if(pref.getNameUser()==ConstString().userName1) {
            Glide.with(this)
                .load(ConstString().avatar2)
                .apply(RequestOptions().transform(CircleCrop()))
                .into(iv_user_image)
            tv_user_name.text = ConstString().userName2
        }
        if(pref.getNameUser()==ConstString().userName2) {
            Glide.with(this)
                .load(ConstString().avatar1)
                .apply(RequestOptions().transform(CircleCrop()))
                .into(iv_user_image)
            tv_user_name.text = ConstString().userName1
        }
    }

    override fun whenSuccessGetChats(listChat: ArrayList<ChatData>) {
        adapter = pref.getNameUser()?.let { ChatAdapter(listChat, it) }!!
        rv_list_chat.adapter = adapter
        layoutManager.scrollToPosition(0)
    }

    override fun whenFailureGetChats(msg: String) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }
}
