package com.damir.android.translator

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_kir_lat.*

class KirLatFragment : Fragment() {

    private lateinit var chatAdapter: ChatAdapter
    private val messages = mutableListOf<Message>()

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
        setChatRecycler()
        setSendMessageBtn()
        initializeChat()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_kirlat, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun initializeChat() {
        val abayMessage =
                "Абай (Ибраһим) Құнанбаев (1845-1904) — ақын, ағартушы, " +
                "жазба қазақ әдебиетінің, " +
                "қазақ әдеби тілінің негізін қалаушы, " +
                "философ, композитор"
        val salemMessage = "Сәлем, қалайсың?"
        sendMessage(abayMessage)
        sendMessage(salemMessage)
    }

    private fun setToolbar() {
        toolbar.title = getString(R.string.kirlat)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
    }

    private fun setChatRecycler() {
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.stackFromEnd = true
        chatAdapter = ChatAdapter(messages)
        recyclerChat.layoutManager = layoutManager
        recyclerChat.adapter = chatAdapter
    }

    private fun setSendMessageBtn() {
        btn_send.setOnClickListener {
            val message = edit_message.text.toString()
            sendMessage(message)
        }
    }

    private fun sendMessage(cyrillic: String?) {
        if(cyrillic.isNullOrBlank())
            return
        val message = Message(cyrillic, true)
        messages.add(message)
        updateMessages()
        translateAndReceiveMessage(cyrillic)
        edit_message.text = null
    }

    private fun translateAndReceiveMessage(cyrillic: String) {
        val latin = KirLatTranslator.translate(cyrillic)
        val message = Message(latin, false)
        messages.add(message)
        updateMessages()
    }

    private fun updateMessages() {
        chatAdapter.updateMessages()
        recyclerChat.scrollToPosition(messages.size - 1)
    }
}