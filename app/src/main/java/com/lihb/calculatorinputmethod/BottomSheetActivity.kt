package com.lihb.calculatorinputmethod

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_bottom_sheet.*

class BottomSheetActivity : AppCompatActivity() {

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

        var hideable = false
        btnHideable.setOnClickListener({
            keyBoardView.setHideable(hideable)
            Toast.makeText(this, hideable.toString(), Toast.LENGTH_SHORT).show()
            hideable = !hideable
        })

        val textSizes = intArrayOf(8, 16, 24, 32, 64)
        var index = 0
        btnTextSize.setOnClickListener {
            keyBoardView.setTextSize(textSizes[index].toFloat())
            index++
            if (index == textSizes.size) {
                index = 0
            }
        }

        val colors = intArrayOf(Color.BLACK, Color.BLUE, Color.RED, Color.YELLOW, Color.GRAY)
        var colorIndex = 0

        btnTextColor.setOnClickListener{
            keyBoardView.setTextColor(colors[colorIndex])
            colorIndex++
            if (colorIndex == colors.size) {
                colorIndex = 0
            }
        }

        btnKeyBackground.setOnClickListener {
            keyBoardView.setKeyBackgroundColor(colors[colorIndex])
            colorIndex++
            if (colorIndex == colors.size) {
                colorIndex = 0
            }
        }
    }
}