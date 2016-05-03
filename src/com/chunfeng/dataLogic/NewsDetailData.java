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
public class NewsDetailData {
	
	//属性名字必须与将要解析的json数据中的键值完全一致,区分大小写
	public int retcode;
	public Detail data;
	
	public class Detail{
		public String countcommenturl;
		public String more;
		public String title;

		public List<News> news;
		public List<Topic> topic;
		public List<Topnews> topnews;

		public class News{
			public boolean comment;
			public int id;
			public String commentlist;
			public String commenturl;
			public String listimage;
			public String pubdate;
			public String title;
			public String type;
			public String url;
		}
		public class Topic{
			public int id;
			public String description;
			public String listimage;
			public String sort;
			public String title;
			public String url;
		}
		public class Topnews{
			public boolean comment;
			public String commentlist;
			public String commenturl;
			public int id;
			public String pubdate;
			public String title;
			public String topimage;
			public String type;
			public String url;
		}
	}
}
