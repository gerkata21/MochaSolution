package com.android.interviewtask.model


import com.google.gson.annotations.SerializedName

data class Context(
    @SerializedName("@version")
    val version: String
)