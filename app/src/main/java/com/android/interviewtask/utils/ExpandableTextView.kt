package com.android.interviewtask.utils

import android.content.Context
import kotlin.jvm.JvmOverloads
import android.widget.TextView
import android.widget.TextView.BufferType
import com.android.interviewtask.utils.ExpandableTextView
import android.content.res.TypedArray
import android.text.SpannableStringBuilder
import android.util.AttributeSet
import com.android.interviewtask.R

class ExpandableTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    androidx.appcompat.widget.AppCompatTextView(context, attrs) {
    var originalText: CharSequence? = null
        private set
    private var trimmedText: CharSequence? = null
    private var bufferType: BufferType? = null
    private var trim = true
    private var trimLength: Int
    private fun setText() {
        super.setText(displayableText, bufferType)
    }

    private val displayableText: CharSequence?
        private get() = if (trim) trimmedText else originalText

    override fun setText(text: CharSequence, type: BufferType) {
        originalText = text
        trimmedText = getTrimmedText(text)
        bufferType = type
        setText()
    }

    private fun getTrimmedText(text: CharSequence?): CharSequence? {
        return if (originalText != null && originalText!!.length > trimLength) {
            SpannableStringBuilder(originalText, 0, trimLength + 1)
                .append(ELLIPSIS)
        } else {
            originalText
        }
    }

    fun setTrimLength(trimLength: Int) {
        this.trimLength = trimLength
        trimmedText = getTrimmedText(originalText)
        setText()
    }

    fun getTrimLength(): Int {
        return trimLength
    }

    companion object {
        private const val DEFAULT_TRIM_LENGTH = 200
        private const val ELLIPSIS = "....."
    }

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ExpandableTextView)
        trimLength =
            typedArray.getInt(R.styleable.ExpandableTextView_trimLength, DEFAULT_TRIM_LENGTH)
        typedArray.recycle()
        setOnClickListener {
            trim = !trim
            setText()
            requestFocusFromTouch()
        }
    }
}