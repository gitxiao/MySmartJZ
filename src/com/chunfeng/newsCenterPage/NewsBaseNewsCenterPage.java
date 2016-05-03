/**
 * 
 */
package com.chunfeng.newsCenterPage;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.chunfeng.dataLogic.NewsBasicData;
import com.chunfeng.dataLogic.NewsBasicData.NewsType.NewsTag;
import com.chunfeng.newsChildPage.VPINewsChildPage;
import com.chunfeng.zhjz.activity.MainActivity;
import com.example.test.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.viewpagerindicator.TabPageIndicator;

/**
 * @author Cfrjkj
 * @date 2016-5-2
 * @time 上午8:55:32
 * @todo TODO
 */
public class NewsBaseNewsCenterPage extends BaseNewsCenterPage {

	@ViewInject(R.id.newsViewpager)
	private ViewPager newsViewpager;
	
	@ViewInject(R.id.newsIndicator)
	private TabPageIndicator pageIndicator;
	
	@ViewInject(R.id.indicatorRightButton)
	private ImageButton btnRightArr;
	
	@OnClick(R.id.indicatorRightButton) 			//直接注入button的点击事件
	public void nextTag(View v){
		System.out.println("ImageButton被点击了 2 v :" + v.getClass().getName());
		newsViewpager.setCurrentItem(newsViewpager.getCurrentItem() + 1);
	}
	
	private View ll_simpleTabs;			//布局文件	
	
	private List<NewsBasicData.NewsType.NewsTag> newsTagList = new ArrayList<NewsBasicData.NewsType.NewsTag>();
	
	/**
	 * @param activity
	 * @param children 
	 */
	public NewsBaseNewsCenterPage(MainActivity activity, List<NewsTag> children) {
		super(activity);
		newsTagList = children;
	}

	@Override
	protected View initView() {
//		TextView tv = new TextView(mainActivity);
//		tv.setText("新闻内容");
////		tv.setTextColor(""#000000");
//		tv.setTextSize(25);
//		tv.setGravity(Gravity.CENTER);
		
		ll_simpleTabs = View.inflate(mainActivity, R.layout.ll_news_simple_tabs, null);
		ViewUtils.inject(this, ll_simpleTabs);
        
		return ll_simpleTabs;
	}
	

/**
 * 	
	注意ViewPagerIndicator的用法:用indicator代替viewpager来设置事件监听
		(Optional) If you use an OnPageChangeListener with your view pager you should set it in the indicator rather than on the pager directly.
			continued from above
		 titleIndicator.setOnPageChangeListener(mPageChangeListener);
		 
	用viewpager来设置事件监听也会无效, 因为indicator在setViewPager时会把viewpager的事件监听器清除.参照ViewPagerIndicator的源代码(应该先设置事件监听, 再调用setViewPager)
 */
	public void initEvent(){
		
		pageIndicator.setOnPageChangeListener(new OnPageChangeListener() {
//		newsViewpager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				if(position != 0) {
					mainActivity.getSlidingMenu().setSlidingEnabled(false);
				}else{
					mainActivity.getSlidingMenu().setSlidingEnabled(true);
				}
			}
			
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {
				
			}
		});
		
//		btnRightArr.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View arg0) {
//				System.out.println("ImageButton被点击了 1");
//				newsViewpager.setCurrentItem(newsViewpager.getCurrentItem() + 1);
//			}
//		});
	}
	
	/**
	 * 注意数据初始化的时机, 要在读取网络数据之后
	 */
	public void initData(){
		MyAdapter adapter = new MyAdapter();
		newsViewpager.setAdapter(adapter);
//		TabPageIndicator pageIndicator = (TabPageIndicator)mainActivity.findViewById(R.id.newsIndicator);		//在这里不能用mainActivity, 用mainActivity获取不到, 必须用simpleTabs
//		TabPageIndicator pageIndicator = (TabPageIndicator)ll_simpleTabs.findViewById(R.id.newsIndicator);
		pageIndicator.setViewPager(newsViewpager);
	}
	
	class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return newsTagList.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
//			TextView view = new TextView(mainActivity);
//			view.setText(newsTagList.get(position).title + "内容");
////			view.setTextColor(""#000000");
//			view.setTextSize(60);
//			view.setGravity(Gravity.CENTER);
//			container.addView(view);
			
//			View view = View.inflate(mainActivity, R.id.layout_news_content_main, null);
//			ViewUtils.inject(this,view);
			
			VPINewsChildPage vpiNcp = new VPINewsChildPage(mainActivity,newsTagList.get(position));
			View view = vpiNcp.getRootView();
			
			container.addView(view);
			System.out.println("container.getClass().getName() = " + container.getClass().getName());
			return view;
//			return super.instantiateItem(container, position);
		}
		
		@Override
        public CharSequence getPageTitle(int position) {
            return newsTagList.get(position).title;
        }
    }

}
