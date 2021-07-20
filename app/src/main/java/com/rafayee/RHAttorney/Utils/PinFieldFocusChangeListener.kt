package com.rafayee.RHAttorney.Utils

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.rafayee.RHAttorney.R

class PinFieldFocusChangeListener(
    context: Context, text: EditText,
    focusleft: Int, focusRight: Int, focusTop: Int, focusBottom: Int,
    left: Int, right: Int, top: Int, bottom: Int
) {
    init {
        text.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                text.background = (context.resources.getDrawable(R.drawable.shadow_verify))
                val params = text.layoutParams as ViewGroup.MarginLayoutParams
                params.setMargins(
                    focusleft, focusTop,
                    focusRight, focusBottom
                )
                text.layoutParams = params

                val imm: InputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(text, InputMethodManager.SHOW_IMPLICIT)
            } else {
                // Required to show/hide white background behind floating label during focus change
                if (text.getText()?.length!! > 0) {
                    text.background = (context.resources.getDrawable(R.drawable.shadow_verify))
                    val params = text.layoutParams as ViewGroup.MarginLayoutParams
                    params.setMargins(
                        focusleft, focusTop,
                        focusRight, focusBottom
                    )
                    text.layoutParams = params
                } else {
                    text.background = context.resources.getDrawable(R.drawable.bg_edittext)
                    val params = text.layoutParams as ViewGroup.MarginLayoutParams
                    params.setMargins(
                        left, top,
                        right, bottom
                    )
                    text.layoutParams = params
                }
            }
            v.invalidate()
        }
    }
}