package com.damir.android.translator.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Message(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val text: String,
    val isSender: Boolean,
    val isTranslation: Boolean)