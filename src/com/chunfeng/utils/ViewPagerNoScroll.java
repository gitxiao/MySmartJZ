/**
 * 
 */
package com.chunfeng.utils;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * @author Cfrjkj
 * @date 2016-4-29
 * @time 下午9:12:10
 * @todo 不能滑动的ViewPager, ViewPager的父类是ViewGroup,有事件分发功能dispathc, 禁用这个功能就能实现不能滑动
 */
public class ViewPagerNoScroll extends MyViewPager {

	/**
	 * @param context
	 */
	public ViewPagerNoScroll(Context context) {
		super(context);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public ViewPagerNoScroll(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	
	/**
	 * ViewGroup里面的onInterceptTouchEvent 默认返回false, 回把事件丢给view去处理, 如果在这里返回true, 表示group已经处理事件, 不回传递给下面的view
	 * 这样ViewPager中的各个pager就不能获取事件了, 也就不能滑动了
	 * 
	 * 上面这个解释是有问题的. onInterceptTouchEvent返回值改为true对ViewPager是否能滑动好像不起作用, 真正起作用的是onTouchEvent返回值改为false
	 */
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		return super.onInterceptTouchEvent(ev);
//		return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		return true;
	}
	
}
