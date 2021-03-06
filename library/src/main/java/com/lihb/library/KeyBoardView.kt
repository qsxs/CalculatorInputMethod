package com.lihb.library

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Handler
import android.os.Message
import android.support.annotation.*
import android.support.design.widget.BottomSheetBehavior
import android.support.design.widget.CoordinatorLayout
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.*
import android.view.View.OnClickListener
import android.view.View.OnFocusChangeListener
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Checkable
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.lihb.library.util.CalculatorUtil


open class KeyBoardView : LinearLayout {
    private val TAG = "KeyBoardView"
    private var editText: EditText? = null
    private var headerView: View? = null
    private var contentView: View? = null
    private var behavior: BottomSheetBehavior<KeyBoardView>? = null
    /**
     * 键盘点击监听
     */
    private var onButtonClickListener: OnKeyClickListener? = null
    /**
     * 键盘状态改变监听
     */
    private var onStateChangedListener: OnStateChangedListener? = null
    /**
     * 输入小数部分超过限制监听
     */
    private var onDecimalSizeOutOfBoundsListener: OnDecimalSizeOutOfBoundsListener? = null

    private var hideable: Boolean = false//dismiss时是否隐藏头部
    private var show: Boolean = false//初始时状态
    private var textSize: Float = (-1).toFloat()//字体大小
    private var textColor: Int = Color.TRANSPARENT//字体颜色
    private var keyBackground: Drawable? = null//按键背景
    private var mBackgroundResource: Int = 0//按键背景ResourceId
    private var decimalSize: Int = -1//允许的小数位数

    enum class Action {
        DELETE,
        DONE,
        ADD,
        SUBTRACT,
        MULTIPLY,
        DIVIDE,
        OPPOSITE
    }

    fun setEditText(view: EditText?) {
        editText = view
    }

