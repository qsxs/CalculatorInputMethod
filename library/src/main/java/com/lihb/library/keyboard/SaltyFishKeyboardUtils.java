package com.lihb.library.keyboard;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.PopupWindow;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

class SaltyFishKeyboardUtils {

	public static final String TAG = "SaltyFish";

	/**
	 * 获取一个视图的(left,top) 点, 在屏幕上的 位置坐标
	 * 
	 * @param view
	 * @return
	 */
	static int getViewTopAtScreen(View view) {
		int[] viewLocation = new int[2];
		view.getLocationOnScreen(viewLocation);
		return viewLocation[1];
	}

	/**
	 * 计算无调节情况下, 视图的左上坐标@屏幕坐标系
	 * 
	 * @param v
	 * @return
	 */
	static int calcViewPositionWithoutAdjust(View v) {
		Activity act = getActivity(v);
		if (null == act)
			return 0;
		ViewGroup contentView = (ViewGroup) act.getWindow().getDecorView().findViewById(android.R.id.content);
		View rootView = contentView.getChildAt(0);
		int currAdjustValue = getViewTopAtScreen(rootView);// 当前已经调整的位置
		int currViewPosi = getViewTopAtScreen(v);// 当前视图位置
		return currViewPosi - currAdjustValue;
	}

	/**
	 * 根据系统版本处理自定义键盘光标问题
	 */
	static void hideSystemBoard(EditText focusEt) {
		Context ctx = focusEt.getContext();
		if (ctx instanceof Activity) {
			Activity act = (Activity) ctx;
			if (act.isChild()) {
				while (act.isChild()) {
					act = act.getParent();
				}
				ctx = act;
			}
		}

		if (android.os.Build.VERSION.SDK_INT <= 10) {
			InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(focusEt.getWindowToken(), 0);
		} else {
			InputMethodManager imm = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(focusEt.getWindowToken(), 0);
			try {
				Class<EditText> cls = EditText.class;
				Method setSoftInputShownOnFocus;
				setSoftInputShownOnFocus = cls.getMethod("setSoftInputShownOnFocus", boolean.class);
				setSoftInputShownOnFocus.setAccessible(true);
				setSoftInputShownOnFocus.invoke(focusEt, false);
			} catch (Exception e) {
				// e.printStackTrace();
			}
			try {
				Class<EditText> cls = EditText.class;
				Method setShowSoftInputOnFocus;
				setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus", boolean.class);
				setShowSoftInputOnFocus.setAccessible(true);
				setShowSoftInputOnFocus.invoke(focusEt, false);
			} catch (Exception e) {
				// e.printStackTrace();
			}
		}
	}

	/**
	 * 通过反射显示光标 只能是默认颜色
	 * 
	 * @param currEt
	 */
	public static void setCursorVisible(EditText currEt) {
		// 设置显示光标
		currEt.setCursorVisible(true);
		try {
			Field cursorField = currEt.getClass().getSuperclass().getDeclaredField("mCursorDrawableRes");
			cursorField.setAccessible(true);
			cursorField.set(currEt, 0);
		} catch (Exception e) {
			Log.w(TAG, "mCursorDrawableRes set failure");
		}
	}

	public static void updatePopuWindowViewGroup(PopupWindow popupWindow, View contentView) {
		try {
			Field popuWindowField = popupWindow.getClass().getDeclaredField("mWindowManager");
			popuWindowField.setAccessible(true);
			WindowManager wm = (WindowManager) popuWindowField.get(popupWindow);
			popuWindowField = popupWindow.getClass().getDeclaredField("mDecorView");
			popuWindowField.setAccessible(true);
			ViewGroup mDecorView= (ViewGroup) popuWindowField.get(popupWindow);
			mDecorView.removeAllViews();
			WindowManager.LayoutParams p = (WindowManager.LayoutParams) mDecorView.getLayoutParams();
			mDecorView.addView(contentView);
			wm.updateViewLayout(mDecorView,p);
		} catch (Exception e) {
			Log.w(TAG, "get mPopupView failure");
		}
	}

	/**
	 * 从Popuwindow中获取cintainer
	 * 
	 * @param popupWindow
	 * @return
	 */
	public static ViewGroup getPopuWindowViewGroup(PopupWindow popupWindow) {

		ViewGroup viewGroup = null;
		try {
			Field popuWindowField = popupWindow.getClass().getDeclaredField("mPopupView");
			popuWindowField.setAccessible(true);
			viewGroup = (ViewGroup) popuWindowField.get(popupWindow);
			viewGroup.removeAllViews();
		} catch (Exception e) {
			Log.w(TAG, "get mPopupView failure");
		}

		return viewGroup;
	}

	/**
	 * 调整布局 避免输入框被遮挡
	 * 
	 * @param v
	 *            当前输入框
	 * @param h
	 *            需要调整的高度
	 */
	static void adjustLayout(View v, int h) {
		Activity act = getActivity(v);

		if (null == act)
			return;
		View contentView = act.getWindow().getDecorView().findViewById(android.R.id.content);
		if (h > 0)
			return;
		ObjectAnimator animator = ObjectAnimator.ofFloat(contentView, "TranslationY", h).setDuration(250);
		animator.setInterpolator(new DecelerateInterpolator());
		animator.start();
	}

	/**
	 * 恢复布局
	 * 
	 * @param v
	 */
	static void restoreLayout(View v) {
		Activity act = getActivity(v);
		if (null == act)
			return;
		View contentView = act.getWindow().getDecorView().findViewById(android.R.id.content);
		ObjectAnimator animator = ObjectAnimator.ofFloat(contentView, "TranslationY", 0).setDuration(250);
		animator.setInterpolator(new DecelerateInterpolator());
		animator.start();

	}

	/**
	 * 获取调节高度大小
	 *
	 * @param
	 * @return
	 */
	static int getAdjustValue(View keyboardView, EditText et) {
		Activity act = getActivity(et);
		if (null == act)
			return 0;
		ViewGroup contentView = (ViewGroup) act.getWindow().getDecorView().findViewById(android.R.id.content);

		int availHeight = contentView.getHeight();// 整个Activity 视图的高度

		int etHeight = et.getHeight();// 输入框高度
		int etTop = SaltyFishKeyboardUtils.calcViewPositionWithoutAdjust(et);// 输入框在整个视图中的高度

		int etBtmCoord = etTop + etHeight;// et的baseline坐标
		int etToBtm = availHeight - etBtmCoord;// et下的空间

		// 如果etToBtm > popupwindow的高度,则不需调整,否则调整
		int keyboardHeight = keyboardView.getHeight();
		if (0 == keyboardHeight) {
			// 初始化:测量高度
			int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
			int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
			keyboardView.measure(w, h);
			keyboardHeight = keyboardView.getMeasuredHeight();
		}
		return etToBtm - keyboardHeight;

	}

	private static Activity getActivity(View view) {
		Context context = view.getContext();

		while (context instanceof ContextWrapper) {
			if (context instanceof Activity) {
				return  (Activity)context;
			}
			context = ((ContextWrapper)context).getBaseContext();
		}

		return null;
	}

}
