package com.lihb.calculatorinputmethod

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnRegister.setOnClickListener({
            keyBoardView.registerEditText(et)
        })
        btnUnRegister.setOnClickListener({
            keyBoardView.registerEditText(null)
        })
        var b = true
        btnChangerHeader.setOnClickListener({
            b = if (b) {
                keyBoardView.setHeaderView(R.layout.header2)
                false
            } else {
                keyBoardView.setHeaderView(R.layout.header)
                true
            }
            keyBoardView.setOnHeaderChildClickListener(R.id.header, View.OnClickListener {
                Toast.makeText(it.context, "header onClick", Toast.LENGTH_SHORT).show()
            })
        })
        keyBoardView.setOnHeaderChildClickListener(R.id.header, View.OnClickListener {
            Toast.makeText(it.context, "header onClick", Toast.LENGTH_SHORT).show()
        })
//        keyBoardView.setHeaderView(R.layout.header2)
//        keyBoardView.registerEditText(et)
    }
}
