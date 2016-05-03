/**
 * 
 */
package com.chunfeng.newsChildPage;

import android.view.View;

import com.chunfeng.zhjz.activity.MainActivity;
import com.example.test.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @author Cfrjkj
 * @date 2016-5-3
 * @time 上午11:34:30
 * @todo TODO
 */
public class VPINewsChildPage {

	private View layoutNewsContentMain;
	
	private MainActivity mainActivity;
	
	
	public VPINewsChildPage(MainActivity mainActivity){
		this.mainActivity = mainActivity;
		layoutNewsContentMain = initView();
		initEvent();
	}
	
	
	public View getRootView(){
		return layoutNewsContentMain;
	}
	
	/**
	 * 
	 */
	public View initView() {
		View view = View.inflate(mainActivity, R.layout.news_pager_content, null);
		ViewUtils.inject(this, view);
		return view;
	}

	/**
	 * 
	 */
	public void initData(){
		
	}
	
	/**
	 * 
	 */
	public void initEvent() {
		
	}
}
