package com.lihb.library

import android.content.Context
import android.os.Build
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast


class KeyBoardView : LinearLayout {
    private var headerView: View? = null
    private var contentView: View? = null
    private var onNumberClickListener: OnNumberClickListener? = null

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context, attrs)
    }

    fun setHeaderView(view: View?) {
        removeView(headerView)
        headerView = view
        addView(headerView, 0)
        invalidate()
    }

    fun setHeaderView(@LayoutRes layout: Int) {
        val view = LayoutInflater.from(context).inflate(layout, this, false)
        setHeaderView(view)
    }

    fun getHeaderView(): View? {
        return headerView
    }

    fun setOnNumberClickListener(onNumberClickListener: OnNumberClickListener?) {
        this.onNumberClickListener = onNumberClickListener
    }

    fun getOnNumberClickListener(): OnNumberClickListener? {
        return onNumberClickListener
    }

    fun setOnHeaderChildClickListener(@IdRes idRes: Int, listener: OnClickListener?) {
        headerView?.findViewById<View>(idRes)?.setOnClickListener(listener)
    }

    private fun init(context: Context, attrs: AttributeSet? = null) {
        orientation = VERTICAL
        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.KeyBoardView, 0, 0)
            val header = a.getResourceId(R.styleable.KeyBoardView_header, 0)
            a.recycle()
            if (header != 0) {
                headerView = LayoutInflater.from(context).inflate(header, this, false)
//                headerView = View.inflate(context, header, this)
//                addView(inflate)
            }
        }
        if (headerView != null) {
            addView(headerView)
        }

        contentView = LayoutInflater.from(context).inflate(R.layout.view_keyboard, this, false)
        addView(contentView)
        initViews()
    }

    private fun initViews() {
        val buttons = ArrayList<Button>()
        getButtons(contentView as ViewGroup, buttons)
        val onClickListener = OnClickListener {
            onNumberClickListener?.onNumberClick(it as Button)
            Toast.makeText(context, (it as Button).text.toString() + " button onClick", Toast.LENGTH_SHORT).show();
        }
        for (button in buttons) {
            button.setOnClickListener(onClickListener)
        }
    }

    /**
     * 获取viewGroup下所有button
     */
    private fun getButtons(viewGroup: ViewGroup?, buttons: MutableList<Button>) {
        if (viewGroup == null) {
            return
        }
        val count = viewGroup.childCount
        for (i in 0 until count) {
            val view = viewGroup.getChildAt(i)
            if (view is Button) { // 若是Button记录下
                buttons.add(view)
            } else if (view is ViewGroup) {
                // 若是布局控件（LinearLayout或RelativeLayout）,继续查询子View
                this.getButtons(view, buttons)
            }
        }
    }

}
