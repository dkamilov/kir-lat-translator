package com.damir.android.translator.data

import android.net.Uri

data class FirebaseUserInfo(
    val username: String? = null,
    val email: String? = null,
    val photoUrl: String? = null,
    val spentTime: Long? = null,
    val wordsCount: Int? = null
)