package com.chunfeng.utils;

public interface MyConstants {

	/**
	 * apk发布时需要修改ip或域名. 用域名会更合理, 因为服务器的ip地址可能会发生变化, 而域名不会轻易变化
	 * 手机上不能用127.0.0.1,因为这不是手机的本机地址, 应该用局域网或公网ip地址
	 */
//	public static final String STR_NEWS_CENTER_ = "http://127.0.0.1:8080/zhbj/";
	public static final String STR_NEWS_CENTER_ = "http://192.168.33.56:8080/zhbj/categories.json";
	public static final String SAVED_DATA = "savedData";
	public static final String IS_SETUP = "isSetUp";//名字
	
	public static final String HTTP_DATA = "httpData";		//用于缓存httpS数据
}
