/**
 * 
 */
package com.chunfeng.basepage;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.chunfeng.dataLogic.NewsCenterData;
import com.chunfeng.newsCenterPage.BaseNewsCenterPage;
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
	
	public MainActivity activity;

	//父类中抽取公共部分>>>
	@ViewInject(R.id.btnMenuInBaseContent)
	public ImageButton btnMenuButton;
	
	public View root;
	public TextView textTitle;
	//父类中抽取公共部分<<<
	
	public FrameLayout flLayout;		//页面中不同部分, 先初始化一张白纸
	
	public BaseTagPage(MainActivity context) {
		this.activity = context;
		initView();
		//不能在这里初始化数据, 因为当各子类页面对象创建的时候就会自动调用了,会浪费流量
		//应该在各个子类页面初始化的时候调用, 是否初始化由ViewPager(在MainContentFragment类中)的适配器决定 
//		initData();  		
		initEvent();
		initPrivateView();
	}

	/**
	 * 初始化各个页面的私有内容
	 */
	private void initPrivateView() {
		
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
		
		textTitle = (TextView)root.findViewById(R.id.textTitleInBaseContent);
		
		flLayout = (FrameLayout) root.findViewById(R.id.fl_base_content_tag);
	}
	
	/**
	 * 获取到根布局
	 * @return
	 */
	public View getRoot(){
		return root;
	}
	
	/**
	 * 控制个界面中子页面的显示, 可以在类外调用, 当在LeftMenuFragment中选择左侧按钮时调用这个函数来控制右侧内容的显示
	 * @param position
	 */
	public void switchPageFromSuper(int position){
		System.out.println("子类没有覆盖这个函数");
	}
	
}
