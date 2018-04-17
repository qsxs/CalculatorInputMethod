package com.lihb.library

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v4.app.FragmentManager
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText

open class KeyBoardSheetDialogFragment : BottomSheetDialogFragment() {
    private val TAG = "KeyBoardSheetDialogFragment"
    private var rootView: KeyBoardView? = null
    private var manager: FragmentManager? = null
    private var editText: EditText? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = KeyBoardView(context!!)
        //设置显示和消失动画
        dialog.window!!.setWindowAnimations(R.style.ccb_keyboard_style)
        return rootView
    }

    @SuppressLint("ClickableViewAccessibility")
    fun registerEditText(view: EditText?, manager: FragmentManager) {
        this.manager = manager
        rootView?.registerEditText(view)
        if (view == null) {
            editText?.setOnTouchListener(null)
            editText?.setOnKeyListener(null)
        }
        editText = view
        editText?.setOnTouchListener({ _, _ ->
            show()
            true
        })
        editText?.setOnKeyListener({ _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                dismiss()
                true
            } else {
                false
            }
        })
    }

    fun show() {
        if (manager != null && !isAdded)
            show(manager, TAG)
    }

    fun setHeaderView(view: View?) {
        rootView?.setHeaderView(view)
    }

    fun setHeaderView(@LayoutRes layout: Int) {
        rootView?.setHeaderView(layout)
    }

    fun getHeaderView(): View? {
        return rootView?.getHeaderView()
    }

    fun setOnNumberClickListener(onButtonClickListener: OnButtonClickListener?) {
        rootView?.setOnNumberClickListener(onButtonClickListener)
    }

    fun getOnNumberClickListener(): OnButtonClickListener? {
        return rootView?.getOnNumberClickListener()
    }

    fun setOnHeaderChildClickListener(@IdRes idRes: Int, listener: View.OnClickListener?) {
        rootView?.setOnHeaderChildClickListener(idRes, listener)
    }

}
