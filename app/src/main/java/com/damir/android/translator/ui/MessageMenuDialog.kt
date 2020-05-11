package com.damir.android.translator.ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import com.damir.android.translator.R
import kotlinx.android.synthetic.main.dialog_message_menu.view.*
import java.lang.ClassCastException
import java.lang.IllegalStateException

class MessageMenuDialog: DialogFragment() {

    private lateinit var listener: MessagePopupDialogListener

    interface MessagePopupDialogListener {
        fun onMenuCopyClicked()
        fun onMenuShareClicked()
        fun onMenuDeleteClicked()
        fun onMenuFavoriteClicked()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            listener = parentFragment as MessagePopupDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException((context.toString()) +
                    "must implement MessagePopupDialogListener")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.dialog_message_menu, null)

            setMenuItemClicks(view)
            builder.setView(view)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    private fun setMenuItemClicks(view: View) {
        view.menu_copy.setOnClickListener {
            listener.onMenuCopyClicked()
            dismiss()
        }
        view.menu_share.setOnClickListener {
            listener.onMenuShareClicked()
            dismiss()
        }
        view.menu_delete.setOnClickListener {
            listener.onMenuDeleteClicked()
            dismiss()
        }
        view.menu_add_favorite.setOnClickListener {
            listener.onMenuFavoriteClicked()
            dismiss()
        }
    }
}