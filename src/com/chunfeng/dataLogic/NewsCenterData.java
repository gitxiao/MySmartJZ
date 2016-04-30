/**
 * 
 */
package com.chunfeng.dataLogic;

import java.util.List;

/**
 * @author Cfrjkj
 * @date 2016-4-30
 * @time 上午11:01:15
 * @todo 新闻json数据的封装
 */
public class NewsCenterData {
	
	//属性名字必须与将要解析的json数据中的键值完全一致,区分大小写
	public int retcode;
	public List<NewsType> data;
	public List<String> extend;
	
	public class NewsType{
		public List<NewsTag> children;
		
		public int id;
		public int type;
		public String title;
		public String url;
		public String url1;
		public String dayurl;
		public String excurl;
		public String weekurl;

		public class NewsTag{
			public int id;
			public int type;
			public String title;
			public String url;
		}
	}
}
