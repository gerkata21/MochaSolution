package com.android.interviewtask.ui.alertlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import  com.android.interviewtask.model.WeatherData
import com.android.interviewtask.R
import com.android.interviewtask.adapters.AlertsAdapter
import com.android.interviewtask.databinding.FragmentAlertListBinding
import com.android.interviewtask.ui.alertdetails.ARG_ALERT_ITEM
import com.android.interviewtask.ui.alertdetails.ARG_INDEX
import com.android.interviewtask.utils.UIState
import org.koin.androidx.viewmodel.ext.android.viewModel


class AlertListFragment : Fragment() {

    private val binding by lazy {
        FragmentAlertListBinding.inflate(layoutInflater)
    }

    private val alertListViewModel: AlertListViewModel by viewModel()
    private lateinit var alertsAdapter: AlertsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        alertsAdapter=AlertsAdapter()
        binding.rcvAlerts.adapter = alertsAdapter
        alertListViewModel.weatherLiveData.observe(viewLifecycleOwner, { response ->
            when (response) {
                is UIState.LOADING -> {}
                is UIState.SUCCESS -> {
                    val weatherData=response.success as WeatherData
                    alertsAdapter.loadData(weatherData.features)
                    alertsAdapter.notifyDataSetChanged()
                }
                is UIState.ERROR -> {}
            }

        })

        alertsAdapter.onItemClick={alertitem,index ->
            val bundle=Bundle().apply{
                putSerializable(ARG_ALERT_ITEM,alertitem)
                putInt(ARG_INDEX,index)
            }
            findNavController().navigate(R.id.action_interviewtaskListFragment_to_interviewtaskDetailFragment,bundle)
        }
        alertListViewModel.subscribeToWeatherInfo()
        return binding.root
    }

}