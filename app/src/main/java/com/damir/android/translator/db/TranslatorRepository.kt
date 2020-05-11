package com.damir.android.translator.db

import androidx.lifecycle.LiveData
import com.damir.android.translator.db.entity.Favorite
import com.damir.android.translator.db.entity.KirLat

class TranslatorRepository(
    private val kirLatDao: KirLatDao = TranslatorDb.get().kirLatDao(),
    private val favoritesDao: FavoritesDao = TranslatorDb.get().favoritesDao()
) {

    val kirLats: LiveData<List<KirLat>> = kirLatDao.getMessages()
    val favorites: LiveData<List<Favorite>> = favoritesDao.getFavorites()

    suspend fun addKirLat(kirLat: KirLat) {
        kirLatDao.addMessage(kirLat)
    }

    suspend fun addFavorite(favorite: Favorite) {
        favoritesDao.addFavorite(favorite)
    }

    suspend fun deleteFavorite(favorite: Favorite) {
        favoritesDao.deleteFavorite(favorite)
    }
}