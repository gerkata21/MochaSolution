package com.android.interviewtask.model


import com.google.gson.annotations.SerializedName

data class PropertiesX(
    @SerializedName("cwa")
    val cwa: List<String>,
    @SerializedName("effectiveDate")
    val effectiveDate: String,
    @SerializedName("expirationDate")
    val expirationDate: String,
    @SerializedName("forecastOffices")
    val forecastOffices: List<String>,
    /*@SerializedName("@id")
    val id: String,*/
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("observationStations")
    val observationStations: List<String>,
    @SerializedName("radarStation")
    val radarStation: String,
    @SerializedName("state")
    val state: String,
    @SerializedName("timeZone")
    val timeZone: List<String>,
    /*@SerializedName("@type")
    val type: String,
    @SerializedName("type")
    val type: String*/
)