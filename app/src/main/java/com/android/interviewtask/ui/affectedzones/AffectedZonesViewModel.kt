package com.android.interviewtask.ui.affectedzones

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.interviewtask.rest.AffectedZonesRepository
import com.android.interviewtask.utils.UIState
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect

class AffectedZonesViewModel(private val affetecdZonesRepository: AffectedZonesRepository,
                             private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
                             private val coroutineScope: CoroutineScope = CoroutineScope(
                                 SupervisorJob() + ioDispatcher)
) : CoroutineScope by coroutineScope, ViewModel() {

    private val _affetecdZonesLiveData: MutableLiveData<UIState> = MutableLiveData(UIState.LOADING())
    val affetecdZonesLiveData: LiveData<UIState> get() = _affetecdZonesLiveData

    fun subscribeToAffectedInfo(methodname:String,zonecode:String) {
        _affetecdZonesLiveData.postValue(UIState.LOADING())

        collectAffectedInfo()

        launch {
            affetecdZonesRepository.getAffectedZoneDetails(methodname,zonecode)
        }
    }

    private fun collectAffectedInfo() {
        launch {
            affetecdZonesRepository.responseFlow.collect { uiState ->
                when(uiState) {
                    is UIState.LOADING -> { _affetecdZonesLiveData.postValue(uiState) }
                    is UIState.SUCCESS -> { _affetecdZonesLiveData.postValue(uiState) }
                    is UIState.ERROR -> { _affetecdZonesLiveData.postValue(uiState) }
                }
            }
        }
    }
}