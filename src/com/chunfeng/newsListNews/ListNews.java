///**
// * 
// */
//package com.chunfeng.newsListNews;
//
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.example.test.R;
//
///**
// * @author Cfrjkj
// * @date 2016-5-4
// * @time 下午1:30:14
// * @todo TODO
// */
//public class ListNews{
//	
//	
//	public ListNews(){
//		
//	}
//	//------------------------------------ListView-------------------------------->>>
//	private class ListNewsAdapter extends BaseAdapter{
//
//		@Override
//		public int getCount() {
//			/*
//			 * 写成这样的话,作用相同, 但是因为没有获取到网络数据时,
//			 * newsDetailData可能为空,所以其data.news也为空, 在这里会报空指针异常
//			 */
////				return newsDetailData.data.news.size();	
//			return listNews.size();
//		}
//
//		/* (non-Javadoc)
//		 * @see android.widget.Adapter#getItem(int)
//		 */
//		@Override
//		public Object getItem(int arg0) {
//			// TODO Auto-generated method stub
//			return null;
//		}
//
//		/* (non-Javadoc)
//		 * @see android.widget.Adapter#getItemId(int)
//		 */
//		@Override
//		public long getItemId(int arg0) {
//			// TODO Auto-generated method stub
//			return 0;
//		}
//
//		@Override
//		public View getView(int position, View converView, ViewGroup parent) {
//			ListViewHolder holder = null;
//			if(converView == null){
//				//没有缓存视图
//				converView = View.inflate(mainActivity, R.layout.vpi_news_listview_item, null);
//				holder = new ListViewHolder();
//				holder.iv_con = (ImageView)converView.findViewById(R.id.iv_vpi_news_listview_item_icon);
//				holder.iv_newPic = (ImageView) converView.findViewById(R.id.iv_vpi_news_listview_item_pic);
//				holder.tv_time = (TextView)converView.findViewById(R.id.tv_vpi_news_listview_item_time);
//				holder.tv_title = (TextView)converView.findViewById(R.id.tv_vpi_news_listview_item_title);
//				converView.setTag(holder);
//			}else{
//				holder = (ListViewHolder)converView.getTag();
//			}
//			
//			//设置数据
////				listNewsData = newsDetailData.data.news.get(position);
//			holder.tv_title.setText(listNews.get(position).title);
//			holder.tv_time.setText(listNews.get(position).pubdate);
//			
//			bitmapUtils.display(holder.iv_newPic, listNews.get(position).listimage);
//			return null;
//		}
//		
//	}
//		
//	/**
//	 * ListView的holder
//	 * @author Cfrjkj
//	 * @date 2016-5-4
//	 * @time 下午12:01:24
//	 * @todo TODO
//	 */
//	private class ListViewHolder{
//		private ImageView iv_newPic;
//		private ImageView iv_con;
//		private TextView tv_title;
//		private TextView tv_time;
//	}
//	//------------------------------------ListView--------------------------------<<<
//}
