package com.lihb.calculatorinputmethod

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.lihb.library.KeyBoardSheetDialogFragment
import kotlinx.android.synthetic.main.activity_bottom_sheet_dialog.*

class BottomSheetDialogActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_sheet_dialog)
        val keyBoardView = KeyBoardSheetDialogFragment()
        btnRegister.setOnClickListener({
            keyBoardView.registerEditText(et,supportFragmentManager)
        })
        btnUnRegister.setOnClickListener({
            keyBoardView.registerEditText(null,supportFragmentManager)
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
