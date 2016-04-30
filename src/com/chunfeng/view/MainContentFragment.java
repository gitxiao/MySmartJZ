/**
 * 
 */
package com.chunfeng.view;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.View.OnAttachStateChangeListener;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.chunfeng.basepage.BaseTagPage;
import com.chunfeng.basepage.GovTagPage;
import com.chunfeng.basepage.HomeTagPage;
import com.chunfeng.basepage.NewsTagPage;
import com.chunfeng.basepage.SetTagPage;
import com.chunfeng.basepage.SmartTagPage;
import com.chunfeng.utils.MyViewPager;
import com.example.test.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @author Cfrjkj
 * @创建时间 2016年4月28日 上午11:54:02
 * @描述 主界面的fragment
 * @svn提交者:$Author$
 * @提交时间: $Date$
 * @当前版本: $Rev$
 */
public class MainContentFragment extends BaseFragment {
	
	private List<BaseTagPage> pages = new ArrayList<BaseTagPage>();
	
	@ViewInject(R.id.vp_pages_main)  
	private MyViewPager viewPager;	//使用了XUtils开源插件,这里的注解已经初始化了viewPager, 不需要再用findViewById去获取控件对象.
	
	@ViewInject(R.id.radioGroup_main)
	private RadioGroup rGroup;  	//单选按钮
	
	@ViewInject(R.id.mainContentView)
	private ViewGroup mainViewGroup;
	
	@Override
	public View initView() {
		
		View root = View.inflate(getConainerActivity(), R.layout.activity_main_fm_content_view, null);
		
		//动态注入  使用xutils开源框架
		ViewUtils.inject(this, root);
		
		//第一个按钮默认被选中, 在布局文件中设置属性checked=true也可以
//		RadioButton rButton = (RadioButton) rGroup.getChildAt(0);
//		rButton.setChecked(true);
		
//		rGroup.check(0);		或者直接这样设置第几个选中也可以
		return root;
	}

	
	@SuppressLint("NewApi")
	public void initEvent() {
		rGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup radioGroup, int id) {
				int pageIndex = -1;
				switch (id) {
				case R.id.mainBtnHome:
					pageIndex = 0;
					break;
				case R.id.mainBtnNews:
					pageIndex = 1;
					break;
				case R.id.mainBtnSmart:
					pageIndex = 2;
					break;
				case R.id.mainBtnGov:
					pageIndex = 3;
					break;
				case R.id.mainBtnSet:
					pageIndex = 4;
					break;

				default:
					break;
				}
				switchPage(pageIndex);
			}

		});
		
		rGroup.addOnAttachStateChangeListener(new OnAttachStateChangeListener() {
			
			@Override
			public void onViewDetachedFromWindow(View arg0) {
System.out.println("onViewDetachedFromWindow---------------------------------------------");
			}
			
			@Override
			public void onViewAttachedToWindow(View arg0) {
System.out.println("onViewAttachedToWindow+++++++++++++++++++++++++++++++++++++++++++++++");
			}
		});
	}

	/**
	 * 选中的页面显示
	 * @param pageIndex
	 */
	private void switchPage(int pageIndex) {
//		View view1 = mainViewGroup.getRootView();
//		mainViewGroup.removeView(view1);
//		BaseTagPage btp = pages.get(pageIndex);		//取页面要从pageList中取, 而不是ViewPager中
//		View view2 = btp.getRoot();
//		mainViewGroup.addView(view2); 				//不能用addView,addView是在原view基础上添加, 应该是替换,但是没有对应的函数...
		
//		viewPager.setCurrentItem(pageIndex, false);
		viewPager.setCurrentItem(pageIndex);
		
		
		//如果是第一页或最后一页, 不让左侧菜单显示出来
		if(pageIndex == 0 || pageIndex == pages.size() - 1) {
			getConainerActivity().getSlidingMenu().setSlidingEnabled(false);
			getConainerActivity().getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		}else{
			getConainerActivity().getSlidingMenu().setSlidingEnabled(true);
			getConainerActivity().getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		}
//		.setSlidingEnabled(false);
	}
	
	
	public void initData(){
		pages.add(new HomeTagPage(getConainerActivity()));
		pages.add(new NewsTagPage(getConainerActivity()));
		pages.add(new SmartTagPage(getConainerActivity()));
		pages.add(new GovTagPage(getConainerActivity()));
		pages.add(new SetTagPage(getConainerActivity()));
		
		MyAdapter adapter = new MyAdapter();
		
		viewPager.setAdapter(adapter);
//		viewPager.setOffscreenPageLimit(0); // 设置预加载页面数量为0,无效
//		viewPager.setOffscreenPageLimit(1); // 设置预加载页面数量为1,无效
//		viewPager.setOffscreenPageLimit(2);
		
		System.out.println("pages.getClass().getClassLoader() " + pages.getClass().getClassLoader());
		System.out.println("pages.getClass().getName() " + pages.getClass().getName());
		System.out.println("pages instanceof List " + (pages instanceof List));
	}
	
	private class MyAdapter extends PagerAdapter{

		@Override
		public int getCount() {
			// 返回数据的个数
			return pages.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// 过滤和缓存的作用
			return arg0 == arg1;
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
System.out.println("销毁页面destroyItem position = " + position);
			container.removeView((View) object);
//			destroyItem(container, position, object); 
		}
		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
System.out.println("初始化页面 instantiateItem position = " + position);
			BaseTagPage baseTagPage = pages.get(position);
			View rootView = baseTagPage.getRoot();
			baseTagPage.initData();		//页面显示时才初始化数据, 而不是创建对象的时候
			container.addView(rootView);
			return rootView;
//			return instantiateItem(container, position);
		}
	}
	
}
