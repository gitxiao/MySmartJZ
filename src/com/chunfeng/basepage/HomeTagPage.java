/**
 * 
 */
package com.chunfeng.basepage;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.chunfeng.zhjz.activity.MainActivity;
import com.example.test.R;

/**
 * @author Cfrjkj
 * @date 2016-4-29
 * @time 上午10:53:03
 * @todo TODO
 */
public class HomeTagPage extends BaseTagPage{

	/**
	 * @param context
	 */
	public HomeTagPage(MainActivity context) {
		super(context);
	}

	public void initData() {
		btnMenuButton.setVisibility(View.VISIBLE);
		
		textTitle.setText(activity.getString(R.string.mainBtn1));
		TextView tv = new TextView(activity);
		tv.setText("主界面内容");
//		tv.setColor("#000000");
		tv.setTextSize(50);
		tv.setGravity(Gravity.CENTER);
		
		flLayout.addView(tv);
		
		btnMenuButton.setVisibility(View.GONE);
	}
}
