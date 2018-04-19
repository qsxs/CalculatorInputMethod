package com.lihb.library


interface OnStateChangedListener {
    fun onShow(keyboardView: KeyBoardView)
    fun onDismiss(keyboardView: KeyBoardView)
    fun onSlide(keyboardView: KeyBoardView,slideOffset: Float)
}