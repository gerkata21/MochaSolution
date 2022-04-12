package com.android.interviewtask.ui.alertdetails

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.interviewtask.R
import com.android.interviewtask.databinding.FragmentAlertDetailsBinding
import com.android.interviewtask.model.AffectedZones
import com.android.interviewtask.model.Feature
import com.android.interviewtask.utils.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.material.chip.Chip
import org.koin.androidx.viewmodel.ext.android.viewModel


const val ARG_ALERT_ITEM = "alertitem"
const val ARG_INDEX = "index"

class AlertDetailsFragment : Fragment(){

    private var alertitem: Feature? = null
    private var index: Int? = null
    private lateinit var img_alertImage: DraggableView<ImageView>
    private val binding by lazy {
        FragmentAlertDetailsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            alertitem = it.getSerializable(ARG_ALERT_ITEM) as Feature
            index= it.getInt(ARG_INDEX)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        alertitem?.let { item ->
            val alert = item.properties
            binding.alertImage.loadImagefromUrl(index ?: 0)
            binding.alertTitle.text = alert.event
            binding.alertTitle.text = alert.event + " (${alert.category})"
            binding.alertHeadline.text = alert.headline
            binding.alertDescription.text = alert.description
            binding.alertSeverity.text = "Severity: " + alert.severity
            binding.alertCertainty.text = "Certainty: " + alert.certainty
            binding.alertUrgency.text = "Urgency: " + alert.urgency
            binding.alertSource.text = "Sender Name: " + alert.senderName
            alert.instruction?.let {
                binding.alertInstructions.text = "" + alert.instruction
            }
            binding.alertStarttime.text = getDisplayformatTime(alert.onset)
            binding.alertEndtime.text = getDisplayformatTime(alert.expires)

            img_alertImage = binding.alertImage.setupDraggable()
                .setStickyMode(DraggableView.Mode.STICKY_X)
                .build()
            img_alertImage.enableDrag()
            img_alertImage.animated = true

            binding.btnAffected.setOnClickListener {
                val bundle=Bundle().apply{
                    putSerializable(ARG_ALERT_ITEM,alertitem)
                }
                findNavController().navigate(R.id.action_interviewtaskDetailFragment_to_interviewtaskAffectedZonesFragment,bundle)
            }
        }

        return binding.root
    }



    @SuppressLint("ClickableViewAccessibility")
    inline fun NestedScrollView.scrollState(
        crossinline onIdle: () -> Unit,
        crossinline onScrolled: () -> Unit
    ) {
        val handler = Handler()
        var isScrolled = false
        setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_SCROLL,
                MotionEvent.ACTION_DOWN -> {
                    handler.removeCallbacksAndMessages(null)
                    onScrolled()
                    isScrolled = true
                }
                MotionEvent.ACTION_MOVE -> {
                    handler.removeCallbacksAndMessages(null)
                    if (!isScrolled) {
                        onScrolled()
                        isScrolled = true
                    }
                }
                MotionEvent.ACTION_CANCEL,
                MotionEvent.ACTION_UP -> {
                    handler.postDelayed({ onIdle() }, 500)
                    isScrolled = false
                }
            }
            false
        }
    }
}