package com.lihb.calculatorinputmethod

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
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
        keyBoardView.setOnHeaderChildClickListener(R.id.header, View.OnClickListener
        {
            Toast.makeText(it.context, "header onClick", Toast.LENGTH_SHORT).show()
        })
//        keyBoardView.setHeaderView(R.layout.header2)
//        keyBoardView.registerEditText(et)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.bottom_sheet -> startActivity(Intent(this, BottomSheetActivity::class.java))
            R.id.sheet_dialog -> startActivity(Intent(this, PopupWindowActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }
}
