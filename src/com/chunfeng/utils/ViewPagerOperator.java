///**
// * 
// */
//package com.chunfeng.utils;
//
//import java.util.ArrayList;
//import java.util.List;
//import com.example.test.R;
//import android.app.Activity;
//import android.support.v4.view.PagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//
///**
// * @author Cfrjkj
// * @创建时间 2016年4月28日 下午3:12:27
// * @描述 从MainActivity中抽离出来的ViewPagers处理类,避免MainActivity中的代码太繁琐
// * @svn提交者:$Author$
// * @提交时间: $Date$
// * @当前版本: $Rev$
// */
//public class ViewPagerOperator {
//	
//	private Activity activity;
//	private ViewPager vPager;
//	private MyAdapter adapter;
//	private List<View> viewList;
//	public ViewPagerOperator(Activity activity){
//		this.activity = activity;
//	}
//	
//	public void initView(int viewId){
//		//ViewPager组件
//		vPager = (ViewPager) activity.findViewById(viewId);
//	}
//	
//	public void initData(View[] views){
//		//ViewPager list容器
//		viewList = new ArrayList<View>();
//		//设置灰色点的大小
//		for (View view : views) {
//			viewList.add(view);
//		}
//		
//		//ViewPager的适配器
//		adapter = new MyAdapter();
//		vPager.setAdapter(adapter);
//		System.out.println("GuideActivity initData 2");
//	}
//	
//	public void initEvent(){
//		
//	}
//	
//	private class MyAdapter extends PagerAdapter{
//
//		@Override
//		public int getCount() {
//			// 返回数据的个数
//			return viewList.size();
//		}
//
//		@Override
//		public boolean isViewFromObject(View arg0, Object arg1) {
//			// 过滤和缓存的作用
//			System.out.println("isViewFromObject arg0 == arg1 = " + (arg0 == arg1));
//			return arg0 == arg1;
//		}
//		
//		@Override
//		public void destroyItem(ViewGroup container, int position, Object object) {
//			// TODO Auto-generated method stub
//			System.out.println("destroyItem position = " + position);
//			container.removeView((View) object);
////			super.destroyItem(container, position, object); 
//		}
//		
//		@Override
//		public Object instantiateItem(ViewGroup container, int position) {
//			// TODO Auto-generated method stub
//			System.out.println("instantiateItem position = " + position);
//			View child = (View)viewList.get(position);
//			container.addView(child );
//			return child;
////			return super.instantiateItem(container, position);
//		}
//	}
//}
