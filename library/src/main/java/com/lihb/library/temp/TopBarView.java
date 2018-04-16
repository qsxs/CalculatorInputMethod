package com.lihb.library.temp;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * headerBar
 * Created by zhengwenjie on 2015/8/21.
 */
public class TopBarView extends LinearLayout {

    private View parentView;
    private ImageButton imgBtnBack;
    private ImageButton ib_left_second;
    private ImageButton imgBtnRight;
    private ImageButton imgBtnRightSecond;
    private TextView txtLeft;
    private TextView txtTitle;
    private TextView txtRight;

    private View bottom_line;

    public TextView getTxtRight() {
        return txtRight;
    }

    public TextView getTxtTitle() {
        return txtTitle;
    }

    private void assignViews(View view) {
//        imgBtnBack = (ImageButton) view.findViewById(R.id.imgBtn_back);
//        ib_left_second = (ImageButton) view.findViewById(R.id.ib_left_second);
//        txtLeft = (TextView) view.findViewById(R.id.txt_left);
//        txtTitle = (TextView) view.findViewById(R.id.txt_title);
//        txtRight = (TextView) view.findViewById(R.id.txt_right);
//        imgBtnRight = (ImageButton) view.findViewById(R.id.imgBtn_right);
//        bottom_line = view.findViewById(R.id.bottom_line);
//        imgBtnRightSecond = (ImageButton) view.findViewById(R.id.imgBtn_right_second);
    }


    public TopBarView(Context context) {
        super(context);
        init(context);
    }

