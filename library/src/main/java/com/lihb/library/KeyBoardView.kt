package com.lihb.library

import android.content.Context
import android.os.Build
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.annotation.RequiresApi
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import java.math.BigDecimal


open class KeyBoardView : LinearLayout {
    private var editText: EditText? = null
    private var headerView: View? = null
    private var contentView: View? = null
    private var onNumberClickListener: OnNumberClickListener? = null

    enum class Action {
        DELETE,
        DONE,
        ADD,
        SUBTRACT,
        MULTIPLY,
        DIVIDE,
        OPPOSITE
    }

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

    fun registerEditText(view: EditText?) {
        editText = view
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
            onButtonClick(it as Button)
            onNumberClickListener?.onNumberClick(it as Button)
        }
        for (button in buttons) {
            button.setOnClickListener(onClickListener)
        }
    }

    private fun onButtonClick(button: Button) {
        when (button.id) {
            R.id.delete -> doAction(Action.DELETE)
            R.id.done -> doAction(Action.DONE)
            R.id.add -> doAction(Action.ADD)
            R.id.subtract -> doAction(Action.SUBTRACT)
            R.id.multiply -> doAction(Action.MULTIPLY)
            R.id.divide -> doAction(Action.DIVIDE)
            else ->
                if (editText != null) {
                    editText!!.text.insert(editText!!.selectionStart, button.text)
                }
        }

    }

    private fun doAction(action: Action) {
        val text = editText?.text
        if (TextUtils.isEmpty(text)) {
            return
        }
        text!!
        if (!text.contains("+")
                && !text.contains("-")
                && !text.contains("*")
                && !text.contains("/")
        ) {
            return
        }

        editText?.setText(doCalculator(text.toString()))

    }

    public fun doCalculator(text: String): String {
        var replace = text.toString().replace(" ", "")
        var isMinus = false //第一个数是否为负数
        if (replace.startsWith("-", true)) {
            isMinus = true
            replace = replace.substring(1)
        } else if (replace.startsWith("+", true)) {
            replace = replace.substring(1)
        }
        var finalString = ""
        var cs: String? = null
        if (replace.contains("+", true)) {
            cs = "+"
            val addIndex = replace.indexOf(cs)
            var one = replace.subSequence(0, addIndex)
            val two = replace.subSequence(addIndex + 1, replace.length)
            if (isMinus) {
                one = "-".plus(one)
            }
            val bigDecimalOne = BigDecimal(one.toString())
            val bigDecimalTwo = BigDecimal(two.toString())
            finalString = bigDecimalOne.add(bigDecimalTwo).toString()
        } else if (replace.contains("-", true)) {
            cs = "-"
            val addIndex = replace.indexOf(cs)
            var one = replace.subSequence(0, addIndex)
            val two = replace.subSequence(addIndex + 1, replace.length)
            if (isMinus) {
                one = "-".plus(one)
            }
            val bigDecimalOne = BigDecimal(one.toString())
            val bigDecimalTwo = BigDecimal(two.toString())
            finalString = bigDecimalOne.subtract(bigDecimalTwo).toString()
        } else if (replace.contains("*", true)) {
            cs = "*"
            val addIndex = replace.indexOf(cs)
            var one = replace.subSequence(0, addIndex)
            val two = replace.subSequence(addIndex + 1, replace.length)
            if (isMinus) {
                one = "-".plus(one)
            }
            val bigDecimalOne = BigDecimal(one.toString())
            val bigDecimalTwo = BigDecimal(two.toString())
            finalString = bigDecimalOne.multiply(bigDecimalTwo).toString()
        } else if (replace.contains("/", true)) {
            cs = "/"
            val addIndex = replace.indexOf(cs)
            var one = replace.subSequence(0, addIndex)
            val two = replace.subSequence(addIndex + 1, replace.length)
            if (isMinus) {
                one = "-".plus(one)
            }
            val bigDecimalOne = BigDecimal(one.toString())
            val bigDecimalTwo = BigDecimal(two.toString())
            finalString = bigDecimalOne.divide(bigDecimalTwo).toString()
        }

        return finalString.plus(cs)
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
