package com.rafayee.RHAttorney.Utils

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast

class PinInFiled(context:Context, ed1:EditText,ed2:EditText,ed3:EditText,ed4:EditText) {

    init {

        ed1.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                //    TODO("Not yet implemented")
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //  TODO("Not yet implemented")
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if(s?.length==1){
                    ed2.requestFocus()
                }
                //TODO("Not yet implemented")
            }
        })

        ed2.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                //TODO("Not yet implemented")
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //TODO("Not yet implemented")
                if(s?.length==1){
                    ed3.requestFocus()
                }else{
                    ed1.requestFocus()
                }
            }
        })

        ed3.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // TODO("Not yet implemented")
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //TODO("Not yet implemented")
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //TODO("Not yet implemented")
                if(s?.length==1){
                    ed4.requestFocus()
                }else{
                    ed2.requestFocus()
                }
            }
        })

        ed4.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // TODO("Not yet implemented")
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //    TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // TODO("Not yet implemented")

                if(s?.length==0){
                    ed3.requestFocus()

                }else{
                    //Toast.makeText(context,"Done", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

}