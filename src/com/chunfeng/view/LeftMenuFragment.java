/**
 * 
 */
package com.chunfeng.view;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

/**
 * @author Cfrjkj
 * @创建时间 2016年4月28日 上午11:53:38
 * @描述 左侧菜单的fragment
 * @svn提交者:$Author$
 * @提交时间: $Date$
 * @当前版本: $Rev$
 */
public class LeftMenuFragment extends BaseFragment {

	/* (non-Javadoc)
	 * @see com.chunfeng.view.BaseFragment#initView()
	 */
	@Override
	public View initView() {
		TextView textView = new TextView(super.getConainerActivity());
		textView.setText("左侧菜单");
//		textView.setTextColor(#000000);
		textView.setTextSize(25);
		textView.setGravity(Gravity.CENTER);
		return textView;
	}

}
