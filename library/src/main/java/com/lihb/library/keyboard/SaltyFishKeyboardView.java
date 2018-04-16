//package com.lihb.library.keyboard;
//
//import android.content.Context;
//import android.os.Handler;
//import android.os.Message;
//import android.text.Editable;
//import android.text.TextUtils;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.lihb.library.R;
//
//
///**
// * ClassName: SaltyFishKeyboardView<br/>
// * Description: 键盘整体布局 <br/>
// * Date: 2016-6-15 10:20 <br/>
// * <p/>
// * Author 昊<br/>
// * Version 1.0<br/>
// * since JDK 1.6<br/>
// * <p/>
// */
//class SaltyFishKeyboardView {
//
//    private Context mContext;
//    private TextView ct_submit;
//
//    /**
//     * 构造
//     *
//     * @param mContext 上下文
//     */
//    SaltyFishKeyboardView(Context mContext) {
//        this.mContext = mContext;
//    }
//
//    /**
//     * 获取键盘视图
//     *
//     * @return
//     */
//    View getKeyboardView() {
//        if (null == keyboardView) {
//            initLayer();
//        }
//
//        return keyboardView;
//    }
//
//    /**
//     * 键盘视图container
//     */
//    private ViewGroup keyboardView = null;
//
//    /**
//     * 初始化布局
//     */
//    private void initLayer() {
//        if (null == keyboardView) {
//            keyboardView = (ViewGroup) View.inflate(mContext, R.layout.view_keyboard, null);
//        }
//
//        String layerString = SaltyFishKeyResId.CHAR_DIG;
//        int[] keyIds = SaltyFishKeyResId.resIdDigital;
//        int deleteId = 0;//R.id.digit_row_one_delete;
//        //int keyBackgroundResid = R.drawable.keyboard_key_bg;
//
//        // 关闭按键
////        ct_submit = (TextView) keyboardView.findViewById(R.id.ct_submit);
////        keyboardView.findViewById(R.id.close).setOnClickListener(mOkKeyOnClickListener);
//
//        // 设置数字键以及监听
//        if (null != keyIds) {
//            for (int i = 0, count = keyIds.length; i < count; i++) {
//                Button keyButton = (Button) keyboardView.findViewById(keyIds[i]);
////              keyButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, mContext.getResources().getDimensionPixelSize(R.dimen.keyboard_txt_size));
////				keyButton.setTextColor(Color.WHITE);
//                keyButton.setText(layerString.substring(i, i + 1));
////				keyButton.setBackgroundResource(keyBackgroundResid);
//                keyButton.setOnClickListener(mKeyOnClickListener);
//                if (i == 5) {
//                    keyButton.setOnLongClickListener(new View.OnLongClickListener() {
//                        @Override
//                        public boolean onLongClick(View v) {
//                            Toast.makeText(mContext, "R.string.i_am_666", Toast.LENGTH_SHORT).show();
//                            return false;
//                        }
//                    });
//                }
//            }
//        }
//        // 删除键监听
//        ImageButton deleteButton = (ImageButton) keyboardView.findViewById(deleteId);
//        if (null != deleteButton) {
//            deleteButton.setOnLongClickListener(deleteLongClickListener);
//            deleteButton.setOnClickListener(deleteOnClickListener);
//            deleteButton.setOnTouchListener(deleteTouchListener);
//        }
//
//        Button addKey = (Button) keyboardView.findViewById(R.id.digit_row_two_add);
//        Button subKey = (Button) keyboardView.findViewById(R.id.digit_row_three_sub);
//        Button dotKey = (Button) keyboardView.findViewById(R.id.digit_row_four_x);
//        addKey.setText("+");
//        subKey.setText("-");
//        dotKey.setText(".");
////        addKey.setTextSize(TypedValue.COMPLEX_UNIT_PX, mContext.getResources().getDimensionPixelSize(R.dimen.keyboard_txt_size));
////        subKey.setTextSize(TypedValue.COMPLEX_UNIT_PX, mContext.getResources().getDimensionPixelSize(R.dimen.keyboard_txt_size));
////        dotKey.setTextSize(TypedValue.COMPLEX_UNIT_PX, mContext.getResources().getDimensionPixelSize(R.dimen.keyboard_txt_size));
//        addKey.setOnClickListener(mCalculateKeyOnClickListener);
//        subKey.setOnClickListener(mCalculateKeyOnClickListener);
//        dotKey.setOnClickListener(mDotKeyOnClickListener);
//        Button okKey = (Button) keyboardView.findViewById(R.id.digit_row_four_ok);
//        okKey.setText("R.string.done");
////        okKey.setTextSize(TypedValue.COMPLEX_UNIT_PX, mContext.getResources().getDimensionPixelSize(R.dimen.keyboard_txt_size));
//        okKey.setOnClickListener(mOkKeyOnClickListener);
//    }
//
////    /**
////     * 图层切换监听
////     */
////    private View.OnClickListener switchLayerClickListener = new View.OnClickListener() {
////        @Override
////        public void onClick(View v) {
////            SaltyFishKeyboard.hideKeyboard();
////        }
////    };
//
//    /**
//     * 长按删除监听
//     */
//    private View.OnLongClickListener deleteLongClickListener = new View.OnLongClickListener() {
//        @Override
//        public boolean onLongClick(View v) {
//            Message msg = new Message();
//            msg.arg1 = -1;
//            msg.what = DELETE_AUTO;
//            deleteHandler.sendMessage(msg);
//            return false;
//        }
//    };
//
//    /**
//     * 单个删除监听
//     */
//    private View.OnTouchListener deleteTouchListener = new View.OnTouchListener() {
//        @Override
//        public boolean onTouch(View v, MotionEvent event) {
//            switch (event.getAction()) {
//                case MotionEvent.ACTION_UP:
//                case MotionEvent.ACTION_CANCEL:
//                    deleteHandler.removeCallbacksAndMessages(null);
//                    break;
//            }
//            return false;
//        }
//    };
//
//    /**
//     * 删除放开监听
//     */
//    private View.OnClickListener deleteOnClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            deleteHandler.sendEmptyMessage(DELETE);
//        }
//    };
//
//    /**
//     * 删除对应标识
//     */
//    private final int DELETE = 200;
//    private final int DELETE_AUTO = 400;
//
//    /**
//     * 删除速度
//     */
//    private final int DELETE_SPEED = 50;
//
//    /**
//     * 删除处理
//     */
//    private Handler deleteHandler = new Handler() {
//
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case DELETE:
//                    deleteText();
//                    break;
//                case DELETE_AUTO:
//                    deleteText();
//                    if (msg.arg1 == -1) {
//                        msg.arg1 = DELETE_SPEED;
//                    }
//                    int speed = msg.arg1 > 0 ? --msg.arg1 : 0;
////                    Log.i(SaltyFishKeyboardUtils.TAG, "speed===============>>" + speed);
//                    Message message = obtainMessage();
//                    message.what = DELETE_AUTO;
//                    message.arg1 = speed;
//                    sendMessageDelayed(message, speed);
//                    break;
//            }
//        }
//    };
//
//    /**
//     * 从输入框删除文字
//     */
//    private void deleteText() {
//        EditText currEt = SaltyFishKeyboard.getCurrEditText();
//        ct_submit.setText(null);
//        int currPos = currEt.getSelectionEnd();
//        if (-1 == currPos)
//            return; // 错误
//        if (0 == currPos)
//            return; // 已经删完
//        Editable text = currEt.getText();
//        text.delete(currPos - 1, currPos);
//        currEt.setSelection(currEt.getSelectionEnd());
//    }
//
//    /**
//     * 运算键输入监听
//     */
//    private View.OnClickListener mCalculateKeyOnClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            EditText currEt = SaltyFishKeyboard.getCurrEditText();
//            if (null == currEt) {
//                return;
//            }
//
//            String finalString = currEt.getText().toString();
//            if (TextUtils.isEmpty(finalString)) {
//                return;
//            }
//            char cs = ((Button) v).getText().charAt(0);
//            char lastChar = finalString.charAt(finalString.length() - 1);
//
//            if (cs == '+') {//按下加号
//                if (finalString.contains("+")) {
//                    if (lastChar == '+') {
//                        return;//如果已经存在加号，而且加号在最后一个，则不做任何处理
//                    } else {
//                        finalString = doAddCalculate(finalString) + cs;
//                    }
//                } else if (finalString.contains("-")) {
//                    if (lastChar != '-') {
//                        finalString = doSubCalculate(finalString) + cs;
//                    } else {
//                        finalString = finalString.replace(lastChar, cs);
//                    }
//                } else {
//                    finalString = finalString + cs;
//                }
//            } else if (cs == '-') {//按下减号
//                if (finalString.contains("-")) {
//                    if (lastChar == '-') {
//                        return;//如果已经存在减号，而且加号在最后一个，则不做任何处理
//                    } else {
//                        finalString = doSubCalculate(finalString) + cs;
//                    }
//                } else if (finalString.contains("+")) {
//                    if (lastChar != '+') {
//                        finalString = doAddCalculate(finalString) + cs;
//                    } else {
//                        finalString = finalString.replace(lastChar, cs);
//                    }
//                } else {
//                    finalString = finalString + cs;
//                }
//            }
//            ct_submit.setText(null);
//            currEt.setText(finalString);
//            currEt.setSelection(finalString.length());
//        }
//    };
//
//    /**
//     * 数字按键输入监听
//     */
//    private View.OnClickListener mKeyOnClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            EditText currEt = SaltyFishKeyboard.getCurrEditText();
//            if (null == currEt)
//                return;
//            String text = currEt.getText().toString();
//            if ((text.contains("+") || text.contains("-")) && text.length() > 19) {
//                ct_submit.setText("R.string.money_num_to_large");
//                return;
//            } else if (!(text.contains("+") || text.contains("-")) && text.length() > 9) {
//                ct_submit.setText("R.string.money_num_to_large");
//                return;
//            }
//
//            if (!TextUtils.isEmpty(text) && text.length() >= 4
//                    && text.charAt(text.length() - 3) == '.'
//                    && text.charAt(text.length() - 1) != '+'
//                    && text.charAt(text.length() - 1) != '-') {
//                ct_submit.setText("R.string.Support_only_two_decimal");
//            } else {
//                text = text + ((Button) v).getText();
//                ct_submit.setText(null);
//                currEt.setText(text);
//                currEt.setSelection(text.length());
//            }
//        }
//    };
//
//    /**
//     * 小数点按键输入监听
//     */
//    private View.OnClickListener mDotKeyOnClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            EditText currEt = SaltyFishKeyboard.getCurrEditText();
//            if (null == currEt)
//                return;
//
//            // 在光标之后加入字符
//            String text = currEt.getText().toString();
//            char cs = ((Button) v).getText().charAt(0);
//            if (TextUtils.isEmpty(text)) {
//                text = "0.";
//            } else {
//                char lastChar = text.charAt(text.length() - 1);
//                if (text.contains(String.valueOf(cs))) {
//                    if (lastChar == cs) {
//
//                    } else if (lastChar == '+' || lastChar == '-') {
//                        text = text + "0.";
//                    } else {
//                        text = text + cs;
//                    }
//                } else {
//                    if (lastChar == '+' || lastChar == '-') {
//                        text = text + "0.";
//                    } else {
//                        text = text + cs;
//                    }
//                }
//            }
//            ct_submit.setText(null);
//            currEt.setText(text);
//            currEt.setSelection(text.length());
//        }
//    };
//
//    /**
//     * 完成按键输入监听
//     */
//    private View.OnClickListener mOkKeyOnClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            EditText currEt = SaltyFishKeyboard.getCurrEditText();
//            if (null == currEt)
//                return;
//
//            String text = currEt.getText().toString();
//            if (TextUtils.isEmpty(text)) {
//                SaltyFishKeyboard.hideKeyboard();
//                return;
//            }
//
//            char lastChar = text.charAt(text.length() - 1);
//            if (lastChar == '.') {
//                text = text.substring(0, text.length() - 1);
//            }
//            if (text.contains("+")) {
//                if (lastChar == '+') {
//                    text = text.substring(0, text.length() - 1);
//                } else {
//                    text = doAddCalculate(text);
//                }
//            } else if (text.contains("-")) {
//                if (lastChar == '-') {
//                    text = text.substring(0, text.length() - 1);
//                } else {
//                    text = doSubCalculate(text);
//                }
//            }
//            ct_submit.setText(null);
//            currEt.setText(text);
//            currEt.setSelection(text.length());
//            SaltyFishKeyboard.hideKeyboard();
//        }
//    };
//
//    /**
//     * 简单的加法运算
//     *
//     * @param equation 运算公式
//     * @return 运算结果，保留两位小数
//     */
//    private String doAddCalculate(String equation) {
//        int addIndex = equation.indexOf("+");
//        CharSequence one = equation.subSequence(0, addIndex);
//        CharSequence two = equation.subSequence(addIndex + 1, equation.length());
//        double intOne = 0;
//        double intTwo = 0;
//        try {
//            intOne = Double.parseDouble(one.toString());
//            intTwo = Double.parseDouble(two.toString());
//        } catch (NumberFormatException e) {
//            Toast.makeText(mContext, equation + "mContext.getString(R.string.not_a_num_or_equation)", Toast.LENGTH_SHORT).show();
//        }
//        return String.valueOf(intOne + intTwo);
//    }
//
//    /**
//     * 简单的减法运算
//     *
//     * @param equation 运算公式
//     * @return 运算结果，保留两位小数
//     */
//    private String doSubCalculate(String equation) {
//        int addIndex = equation.indexOf("-");
//        CharSequence one = equation.subSequence(0, addIndex);
//        CharSequence two = equation.subSequence(addIndex + 1, equation.length());
//        double intOne = 0;
//        double intTwo = 0;
//        try {
//            intOne = Double.parseDouble(one.toString());
//            intTwo = Double.parseDouble(two.toString());
//        } catch (NumberFormatException e) {
//            Toast.makeText(mContext, equation + "mContext.getString(R.string.not_a_num_or_equation)", Toast.LENGTH_SHORT).show();
//        }
//        return String.valueOf(Math.abs(intOne - intTwo));
//    }
//}
