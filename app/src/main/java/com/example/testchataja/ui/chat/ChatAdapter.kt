package com.example.testchataja.ui.chat

import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testchataja.R
import com.example.testchataja.models.ChatData
import kotlinx.android.synthetic.main.list_chat.view.*
import org.apache.commons.lang3.StringEscapeUtils
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ChatAdapter(private val listChat : ArrayList<ChatData>, private val nameUser : String)
    : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_chat, parent,false)
        return ChatViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listChat.size
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(listChat[position],nameUser)
    }

    class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(lc : ChatData, nameRight : String) {
            with(itemView) {
                with(lc) {
                    val dateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())
                    val frmt = dateFormat.parse(createdAt!!)
                    val format = SimpleDateFormat("E, dd MMMM yyyy HH:mm", Locale.getDefault())
                    val dateStr = format.format(frmt!!)
                    if(name != nameRight) {
                        chat_right.visibility = GONE
                        chat_left.visibility = VISIBLE
                        tv_message1.text = StringEscapeUtils.unescapeJava(message)
                        tv_message2.text = StringEscapeUtils.unescapeJava(message)
                        tv_date1.text = dateStr
                        tv_date2.text = dateStr
                    }
                    if (name == nameRight){
                        chat_left.visibility = GONE
                        chat_right.visibility = VISIBLE
                        tv_message1.text = StringEscapeUtils.unescapeJava(message)
                        tv_message2.text = StringEscapeUtils.unescapeJava(message)
                        tv_date1.text = dateStr
                        tv_date2.text = dateStr
                    }
                }
            }
        }
    }
}