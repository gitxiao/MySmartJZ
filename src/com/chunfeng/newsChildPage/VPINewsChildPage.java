/**
 * 
 */
package com.chunfeng.newsChildPage;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chunfeng.dataLogic.NewsBasicData;
import com.chunfeng.dataLogic.NewsDetailData;
import com.chunfeng.dataLogic.NewsDetailData.Detail.News;
import com.chunfeng.utils.MyConstants;
import com.chunfeng.utils.SPTools;
import com.chunfeng.zhjz.activity.MainActivity;
import com.example.test.R;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
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
	private NewsBasicData.NewsType.NewsTag newsTag;
	
	private Gson gson;
	private NewsDetailData detailData;
	
	@ViewInject(R.id.tv_news_content_picinfo)
	private TextView tvDesc;
	
	@ViewInject(R.id.vp_news_content_lunbo)
	private ViewPager vp_lunbo;
	
	public VPINewsChildPage(MainActivity mainActivity,NewsBasicData.NewsType.NewsTag newsTag){
		this.mainActivity = mainActivity;
		layoutNewsContentMain = initView();
		this.newsTag = newsTag;
		initEvent();
		initData();
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
		String cacheString = SPTools.getString(mainActivity, MyConstants.NEWS_DETAIL_DATA, null);
		System.out.println("缓存的数据: cacheString = " + cacheString);
		if(cacheString != null) {
			parseJsonData(cacheString);
		}else {
			//可以在这里添加一个loading提示
			System.out.println("还没有缓存数据" + MyConstants.NEWS_DETAIL_DATA);
		}
		initHttpData(newsTag.url);
	}
	
	/**
	 * 
	 */
	public void initEvent() {
		
	}
	
	/**
	 * 获取网络数据
	 * @param <T>
	 */
	private <T> void initHttpData(String url) {
		//获取网络数据之前先取出本地缓存的数据
		String cacheString = SPTools.getString(mainActivity, MyConstants.NEWS_DETAIL_DATA, null);
		System.out.println("缓存的数据: cacheString = " + cacheString);
		if(cacheString != null) {
			parseJsonData(cacheString);
		}else {
			//可以在这里添加一个loading提示
			System.out.println("还没有缓存数据");
		}
		
		HttpUtils httpUtils = new HttpUtils();
		try {
			httpUtils.send(HttpMethod.GET, MyConstants.URL_NEWS_DETAIL_HEAD + url, new RequestCallBack<T>(){
				
				@Override
				public void onSuccess(ResponseInfo<T> responseInfo) {
					System.out.println("网络访问成功 responseInfo.result = " + responseInfo.result);				
					parseJsonData((String)(responseInfo.result));
					SPTools.setString(mainActivity, MyConstants.NEWS_DETAIL_DATA, (String)(responseInfo.result));
				}
				
				@Override
				public void onFailure(HttpException error, String msg) {
					System.out.println("网络访问失败 msg = " + msg);				
					
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 解析json数据,
	 * @param jsonData
	 */
	protected void parseJsonData(String jsonData){
		if(gson == null){  	//parseJsonData可能会被执行两次, 要避免多次创建gson对象
			gson = new Gson();
		}
		detailData = gson.fromJson(jsonData, NewsDetailData.class);
		tvDesc.setText(detailData.data.topnews.get(0).title);
		System.out.println("解析后的json数据: " + detailData.data.topnews.get(0).title);
		
		
//		vp_lunbo.setAdapter(new PagerAdapter() {
//			@Override
//			public boolean isViewFromObject(View view, Object object) {
//				return view == object;
//			}
//			
//			@Override
//			public int getCount() {
//				return detailData.data.news.size();
//			}
//			
//			@Override
//			public void destroyItem(ViewGroup container, int position, Object object) {
//				container.removeView((View) object);
//			}
//			
//			@Override
//			public Object instantiateItem(ViewGroup container, int position) {
//				// TODO Auto-generated method stub
//				System.out.println("instantiateItem position = " + position);
//				String urlString = null;
//				ImageView iView = null;
////				iView = View.
//				container.addView(iView);
//				return iView;
////				return super.instantiateItem(container, position);
//			}
//		});
		
	}
}
