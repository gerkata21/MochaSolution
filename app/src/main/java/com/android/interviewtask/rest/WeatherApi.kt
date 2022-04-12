package com.android.interviewtask.rest

import com.android.interviewtask.model.AffectedZones
import  com.android.interviewtask.model.WeatherData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherApi {

    @GET(ACTIVE_ALERTS)
    suspend fun getActiveAlerts(
        @Query("status")status:String="actual",
        @Query("message_type")type: String="alert"
    ): Response<WeatherData>


    @GET(AFFECTED_ZONES)
    suspend fun getAffectedZoneDetails(
        @Path("method")status:String,
        @Path("zonecode")type: String
    ): Response<AffectedZones>

    companion object {
        const val BASE_URL = "https://api.weather.gov/"
        const val IMAGE_URL = "https://picsum.photos/1000?random="
        private const val ACTIVE_ALERTS = "alerts/active"
        private const val AFFECTED_ZONES = "zones/{method}/{zonecode}"
    }

}