/**
 * 
 */
package com.chunfeng.basepage;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.chunfeng.dataLogic.NewsCenterData;
import com.chunfeng.dataLogic.NewsCenterData.NewsType;
import com.chunfeng.newsCenterPage.BaseNewsCenterPage;
import com.chunfeng.newsCenterPage.InteractBaseNewsCenterPage;
import com.chunfeng.newsCenterPage.NewsBaseNewsCenterPage;
import com.chunfeng.newsCenterPage.PicturesBaseNewsCenterPage;
import com.chunfeng.newsCenterPage.TopicBaseNewsCenterPage;
import com.chunfeng.utils.MyConstants;
import com.chunfeng.view.LeftMenuFragment.OnLeftMenuSwitchListener;
import com.chunfeng.zhjz.activity.MainActivity;
import com.example.test.R;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

/**
 * @author Cfrjkj
 * @date 2016-4-29
 * @time 上午10:53:03
 * @todo 新闻中心主页面,处理包括http请求,数据解析,子页面显示逻辑等内容
 */
public class NewsTagPage extends BaseTagPage implements OnLeftMenuSwitchListener{

	
	private View privateView;
	
	/**
	 * @param context
	 */
	public NewsTagPage(MainActivity context) {
		super(context);
	}
	
	public void initData() {
		//获取网络数据
		initHttpData();
		
		textTitle.setText(activity.getString(R.string.mainBtn2));
//		TextView tv = new TextView(activity);
//		tv.setText(R.string.mainBtn2);
////		tv.setTextColor(""#000000");
//		tv.setTextSize(25);
//		tv.setGravity(Gravity.CENTER);
//		flLayout.addView(tv);

		btnMenuButton.setVisibility(View.VISIBLE);
	}

//	/**
//	 * 初始化各个页面的私有内容.  my
//	 */
//	private void initPrivateView() {
//		NewsBaseNewsCenterPage nbncp = new NewsBaseNewsCenterPage(activity);
//		privateView = nbncp.getRoot();
//		
//	}
	
	/**
	 * 获取网络数据
	 * @param <T>
	 */
	private <T> void initHttpData() {
		HttpUtils httpUtils = new HttpUtils();
		try {
			httpUtils.send(HttpMethod.GET, MyConstants.STR_NEWS_CENTER_, new RequestCallBack<T>(){
				
				@Override
				public void onSuccess(ResponseInfo<T> responseInfo) {
//					System.out.println("网络访问成功 responseInfo.result = " + responseInfo.result);				
					parseJsonData((String)(responseInfo.result));
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
	 * 解析json数据, 这个方法是否应该提到父类中??
	 * @param jsonData
	 */
	protected void parseJsonData(String jsonData){
		Gson gson = new Gson();
		newsData = gson.fromJson(jsonData, NewsCenterData.class);
	
		System.out.println("解析后的json数据: " + newsData.retcode);
		System.out.println("解析后的json数据: " + newsData.data.get(0).children.get(0).title);
		
		activity.getLeftMenuFragment().setNewsData(newsData.data);			//给左侧菜单传递数据(新闻,专题,组图,互动)
		activity.getLeftMenuFragment().setOnLeftMenuSwitchListener(this);
	
		BaseNewsCenterPage bncp = null;
		int index = 0;
		for(NewsType newsType : newsData.data){
			System.out.println("newsData.data.newsType = " + newsData.data.get(index ++).title);
			switch (newsType.type) {
			case 1:			//新闻, 子页面的顺序不能写死, 因为服务器上的内容有可能发生变化
				bncp = new NewsBaseNewsCenterPage(activity,newsData.data.get(0).children);
				break;
			case 2:			//组图
				bncp = new PicturesBaseNewsCenterPage(activity);
				break;
			case 3:			//互动
				bncp = new InteractBaseNewsCenterPage(activity);
				break;
			case 10:		//专题
				bncp = new TopicBaseNewsCenterPage(activity);
				break;
			default:
				break;
			}
			newsPageList.add(bncp);
		}
		switchPageFromSuper(0); 			//默认显示新闻的内容
	}

	protected List<BaseNewsCenterPage> newsPageList = new ArrayList<BaseNewsCenterPage>();
	protected NewsCenterData newsData;
	
	/**
	 * 控制新闻中心子页面的显示, 可以在类外调用, 当在LeftMenuFragment中选择左侧按钮时调用这个函数来控制右侧内容的显示
	 * @param position
	 */
	public void switchPageFromSuper(int position){
		if(newsData != null) {
			textTitle.setText(newsData.data.get(position).title);
			BaseNewsCenterPage bncpBaseNewsCenterPage = newsPageList.get(position);
			bncpBaseNewsCenterPage.initData(); 				//切换页面时初始化新闻界面的数据
			flLayout.removeAllViews();
			flLayout.addView(bncpBaseNewsCenterPage.getView());
		}else {
			System.out.println("这个页面的数据还没加载,或加载失败");
		}
	}

	/* (non-Javadoc)
	 * @see com.chunfeng.view.LeftMenuFragment.OnLeftMenuSwitchListener#switchPageFromListener()
	 */
	@Override
	public void switchPageFromListener(int position) {
		switchPageFromSuper(position);
	}

}
