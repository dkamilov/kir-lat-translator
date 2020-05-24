package com.damir.android.translator.utils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

class TextUtils {

    companion object {

        fun copyToClipboard(context: Context, text: String) {
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Simple text", text)
            clipboard.setPrimaryClip(clip)
        }
    }
}