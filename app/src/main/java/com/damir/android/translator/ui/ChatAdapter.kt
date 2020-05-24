package com.damir.android.translator.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.damir.android.translator.R
import com.damir.android.translator.data.db.entity.Message

class ChatAdapter(
    private val onTextMessageClicked: (view: View, position: Int) -> Unit
): ListAdapter<Message, MessageHolder>(
    MessageDiffUtil
) {

    private val VIEW_TYPE_MESSAGE_SENDER = 0
    private val VIEW_TYPE_MESSAGE_RECEIVER = 1

    override fun getItemViewType(position: Int): Int {
        return if(getItem(position).isSender){
            VIEW_TYPE_MESSAGE_SENDER
        }else{
            VIEW_TYPE_MESSAGE_RECEIVER
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if(viewType == VIEW_TYPE_MESSAGE_SENDER) {
            val view = inflater.inflate(R.layout.item_send_message, parent, false)
            MessageHolder(view)
        }else{
            val view = inflater.inflate(R.layout.item_receive_message, parent, false)
            MessageHolder(view)
        }
    }

    override fun onBindViewHolder(holder: MessageHolder, position: Int) {
        holder.bind(getItem(position), position, onTextMessageClicked)
    }
}

class MessageHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val messageText = itemView.findViewById<TextView>(R.id.text_message)

    fun bind(
        message: Message,
        position: Int,
        onTextMessageClicked: (view: View, position: Int) -> Unit
    ) {
        setBackgroundTranslation(message)
        messageText.text = message.text
        messageText.setOnClickListener {
            onTextMessageClicked(it, position)
        }
    }

    private fun setBackgroundTranslation(message: Message) {
        if(message.isTranslation && message.isSender) {
            messageText.setBackgroundResource(R.drawable.bg_item_send_message_translation)
            messageText.setTextColor(ContextCompat.getColor(itemView.context, R.color.white))
        }
    }
}

object MessageDiffUtil : DiffUtil.ItemCallback<Message>(){

    override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem == newItem
    }

}