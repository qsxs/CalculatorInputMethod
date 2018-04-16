package com.lihb.calculatorinputmethod

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        keyBoardView.setHeaderView(R.layout.header2)
        keyBoardView.setOnHeaderChildClickListener(R.id.header, View.OnClickListener {
            Log.i("TAG ", it.id.toString())
        })
    }
}
