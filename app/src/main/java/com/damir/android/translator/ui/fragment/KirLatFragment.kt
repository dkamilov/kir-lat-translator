package com.damir.android.translator.ui.fragment

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.damir.android.translator.ChatAdapter
import com.damir.android.translator.R
import com.damir.android.translator.db.entity.Favorite
import com.damir.android.translator.db.entity.KirLat
import com.damir.android.translator.ui.MainActivity
import com.damir.android.translator.ui.MessageMenuDialog
import com.damir.android.translator.utils.setToolbarTitle
import com.damir.android.translator.vm.KirLatViewModel
import kotlinx.android.synthetic.main.fragment_kir_lat.*

class KirLatFragment : Fragment(R.layout.fragment_kir_lat),
    MessageMenuDialog.MessagePopupDialogListener {

    private lateinit var chatAdapter: ChatAdapter
    private lateinit var kirLatViewModel: KirLatViewModel
    private var clickedMessagePosition: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setToolbarTitle(R.string.kirlat)
        setObservers()
        setChatRecycler()
        setSendMessageBtn()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_kirlat, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onMenuCopyClicked() {
    }

    override fun onMenuShareClicked() {
    }

    override fun onMenuDeleteClicked() {
    }

    override fun onMenuFavoriteClicked() {
        addFavorite()
    }

    private fun setObservers() {
        kirLatViewModel = ViewModelProvider(this).get(KirLatViewModel::class.java)
        kirLatViewModel.kirLats.observe(viewLifecycleOwner, Observer {
            updateMessages(it)
        })
    }

    private fun setChatRecycler() {
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.stackFromEnd = true
        chatAdapter = ChatAdapter { view, position ->
            showDialogMessagePopup()
            clickedMessagePosition = position
        }

        recyclerChat.apply {
            this.layoutManager = layoutManager
            adapter = chatAdapter
            addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
                if (bottom < oldBottom) {
                    recyclerChat.post {
                        recyclerScrollToEnd()
                    }
                }
            }
        }
    }

    private fun setSendMessageBtn() {
        btn_send.setOnClickListener {
            sendMessage()
        }
    }

    private fun showDialogMessagePopup() {
        val popup = MessageMenuDialog()
        popup.show(childFragmentManager, "popup")
    }

    private fun sendMessage() {
        val messageText = edit_message
            .text
            .toString()
            .trim()
        if(messageText.isBlank())
            return
        kirLatViewModel.addKirLat(messageText)
        edit_message.text = null
    }

    private fun updateMessages(kirLats: List<KirLat>) {
        chatAdapter.submitList(kirLats) {
            recyclerScrollToEnd()
        }
    }

    private fun recyclerScrollToEnd() {
        recyclerChat.scrollToPosition(chatAdapter.currentList.size - 1)
    }

    private fun addFavorite() {
        val kirLats = chatAdapter.currentList
        val favorite = if(kirLats[clickedMessagePosition!!].isSender) {
            Favorite(
                sendMessage = kirLats[clickedMessagePosition!!].text,
                receiveMessage = kirLats[clickedMessagePosition!! + 1].text
            )
        }else {
            Favorite(
                sendMessage = kirLats[clickedMessagePosition!!].text,
                receiveMessage = kirLats[clickedMessagePosition!! - 1].text
            )
        }
        kirLatViewModel.addFavorite(favorite)
        navigateToFavorite()
    }

    private fun navigateToFavorite() {
        val a = activity as MainActivity
        a.navigateToFavorite()
    }
}