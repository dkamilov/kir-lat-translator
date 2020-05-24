package com.damir.android.translator.data.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.damir.android.translator.data.db.entity.Favorite
import com.damir.android.translator.data.db.entity.Message
import com.damir.android.translator.utils.*
import kotlinx.coroutines.*

@Database(
    entities = [Message::class, Favorite::class],
    version = 1,
    exportSchema = false
)
abstract class TranslatorDb: RoomDatabase() {

    abstract fun messageDao(): MessageDao
    abstract fun favoritesDao(): FavoritesDao

    companion object {
        private lateinit var INSTANCE: TranslatorDb

        fun initialize(context: Context) {
            INSTANCE = Room.databaseBuilder(
                context,
                TranslatorDb::class.java,
                "translatorDb"
            )
                .addCallback(object: Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        populateDb()
                        Log.i("TranslatorDb", "onCreate in callback")
                        super.onCreate(db)
                    }
                })
                .build()
        }

        fun get(): TranslatorDb =
            INSTANCE

        private fun populateDb() {
            GlobalScope.launch {
                val abayTranslated = KirLatTranslator.translate(abayMessage)
                val salemTranslated = KirLatTranslator.translate(salemMessage)
                get()
                    .messageDao().addMessage(Message(text = abayMessage, isSender = true, isTranslation = false))
                get()
                    .messageDao().addMessage(Message(text = abayTranslated, isSender = false, isTranslation = false))
                get()
                    .messageDao().addMessage(Message(text = salemMessage, isSender = true, isTranslation = false))
                get()
                    .messageDao().addMessage(Message(text = salemTranslated, isSender = false, isTranslation = false))
            }
        }
    }
}