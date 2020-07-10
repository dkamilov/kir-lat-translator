package com.damir.android.translator.vm

import androidx.lifecycle.*
import com.damir.android.translator.data.TranslatorRepository
import com.damir.android.translator.data.db.entity.Favorite
import com.damir.android.translator.data.db.entity.Message
import com.damir.android.translator.utils.KirLatTranslator
import com.damir.android.translator.utils.ioDispatcher
import kotlinx.coroutines.launch

class KirLatViewModel(
    private val translatorRepository: TranslatorRepository = TranslatorRepository()
): ViewModel(){

    var clickedMessagePosition: Int? = null
    var selectedLang: String = "kk-en"
    val kirLats = translatorRepository.latinMessages
    val translations = translatorRepository.translationMessages

    fun addLatinMessage(messageText: String) {
        val kirLat = Message(
            text = messageText,
            isSender = true,
            isTranslation = false
        )
        val latin = translateKirLat(messageText)
        addMessage(kirLat)
        addMessage(latin)
    }

    fun addTranslationMessage(messageText: String) {
        val original = Message (
            text = messageText,
            isSender = true,
            isTranslation = true
        )
        addMessage(original)
        translate(messageText)
    }

    fun addFavorite(favorite: Favorite) {
        viewModelScope.launch(ioDispatcher) {
            translatorRepository.addFavorite(favorite)
        }
    }

    private fun translate(text: String) {
        viewModelScope.launch(ioDispatcher) {
            val translated = translatorRepository.translate(text, selectedLang)
            val message =  Message(
                text = translated.text[0],
                isSender = false,
                isTranslation = true
            )
            addMessage(message)
        }
    }

    private fun translateKirLat(messageText: String): Message {
        val latin = KirLatTranslator
            .translate(messageText)
        return Message(
            text = latin,
            isSender = false,
            isTranslation = false
        )
    }

    private fun addMessage(message: Message) {
        viewModelScope.launch(ioDispatcher) {
            translatorRepository.addMessage(message)
        }
    }
}