package com.android.interviewtask.ui.alertlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.interviewtask.rest.WeatherRepository
import com.android.interviewtask.utils.UIState

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

class AlertListViewModel(private val weatherRepository: WeatherRepository,
                         private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
                         private val coroutineScope: CoroutineScope = CoroutineScope(SupervisorJob() + ioDispatcher)
) : CoroutineScope by coroutineScope, ViewModel() {

    private val _weatherLiveData: MutableLiveData<UIState> = MutableLiveData(UIState.LOADING())
    val weatherLiveData: LiveData<UIState> get() = _weatherLiveData

    fun subscribeToWeatherInfo() {
        _weatherLiveData.postValue(UIState.LOADING())

        collectWeatherInfo()

        launch {
            weatherRepository.getWeatherActiveList()
        }
    }

    private fun collectWeatherInfo() {
        launch {
            weatherRepository.responseFlow.collect { uiState ->
                when(uiState) {
                    is UIState.LOADING -> { _weatherLiveData.postValue(uiState) }
                    is UIState.SUCCESS -> { _weatherLiveData.postValue(uiState) }
                    is UIState.ERROR -> { _weatherLiveData.postValue(uiState) }
                }
            }
        }
    }
}