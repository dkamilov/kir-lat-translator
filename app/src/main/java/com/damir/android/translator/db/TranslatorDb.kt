package com.damir.android.translator.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.damir.android.translator.db.entity.Favorite
import com.damir.android.translator.db.entity.KirLat
import com.damir.android.translator.utils.*
import kotlinx.coroutines.*
import kotlin.coroutines.coroutineContext

@Database(
    entities = [KirLat::class, Favorite::class],
    version = 1,
    exportSchema = false
)
abstract class TranslatorDb: RoomDatabase() {

    abstract fun kirLatDao(): KirLatDao
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

        fun get(): TranslatorDb = INSTANCE

        private fun populateDb() {
            GlobalScope.launch {
                val abayTranslated = KirLatTranslator.translate(abayMessage)
                val salemTranslated = KirLatTranslator.translate(salemMessage)
                get().kirLatDao().addMessage(KirLat(text = abayMessage, isSender = true))
                get().kirLatDao().addMessage(KirLat(text = abayTranslated, isSender = false))
                get().kirLatDao().addMessage(KirLat(text = salemMessage, isSender = true))
                get().kirLatDao().addMessage(KirLat(text = salemTranslated, isSender = false))
            }
        }
    }
}