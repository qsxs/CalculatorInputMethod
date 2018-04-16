//package com.lihb.library.keyboard;
//
//import android.os.Build;
//import android.os.SystemClock;
//import android.view.Gravity;
//import android.view.KeyEvent;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.WindowManager;
//import android.widget.EditText;
//import android.widget.PopupWindow;
//
//import com.lihb.library.R;
//
//
///**
// * Project: CCBClient_V4.00<br/>
// * Package: com.ccb.framework.keyboard<br/>
// * ClassName: SaltyFishKeyboard<br/>
// * Description: 键盘 <br/>
// * Date: 2016-6-14 19:30 <br/>
// * <p/>
// * Author 昊<br/>
// * Version 1.0<br/>
// * since JDK 1.6<br/>
// * <p/>
// */
//public class SaltyFishKeyboard {
//
//    private static EditText currEditText;
//    private static PopupWindow mKeyboardContent = null;
//
//    /**
//     * 注册键盘
//     *
//     * @param et 需要注册键盘的edittext
//     */
//    public static void registerEditText(EditText et) {
//        setListeners(et);
//    }
//
//    public static void unregister() {
//        currEditText = null;
//        mKeyboardContent = null;
//    }
//
//    /**
//     * 注册键盘
//     *
//     * @param ets 需要注册的输入框
//     */
//    public static void registerEditText(EditText... ets) {
//        for (EditText et : ets) {
//            registerEditText(et);
//        }
//    }
//
//    /**
//     * 获取当前edittext
//     *
//     * @return
//     */
//    static EditText getCurrEditText() {
//        return currEditText;
//    }
//
//    /**
//     * 设置监听
//     *
//     * @param et
//     */
//    private static void setListeners(EditText et) {
//        SaltyFishKeyboardUtils.setCursorVisible(et);
//        et.setOnTouchListener(touchListener);
//        et.setOnKeyListener(listener_backKey);
//        et.setOnFocusChangeListener(getFocusListener(et));
//    }
//
//
//    public static PopupWindow getKeyBoardContent() {
//        return mKeyboardContent;
//    }
//
//    /**
//     * 弹出键盘
//     *
//     * @param focusEt 当前edittext
//     */
//    private synchronized static void show(final EditText focusEt) {
//
//        View keyboardView = new SaltyFishKeyboardView(focusEt.getContext()).getKeyboardView();
//
//        if (null == mKeyboardContent) {
//            mKeyboardContent = new PopupWindow(keyboardView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
//            mKeyboardContent.setAnimationStyle(R.style.ccb_keyboard_style);
//        } else {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                SaltyFishKeyboardUtils.updatePopuWindowViewGroup(mKeyboardContent, keyboardView);
//            } else {
//                ViewGroup popuViewGroup = SaltyFishKeyboardUtils.getPopuWindowViewGroup(mKeyboardContent);
//                if (null == popuViewGroup) {
//                    hideKeyboard();
//                    mKeyboardContent.setContentView(keyboardView);
//                } else {
//                    popuViewGroup.addView(keyboardView);
//                }
//            }
//        }
//        if (mKeyboardContent.isShowing()) {
//            return;
//        }
//        mKeyboardContent.showAtLocation(focusEt.getRootView(), Gravity.CENTER | Gravity.BOTTOM, 0, 0);
//        mKeyboardContent.setOnDismissListener(ON_DISMISS_LISTENER);
//        SaltyFishKeyboardUtils.adjustLayout(focusEt, SaltyFishKeyboardUtils.getAdjustValue(keyboardView, focusEt));
//    }
//
//    /**
//     * 回退监听
//     */
//    private static final View.OnKeyListener listener_backKey = new View.OnKeyListener() {
//        @Override
//        public boolean onKey(View v, int keyCode, KeyEvent event) {
//            if (keyCode != KeyEvent.KEYCODE_BACK)
//                return false;
////            Log.i(getClass().getSimpleName(), "back============>>");
//            if (mKeyboardContent != null && mKeyboardContent.isShowing()) {
//                mKeyboardContent.dismiss();
//                return true;
//            }
//            return false;
//        }
//    };
//
//    /**
//     * 键盘关闭监听
//     */
//    private static final PopupWindow.OnDismissListener ON_DISMISS_LISTENER = new PopupWindow.OnDismissListener() {
//        @Override
//        public void onDismiss() {
//            if (null == currEditText)
//                return;
//            SaltyFishKeyboardUtils.restoreLayout(currEditText);
//        }
//    };
//
//    /**
//     * 触摸监听
//     */
//    private final static View.OnTouchListener touchListener = new View.OnTouchListener() {
//        @Override
//        public boolean onTouch(View v, MotionEvent event) {
//
//            if (null != currEditText && currEditText == v && null != mKeyboardContent && mKeyboardContent.isShowing())
//                return false;
//            final EditText focusEt = (EditText) v;
//            currEditText = focusEt;
//            SaltyFishKeyboardUtils.hideSystemBoard(focusEt);
//            focusEt.requestFocus();
//
//            show(focusEt);
//            return false;
//        }
//    };
//
//
//    /**
//     * 焦点转移收起键盘
//     */
//    private static final View.OnFocusChangeListener LISTENER_FOCUS = new View.OnFocusChangeListener() {
//        @Override
//        public void onFocusChange(View v, boolean hasFocus) {
//            if (!hasFocus) {
//                hideKeyboard();
//            }
//        }
//    };
//
//    private static View.OnFocusChangeListener getFocusListener(EditText et) {
//        final View.OnFocusChangeListener originListener = et.getOnFocusChangeListener();
//        if (originListener == null) {
//            return LISTENER_FOCUS;
//        }
//
//        return new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                originListener.onFocusChange(v, hasFocus);// 调用原键盘的输入监听事件;
//                if (!hasFocus) {
//                    hideKeyboard();
//                }
//            }
//        };
//    }
//
//
//    /**
//     * 关闭键盘
//     */
//    public static void hideKeyboard() {
//        if (null == mKeyboardContent || !mKeyboardContent.isShowing())
//            return;
//        mKeyboardContent.dismiss();
//    }
//
//    public static boolean isShowing() {
//        if (mKeyboardContent == null) {
//            return false;
//        }
//        return mKeyboardContent.isShowing();
//    }
//
//    /**
//     * 自动弹框键盘 需要先注册键盘{@link #registerEditText(EditText)}
//     *
//     * @param et 需要弹出的弹出框
//     */
//    public synchronized static void showKeyboard(final EditText et) {
//
//        et.post(new Runnable() {
//            @Override
//            public void run() {
//                et.dispatchTouchEvent(getSimulationEvent());
//            }
//        });
//
//    }
//
//    private static MotionEvent getSimulationEvent() {
//        long downTime = SystemClock.uptimeMillis();
//        long eventTime = downTime + 100;
//
//        float x = 0.0f;
//        float y = 0.0f;
//        return MotionEvent.obtain(downTime, eventTime, MotionEvent.ACTION_DOWN, x, y, 0);
//    }
//}