    public TopBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TopBarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }


    private void init(final Context context) {
//        parentView = View.inflate(context, R.layout.include_top_bar, null);
        assignViews(parentView);
        addView(parentView);
        imgBtnBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = (Activity) context;
                if (activity != null) {
                    activity.finish();
                }
            }
        });
    }

    public void config(String title) {
        this.config(title, true);
    }

    public void config(String title, boolean isBack) {
        if (isBack) {
            imgBtnBack.setVisibility(View.VISIBLE);
        } else {
            imgBtnBack.setVisibility(View.INVISIBLE);
        }
        if (title == null) {
            title = "";
        }
        txtTitle.setText(title);
    }


    /**
     * 左边按钮实现
     *
     * @param leftListener
     */
    public void configLeft(OnClickListener leftListener) {
        imgBtnBack.setVisibility(View.VISIBLE);
        if (leftListener != null) {
            imgBtnBack.setOnClickListener(leftListener);
        }
    }


    /**
     * 左边按钮隐藏
     */
    public void configLeftVisible(int i) {
        imgBtnBack.setVisibility(i);
    }

    /**
     * 右边文字按钮隐藏
     */
    public void configRightTxtVisible(int i) {
        txtRight.setVisibility(i);
    }

    /**
     * 左边按钮实现
     *
     * @param resId
     * @param leftListener
     */
    public void configLeft(int resId, OnClickListener leftListener) {
        imgBtnBack.setVisibility(View.VISIBLE);
        imgBtnBack.setImageResource(resId);
        if (leftListener != null) {
            imgBtnBack.setOnClickListener(leftListener);
        }
    }

    /**
     * 左边文字按钮实现
     *
     * @param leftStr
     * @param leftListener
     */
    public void configLeftTxt(String leftStr, OnClickListener leftListener) {
        imgBtnBack.setVisibility(View.INVISIBLE);
        txtLeft.setVisibility(View.VISIBLE);
        txtLeft.setText(leftStr);
        if (leftListener != null) {
            txtLeft.setOnClickListener(leftListener);
        }
    }


    /**
     * 右边按钮实现
     *
     * @param resId
     * @param rightListener
     */
    public void configRight(int resId, OnClickListener rightListener) {
        imgBtnRight.setVisibility(View.VISIBLE);
        imgBtnRight.setImageResource(resId);
        if (rightListener != null) {
            imgBtnRight.setOnClickListener(rightListener);
        }
    }

    /**
     * 右边按钮实现
     *
     * @param resId
     * @param rightSecondListener
     */
    public void configRightSecond(int resId, OnClickListener rightSecondListener) {
        ib_left_second.setVisibility(INVISIBLE);//防止标题不居中
        imgBtnRightSecond.setVisibility(View.VISIBLE);
        imgBtnRightSecond.setImageResource(resId);
        if (rightSecondListener != null) {
            imgBtnRightSecond.setOnClickListener(rightSecondListener);
        }
    }

    /**
     * 右边文字按钮实现
     *
     * @param rightStr
     * @param rightListener
     */
    public TextView configRightTxt(String rightStr, OnClickListener rightListener) {
        txtRight.setVisibility(View.VISIBLE);
        txtRight.setText(rightStr);
        if (rightListener != null) {
            txtRight.setOnClickListener(rightListener);
        }
        return txtRight;
    }

    /**
     * 右边文字按钮实现
     *
     * @param rightStr
     * @param rightListener
     */
    public void configRightTxt(String rightStr, OnClickListener rightListener, int drawbleId) {
        txtRight.setVisibility(View.VISIBLE);
        Drawable drawable = getResources().getDrawable(drawbleId);
        /// 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        txtRight.setCompoundDrawables(drawable, null, null, null);
        txtRight.setText(rightStr);
        if (rightListener != null) {
            txtRight.setOnClickListener(rightListener);
        }
    }

    /**
     * 右边文字按钮
     *
     * @param rightStr
     */
    public void configRightTxt(String rightStr) {
        txtRight.setVisibility(View.VISIBLE);
        txtRight.setText(rightStr);
    }

    /**
     * 中间主要title的实现
     *
     * @param title
     */
    public void configcintent(String title) {
        txtTitle.setText(title);
    }

    public void configLeftSecondButton(int resId) {
        if (ib_left_second != null) {
            ib_left_second.setImageResource(resId);
        }
    }

    public void configLeftSecondButton(int resId, OnClickListener leftSecondListener) {
        if (ib_left_second != null) {
            ib_left_second.setImageResource(resId);
            ib_left_second.setOnClickListener(leftSecondListener);
        }
    }

    public void configLeftSecondButton(OnClickListener leftSecondListener) {
        if (ib_left_second != null) {
            ib_left_second.setOnClickListener(leftSecondListener);
        }
    }

    public void setLeftSecondButtonVisible(boolean visible) {
        if (ib_left_second != null) {
            if (visible) {
                ib_left_second.setVisibility(View.VISIBLE);
            } else {
                ib_left_second.setVisibility(View.GONE);
            }
        }
    }


    public void hideLine() {
        bottom_line.setVisibility(GONE);
    }

    public void config(int bgColor, boolean isHideLine, int titleColor) {
        configBgColor(bgColor);
        configTitleColor(titleColor);
        if (isHideLine) {
            hideLine();
        }

    }

    public void configBgColor(int bgColor) {
        parentView.setBackgroundColor(bgColor);
    }

    public void configTitleColor(int titleColor) {
        txtTitle.setTextColor(titleColor);
    }

    public void config(String title, int titleColor) {
        config(title);
        configTitleColor(titleColor);
    }

    public void config(String title, boolean isHideLine, int titleColor, int bgColor) {
        config(bgColor, isHideLine, titleColor);
        config(title);
    }

    public void config(String title, boolean isBack, int titleColor) {
        config(title, isBack);
        configTitleColor(titleColor);
    }

    public void configRightTxt(String rightStr, int textColor) {
        configRightTxt(rightStr);
        txtRight.setTextColor(textColor);
    }

    public void configRightTxt(String rightStr, OnClickListener onClickListener, int drawbleId, int textColor) {
        configRightTxt(rightStr, onClickListener, drawbleId);
        txtRight.setTextColor(textColor);
    }

    public void configTextColor(int textColor) {
        txtLeft.setTextColor(textColor);
        txtRight.setTextColor(textColor);
        configTitleColor(textColor);
    }

    public void configLeftTxt(String leftStr, OnClickListener onClickListener, int textColor) {
        configLeftTxt(leftStr, onClickListener);
        txtLeft.setTextColor(textColor);
    }

    public void configText(int textColor, String title, String txtLeft, String txtRight) {
        configTextColor(textColor);
        config(title);
        configLeftTxt(txtLeft, null);
        configRightTxt(txtRight);
    }

    public ImageButton getImgBtnRightSecond() {
        return imgBtnRightSecond;
    }

    public ImageButton getImgBtnRight() {
        return imgBtnRight;
    }

    public ImageButton getIb_left_second() {
        return ib_left_second;
    }
}
