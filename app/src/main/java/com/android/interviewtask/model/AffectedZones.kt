package com.android.interviewtask.model


import com.google.gson.annotations.SerializedName

data class AffectedZones(
    @SerializedName("@context")
    val context: Context,
    @SerializedName("geometry")
    val geometry: GeometryX,
    @SerializedName("id")
    val id: String,
    @SerializedName("properties")
    val properties: PropertiesX,
    @SerializedName("type")
    val type: String
)