/**
 * 
 */
package com.chunfeng.view;

import java.util.ArrayList;
import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.chunfeng.dataLogic.NewsCenterData.NewsType;
import com.example.test.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @author Cfrjkj
 * @创建时间 2016年4月28日 上午11:53:38
 * @描述 左侧菜单的fragment
 * @svn提交者:$Author$
 * @提交时间: $Date$
 * @当前版本: $Rev$
 */
public class LeftMenuFragment extends BaseFragment {

	@ViewInject(R.id.listViewInLeftMenu)	
	private ListView lvListView;			//左侧显示的标签用ListView来实现
	private List<NewsType> dataList = new ArrayList<NewsType>();
	private MyAdapter adapter;
	/* (non-Javadoc)
	 * @see com.chunfeng.view.BaseFragment#initView()
	 */
	@Override
	public View initView() {
//		lvListView = new ListView(getActivity());
		
		View root = View.inflate(getConainerActivity(), R.layout.activity_main_fm_left_view, null);
		//动态注入  使用xutils开源框架
		ViewUtils.inject(this, root);
		
//		lvListView.setBackgroundColor(Color.BLACK);
//		lvListView.setCacheColorHint(Color.TRANSPARENT);
//		lvListView.setDividerHeight(0);
//		lvListView.setSelector(Color.TRANSPARENT);
		return root;
	}

	/**
	 * 从新闻中心调用这个函数
	 * 新闻中心显示时会加载网络数据, 然后将数据传输到这进行显示
	 * @param dataList
	 */
	public void setNewsData(List<NewsType> dataList){
		this.dataList = dataList;
		adapter.notifyDataSetChanged();		//设置好数据后, 通知适配器进行刷新, 显示新的页面内容
	}
	
	/**
	 * 初始化4个TextView被按下时的事件
	 */
	public void initEvent(){
		lvListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				System.out.println("按下了左侧按钮 arg2 = " + arg2);
			}
			
		});
	}
	
	public void initData(){
		adapter = new MyAdapter();
		lvListView.setAdapter(adapter);
	}
	
	public class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return dataList.size();
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			//显示从服务器获取到的数据
//			getActivity()
			TextView textView = null;
			if(convertView == null){
				textView = (TextView) View.inflate(getActivity(), R.layout.leftmenu_list_textview, null);
//				textView = (TextView) View.inflate(getActivity(), R.id.textViewInLeftMenu, null);
			}else {
				textView = (TextView)convertView;
			}
			textView.setText(dataList.get(position).title);
			return textView;
		}
		
	}
}
