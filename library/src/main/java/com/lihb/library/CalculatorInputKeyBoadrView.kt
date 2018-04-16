package com.lihb.library

import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.widget.Button
import android.widget.EditText

class CalculatorInputKeyBoadrView : KeyBoardView {
    private var editText: EditText? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init()
    }

//    public final fun registerEditText(view: EditText) {
//        editText = view
//    }


    private fun init() {
        setOnNumberClickListener(object : OnNumberClickListener {
            override fun onNumberClick(view: Button) {
                when(view.id){

                }
            }
        })
    }
}
