package com.damir.android.translator

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_kir_lat.*

class KirLatFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_kir_lat, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setToolbar()
        setSendMessageBtn()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_kirlat, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun setToolbar() {
        toolbar.title = getString(R.string.kirlat)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
    }

    private fun setSendMessageBtn() {
        btn_send.setOnClickListener {
            sendMessage()
        }
    }

    private fun sendMessage() {
        if(edit_message.text.isNullOrBlank())
            return
        val msg = inflateMessageItem(R.layout.item_send_message)
        msg.text = edit_message.text
        addMessageToChat(msg)
        receiveMessage()
        edit_message.text = null
    }

    private fun receiveMessage() {
        val msg = inflateMessageItem(R.layout.item_receive_message)
        val cyrillic = edit_message.text.toString()
        val latin = KirLatTranslator.translate(cyrillic)
        msg.text = latin
        addMessageToChat(msg)
    }

    private fun inflateMessageItem(resource: Int): TextView {
        val msgView = layoutInflater.inflate(resource, layout_chat_list, false)
        return msgView as TextView
    }

    private fun addMessageToChat(msg: View) {
        layout_chat_list.addView(msg)
    }
}