/**
 * 
 */
package com.chunfeng.basepage;

import com.example.test.R;

import android.content.Context;
import android.opengl.Visibility;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * @author Cfrjkj
 * @date 2016-4-29
 * @time 上午10:31:31
 * @todo TODO
 */
public class BaseTagPage {
	public Context context;
	public View root;
	public ImageButton btnMenuButton;
	public TextView textView;
	public FrameLayout flLayout;
	public BaseTagPage(Context context) {
		this.context = context;
		initView();
		initData();
		initEvent();
	}

	/**
	 * 
	 */
	public void initEvent() {
		
	}

	/**
	 * 
	 */
	public void initData() {
	}

	/**
	 * 
	 */
	public void initView() {
		root = View.inflate(context, R.layout.fragment_main_basecontent, null);
		
		btnMenuButton = (ImageButton) root.findViewById(R.id.btnMenuInBaseContent);
		btnMenuButton.setVisibility(View.GONE);
		
		textView = (TextView)root.findViewById(R.id.textViewInBaseContent);
		
		flLayout = (FrameLayout) root.findViewById(R.id.fl_base_content_tag);
	}
	
	/**
	 * 获取到根布局
	 * @return
	 */
	public View getRoot(){
		return root;
	}
}
