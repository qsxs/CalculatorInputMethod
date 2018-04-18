package com.lihb.library

import android.annotation.SuppressLint
import android.content.Context
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.PopupWindow

class KeyBoardPopupWindow {
    private var keyBoardView: KeyBoardView
    private var editText: EditText? = null
    private var popupWindow: PopupWindow

    companion object {
        fun register(view: EditText?) {
            if (view != null) {
                val window = KeyBoardPopupWindow(view.context)
                window.registerEditText(view)
            }
        }
    }

    constructor(context: Context, header: View? = null) {
//        this.editText = editText
        keyBoardView = KeyBoardView(context)
        keyBoardView.setHeaderView(header)
//        keyBoardView.setEditext(editText)
        popupWindow = PopupWindow(keyBoardView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        popupWindow.isOutsideTouchable = false
        popupWindow.animationStyle = R.style.ccb_keyboard_style
    }

    @SuppressLint("ClickableViewAccessibility")
    fun registerEditText(view: EditText?) {
        if (view == null) {
            editText?.setOnTouchListener(null)
            editText?.setOnKeyListener(null)
            dismiss()
        }
        editText = view
        editText?.setOnTouchListener({ _, _ ->
            show()
        })
        editText?.setOnKeyListener({ _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                dismiss()

            } else {
                false
            }
        })
        keyBoardView.setEditText(view)
    }

    fun show(): Boolean {
        if (!popupWindow.isShowing)
            popupWindow.showAtLocation(editText?.rootView, Gravity.CENTER or Gravity.BOTTOM, 0, 0)
        return editText != null
    }

    fun dismiss(): Boolean {
        return if (popupWindow.isShowing) {
            popupWindow.dismiss()
            true
        } else false
    }

    fun setHeaderView(view: View?) {
        keyBoardView.setHeaderView(view)
    }

    fun setHeaderView(@LayoutRes layout: Int) {
        keyBoardView.setHeaderView(layout)
    }

    fun setOnHeaderChildClickListener(@IdRes idRes: Int, listener: View.OnClickListener?) {
        keyBoardView.setOnHeaderChildClickListener(idRes, listener)
    }
}
