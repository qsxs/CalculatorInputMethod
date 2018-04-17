package com.lihb.calculatorinputmethod

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class BottomSheetActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_sheet)
        keyBoardView.registerEditText(et)
        btnRegister.setOnClickListener({
            keyBoardView.registerEditText(et)
        })
        btnUnRegister.setOnClickListener({
            keyBoardView.registerEditText(null)
        })
        var i = 0
        btnChangerHeader.setOnClickListener({
            when (i) {
                0 -> keyBoardView.setHeaderView(R.layout.header2)
                1 -> keyBoardView.setHeaderView(null)
                else -> keyBoardView.setHeaderView(R.layout.header)
            }
            i++
            if (i == 3) {
                i = 0
            }
            keyBoardView.setOnHeaderChildClickListener(R.id.header, View.OnClickListener {
                Toast.makeText(it.context, "header onClick", Toast.LENGTH_SHORT).show()
            })
        })
    }
}