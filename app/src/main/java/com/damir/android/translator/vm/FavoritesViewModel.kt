package com.damir.android.translator.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.damir.android.translator.data.TranslatorRepository
import com.damir.android.translator.utils.ioDispatcher
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val translatorRepository: TranslatorRepository = TranslatorRepository()
): ViewModel() {

    val favorites = translatorRepository.favorites

    fun deleteFavorite(favoriteId: Int) {
        viewModelScope.launch(ioDispatcher) {
            translatorRepository.deleteFavorite(favoriteId)
        }
    }

    fun deleteFavoritePermanently() {
        viewModelScope.launch(ioDispatcher) {
            translatorRepository.deleteFavoritePermanently()
        }
    }

    fun undoFavoriteDeletion(favoriteId: Int) {
        viewModelScope.launch(ioDispatcher) {
            translatorRepository.undoFavoriteDeletion(favoriteId)
        }
    }
}