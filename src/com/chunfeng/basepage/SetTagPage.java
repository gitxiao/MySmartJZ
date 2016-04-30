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
public class SetTagPage extends BaseTagPage{

	/**
	 * @param context
	 */
	public SetTagPage(MainActivity context) {
		super(context);
	}

	public void initData() {
		textView.setText(activity.getString(R.string.mainBtn5));
		TextView tv = new TextView(activity);
		tv.setText(R.string.mainBtn5);
//		tv.setTextColor(""#000000");
		tv.setTextSize(25);
		tv.setGravity(Gravity.CENTER);
		
		flLayout.addView(tv);
		btnMenuButton.setVisibility(View.GONE);
	}
}
