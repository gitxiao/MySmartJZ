/**
 * 
 */
package com.chunfeng.newsCenterPage;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.chunfeng.zhjz.activity.MainActivity;

/**
 * @author Cfrjkj
 * @date 2016-5-2
 * @time 上午8:55:32
 * @todo TODO
 */
public class PicturesBaseNewsCenterPage extends BaseNewsCenterPage {

	/**
	 * @param activity
	 */
	public PicturesBaseNewsCenterPage(MainActivity activity) {
		super(activity);
	}

	@Override
	protected View initView() {
		TextView tv = new TextView(mainActivity);
		tv.setText("组图内容");
//		tv.setTextColor(""#000000");
		tv.setTextSize(25);
		tv.setGravity(Gravity.CENTER);
		
		return tv;
	}

}
