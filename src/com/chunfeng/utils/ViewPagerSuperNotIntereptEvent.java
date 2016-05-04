/**
 * 
 */
package com.chunfeng.utils;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewParent;

/**
 * @author Cfrjkj
 * @date 2016-4-29
 * @time 下午9:12:10
 * @todo 可以设置父组件不拦截事件的ViewPager
 */
public class ViewPagerSuperNotIntereptEvent extends ViewPager {


	private float downX;
	private float downY;
//	private float moveX;
//	private float moveY;


	/**
	 * @param context
	 */
	public ViewPagerSuperNotIntereptEvent(Context context) {
		super(context);
//		requestParentDisallowInterceptTouchEvent(true);
	}
	
	public ViewPagerSuperNotIntereptEvent(Context context, AttributeSet attrs) {
        super(context, attrs);
//        requestParentDisallowInterceptTouchEvent(true); 		//不起作用, 在这里时,parent为空
    }
	
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		//事件完全由自己控制
		getParent().requestDisallowInterceptTouchEvent(true);
		//其他情况都不让父控件拦截
		switch(ev.getAction()){
		case MotionEvent.ACTION_DOWN:
			downX = ev.getX();
			downY = ev.getY();
			
			break;
		case MotionEvent.ACTION_MOVE:
			float moveX = ev.getX();
			float moveY = ev.getY();
			float offX = Math.abs(moveX - downX);
			float offY = Math.abs(moveY - downY);
			if(offX > offY){
				//横向移动
				if(this.getCurrentItem() == 0 && moveX > downX){
					//在第一个页面时,从左往右滑由父控件拦截
					getParent().requestDisallowInterceptTouchEvent(false);
				}else if(this.getCurrentItem() == getAdapter().getCount() - 1 && moveX < downX){
					//在最后一个页面时, 从右往左滑时,父控件拦截
					getParent().requestDisallowInterceptTouchEvent(false);
				}
			}else {
				//纵向移动,父控件拦截
				getParent().requestDisallowInterceptTouchEvent(false);
			}
			break;
		}
		
		return super.dispatchTouchEvent(ev);
	}

	
	
	
	/**
	 * 申请父组件不要拦截事件处理, 交给viewPager本身来处理,
	 * (这样写不起作用???, 不管是在构造函数中调用(parent为空), 还是在设置完适配器后调用(parent!=null) 都不起作用)
	 * @param disallowIntercept
	 */
	public void requestParentDisallowInterceptTouchEvent(boolean disallowIntercept) {
        final ViewParent parent = getParent();
        System.out.println("requestParentDisallowInterceptTouchEvent----------------------------------------parent = " + parent);
        if (parent != null) {
        	System.out.println("申请父组件不要拦截事件处理, 交给viewPager本身来处理-----------------------------------------");
            parent.requestDisallowInterceptTouchEvent(disallowIntercept);
        }
    }

}
