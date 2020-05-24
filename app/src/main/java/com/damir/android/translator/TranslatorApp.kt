package com.damir.android.translator

import android.app.Application
import com.damir.android.translator.data.db.TranslatorDb

class TranslatorApp: Application() {

    override fun onCreate() {
        TranslatorDb.initialize(this)

        super.onCreate()
    }

    //TODO: Fix undoFavoriteDeletion when two items deleted together
}
