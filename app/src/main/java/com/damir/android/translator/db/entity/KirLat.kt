package com.damir.android.translator.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class KirLat(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val text: String,
    val isSender: Boolean
)