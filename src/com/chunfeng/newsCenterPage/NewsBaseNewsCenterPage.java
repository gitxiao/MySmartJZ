/**
 * 
 */
package com.chunfeng.newsCenterPage;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chunfeng.dataLogic.NewsCenterData;
import com.chunfeng.dataLogic.NewsCenterData.NewsType.NewsTag;
import com.chunfeng.zhjz.activity.MainActivity;
import com.example.test.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
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
	
	private View ll_simpleTabs;			//布局文件	
	
	private List<NewsCenterData.NewsType.NewsTag> newsTagList = new ArrayList<NewsCenterData.NewsType.NewsTag>();
	
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
		ViewUtils.inject(this,ll_simpleTabs);
        
		return ll_simpleTabs;
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
			TextView tv = new TextView(mainActivity);
			tv.setText(newsTagList.get(position).title);
//			tv.setTextColor(""#000000");
			tv.setTextSize(25);
			tv.setGravity(Gravity.CENTER);
			return tv;
//			return super.instantiateItem(container, position);
		}
		
		@Override
        public CharSequence getPageTitle(int position) {
            return newsTagList.get(position).title;
        }
    }

}
