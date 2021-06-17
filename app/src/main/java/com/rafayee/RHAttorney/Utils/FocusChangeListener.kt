package com.rafayee.RH.Utils

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.rafayee.RHAttorney.R


@SuppressLint("NewApi")
class FocusChangeListener(
    context: Context, textInputLayout: TextInputLayout, text: TextInputEditText,
    focusleft: Int, focusRight: Int, focusTop: Int, focusBottom: Int,
    left: Int, right: Int, top: Int, bottom: Int
) {
    init {
        text.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                textInputLayout.boxStrokeWidth = 0
                textInputLayout.boxStrokeColor = context.resources.getColor(R.color.white)
                textInputLayout.background = (context.resources.getDrawable(R.drawable.shadow))
                val params = textInputLayout.layoutParams as ViewGroup.MarginLayoutParams
                params.setMargins(
                    focusleft, focusTop,
                    focusRight, focusBottom
                )
                textInputLayout.layoutParams = params
                textInputLayout.setPadding(0,15,0,0)

                //text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
                textInputLayout.setHintTextAppearance(R.style.hintText);
                text.setTextAppearance(R.style.editText);
                val imm: InputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(text, InputMethodManager.SHOW_IMPLICIT)
            } else {
                // Required to show/hide white background behind floating label during focus change
                if (text.getText()?.length!! > 0) {
                    textInputLayout.boxStrokeWidth = 0
                    textInputLayout.boxStrokeColor = context.resources.getColor(R.color.white)
                    textInputLayout.background = (context.resources.getDrawable(R.drawable.shadow))
                    val params = textInputLayout.layoutParams as ViewGroup.MarginLayoutParams
                    params.setMargins(
                        focusleft, focusTop,
                        focusRight, focusBottom
                    )
                    textInputLayout.layoutParams = params
                    textInputLayout.setPadding(0,15,0,0)
                    //text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
                    textInputLayout.setHintTextAppearance(R.style.hintText);
                    text.setTextAppearance(R.style.editText);
                } else {
                    textInputLayout.background = context.resources.getDrawable(R.drawable.bg_edittext)
                    val params = textInputLayout.layoutParams as ViewGroup.MarginLayoutParams
                    params.setMargins(
                        left, top,
                        right, bottom
                    )
                    textInputLayout.layoutParams = params
                    textInputLayout.boxStrokeWidth = 0
                    textInputLayout.boxStrokeColor = context.resources.getColor(R.color.white)

                    //text.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11f)
                    textInputLayout.setHintTextAppearance(R.style.hintText);
                    text.setTextAppearance(R.style.hintText);
                }
            }
            v.invalidate()
        }
        text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

    }

}