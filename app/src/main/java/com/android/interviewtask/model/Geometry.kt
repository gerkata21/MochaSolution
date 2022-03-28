package com.android.interviewtask.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Geometry(
    @SerializedName("coordinates")
    val coordinates: List<List<List<Double>>>?,
    @SerializedName("type")
    val type: String
): Serializable