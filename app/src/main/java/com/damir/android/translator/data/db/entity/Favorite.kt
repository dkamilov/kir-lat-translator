package com.damir.android.translator.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Favorite(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val sendMessage: String,
    val receiveMessage: String,
    val toDelete: Boolean = false
)