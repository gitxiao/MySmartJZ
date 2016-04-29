/**
 * 
 */
package com.chunfeng.basepage;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.test.R;

/**
 * @author Cfrjkj
 * @date 2016-4-29
 * @time 上午10:53:03
 * @todo TODO
 */
public class HomeBaseTagPage extends BaseTagPage{

	/**
	 * @param context
	 */
	public HomeBaseTagPage(Context context) {
		super(context);
	}

	public void initData() {
		btnMenuButton.setVisibility(View.VISIBLE);
		
		textView.setText(context.getString(R.string.mainBtn1));
		TextView tv = new TextView(context);
		tv.setText("主界面内容");
//		tv.setTextColor(""#000000");
		tv.setTextSize(25);
		tv.setGravity(Gravity.CENTER);
		
		flLayout.addView(tv);
	}
}
