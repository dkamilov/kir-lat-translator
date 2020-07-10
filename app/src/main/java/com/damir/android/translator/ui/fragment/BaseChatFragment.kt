package com.damir.android.translator.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.damir.android.translator.MainActivity
import com.damir.android.translator.R
import com.damir.android.translator.data.db.entity.Favorite
import com.damir.android.translator.data.db.entity.Message
import com.damir.android.translator.firebase.FirebaseDatabase
import com.damir.android.translator.ui.ChatAdapter
import com.damir.android.translator.ui.MessageMenuDialog
import com.damir.android.translator.utils.TextUtils
import com.damir.android.translator.utils.isThemeNight
import com.damir.android.translator.vm.KirLatViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

abstract class BaseChatFragment(layoutRes: Int): Fragment(layoutRes),
    MessageMenuDialog.MessagePopupDialogListener {

    protected abstract var btnSend: ImageView
    protected abstract var fab: FloatingActionButton
    protected abstract var recyclerView: RecyclerView
    protected lateinit var layoutManager: LinearLayoutManager
    protected lateinit var kirLatViewModel: KirLatViewModel

    private lateinit var chatAdapter: ChatAdapter
    private val chatRecyclerScrollListener = object: RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            if(layoutManager.findLastCompletelyVisibleItemPosition() + 1 < layoutManager.itemCount) {
                showFabScroll(fab)
            } else {
                hideFabScroll(fab)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setButtons()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setToolbar()
        setViewModel()
        setObservers()
        setBaseRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        recyclerView.addOnScrollListener(chatRecyclerScrollListener)
    }

    override fun onPause() {
        super.onPause()
        recyclerView.removeOnScrollListener(chatRecyclerScrollListener)
    }

    protected abstract fun initViews()

    protected abstract fun setToolbar()

    protected abstract fun setObservers()

    protected abstract fun sendMessage()

    protected fun addFavorite() {
        val messages = chatAdapter.currentList
        val clickedMessagePosition = kirLatViewModel.clickedMessagePosition
        val favorite = if(messages[clickedMessagePosition!!].isSender) {
            Favorite(
                sendMessage = messages[clickedMessagePosition].text,
                receiveMessage = messages[clickedMessagePosition + 1].text
            )
        }else {
            Favorite(
                sendMessage = messages[clickedMessagePosition].text,
                receiveMessage = messages[clickedMessagePosition - 1].text
            )
        }
        kirLatViewModel.addFavorite(favorite)
        navigateToFavorite()
    }

    protected fun copyToClipboard() {
        val clickedMessagePosition = kirLatViewModel.clickedMessagePosition ?: 0
        val text = chatAdapter.currentList[clickedMessagePosition].text
        TextUtils.copyToClipboard(requireContext(), text)
        showSnackbarCopied()
    }

    protected fun incrementTranslatedWordCount() {
        FirebaseDatabase.incrementWordsCount()
    }

    protected fun updateMessages(items: List<Message>) {
        chatAdapter.submitList(items) {
            recyclerScrollToEnd()
        }
    }

    private fun setButtons() {
        fab.setOnClickListener {
            recyclerSmoothScrollToEnd()
        }
        btnSend.setOnClickListener {
            sendMessage()
        }
    }

    private fun setViewModel() {
        kirLatViewModel = ViewModelProvider(this).get(KirLatViewModel::class.java)
    }

    private fun setBaseRecyclerView() {
        setLayoutManager()
        setChatAdapter()
        recyclerView.layoutManager = this.layoutManager
        recyclerView.adapter = chatAdapter
        recyclerView.addOnLayoutChangeListener { _, _, _, _, bottom, _, _, _, oldBottom ->
            if (bottom < oldBottom) {
                recyclerView.post {
                    recyclerScrollToEnd()
                }
            }
        }
        if(requireActivity().isThemeNight)
            recyclerView.setBackgroundResource(R.drawable.bg_chat_list_night)
    }

    private fun setLayoutManager() {
        layoutManager = LinearLayoutManager(requireContext())
        layoutManager.stackFromEnd = true
    }

    private fun setChatAdapter() {
        chatAdapter = ChatAdapter { view, position ->
            showDialogMessagePopup()
            kirLatViewModel.clickedMessagePosition = position
        }
    }

    private fun recyclerScrollToEnd() {
        val position = recyclerView.adapter?.itemCount?.minus(1) ?: 0
        recyclerView.scrollToPosition(position)
    }

    private fun recyclerSmoothScrollToEnd() {
        val position = recyclerView.adapter?.itemCount?.minus(1) ?: 0
        recyclerView.smoothScrollToPosition(position)
    }

    private fun showDialogMessagePopup() {
        val popup = MessageMenuDialog()
        popup.show(childFragmentManager, "popup")
    }

    private fun showFabScroll(fab: FloatingActionButton) {
        fab.show()
    }

    private fun hideFabScroll(fab: FloatingActionButton) {
        fab.hide()
    }

    private fun showSnackbarCopied() {
        Snackbar.make(requireView().rootView, "Copied to clipboard", Snackbar.LENGTH_SHORT).show()
    }

    private fun navigateToFavorite() {
        val a = activity as MainActivity
        a.navigateToFavorite()
    }
}