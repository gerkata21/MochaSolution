package com.android.interviewtask.ui.affectedzones

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.ColorUtils
import androidx.fragment.app.Fragment
import com.android.interviewtask.R
import com.android.interviewtask.databinding.AffectedZonesFragmentBinding
import com.android.interviewtask.model.AffectedZones
import com.android.interviewtask.model.Feature
import com.android.interviewtask.ui.alertdetails.ARG_ALERT_ITEM
import com.android.interviewtask.utils.UIState
import com.android.interviewtask.utils.getDynamicColor
import com.android.interviewtask.utils.getPath
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AffectedZonesFragment : Fragment(), OnMapReadyCallback {

    private val binding by lazy {
        AffectedZonesFragmentBinding.inflate(layoutInflater)
    }
    private var alertitem: Feature? = null
    private lateinit var mMap: GoogleMap
    private val affectedZonesViewModel: AffectedZonesViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            alertitem = it.getSerializable(ARG_ALERT_ITEM) as Feature
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        alertitem?.let { item ->
            val alert = item.properties



            affectedZonesViewModel.affetecdZonesLiveData.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is UIState.LOADING -> {}
                    is UIState.SUCCESS -> {
                        val affectedZones = response.success as AffectedZones

                        affectedZones.geometry.coordinates.forEach { alllatlngs ->
                            var pathdetails = arrayListOf<LatLng>()
                            alllatlngs.forEach { latlng ->
                                pathdetails.add(LatLng(latlng[1], latlng[0]))
                            }
                            val polycolor=Color.rgb(getDynamicColor(),getDynamicColor(),getDynamicColor())
                            val polygon= mMap.addPolygon(
                                PolygonOptions()
                                    .clickable(false)
                                    .addAll(
                                        pathdetails
                                    )
                            )
                            polygon.strokeColor=polycolor
                            polygon.strokeWidth=5f
                            polygon.fillColor = ColorUtils.setAlphaComponent(polycolor, 100)
                            val centerlatlng= centerPoint(pathdetails)
                            mMap.addMarker(MarkerOptions()
                                .position(centerlatlng)
                                .title(affectedZones.properties.name+" ("+affectedZones.properties.id+")"))

                            if(pathdetails.size>0) {
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pathdetails[0],9f))

                            }
                        }
                    }
                    is UIState.ERROR -> {}
                }

            }
        }

        return binding.root
    }

    fun centerPoint(points: List<LatLng>): LatLng {
        val centroid = doubleArrayOf(0.0, 0.0)
        for (i in points.indices) {
            centroid[0] += points[i].latitude
            centroid[1] += points[i].longitude
        }
        val totalPoints = points.size
        centroid[0] = centroid[0] / totalPoints
        centroid[1] = centroid[1] / totalPoints
        return LatLng(centroid[0],centroid[1])
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled=true

        alertitem?.let{ item->
            item.properties.affectedZones.forEach { url->
                val codes=url.getPath().split("/")
                affectedZonesViewModel.subscribeToAffectedInfo(codes[0],codes[1])
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.google_map2) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }
}