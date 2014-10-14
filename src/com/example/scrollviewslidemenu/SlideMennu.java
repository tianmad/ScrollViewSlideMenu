package com.example.scrollviewslidemenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.nineoldandroids.view.ViewHelper;

public class SlideMennu extends HorizontalScrollView {

	/**
	 * 屏幕宽度
	 */
	private int screenWidth;
	/**
	 * 菜单边距
	 */
	private int menu_padding_right = 100;
	/**
	 * 菜单1宽度
	 */
	private int menu_width;
	/**
	 * 菜单触发临界点
	 */
	private int menu_halfwidth;

	private boolean once;

	private boolean isOpen;
	private ViewGroup mLeftMenu;
	private ViewGroup mContent;
	private ViewGroup mRightMenu;
	boolean isOperateRight, isOperateLeft;
	private OnMenuOpenListener menulistener;

	public void setOnMenuOpenListener(OnMenuOpenListener listener) {
		this.setOnMenuOpenListener(listener);
	}

	public SlideMennu(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		screenWidth = getScreenWidth(context);
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
				R.styleable.slidemenu, defStyle, 0);
		int n = a.getIndexCount();
		for (int i = 0; i < n; i++) {
			int attr = a.getIndex(i);
			switch (attr) {
			case R.styleable.slidemenu_menuPaddingRight:
				// 默认50
				menu_padding_right = a.getDimensionPixelSize(attr,
						(int) TypedValue.applyDimension(
								TypedValue.COMPLEX_UNIT_DIP, 50f,
								getResources().getDisplayMetrics()));// 默认为10DP
				break;
			}
		}
		a.recycle();
	}

	public SlideMennu(Context context, AttributeSet attrs) {
		this(context, attrs, 0);

	}

	public SlideMennu(Context context) {
		this(context, null, 0);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if (!once) {
			LinearLayout wrap = (LinearLayout) getChildAt(0);
			mLeftMenu = (ViewGroup) wrap.getChildAt(0);
			mContent = (ViewGroup) wrap.getChildAt(1);
			mRightMenu = (ViewGroup) wrap.getChildAt(2);

			menu_width = screenWidth - menu_padding_right;
			menu_halfwidth = menu_width / 2;
			mLeftMenu.getLayoutParams().width = menu_width;
			mRightMenu.getLayoutParams().width = menu_width;
			mContent.getLayoutParams().width = screenWidth;
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		super.onLayout(changed, l, t, r, b);
		if (changed) {
			this.scrollTo(menu_width, 0);
			once = true;
		}
	}

	/**
	 * 获得屏幕高度
	 * 
	 * @param context
	 * @return
	 */
	public static int getScreenWidth(Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.widthPixels;
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		if (l > menu_width) {
			isOperateRight = true;
			isOperateLeft = false;
		} else {
			isOperateRight = false;
			isOperateLeft = true;
		}
//		float scale = l * 1.0f / menu_width;
//		float leftScale = 1 - 0.3f * scale;
//		float rightScale = 0.8f + scale * 0.2f;
//
//		ViewHelper.setScaleX(mLeftMenu, leftScale);
//		ViewHelper.setScaleY(mLeftMenu, leftScale);
//		ViewHelper.setAlpha(mLeftMenu, 0.6f + 0.4f * (1 - scale));
//		ViewHelper.setTranslationX(mLeftMenu, menu_width * scale * 0.7f);
//
//		ViewHelper.setPivotX(mContent, 0);
//		ViewHelper.setPivotY(mContent, mContent.getHeight() / 2);
//		ViewHelper.setScaleX(mContent, rightScale);
//		ViewHelper.setScaleY(mContent, rightScale);
	}

	boolean isLeftMenuOpen;

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		int action = ev.getAction();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			break;
		case MotionEvent.ACTION_UP:
			int scrollX = getScrollX();
			System.out.println("scrollX="+scrollX);
			System.out.println("scrollY=="+getScrollY());
			if (isOperateLeft) {
				if (scrollX > menu_halfwidth) {
					this.smoothScrollTo(menu_width, 0);
					if (isLeftMenuOpen && menulistener != null) {
						menulistener.onMenuOpen(false, 0);
					}
					isLeftMenuOpen = false;
					isOpen = false;
				} else {
					this.smoothScrollTo(0, 0);
					if (isLeftMenuOpen && menulistener != null) {
						menulistener.onMenuOpen(true, 0);
					}
					isLeftMenuOpen = true;
					isOpen = true;
				}
			}
			if (isOperateRight) {
				if (scrollX > menu_halfwidth + menu_width) {
					this.scrollTo(menu_width + menu_width, 0);
				} else {
					this.scrollTo(menu_width, 0);
				}
			}
			return true;
		}
		return super.onTouchEvent(ev);
	}

	public void openMenu() {
		if (isOpen) {
			return;
		}
		this.smoothScrollTo(0, 0);
		isOpen = true;
	}

	public void closeMenu() {
		if (isOpen) {
			this.smoothScrollTo(menu_width, 0);
			isOpen = false;
		}

	}

	public interface OnMenuOpenListener {
		void onMenuOpen(boolean isOpen, int which);
	}

	/**
	 * 切换菜单状态
	 */
	public void toggle() {
		if (isOpen) {
			closeMenu();
		} else {
			openMenu();
		}
	}

}
