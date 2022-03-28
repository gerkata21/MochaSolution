package com.android.interviewtask.model


import  com.android.interviewtask.model.Geometry
import  com.android.interviewtask.model.Properties
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Feature(
    //@SerializedName("geometry")
    //val geometry: Geometry?,
    @SerializedName("id")
    val id: String,
    @SerializedName("properties")
    val properties: Properties,
    @SerializedName("type")
    val type: String
):Serializable