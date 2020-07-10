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
import com.damir.android.translator.utils.MessagePayload

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

    override fun onBindViewHolder(
        holder: MessageHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if(payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            holder.bind(getItem(position), position, onTextMessageClicked, payloads.first() as? Set<*>)
        }
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
        updateText(message.text)
        messageText.setOnClickListener {
            onTextMessageClicked(it, position)
        }
    }

    fun bind(
        message: Message,
        position: Int,
        onTextMessageClicked: (view: View, position: Int) -> Unit,
        fields: Set<*>?
    ) {
        fields?.forEach {
            if(it == MessagePayload.TEXT) {
                updateText(message.text)
            }
        }
        setBackgroundTranslation(message)
        messageText.setOnClickListener {
            onTextMessageClicked(it, position)
        }
    }

    private fun updateText(text: String) {
        messageText.text = text
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

    override fun getChangePayload(oldItem: Message, newItem: Message): Any? {
        val fields = mutableListOf<MessagePayload>()

        if(oldItem.id != newItem.id) fields.add(MessagePayload.ID)
        if(oldItem.text != newItem.text) fields.add(MessagePayload.TEXT)

        return when {
            fields.isNotEmpty() -> fields
            else -> super.getChangePayload(oldItem, newItem)
        }
    }

}