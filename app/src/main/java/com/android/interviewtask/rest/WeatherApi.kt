package com.android.interviewtask.rest

import  com.android.interviewtask.model.WeatherData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET(ACTIVE_ALERTS)
    suspend fun getActiveAlerts(
        @Query("status")status:String="actual",
        @Query("message_type")type: String="alert"
    ): Response<WeatherData>

    companion object {
        const val BASE_URL = "https://api.weather.gov/alerts/"
        const val IMAGE_URL = "https://picsum.photos/1000?random="
        private const val ACTIVE_ALERTS = "active"
    }

}