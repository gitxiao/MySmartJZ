package com.chunfeng.newsChildPage;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap.Config;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.chunfeng.dataLogic.NewsBasicData;
import com.chunfeng.dataLogic.NewsDetailData;
import com.chunfeng.utils.DensityUtil;
import com.chunfeng.utils.MyConstants;
import com.chunfeng.utils.SPTools;
import com.chunfeng.utils.ViewPagerSuperNotIntereptEvent;
import com.chunfeng.zhjz.activity.MainActivity;
import com.example.test.R;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
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
//	private NewsDetailData detailData;
	
	@ViewInject(R.id.tv_news_content_picinfo)
	private TextView tvDesc;
	
	@ViewInject(R.id.vp_news_content_lunbo)
	private ViewPagerSuperNotIntereptEvent vp_lunbo;
	
	@ViewInject(R.id.ll_news_content_points)
	private LinearLayout ll_points;					//轮播图顺序点
	
	@ViewInject(R.id.lv_news_content_detail)
	private com.chunfeng.utils.ListViewRefreshable listViewNews;					//显示新闻的listView
	
	
	
	private List<NewsDetailData.Detail.Topnews> lunboDataList = new ArrayList<NewsDetailData.Detail.Topnews>();
	private LunBoAdapter lunBoAdapter;
	private BitmapUtils bitmapUtils;
	private int picSelectIndex;
	private LunboTask lunboTask = new LunboTask();
	private int delayTime = 2000;
	
	
	private NewsDetailData newsDetailData;
	private List<NewsDetailData.Detail.News> listNews = new ArrayList<NewsDetailData.Detail.News>();
	private ListNewsAdapter listNewsAdapter;
	
