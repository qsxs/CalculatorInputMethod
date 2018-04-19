package com.lihb.library

import android.widget.EditText

interface OnDecimalSizeOutOfBoundsListener {
    fun onDecimalSizeOutOfBounds(et: EditText, decimalSize: Int)
}