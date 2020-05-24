package com.damir.android.translator.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.damir.android.translator.R
import com.damir.android.translator.utils.TextUtils
import com.damir.android.translator.utils.setToolbarTitle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_kir_lat.*

class TranslateFragment: BaseChatFragment(R.layout.fragment_kir_lat),
AdapterView.OnItemSelectedListener{

    override lateinit var btnSend: ImageView
    override lateinit var fab: FloatingActionButton
    override lateinit var recyclerView: RecyclerView

    private lateinit var spinnerLang: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_translate, menu)
        spinnerLang = menu.findItem(R.id.spinner_lang).actionView as Spinner
        val langAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.translation_languages,
            R.layout.spinner_item_languages
        )
        val verticalOffset = (activity as AppCompatActivity).supportActionBar?.height ?: 0
        spinnerLang.apply {
            adapter = langAdapter
            onItemSelectedListener = this@TranslateFragment
            background = null
            dropDownVerticalOffset = verticalOffset
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.change_lang) {
            changeSpinnerLang()
        }
        return super.onOptionsItemSelected(item)
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

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Log.i("Damir", "onItemSelected $position")
        kirLatViewModel.selectedLang = if(position == 0) {
            "kk-en"
        }else {
            "en-kk"
        }
    }

    override fun initViews() {
        btnSend = btn_send
        fab = fab_scroll
        recyclerView = recyclerChat
    }

    override fun setToolbar() {
        setToolbarTitle(R.string.translate)
    }

    override fun setObservers() {
        kirLatViewModel.translations.observe(viewLifecycleOwner, Observer{
            updateMessages(it)
        })
    }

    override fun sendMessage() {
        val messageText = edit_message.text.toString().trim()
        if(messageText.isBlank()) return
        kirLatViewModel.addTranslationMessage(messageText)
        edit_message.text = null
    }

    private fun changeSpinnerLang() {
        val position = spinnerLang.selectedItemPosition
        spinnerLang.setSelection((position + 1) % 2)
    }
}