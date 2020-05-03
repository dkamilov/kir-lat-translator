package com.damir.android.translator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChatAdapter(
    private val messages: List<Message>
): RecyclerView.Adapter<MessageHolder>() {

    private val MESSAGE_SENDER = 0
    private val MESSAGE_RECEIVER = 1

    override fun getItemViewType(position: Int): Int {
        return if(messages[position].isSender){
            MESSAGE_SENDER
        }else{
            MESSAGE_RECEIVER
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if(viewType == MESSAGE_SENDER) {
            val view = inflater.inflate(R.layout.item_send_message, parent, false)
            MessageHolder(view)
        }else{
            val view = inflater.inflate(R.layout.item_receive_message, parent, false)
            MessageHolder(view)
        }
    }

    override fun onBindViewHolder(holder: MessageHolder, position: Int) {
        holder.bind(messages[position])
    }


    override fun getItemCount(): Int = messages.size

    fun updateMessages() {
        notifyDataSetChanged()
    }
}

class MessageHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val messageText = itemView.findViewById<TextView>(R.id.text_message)

    fun bind(message: Message) {
        messageText.text = message.text
    }
}