    /**
     * 注册到edittext
     */
    @SuppressLint("ClickableViewAccessibility")
    fun registerEditText(view: EditText?) {
        if (view == null) {
            editText?.setOnTouchListener(null)
            editText?.setOnKeyListener(null)
            editText?.onFocusChangeListener = null
            dismiss()
        }
        editText = view
        editText?.setOnTouchListener { _, _ ->
            show()
        }
        editText?.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                show()
            } else {
                dismiss()
            }
        }
        editText?.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                dismiss()
            } else {
                false
            }
        }
    }

    fun isViewSelected(@IdRes idRes: Int): Boolean {
        return findViewById<View>(idRes).isSelected
    }

    fun isViewChecked(@IdRes idRes: Int): Boolean {
        val view = findViewById<View>(idRes)
        return view is Checkable && view.isChecked
    }

    fun <T : View> getChildenView(@IdRes idRes: Int): T {
        return findViewById(idRes)
    }

    fun setText(@IdRes idRes: Int, text: CharSequence?) {
        findViewById<TextView>(idRes)?.text = text
    }

    fun setText(@IdRes idRes: Int, @StringRes text: Int) {
        setText(idRes, context.getString(text))
    }

    fun setTextSize(size: Float, unit: Int = TypedValue.COMPLEX_UNIT_SP) {
        val c = context
        val r: Resources = if (c == null) {
            Resources.getSystem()
        } else {
            c.resources
        }
        textSize = TypedValue.applyDimension(unit, size, r.displayMetrics)
        invalidateTextViews()
    }

    fun getTextSize(): Float {
        return textSize
    }

    fun setTextColor(@ColorInt color: Int) {
        textColor = color
        invalidateTextViews()
    }

    fun getTextColor(): Int {
        return textColor
    }

    @RequiresApi(Build.VERSION_CODES.HONEYCOMB)
    fun setKeyBackgroundColor(@ColorInt color: Int) {
        if (keyBackground is ColorDrawable) {
            (keyBackground?.mutate() as ColorDrawable).color = color
            mBackgroundResource = 0

            invalidateTextViews()
        } else {
            setKeyBackground(ColorDrawable(color))
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun setKeyBackgroundResource(@DrawableRes resid: Int) {
        if (resid != 0 && resid == mBackgroundResource) {
            return
        }

        var d: Drawable? = null
        if (resid != 0) {
            d = context.getDrawable(resid)
        }
        setKeyBackground(d)

        mBackgroundResource = resid
    }

    fun setKeyBackground(background: Drawable?) {
        keyBackground = background
        mBackgroundResource = 0

        invalidateTextViews()
    }

    fun getKeyBackGround(): Drawable? {
        return keyBackground
    }

    fun setHideable(hideable: Boolean) {
        this.hideable = hideable
        initPeekHeight()
    }

    fun getHideable(): Boolean {
        return hideable
    }

    fun setHeaderView(view: View?) {
        removeView(headerView)
        headerView = view
        if (headerView != null)
            addView(headerView, 0)
        invalidate()
    }

    fun setHeaderView(@LayoutRes layout: Int) {
        if (layout != 0) {
            val view = LayoutInflater.from(context).inflate(layout, this, false)
            setHeaderView(view)
        }
    }

    fun getHeaderView(): View? {
        return headerView
    }

    fun setOnKeyClickListener(onButtonClickListener: OnKeyClickListener?) {
        this.onButtonClickListener = onButtonClickListener
    }

    fun getOnKeyClickListener(): OnKeyClickListener? {
        return onButtonClickListener
    }

    fun setOnStateChangedListener(onStateChangedListener: OnStateChangedListener?) {
        this.onStateChangedListener = onStateChangedListener
    }

    fun getOnStateChangedListener(): OnStateChangedListener? {
        return onStateChangedListener
    }

    fun setOnDecimalSizeOutOfBoundsListener(onDecimalSizeOutOfBoundsListener: OnDecimalSizeOutOfBoundsListener?) {
        this.onDecimalSizeOutOfBoundsListener = onDecimalSizeOutOfBoundsListener
    }

    fun getOnDecimalSizeOutOfBoundsListener(): OnDecimalSizeOutOfBoundsListener? {
        return onDecimalSizeOutOfBoundsListener
    }

    fun setOnHeaderChildClickListener(@IdRes idRes: Int, listener: OnClickListener?) {
        headerView?.findViewById<View>(idRes)?.setOnClickListener(listener)
    }

    fun isShowing(): Boolean {
        return behavior?.state == BottomSheetBehavior.STATE_EXPANDED
    }

    fun setDecimalSize(decimalSize: Int) {
        this.decimalSize = decimalSize
    }

    fun getDecimalSize(): Int {
        return decimalSize
    }

    fun show(): Boolean {
        if (parent is CoordinatorLayout) {
            initBehavior()
            if (behavior!!.state != BottomSheetBehavior.STATE_EXPANDED) {
                val imm = (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)//隐藏键盘
                imm.hideSoftInputFromWindow(this.windowToken, 0)
                postDelayed({
                    behavior?.state = BottomSheetBehavior.STATE_EXPANDED
                    editText?.requestFocus()
                }, 100)
                return true
            }
        }
        return editText != null
    }

    fun dismiss(): Boolean {
        if (parent is CoordinatorLayout) {
            initBehavior()
            return if (behavior!!.state != BottomSheetBehavior.STATE_COLLAPSED) {
                behavior?.state = BottomSheetBehavior.STATE_COLLAPSED
                true
            } else {
                false
            }
        }
        return false
    }

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    @RequiresApi(Build.VERSION_CODES.HONEYCOMB)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context, attrs)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //这里最早得知parent
        initBehavior()
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        //这里最早得知headerView的高度
        initPeekHeight()
    }

    private fun invalidateTextViews() {
        val buttons = ArrayList<View>()
        getViews(contentView as ViewGroup, buttons)
        for (textView in buttons) {
            if (textView is TextView) {
                textView.textSize = textSize
                textView.setTextColor(textColor)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                textView.background = keyBackground
            } else {
                @Suppress("DEPRECATION")
                textView.setBackgroundDrawable(keyBackground)
            }
            textView.invalidate()
        }
    }

    private fun init(context: Context, attrs: AttributeSet? = null) {
        orientation = VERTICAL
        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.KeyBoardView, 0, 0)
            val header = a.getResourceId(R.styleable.KeyBoardView_header, 0)
            hideable = a.getBoolean(R.styleable.KeyBoardView_hideable, false)
            show = a.getBoolean(R.styleable.KeyBoardView_show, false)
            textSize = a.getDimension(R.styleable.KeyBoardView_textSize, (-1).toFloat())
            textColor = a.getColor(R.styleable.KeyBoardView_textColor, Color.TRANSPARENT)
            keyBackground = a.getDrawable(R.styleable.KeyBoardView_keyBackground)
            decimalSize = a.getInt(R.styleable.KeyBoardView_decimalSize, -1)
            a.recycle()

            setHeaderView(header)
        }

        contentView = LayoutInflater.from(context).inflate(R.layout.view_keyboard, this, false)
        addView(contentView)
        initViews()

    }

    private fun initViews() {
        val views = ArrayList<View>()
        getViews(contentView as ViewGroup, views)
        val onClickListener = OnClickListener {
            if (onButtonClickListener?.onKeyClick(it) != true) {
                onButtonClick(it)
            }

        }
        for (view in views) {
            view.setOnClickListener(onClickListener)
            if (view is TextView) {
                if (textSize > 0) view.textSize = textSize
                if (textColor != Color.TRANSPARENT) view.setTextColor(textColor)
            }
            if (keyBackground != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                view.background = keyBackground
            }
        }
        val view = findViewById<View>(R.id.delete)
        view?.setOnLongClickListener {
            val msg = Message()
            msg.arg1 = -1
            msg.what = DELETE_AUTO
            deleteHandler.sendMessage(msg)
            false
        }
        view?.setOnClickListener {
            deleteHandler.sendEmptyMessage(DELETE)
            onButtonClickListener?.onKeyClick(it)
        }
        view?.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> deleteHandler.removeCallbacksAndMessages(null)
            }
            false
        }
    }

    private fun initBehavior() {
        if (parent is CoordinatorLayout && behavior == null) {
            (layoutParams as CoordinatorLayout.LayoutParams).behavior = BottomSheetBehavior<LinearLayout>()
            behavior = BottomSheetBehavior.from(this)
            if (show) {
                behavior?.state = BottomSheetBehavior.STATE_EXPANDED
                show = false //一次失效
            } else {
                behavior?.state = BottomSheetBehavior.STATE_COLLAPSED
            }
            behavior!!.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    onStateChangedListener?.let {
                        when (newState) {
                            BottomSheetBehavior.STATE_EXPANDED -> it.onShow(this@KeyBoardView)
                            BottomSheetBehavior.STATE_COLLAPSED -> it.onDismiss(this@KeyBoardView)
                        }
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                    onStateChangedListener?.onSlide(this@KeyBoardView, slideOffset)
                }
            })
        }
    }

    private fun initPeekHeight() {
        behavior?.let {
            //            val height =
//                    if (headerView == null) 0
//                    else headerView!!.measuredHeight
//            it.peekHeight = if (hideable) 0 else height
            it.peekHeight = when {
                hideable -> 0
                headerView == null -> 0
                else -> headerView!!.measuredHeight
            }

            Log.i(TAG, "it.peekHeight:" + it.peekHeight.toString())
            //防止peek挡住主体内容
            val coordinatorLayout = parent as CoordinatorLayout
            //防止遮挡
            for (i in 0 until coordinatorLayout.childCount) {
                val childAt = coordinatorLayout.getChildAt(i)
                if (childAt != this) {
                    val params = childAt.layoutParams as MarginLayoutParams
                    if (params.bottomMargin != it.peekHeight &&
                            params.height == ViewGroup.LayoutParams.MATCH_PARENT) {
                        params.bottomMargin = it.peekHeight
                        Log.i(TAG, "params.bottomMargin:" + params.bottomMargin.toString())
                        childAt.layoutParams = params
                    }
                }
            }
        }
    }

    private fun onButtonClick(button: View) {
        when (button.id) {
            R.id.done -> doAction(Action.DONE, button)
            R.id.add -> doAction(Action.ADD, button)
            R.id.subtract -> doAction(Action.SUBTRACT, button)
            R.id.multiply -> doAction(Action.MULTIPLY, button)
            R.id.divide -> doAction(Action.DIVIDE, button)
            R.id.decimal -> onDecimalClick(button)
            R.id.opposite -> onOppositeClick(button)
            else -> {
                editText?.text?.let {
                    val lastIndexOf = it.lastIndexOf(".")
                    if (decimalSize >= 0//大于等于0，表示设置了限制
                            && lastIndexOf >= 0//大于等于0表示包含了小数点
                            && lastIndexOf + decimalSize < it.length
//                            && lastIndexOf+1 < it.length
                            && !it.subSequence(lastIndexOf + 1, it.length).contains('+')
                            && !it.subSequence(lastIndexOf + 1, it.length).contains('-')
                            && !it.subSequence(lastIndexOf + 1, it.length).contains('/')
                            && !it.subSequence(lastIndexOf + 1, it.length).contains('*')
                    ) {
                        onDecimalSizeOutOfBoundsListener?.onDecimalSizeOutOfBounds(editText!!, decimalSize)
                    } else {
                        it.insert(editText!!.selectionStart, (button as TextView).text)
                    }
                }

            }
        }
    }

    /**
     * 点击取反
     */
    private fun onOppositeClick(button: View) {
        val text = editText?.text
        val s =
                when {
                    TextUtils.isEmpty(text) -> ""
                    text!!.startsWith("+") -> "-".plus(text.subSequence(1, text.length))
                    text.startsWith("-") -> text.substring(1, text.length)
                    else -> "-".plus(text)
                }
        editText?.setText(s)
        editText?.setSelection(editText!!.text.length)
    }

    /**
     * 点击小数点
     */
    private fun onDecimalClick(button: View) {
        // 在光标之后加入字符
        val text = editText?.text
        val finalString: String
        val cs = '.'
        if (TextUtils.isEmpty(text)) {
            finalString = "0."
        } else {
            text!!
            val lastChar = text[text.length - 1]
            finalString =
                    if (lastChar == '+'
                            || lastChar == '-'
                            || lastChar == '*'
                            || lastChar == '/') {
                        text.toString().plus("0.")
                    } else {
                        val s =
                                when {
                                    text.contains("+") -> text.split("+")[1]
                                    text.contains("-") -> text.split("-")[1]
                                    text.contains("*") -> text.split("*")[1]
                                    text.contains("/") -> text.split("/")[1]
                                    else -> text
                                }
                        if (!s.contains(".")) {
                            text.toString().plus(cs)
                        } else {
                            text.toString()
                        }
                    }
        }
        editText?.setText(finalString)
        editText?.setSelection(editText!!.text.length)
    }

    private fun doAction(action: Action, button: View) {
        button as TextView
        val text = editText?.text
        if (TextUtils.isEmpty(text)) {
            if (button.text.contains("-")) {
                editText?.setText("-")
            } else if (action != Action.DONE) {
                editText?.setText("0".plus(button.text))
            }
            editText?.setSelection(editText!!.text.length)
            return
        }

        text!!
        if (action != Action.DONE
                && (text.endsWith("+")
                        || text.endsWith("-")
                        || text.endsWith("*")
                        || text.endsWith("/"))
        ) {
            editText?.setText(text.subSequence(0, text.length - 1).toString().plus(button.text))
            editText?.setSelection(editText!!.text.length)
            return
        }

        var doCalculator = doCalculator(text.toString())
        if (action != Action.DONE) {
            doCalculator = doCalculator.plus(button.text)
        } else {
            editText?.onEditorAction(EditorInfo.IME_ACTION_DONE)
        }
        editText?.setText(doCalculator)
        editText?.setSelection(editText!!.text.length)
    }

    private fun doCalculator(text: String?): String {
        return CalculatorUtil.simpleCalculator(text, decimalSize)
    }

    /**
     * 删除对应标识
     */
    private val DELETE = 200
    private val DELETE_AUTO = 400

    /**
     * 删除速度
     */
    private val DELETE_SPEED = 50

    /**
     * 删除处理
     */
    private val deleteHandler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                DELETE -> deleteText()
                DELETE_AUTO -> {
                    deleteText()
                    if (msg.arg1 == -1) {
                        msg.arg1 = DELETE_SPEED
                    }
                    val speed = if (msg.arg1 > 0) --msg.arg1 else 0
                    val message = obtainMessage()
                    message.what = DELETE_AUTO
                    message.arg1 = speed
                    sendMessageDelayed(message, speed.toLong())
                }
            }
        }
    }

    /**
     * 从输入框删除文字
     */
    private fun deleteText() {
        if (editText == null) {
            return
        }
        val currPos = editText!!.selectionEnd
        if (-1 == currPos)
            return  // 错误
        if (0 == currPos)
            return  // 已经删完
        val text = editText?.text
        text?.delete(currPos - 1, currPos)
        editText?.setSelection(editText!!.selectionEnd)
    }

    /**
     * 获取viewGroup下所有button
     */
    private fun getViews(viewGroup: ViewGroup?, buttons: MutableList<View>) {
        if (viewGroup == null) {
            return
        }
        val count = viewGroup.childCount
        for (i in 0 until count) {
            val view = viewGroup.getChildAt(i)
            if (view is ViewGroup) {
                // 若是布局控件（LinearLayout或RelativeLayout）,继续查询子View
                this.getViews(view, buttons)
            } else if (view is View) { // 若是Button记录下
                buttons.add(view)
            }
        }
    }

}
