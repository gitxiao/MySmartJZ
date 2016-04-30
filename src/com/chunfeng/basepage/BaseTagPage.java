/**
 * 
 */
package com.chunfeng.basepage;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.chunfeng.zhjz.activity.MainActivity;
import com.example.test.R;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @author Cfrjkj
 * @date 2016-4-29
 * @time 上午10:31:31
 * @todo TODO
 */
public class BaseTagPage {
	
	@ViewInject(R.id.btnMenuInBaseContent)
	public ImageButton btnMenuButton;
	
	public MainActivity activity;
	public View root;
	public TextView textView;
	public FrameLayout flLayout;
	public BaseTagPage(MainActivity context) {
		this.activity = context;
		initView();
		initData();
		initEvent();
	}

	/**
	 * 
	 */
	public void initEvent() {
		btnMenuButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				activity.getSlidingMenu().toggle();
			}
		});
	}

	/**
	 * 
	 */
	public void initData() {
	}

	/**
	 * 
	 */
	public void initView() {
		root = View.inflate(activity, R.layout.fragment_main_basecontent, null);
		
		btnMenuButton = (ImageButton) root.findViewById(R.id.btnMenuInBaseContent);
		btnMenuButton.setVisibility(View.GONE);
		
		textView = (TextView)root.findViewById(R.id.textViewInBaseContent);
		
		flLayout = (FrameLayout) root.findViewById(R.id.fl_base_content_tag);
	}
	
	/**
	 * 获取到根布局
	 * @return
	 */
	public View getRoot(){
		return root;
	}
}
