/**
 * 
 */
package com.chunfeng.view;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.chunfeng.basepage.BaseTagPage;
import com.chunfeng.basepage.GovBaseTagPage;
import com.chunfeng.basepage.HomeBaseTagPage;
import com.chunfeng.basepage.NewsBaseTagPage;
import com.chunfeng.basepage.SetBaseTagPage;
import com.chunfeng.basepage.SmartBaseTagPage;
import com.example.test.R;
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

	@ViewInject(R.id.vp_pages_main)  
	private ViewPager viewPager;	//这里的注解已经初始化了viewPager, 不需要再用findViewById去获取控件对象
	
	@ViewInject(R.id.radioGroup_main)
	private RadioGroup rGroup;
	@Override
	public View initView() {
		
		View root = View.inflate(context, R.layout.activity_main_fm_content_view, null);
		
		//动态注入  使用xutils开源框架
		ViewUtils.inject(this, root);
		
		return root;
	}

	
	private List<BaseTagPage> pages = new ArrayList<BaseTagPage>();
	public void initData(){
		pages.add(new HomeBaseTagPage(context));
		pages.add(new NewsBaseTagPage(context));
		pages.add(new SmartBaseTagPage(context));
		pages.add(new GovBaseTagPage(context));
		pages.add(new SetBaseTagPage(context));
		
		MyAdapter adapter = new MyAdapter();
		
		viewPager.setAdapter(adapter);
		
		
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
			BaseTagPage baseTagPage = pages.get(position);
			View rootView = baseTagPage.getRoot();
			container.addView(rootView);
			return rootView;
//			return super.instantiateItem(container, position);
		}
	}
	
}
