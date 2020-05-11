package com.damir.android.translator

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.damir.android.translator.db.entity.KirLat

class ChatAdapter(
    private val onTextMessageClicked: (view: View, position: Int) -> Unit
): ListAdapter<KirLat, MessageHolder>(MessageDiffUtil) {

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
        kirLat: KirLat,
        position: Int,
        onTextMessageClicked: (view: View, position: Int) -> Unit
    ) {
        messageText.text = kirLat.text
        messageText.setOnClickListener {
            onTextMessageClicked(it, position)
        }
    }
}

object MessageDiffUtil : DiffUtil.ItemCallback<KirLat>(){

    override fun areItemsTheSame(oldItem: KirLat, newItem: KirLat): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: KirLat, newItem: KirLat): Boolean {
        return oldItem == newItem
    }

}