package com.damir.android.translator.ui.fragment

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.damir.android.translator.R
import com.damir.android.translator.utils.setToolbarTitle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_kir_lat.*

class KirLatFragment: BaseChatFragment(R.layout.fragment_kir_lat) {

    override lateinit var btnSend: ImageView
    override lateinit var fab: FloatingActionButton
    override lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_kirlat, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onMenuCopyClicked() {
        copyToClipboard()
    }

    override fun onMenuShareClicked() {
    }

    override fun onMenuDeleteClicked() {
    }

    override fun onMenuFavoriteClicked() {
        addFavorite()
    }

    override fun initViews() {
        btnSend = btn_send
        fab = fab_scroll
        recyclerView = recyclerChat
    }

    override fun setToolbar() {
        setToolbarTitle(R.string.kirlat)
    }

    override fun setObservers() {
        kirLatViewModel.kirLats.observe(viewLifecycleOwner, Observer {
            updateMessages(it)
        })
    }

    override fun sendMessage() {
        val messageText = edit_message.text.toString().trim()
        if(messageText.isBlank()) return
        kirLatViewModel.addLatinMessage(messageText)
        edit_message.text = null
        incrementTranslatedWordCount()
    }
}