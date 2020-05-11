package com.damir.android.translator

import android.app.Application
import com.damir.android.translator.db.TranslatorDb

class TranslatorApp: Application() {

    override fun onCreate() {
        TranslatorDb.initialize(this)

        super.onCreate()
    }
}