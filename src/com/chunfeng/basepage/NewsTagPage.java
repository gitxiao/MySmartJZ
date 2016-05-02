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
public class NewsTagPage extends BaseTagPage{

	
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
	 * 解析json数据
	 * @param jsonData
	 */
	protected void parseJsonData(String jsonData){
		Gson gson = new Gson();
		newsData = gson.fromJson(jsonData, NewsCenterData.class);
	
		System.out.println("解析后的json数据: " + newsData.retcode);
		System.out.println("解析后的json数据: " + newsData.data.get(0).children.get(0).title);
		
		activity.getLeftMenuFragment().setNewsData(newsData.data);
	
		BaseNewsCenterPage bncp = null;
		int index = 0;
		for(NewsType newsType : newsData.data){
			System.out.println("newsData.data.newsType = " + newsData.data.get(index ++).title);
			switch (newsType.type) {
			case 1:			//新闻, 子页面的顺序不能写死, 因为服务器上的内容有可能发生变化
				bncp = new NewsBaseNewsCenterPage(activity);
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
		switchPage(0); 			//默认显示新闻的内容
	}

	
}