//	final Handler handler = new Handler(){
//		public void handleMessage(android.os.Message msg){
//			//主线程中执行
//			System.out.println("handleMessage");
//		}
//	};
	
	public VPINewsChildPage(MainActivity mainActivity,NewsBasicData.NewsType.NewsTag newsTag){
		this.mainActivity = mainActivity;
		layoutNewsContentMain = initView();
		this.newsTag = newsTag;
		
		bitmapUtils = new BitmapUtils(mainActivity);
		bitmapUtils.configDefaultBitmapConfig(Config.ARGB_4444);
		
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
		View view = View.inflate(mainActivity, R.layout.ll_listview_news, null);
		ViewUtils.inject(this, view);
		
		//listView.轮播图head
		View lunboPicView = View.inflate(mainActivity, R.layout.rl_lunbo_pic, null);
		ViewUtils.inject(this, lunboPicView);
//		listViewNews.addHeaderView(lunboPicView);
		listViewNews.myAddHeadView(lunboPicView);
		return view;
	}

	/**
	 * 
	 */
	public void initData(){
		//这些数据只需要初始化一次
		gson = new Gson();
		
		//轮播图的适配器
		lunBoAdapter = new LunBoAdapter();
		vp_lunbo.setAdapter(lunBoAdapter);
		vp_lunbo.setOnPageChangeListener(new LunBoListener());
		
		//新闻列表的适配器
		listNewsAdapter = new ListNewsAdapter();
		listViewNews.setAdapter(listNewsAdapter);
		
		//获取网络数据之前先取出本地缓存的数据
		String cacheString = SPTools.getString(mainActivity, newsTag.url, null);		//因为数据比较多, 所以用url作为数据存储的key值
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
	 * 处理数据并显示
	 * @param detailData
	 */
	private void operateData(NewsDetailData detailData){
		//1.设置轮播图的数据
		setLunboData(detailData);
		
		//2.轮播图对应点的处理
		initPoints(detailData);
		
		//3.设置图片描述文字和点的选中效果,这个函数应该在页面切换事件中调用
//		setPicDescAndPointSelect(detailData,picSelectIndex);
		
		//4.轮播图的自动播放
//		lunboProcess();
		lunboTask.startTask(2000);
		
		//5.处理新闻列表的数据
		setListViewNewsData(detailData);
	}
	
	/**
	 * 获取网络数据
	 * @param <T>
	 */
	private <T> void initHttpData(final String url) {
		
		HttpUtils httpUtils = new HttpUtils();
		
		try {
			httpUtils.send(HttpMethod.GET, MyConstants.URL_SERVER + url, new RequestCallBack<T>(){
				
				@Override
				public void onSuccess(ResponseInfo<T> responseInfo) {
					System.out.println("网络访问成功");				
					NewsDetailData detailData = parseJsonData((String)(responseInfo.result));
					SPTools.setString(mainActivity, url, (String)(responseInfo.result));
					operateData(detailData);
				}
				
				@Override
				public void onFailure(HttpException error, String msg) {
					System.out.println("网络访问失败 msg = " + msg);				
					operateData(null); 		//网络访问失败也要进行对应的操作
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
	private NewsDetailData parseJsonData(String jsonData){
		newsDetailData = gson.fromJson(jsonData, NewsDetailData.class);
		
		return newsDetailData;
	}
	
	


	/**
	 * 设置新闻列表的数据
	 * @param detailData2
	 */
	private void setListViewNewsData(NewsDetailData detailData2) {
		listNews = detailData2.data.news;
		listNewsAdapter.notifyDataSetChanged();
	}

	
	/**
	 * 处理轮播图
	 */
//	private void lunboProcess() {
//		
//		final int delayTime = 1000;
//		//发消息之前先清空之前的消息
//		handler.removeCallbacksAndMessages(null);	
//		handler.postDelayed(new Runnable() {
//			@Override
//			public void run() {
//				//这里用的是消息机制, 不是新建了一个线程, 还是主线程
//				//每隔一秒播放下个一轮播图
//				vp_lunbo.setCurrentItem((vp_lunbo.getCurrentItem() + 1) % vp_lunbo.getAdapter().getCount());
//
//				//消息发出后再循环发出
//				handler.postDelayed(this, delayTime);		
//			}
//		}, delayTime);
//	}


	private class LunboTask extends Handler implements Runnable{

		private int delayTime;
		public void stopTask(){
			removeCallbacksAndMessages(null);
		}
		public void startTask(int delayTime){
			stopTask();
			this.delayTime = delayTime;
			postDelayed(this, delayTime);
		}
		
		@Override
		public void run() {
			//这里用的是消息机制, 不是新建了一个线程, 还是主线程
			//每隔一秒播放下个一轮播图
			vp_lunbo.setCurrentItem((vp_lunbo.getCurrentItem() + 1) % vp_lunbo.getAdapter().getCount());

			//消息发出后再循环发出
			postDelayed(this, delayTime);	
		}
		
	}

	/**
	 * 设置轮播图的描述信息和点选择状态
	 */
	private void setPicDescAndPointSelect(NewsDetailData detailData,int picSelectIndex) {
		tvDesc.setText(detailData.data.topnews.get(picSelectIndex).title);
		for (int i = 0; i < detailData.data.topnews.size(); i++) {
			ll_points.getChildAt(i).setEnabled(i == picSelectIndex);
		}
	}


	/**
	 * 几个轮播图的点
	 */
	private void initPoints(NewsDetailData detailData) {
		this.ll_points.removeAllViews();		//切换页面后需要先清空以前的点, 再生成新的点
		for (int i = 0; i < detailData.data.topnews.size(); i++) {
			View v_pointView = new View(mainActivity);
			//设置点的背景选择器
			v_pointView.setBackgroundResource(R.drawable.point_selector);
			v_pointView.setEnabled(false); //默认都是灰色点

			//设置点的大小
			android.widget.LinearLayout.LayoutParams params = new  android.widget.LinearLayout.LayoutParams(DensityUtil.dip2px(mainActivity, 5),DensityUtil.dip2px(mainActivity, 5));
			//设置点与点之间的距离
			params.rightMargin = DensityUtil.dip2px(mainActivity, 10);
			params.leftMargin = DensityUtil.dip2px(mainActivity, 10);
			v_pointView.setLayoutParams(params);
			this.ll_points.addView(v_pointView);
		}
		ll_points.getChildAt(0).setEnabled(true);	//默认显示第一张图片,第一个点为红色
	}



	/**
	 * @param detailData2
	 */
	private void setLunboData(NewsDetailData detailData) {
		if(detailData != null){
			lunboDataList = detailData.data.topnews;
		}
		lunBoAdapter.notifyDataSetChanged();
	}
	
	
	/**
	 * 轮播图的适配器
	 */
	private class LunBoAdapter extends PagerAdapter{

		@Override
		public int getCount() {
			return lunboDataList.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
//			return super.instantiateItem(container, position);
			
//			System.out.println("加载本地图片 position = " + position + ", " + System.currentTimeMillis());
			ImageView iv_lunbo_pic = new ImageView(mainActivity);
			//设置默认图片,防止网速较慢. 好像不起作用???
			iv_lunbo_pic.setImageResource(R.drawable.home_scroll_default);
			iv_lunbo_pic.setScaleType(ScaleType.FIT_XY);  		//设置图片显示适配坐标
			
//			ImageView httpImageView = null;
			String urlString = lunboDataList.get(position).topimage;
			String newUrlString = urlString.replaceAll(MyConstants.OLD_IP, MyConstants.NEW_IP);
			//异步加载网络图片并显示
//			System.out.println("加载网络图片 position = " + position + ", "  + System.currentTimeMillis());
			bitmapUtils.display(iv_lunbo_pic, newUrlString);
			
			iv_lunbo_pic.setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View arg0, MotionEvent arg1) {
					
					switch (arg1.getAction()) {
					case MotionEvent.ACTION_DOWN:
						
						lunboTask.stopTask();
						break;
					case MotionEvent.ACTION_MOVE:
						lunboTask.stopTask();
						break;
					case MotionEvent.ACTION_UP:
						lunboTask.startTask(delayTime);
						break;
					case MotionEvent.ACTION_CANCEL:
						//手指滑动后离开屏幕时会触发这个事件
						lunboTask.startTask(delayTime);
						break;
					default:
						break;
					}
					return false;		//false表示其的监听可以获取事件, 如果不想让其他监听器获取事件, 就返回true
				}
			});
			iv_lunbo_pic.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					System.out.println("图片被单击了");
				}
			});
			
			container.addView(iv_lunbo_pic);
//			System.out.println("返回图片 position = " + position + ", "  + System.currentTimeMillis());
			return iv_lunbo_pic;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
//			super.destroyItem(container, position, object);
			container.removeView((View)object);
		}
	}
	
	
	/**
	 * 监听轮播图的翻页状态
	 * @author Cfrjkj
	 * @date 2016-5-3
	 * @time 下午9:38:49
	 * @todo TODO
	 */
	private class LunBoListener implements OnPageChangeListener{

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
			
		}

		@Override
		public void onPageSelected(int position) {
//			if(position == 0) {
//				mainActivity.getSlidingMenu().setSlidingEnabled(true);
//			}else {
//				mainActivity.getSlidingMenu().setSlidingEnabled(false);
//			}
			picSelectIndex = position;		//点的选中状态
			setPicDescAndPointSelect(newsDetailData,position);
		}

		@Override
		public void onPageScrollStateChanged(int state) {
			
		}
	}
	
	//------------------------------------ListView-------------------------------->>>
	private class ListNewsAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			/*
			 * 写成这样的话,作用相同, 但是因为没有获取到网络数据时,
			 * newsDetailData可能为空,所以其data.news也为空, 在这里会报空指针异常
			 */
