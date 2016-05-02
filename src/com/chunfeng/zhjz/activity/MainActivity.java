/**
 * 
 */
package com.chunfeng.zhjz.activity;

import com.chunfeng.utils.DensityUtil;
import com.chunfeng.utils.ViewPagerOperator;
import com.chunfeng.view.LeftMenuFragment;
import com.chunfeng.view.MainContentFragment;
import com.example.test.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;

/**
 * @author Cfrjkj
 * @date 2016-4-27
 * @time 下午3:41:21
 * @todo TODO
 */
public class MainActivity extends SlidingFragmentActivity{
	
	public static final String TAG_LEFT_MENU = "leftMenu_fragment";
	public static final String TAG_MAIN_CONTENT = "mainContent_fragment";
	private SlidingMenu leftMenu;
	public MainActivity() {
//		vpo = new ViewPagerOperator(this);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		initView();
		initData();
		initEvent();
	}

	/**
	 * 
	 */
	private void initEvent() {
		// TODO Auto-generated method stub
//		vpo.initEvent();
	}

	/**
	 * 初始化数据
	 */
	private void initData() {
//		FragmentManager fragmentManager = getFragmentManager();
		FragmentManager fragmentManager = getSupportFragmentManager();
		//1.获取事务
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		
		//2.完成替换
		
		//完成主界面的替换,
		//替换掉已经显示的R.layout.activity_main_fm_content_tag中的空布局R.id.fl_main_content
		transaction.replace(R.id.fl_main_content_tag, new MainContentFragment(), TAG_MAIN_CONTENT);

		//完成左侧菜单的替换
		//用LeftMenuFragment替换掉已经显示的左侧空布局R.id.fl_left_menu, 
		//左侧空布局是放置在leftMenu(SliddingMenu)中的, 所以, 替换后LeftMenuFragment就显示在了slidingmenu中
		//或者说activity有了sliddingMenu的属性, 所以其左侧的LeftMenuFragment就能够滑动
		transaction.replace(R.id.fl_left_menu_tag, new LeftMenuFragment(), TAG_LEFT_MENU);
		
		//3.提交事务
		transaction.commit();
		
//		vpo.initData();
	}

	/**
	 * 可以从外部类, 或者从具有MainActivity上下文的类中获取MainActivity中的LeftMenuFragment
	 * @return
	 */
	public LeftMenuFragment getLeftMenuFragment(){
		FragmentManager fragmentManager = getSupportFragmentManager();
		LeftMenuFragment lmf = (LeftMenuFragment) fragmentManager.findFragmentByTag(TAG_LEFT_MENU);
		return lmf;
	}
	
	/**
	 * 可以从外部类, 或者从具有MainActivity上下文的类中获取MainActivity中的LeftMenuFragment
	 * @return
	 */
	public MainContentFragment getMainContentFragment(){
		FragmentManager fragmentManager = getSupportFragmentManager();
		MainContentFragment mcfContentFragment = (MainContentFragment)fragmentManager.findFragmentByTag(TAG_MAIN_CONTENT);
		return mcfContentFragment;
	}
	
	/**
	 * 代码示例
	 * setTitle(R.string.attach);
        // set the content view
        setContentView(R.layout.content);
        // configure the SlidingMenu
        SlidingMenu menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setShadowWidthRes(R.dimen.shadow_width);
        menu.setShadowDrawable(R.drawable.shadow);
        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.menu);
        
               配置文件示例
        <com.jeremyfeinstein.slidingmenu.lib.SlidingMenu
	    xmlns:sliding="http://schemas.android.com/apk/res-auto"
	    android:id="@+id/slidingmenulayout"
	    android:layout_width="fill_parent"
	    android:layout_height="fill_parent"
	    sliding:viewAbove="@layout/YOUR_ABOVE_VIEW"
	    sliding:viewBehind="@layout/YOUR_BEHIND_BEHIND"
	    sliding:touchModeAbove="margin|fullscreen"
	    sliding:behindOffset="@dimen/YOUR_OFFSET"
	    sliding:behindWidth="@dimen/YOUR_WIDTH"
	    sliding:behindScrollScale="@dimen/YOUR_SCALE"
	    sliding:shadowDrawable="@drawable/YOUR_SHADOW"
	    sliding:shadowWidth="@dimen/YOUR_SHADOW_WIDTH"
	    sliding:fadeEnabled="true|false"
	    sliding:fadeDegree="float"
	    sliding:selectorEnabled="true|false"
	    sliding:selectorDrawable="@drawable/YOUR_SELECTOR"/>
	 */
	private void initView() {
		//设置主界面
		this.setContentView(R.layout.activity_main_fm_content_tag);
		
		leftMenu = this.getSlidingMenu();
		
		//设置左侧界面.使用SlidingFragmentActivity时behindView必须在onCreate中设置
		this.setBehindContentView(R.layout.activity_main_fm_left_tag);
		//设置右侧界面
//		menu.setSecondaryMenu(R.layout.activity_main_fm_right);
		
		//设置滑动点的位置 TOUCHMODE_FULLSCREEN全屏,TOUCHMODE_MARGIN边缘,TOUCHMODE_NONE不能拖动
		leftMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN); //全屏滑动
		
		//设置滑动模式LEFT,RIGHT,LEFT_RIGHT
		leftMenu.setMode(SlidingMenu.LEFT);

		//设置滑动距离
//		leftMenu.setAboveOffset(DensityUtil.dip2px(this, 5));
		leftMenu.setBehindOffset(DensityUtil.dip2px(this, 250));	//右侧主界面剩余显示宽度
//		leftMenu.setBehindOffset(DensityUtil.dip2px(this, 300));
//		leftMenu.setBehindOffset(700);
		
		leftMenu.setSlidingEnabled(false);			//开始默认在主界面, 不能划出左侧菜单
		
//        menu.setFadeDegree(0.35f);
//        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
//		vpo.initView();
	}
	
	
}
