package com.android.interviewtask.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Parameters(
    @SerializedName("AWIPSidentifier")
    val aWIPSidentifier: List<String>,
    @SerializedName("BLOCKCHANNEL")
    val bLOCKCHANNEL: List<String>,
    @SerializedName("EAS-ORG")
    val eASORG: List<String>,
    @SerializedName("eventEndingTime")
    val eventEndingTime: List<String>,
    @SerializedName("eventMotionDescription")
    val eventMotionDescription: List<String>,
    @SerializedName("expiredReferences")
    val expiredReferences: List<String>?,
    @SerializedName("NWSheadline")
    val nWSheadline: List<String>,
    @SerializedName("VTEC")
    val vTEC: List<String>,
    @SerializedName("WMOidentifier")
    val wMOidentifier: List<String>
): Serializable