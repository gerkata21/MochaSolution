package com.android.interviewtask.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class WeatherData(
    //@SerializedName("@context")
    //val context: List<Any>?,
    @SerializedName("features")
    val features: MutableList<Feature>,
    @SerializedName("title")
    val title: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("updated")
    val updated: String
): Serializable