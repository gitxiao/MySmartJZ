/**
 * 
 */
package com.chunfeng.view;

import android.view.View;

import com.example.test.R;

/**
 * @author Cfrjkj
 * @创建时间 2016年4月28日 上午11:54:02
 * @描述 主界面的fragment
 * @svn提交者:$Author$
 * @提交时间: $Date$
 * @当前版本: $Rev$
 */
public class MainContentFragment extends BaseFragment {

	/* (non-Javadoc)
	 * @see com.chunfeng.view.BaseFragment#initView()
	 */
	@Override
	public View initView() {
//		TextView textView = new TextView(context);
//		textView.setText("主界面内容");
////		textView.setTextColor(#000000);
//		textView.setTextSize(25);
//		textView.setGravity(Gravity.CENTER);
//		return textView;
		
		View view = View.inflate(context, R.layout.activity_main_fm_content_view, null);
		return view;
	}

}
