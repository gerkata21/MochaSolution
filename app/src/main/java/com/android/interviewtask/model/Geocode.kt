package com.android.interviewtask.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Geocode(
    @SerializedName("SAME")
    val sAME: List<String>,
    @SerializedName("UGC")
    val uGC: List<String>
): Serializable