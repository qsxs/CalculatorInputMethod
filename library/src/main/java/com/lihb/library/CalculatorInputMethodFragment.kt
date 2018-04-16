package com.lihb.library

import android.os.Bundle
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class CalculatorInputMethodFragment : Fragment() {
    private var keyBoardView: KeyBoardView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragement_calculator_input_method, container, false)
        keyBoardView = view.findViewById<KeyBoardView>(R.id.keyBoardView)
        return view
//        return super.onCreateView(inflater, container, savedInstanceState)
    }

    fun setHeaderView(view: View?) {
        keyBoardView?.setHeaderView(view)
    }

    fun setHeaderView(@LayoutRes layout: Int) {
        keyBoardView?.setHeaderView(layout)
    }

    fun getHeaderView(): View? {
        return keyBoardView?.getHeaderView()
    }

    fun setOnNumberClickListener(onNumberClickListener: OnNumberClickListener?) {
        keyBoardView?.setOnNumberClickListener(onNumberClickListener)
    }

    fun getOnNumberClickListener(): OnNumberClickListener? {
        return keyBoardView?.getOnNumberClickListener()
    }

    fun setOnHeaderChildClickListener(@IdRes idRes: Int, listener: View.OnClickListener?) {
        keyBoardView?.setOnHeaderChildClickListener(idRes, listener)
    }
}