package com.damir.android.translator.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.damir.android.translator.db.TranslatorRepository
import com.damir.android.translator.db.entity.Favorite
import com.damir.android.translator.db.entity.KirLat
import com.damir.android.translator.utils.KirLatTranslator
import com.damir.android.translator.utils.ioDispatcher
import kotlinx.coroutines.launch

class KirLatViewModel(
    private val translatorRepository: TranslatorRepository = TranslatorRepository()
): ViewModel() {

    var clickedMessagePosition: Int? = null
    val kirLats = translatorRepository.kirLats

    fun addKirLat(messageText: String) {
        val kirLat = KirLat(
            text = messageText,
            isSender = true
        )
        val latin = translateKirLat(messageText)
        viewModelScope.launch(ioDispatcher) {
            translatorRepository.addKirLat(kirLat)
            translatorRepository.addKirLat(latin)
        }
    }

    fun addFavorite(favorite: Favorite) {
        viewModelScope.launch(ioDispatcher) {
            translatorRepository.addFavorite(favorite)
        }
    }

    private fun translateKirLat(messageText: String): KirLat {
        val latin =
            KirLatTranslator.translate(
                messageText
            )
        return KirLat(
            text = latin,
            isSender = false
        )
    }
}