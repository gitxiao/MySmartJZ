/**
 * 
 */
package com.chunfeng.view;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.chunfeng.dataLogic.NewsCenterData.NewsType;
import com.chunfeng.zhjz.activity.MainActivity;
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
	
	private int slectedIndex;		//左侧选项被选中的索引
	
	@Override
	public View initView() {
//		lvListView = new ListView(getActivity());
		
		View root = View.inflate(getConainerActivity(), R.layout.activity_main_fm_left_view, null);
		//动态注入  使用xutils开源框架
		ViewUtils.inject(this, root);
		
		lvListView.setBackgroundColor(Color.BLACK);
		lvListView.setCacheColorHint(Color.TRANSPARENT);
		lvListView.setDividerHeight(0);
		lvListView.setSelector(new ColorDrawable(Color.TRANSPARENT));
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
	 * 接口, 右侧新闻界面的子页面实现这个借口后, 可以在LeftMenuFragment这里直接调用选择菜单后的页面切换, 而不用通过getMainContentFragment
	 * @author Cfrjkj
	 * @date 2016-5-2
	 * @time 下午3:25:47
	 * @todo TODO
	 */
	public interface OnLeftMenuSwitchListener{
		void switchPageFromListener(int position);
	}
	
	private  OnLeftMenuSwitchListener onLeftMenuSwitchListener;
	public void setOnLeftMenuSwitchListener(OnLeftMenuSwitchListener onLeftMenuSwitchListener){
		this.onLeftMenuSwitchListener = onLeftMenuSwitchListener;
	}
	
	/**
	 * 初始化4个TextView被按下时的事件
	 */
	public void initEvent(){
		lvListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int position,
					long arg3) {
				System.out.println("按下了左侧按钮 arg2 = " + position);
				slectedIndex = position;
				
				if(onLeftMenuSwitchListener != null) {
					onLeftMenuSwitchListener.switchPageFromListener(position);
				}else{
//					MainContentFragment mcf = ((MainActivity) getActivity()).getMainContentFragment();
//					mcf.switchChildPage(position);			//通知MainContentFragment,修改子页面的显示内容
					System.out.println("此类没有实现这个接口");
				}
				
				((MainActivity) getActivity()).getSlidingMenu().toggle();		//左侧菜单的伸缩	
				
				adapter.notifyDataSetChanged();		//通知adapter, listView中的item的状态发生了变化
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
			
			System.out.println("MyAdapter getView position = " + position);
			TextView textView = null;
			if(convertView == null){
				textView = (TextView) View.inflate(getActivity(), R.layout.leftmenu_list_textview, null);
//				textView = (TextView) View.inflate(getActivity(), R.id.textViewInLeftMenu, null);
			}else {
				textView = (TextView)convertView;
			}
			textView.setText(dataList.get(position).title); 		//设置左侧菜单的按钮标题
			textView.setEnabled(position == slectedIndex);			//选中的textView设为enabled状态
			return textView;
		}
	}
}
