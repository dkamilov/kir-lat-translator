package com.damir.android.translator.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.damir.android.translator.db.TranslatorRepository
import com.damir.android.translator.db.entity.Favorite
import com.damir.android.translator.utils.ioDispatcher
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val translatorRepository: TranslatorRepository = TranslatorRepository()
): ViewModel() {

    val favorites = translatorRepository.favorites

    fun deleteFavorite(favorite: Favorite) {
        viewModelScope.launch(ioDispatcher) {
            translatorRepository.deleteFavorite(favorite)
        }
    }
}