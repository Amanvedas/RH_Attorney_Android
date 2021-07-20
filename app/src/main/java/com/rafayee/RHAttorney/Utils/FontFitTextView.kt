package com.rafayee.RHAttorney.Utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.os.Build
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.rafayee.RHAttorney.R

@SuppressLint("AppCompatCustomView")
class FontFitTextView : TextView {
    // Attributes
    private var mTestPaint: Paint? = null
    private var defaultTextSize = 0f

    constructor(context: Context?) : super(context) {
        initialize()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initialize()
    }

    private fun initialize() {
        mTestPaint = Paint()
        mTestPaint!!.set(this.getPaint())
        defaultTextSize = getTextSize()
    }

    /* Re size the font so the specified text fits in the text box
     * assuming the text box is the specified width.
     */
    private fun refitText(text: String, textWidth: Int) {
        if (textWidth <= 0 || text.isEmpty()) return
        val targetWidth: Int = textWidth - this.getPaddingLeft() - this.getPaddingRight()
        // this is most likely a non-relevant call
        if (targetWidth <= 2) return
        // text already fits with the xml-defined font size?
        mTestPaint!!.set(this.getPaint())
        mTestPaint!!.textSize = defaultTextSize
        if (mTestPaint!!.measureText(text) <= targetWidth) {
            this.setTextSize(TypedValue.COMPLEX_UNIT_PX, defaultTextSize)
            return
        }
        // adjust text size using binary search for efficiency
        var hi = defaultTextSize
        var lo = 2f
        val threshold = 0.5f // How close we have to be
        while (hi - lo > threshold) {
            val size = (hi + lo) / 2
            mTestPaint!!.textSize = size
            if (mTestPaint!!.measureText(text) >= targetWidth) hi = size // too big
            else lo = size // too small
        }
        // Use lo so that we undershoot rather than overshoot
        this.setTextSize(TypedValue.COMPLEX_UNIT_PX, lo)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.setTypeface(getResources().getFont(R.font.raleway_semibold))
        } else {
            this.setTypeface(ResourcesCompat.getFont(getContext(), R.font.raleway_semibold))
        }
    }

    protected override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val parentWidth: Int = MeasureSpec.getSize(widthMeasureSpec)
        val height: Int = getMeasuredHeight()
        refitText(this.getText().toString(), parentWidth)
        this.setMeasuredDimension(parentWidth, height)
    }

    protected override fun onTextChanged(
        text: CharSequence, start: Int,
        before: Int, after: Int
    ) {
        refitText(text.toString(), this.getWidth())
    }

    protected override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        if (w != oldw || h != oldh) {
            refitText(this.getText().toString(), w)
        }
    }
}