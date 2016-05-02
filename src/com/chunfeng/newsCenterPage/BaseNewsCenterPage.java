/**
 * 
 */
package com.chunfeng.newsCenterPage;

import android.view.View;

import com.chunfeng.zhjz.activity.MainActivity;

/**
 * @author Cfrjkj
 * @date 2016-5-2
 * @time 上午8:54:50
 * @todo 新闻中心各个子页面的基类, 新闻,专题,组图,互动4个页面都继承自这个类
 */
public abstract class BaseNewsCenterPage {

	protected MainActivity mainActivity; //上下文activity
	private View rootView;
	
	
	public BaseNewsCenterPage(MainActivity activity){
		mainActivity = activity;
		rootView = initView();
//		initData();		不在这里调用, 会浪费流量, 当用户需要时通过事件触发让用户自己调用
		initEvent();
	}

	public View getView(){
		return rootView;
	}
	
	/**
	 * 
	 */
	protected void initData() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 
	 */
	protected void initEvent() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 此方法子类必须覆盖, 所以声明为抽象的
	 */
	protected abstract View initView();
	
}
