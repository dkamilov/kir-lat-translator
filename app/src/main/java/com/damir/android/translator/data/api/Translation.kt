package com.damir.android.translator.data.api

import com.google.gson.annotations.SerializedName

data class Translation(
    @SerializedName("text")
    val text: List<String>
)