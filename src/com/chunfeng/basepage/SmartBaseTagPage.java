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
public class SmartBaseTagPage extends BaseTagPage{

	/**
	 * @param context
	 */
	public SmartBaseTagPage(Context context) {
		super(context);
	}

	public void initData() {
		textView.setText(context.getString(R.string.mainBtn3));
		TextView tv = new TextView(context);
		tv.setText(R.string.mainBtn3);
//		tv.setTextColor(""#000000");
		tv.setTextSize(25);
		tv.setGravity(Gravity.CENTER);
		
		flLayout.addView(tv);
	}
}
