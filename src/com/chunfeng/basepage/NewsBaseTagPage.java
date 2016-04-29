/**
 * 
 */
package com.chunfeng.basepage;

import com.example.test.R;

import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;

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
	public NewsBaseTagPage(Context context) {
		super(context);
	}
	
	public void initData() {
		textView.setText(context.getString(R.string.mainBtn2));
		TextView textView = new TextView(context);
		textView.setText("新闻中心");
//		textView.setTextColor(""#000000");
		textView.setTextSize(25);
		textView.setGravity(Gravity.CENTER);
	}

}
