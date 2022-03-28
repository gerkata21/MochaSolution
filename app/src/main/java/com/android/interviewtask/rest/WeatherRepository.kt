package com.android.interviewtask.rest

import com.android.interviewtask.utils.UIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface WeatherRepository {
    val responseFlow: StateFlow<UIState>
    suspend fun getWeatherActiveList()
}

class WeatherRepositoryImpl(
    private val weatherApi: WeatherApi
) : WeatherRepository {

    private val _responseFlow: MutableStateFlow<UIState> = MutableStateFlow(UIState.LOADING())

    override val responseFlow: StateFlow<UIState>
        get() = _responseFlow

    override suspend fun getWeatherActiveList() {
        try {
            val response = weatherApi.getActiveAlerts()

            if (response.isSuccessful) {
                response.body()?.let {
                    _responseFlow.value = UIState.SUCCESS(it)
                } ?: run {
                    _responseFlow.value = UIState.ERROR(IllegalStateException("Cards are coming as null!"))
                }

            } else {
                _responseFlow.value = UIState.ERROR(Exception(response.errorBody()?.string()))
            }
        } catch (e: Exception) {
            _responseFlow.value = UIState.ERROR(e)
        }
    }


}