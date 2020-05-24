package com.damir.android.translator.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.damir.android.translator.data.db.entity.Message

@Dao
interface MessageDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addMessage(message: Message)

    @Query("SELECT * FROM Message WHERE isTranslation = 0")
    fun getLatinMessages(): LiveData<List<Message>>

    @Query("SELECT * FROM Message WHERE isTranslation = 1")
    fun getTranslationMessages(): LiveData<List<Message>>
}