package com.android.interviewtask.model


import com.google.gson.annotations.SerializedName

data class GeometryX(
    @SerializedName("coordinates")
    val coordinates: List<List<List<Double>>>,
    @SerializedName("type")
    val type: String
)