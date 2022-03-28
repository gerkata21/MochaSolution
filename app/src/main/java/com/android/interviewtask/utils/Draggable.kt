package com.android.interviewtask.utils

import android.view.WindowManager

object Draggable {

    @Deprecated(
        "Use DraggableView.Mode",
        ReplaceWith("DraggableView.Mode", "io.github.hyuwah.draggableviewlib.DraggableView.Mode")
    )
    enum class STICKY {
        NONE,
        AXIS_X,
        AXIS_Y,
        AXIS_XY
    }

    const val DRAG_TOLERANCE = 16
    const val DURATION_MILLIS = 250L
}

interface OverlayDraggableListener {
    fun onParamsChanged(updatedParams: WindowManager.LayoutParams)
}