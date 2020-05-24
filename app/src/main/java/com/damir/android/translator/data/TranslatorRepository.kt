package com.damir.android.translator.data

import androidx.lifecycle.LiveData
import com.damir.android.translator.data.api.Translation
import com.damir.android.translator.data.api.TranslatorApiService
import com.damir.android.translator.data.db.FavoritesDao
import com.damir.android.translator.data.db.MessageDao
import com.damir.android.translator.data.db.TranslatorDb
import com.damir.android.translator.data.db.entity.Favorite
import com.damir.android.translator.data.db.entity.Message

class TranslatorRepository(
    private val messageDao: MessageDao = TranslatorDb.get().messageDao(),
    private val favoritesDao: FavoritesDao = TranslatorDb.get().favoritesDao(),
    private val api: TranslatorApiService = TranslatorApiService.create()
) {

    val latinMessages: LiveData<List<Message>> = messageDao.getLatinMessages()
    val translationMessages: LiveData<List<Message>> = messageDao.getTranslationMessages()
    val favorites: LiveData<List<Favorite>> = favoritesDao.getFavorites()

    suspend fun addMessage(message: Message) {
        messageDao.addMessage(message)
    }

    suspend fun addFavorite(favorite: Favorite) {
        favoritesDao.addFavorite(favorite)
    }

    suspend fun deleteFavorite(favoriteId: Int) {
        favoritesDao.deleteFavorite(favoriteId)
    }

    suspend fun deleteFavoritePermanently() {
        favoritesDao.deleteFavoritePermanently()
    }

    suspend fun undoFavoriteDeletion(favoriteId: Int) {
        favoritesDao.undoFavoriteDeletion(favoriteId)
    }

    suspend fun translate(text: String, lang: String): Translation {
        return api.translate(text, lang)
    }
}