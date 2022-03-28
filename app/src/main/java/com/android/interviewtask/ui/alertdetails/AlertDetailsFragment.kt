package com.android.interviewtask.ui.alertdetails

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.widget.NestedScrollView
import com.google.android.material.chip.Chip
import  com.android.interviewtask.model.Feature
import com.android.interviewtask.R
import com.android.interviewtask.databinding.FragmentAlertDetailsBinding
import com.android.interviewtask.databinding.FragmentAlertListBinding
import com.android.interviewtask.utils.*


const val ARG_ALERT_ITEM = "alertitem"
const val ARG_INDEX = "index"

class AlertDetailsFragment : Fragment() {

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
        alertitem?.let {item->
            val alert=item.properties
            binding.alertImage.loadImagefromUrl(index?:0)
            binding.alertTitle.text = alert.event
            binding.alertTitle.text = alert.event + " (${alert.category})"
            binding.alertHeadline.text=alert.headline
            binding.alertDescription.text = alert.description
            binding.alertSeverity.text="Severity: "+alert.severity
            binding.alertCertainty.text="Certainty: "+alert.certainty
            binding.alertUrgency.text="Urgency: "+alert.urgency
            binding.alertSource.text="Sender Name: "+alert.senderName
            alert.instruction?.let {
                binding.alertInstructions.text=""+alert.instruction
            }?:{
                binding.alertInstructions.text=""
            }
            binding.alertStarttime.text= getDisplayformatTime(alert.onset)
            binding.alertEndtime.text= getDisplayformatTime(alert.expires)
            setCategoryChips(item.properties.affectedZones)

            img_alertImage = binding.alertImage.setupDraggable()
                .setStickyMode(DraggableView.Mode.STICKY_X)
                .build()
            img_alertImage.enableDrag()
            img_alertImage.animated=true


            /*binding.alertImage.setOnClickListener {
                if (img_alertImage.isMinimized) {
                    img_alertImage.undock()
                } else {
                    img_alertImage.dockToEdge()
                }
            }*/

            /*binding.scrollView.scrollState(
                onIdle = {
                    //binding.tvScroll.text = "Idle/Show"
                    img_alertImage.show()
                },
                onScrolled = {
                    //binding.tvScroll.text = "Scrolling/Hide"
                    img_alertImage.hide()
                }
            )*/

        }

        return binding.root
    }

    fun setCategoryChips(areacodes: List<String>) {
        if (areacodes.isNotEmpty()) {
            binding.chipsPrograms.visibility = View.VISIBLE
            for (areacode in areacodes) {
                val mChip =
                    this.layoutInflater.inflate(R.layout.item_chips, null, false) as Chip
                mChip.text = areacode.getAreaCode()
                val paddingDp = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 10f,
                    resources.displayMetrics
                ).toInt()
                mChip.setPadding(paddingDp, 0, paddingDp, 0)
                mChip.setOnCheckedChangeListener { compoundButton, b -> }
                binding.chipsPrograms.addView(mChip)
            }
        } else {
            binding.chipsPrograms.visibility = View.GONE
        }
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