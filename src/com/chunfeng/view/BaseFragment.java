/**
 * 
 */
package com.chunfeng.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author Cfrjkj
 * @创建时间 2016年4月28日 上午11:27:17
 * @描述 左侧菜单
 * @svn提交者:$Author$
 * @提交时间: $Date$
 * @当前版本: $Rev$
 */
@SuppressLint("NewApi")
public abstract class BaseFragment extends Fragment {

//	public MainActivity mainActivity;
	public Context context;
	
	/**
	 * @return the context
	 */
	public Context getContext() {
		return context;
	}

	/**
	 * @param context the context to set
	 */
	public void setContext(Context context) {
		this.context = context;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getActivity();
//		context = getApplicationContext();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View root = initView();
		return root;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initData();		//父类自动调用initData函数,子类只需要专注于数据处理即可
		initEvent();
	}
	
	/**
	 * 子类必须实现初始化视图的方法
	 */
	public abstract View initView();
	
	/**
	 * 子类需要初始化数据时可以调用此方法, 不是必须实现
	 */
	public void initData(){
		
	}
	
	/**
	 * 子类需要处理事件时可以调用此方法, 不是必须实现
	 */
	public void initEvent(){
		
	}
}