//			return newsDetailData.data.news.size();	
			return listNews.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View converView, ViewGroup parent) {
			ListViewHolder holder = null;
			if(converView == null){
				//没有缓存视图
				converView = View.inflate(mainActivity, R.layout.vpi_news_listview_item, null);
				holder = new ListViewHolder();
				holder.iv_con = (ImageView)converView.findViewById(R.id.iv_vpi_news_listview_item_icon);
				holder.iv_newPic = (ImageView) converView.findViewById(R.id.iv_vpi_news_listview_item_pic);
				holder.tv_time = (TextView)converView.findViewById(R.id.tv_vpi_news_listview_item_time);
				holder.tv_title = (TextView)converView.findViewById(R.id.tv_vpi_news_listview_item_title);
				converView.setTag(holder);
			}else{
				holder = (ListViewHolder)converView.getTag();
			}
			
			//设置数据
//			listNewsData = newsDetailData.data.news.get(position);
			holder.tv_title.setText(listNews.get(position).title);
			holder.tv_time.setText(listNews.get(position).pubdate);
			
			String urlOld = listNews.get(position).listimage;
			String urlNew = urlOld.replaceAll(MyConstants.OLD_IP, MyConstants.NEW_IP);
			bitmapUtils.display(holder.iv_newPic, urlNew);
			return converView;
		}
	}
	
	/**
	 * ListView的holder
	 * @author Cfrjkj
	 * @date 2016-5-4
	 * @time 下午12:01:24
	 * @todo TODO
	 */
	private class ListViewHolder{
		private ImageView iv_newPic;
		private ImageView iv_con;
		private TextView tv_title;
		private TextView tv_time;
	}
	//------------------------------------ListView--------------------------------<<<
}
