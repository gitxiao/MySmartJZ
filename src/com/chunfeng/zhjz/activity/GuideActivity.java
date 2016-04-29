/**
 * 
 */
package com.chunfeng.zhjz.activity;

import java.util.ArrayList;
import java.util.List;

import com.chunfeng.utils.DensityUtil;
import com.chunfeng.utils.MyConstants;
import com.chunfeng.utils.Sptools;
import com.example.test.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

/**
 * @author Cfrjkj
 * @date 2016-4-27
 * @time 上午9:54:45
 * @todo 设置向导界面, 采用ViewPager实现页面切换
 */
@SuppressLint("NewApi")
public class GuideActivity extends Activity {

	private ViewPager vp_guids;
	private LinearLayout ll_points;
	private View v_redPoint;
	private Button bt_startExp;
	private List<ImageView> guids;
	private MyAdapter adapter;
	
	private float disPoints;			//点与点之间的距离 

	/**
	 * 
	 */
	public GuideActivity() {
		System.out.println("GuideActivity 构造");
	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		System.out.println("GuideActivity onCreate");
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		initView();
		initData();
		initEvent();
	}

	/**
	 * ViewPager事件监听
	 */
	private void initEvent() {
		
		//监听布局完成
		v_redPoint.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			
			@Override
			public void onGlobalLayout() {
				v_redPoint.getViewTreeObserver().removeOnGlobalLayoutListener(this);
				
				//界面布局前, 点的位置无法确定, 布局完成, 才能求点之间的距离
				View point0 = ll_points.getChildAt(0);
				View point1 = ll_points.getChildAt(1);
				int left0 = point0.getLeft();
				int left1 = point1.getLeft();
				
				disPoints = left1 - left0; 
				
//				f_redPointLeftOri = v_redPoint.getLeft();
//				System.out.println("left0,left1,f_redPointLeftOri = " + left0 + ", " + left1 + ", " + f_redPointLeftOri);
			}
		});
		
		//btn添加点击事件
		bt_startExp.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Sptools.setBoolean(GuideActivity.this, MyConstants.IS_SETUP, true);
				Intent intent = new Intent(GuideActivity.this,MainActivity.class);
				GuideActivity.this.startActivity(intent);
				GuideActivity.this.finish();
			}
		});
		
		//ViewPager添加页面改变事件监听
		vp_guids.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				//当前显示的位置编号
				if(position == guids.size() - 1){
					bt_startExp.setVisibility(View.VISIBLE);
				}else{
					bt_startExp.setVisibility(View.GONE);
				}
			}
			
			@Override
			/**
			 * 页面滑动中触发的事件
			 * position: 滑动过程中的当前位置
			 * positionOffset: 偏移初始位置的比例值
			 * positionOffsetPixels: 偏移初始位置的像素值
			 */
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				//滑动时的参数
				System.out.println("position = " + position);
				System.out.println("positionOffset = " + positionOffset);
				System.out.println("positionOffsetPixels = " + positionOffsetPixels);
				
//				LayoutParams params = (LayoutParams) v_redPoint.getLayoutParams();
				RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) v_redPoint.getLayoutParams();
				System.out.println("params.leftMargin = " + params.leftMargin);
//				System.out.println("f_disPoints * positionOffset = " + f_disPoints * positionOffset);
				params.leftMargin = Math.round(disPoints * (positionOffset + position));
				v_redPoint.setLayoutParams(params);
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {
				
			}
		});
	}

	/**
	 * 
	 */
	private void initData() {
		System.out.println("GuideActivity initData 1");
		int[] pics = new int[]{R.drawable.guide_1,R.drawable.guide_2,R.drawable.guide_3};
		
		//ViewPager list容器
		guids = new ArrayList<ImageView>();
		//设置灰色点的大小
		for(int i = 0;i < pics.length;i ++) {
			//设置viewPager中的页面背景图
			ImageView iv = new ImageView(this.getApplicationContext());
			iv.setBackgroundResource(pics[i]);
			guids.add(iv);
			
			
			//给点容器ll_points添加对应数量的点
			View v_pointView = new View(getApplicationContext());
//			View v_pointView = new View(this);
			v_pointView.setBackgroundResource(R.drawable.gray_point);
//
			LayoutParams lParam = new LayoutParams(DensityUtil.dip2px(this.getApplicationContext(), 10), DensityUtil.dip2px(this.getApplicationContext(), 10)); //默认单位是像素px, 不是dp
//			//点与点之间的空隙
			if(i != 0) {
				lParam.leftMargin = DensityUtil.dip2px(this.getApplicationContext(), 10);
			}
//			
			//不设置空隙的话, 默认无缝挨在一起
			v_pointView.setLayoutParams(lParam);  
//			
			//灰色的点添加到线性布局中
			ll_points.addView(v_pointView);
		}
		
		//ViewPager的适配器
		adapter = new MyAdapter();
		vp_guids.setAdapter(adapter);
		System.out.println("GuideActivity initData 2");
	}
	
	private class MyAdapter extends PagerAdapter{

		@Override
		public int getCount() {
			// 返回数据的个数
			return guids.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// 过滤和缓存的作用
			System.out.println("isViewFromObject arg0 == arg1 = " + (arg0 == arg1));
			return arg0 == arg1;
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			System.out.println("destroyItem position = " + position);
			container.removeView((View) object);
//			super.destroyItem(container, position, object); 
		}
		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			System.out.println("instantiateItem position = " + position);
			View child = guids.get(position);
			container.addView(child );
			return child;
//			return super.instantiateItem(container, position);
		}
	}

	/**
	 * 
	 */
	private void initView() {
		System.out.println("GuideActivity initView");
		this.setContentView(R.layout.activity_guide);
		//ViewPager组件
		vp_guids = (ViewPager) this.findViewById(R.id.vp_guide_pages);
		
		//动态点容器
		ll_points = (LinearLayout) this.findViewById(R.id.ll_guide_points);
	
		//可移动的红点
		v_redPoint = this.findViewById(R.id.v_guide_redpoint);
		
		//开始体验按钮
		bt_startExp = (Button) this.findViewById(R.id.bt_guide_startexp);
		
	}

}

