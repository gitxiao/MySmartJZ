/**
 * 
 */
package com.chunfeng.basepage;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.chunfeng.utils.MyConstants;
import com.chunfeng.zhjz.activity.MainActivity;
import com.example.test.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

/**
 * @author Cfrjkj
 * @date 2016-4-29
 * @time 上午10:53:03
 * @todo TODO
 */
public class NewsBaseTagPage extends BaseTagPage{

	/**
	 * @param context
	 */
	public NewsBaseTagPage(MainActivity context) {
		super(context);
	}
	
	public void initData() {
		//获取网络数据
		initHttpData();
		
		textView.setText(activity.getString(R.string.mainBtn2));
		TextView tv = new TextView(activity);
		tv.setText(R.string.mainBtn2);
//		tv.setTextColor(""#000000");
		tv.setTextSize(25);
		tv.setGravity(Gravity.CENTER);
		
		flLayout.addView(tv);
		btnMenuButton.setVisibility(View.VISIBLE);
	}

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
					System.out.println("网络访问成功 responseInfo = " + responseInfo.toString());				
					
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

}
