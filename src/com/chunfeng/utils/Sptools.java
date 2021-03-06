﻿package com.chunfeng.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SPTools {

	public SPTools() {
	}
	
	/**
	 * @param context 需要存放数据的上下文
	 * @param key 存放数据的关键字
	 * @param value 需要存放数据的值
	 */
	public static void setBoolean(Context context,String key, boolean value){
		SharedPreferences sp = context.getSharedPreferences(MyUtils.getSubStringBut(key,"/"), Context.MODE_PRIVATE);
		sp.edit().putBoolean(MyUtils.getSubStringBut(key,"/"), value).commit(); 	//不要忘记commit操作
	}

	
	/**
	 * @param context 需要获取数据的上下文
	 * @param key 用于存储数据的关键字
	 * @param defaultvalue 默认值
	 * @return
	 */
	public static boolean getBoolean(Context context,String key,boolean defaultvalue){
		SharedPreferences sp = context.getSharedPreferences(MyUtils.getSubStringBut(key,"/"), Context.MODE_PRIVATE);
		boolean result = sp.getBoolean(MyUtils.getSubStringBut(key,"/"), defaultvalue);
		return result;
	}
	
	/**
	 * @param context 需要存放数据的上下文
	 * @param key 存放数据的关键字
	 * @param value 需要存放数据的值
	 */
	public static void setString(Context context,String key, String value){
		SharedPreferences sp = context.getSharedPreferences(MyUtils.getSubStringBut(key,"/"), Context.MODE_PRIVATE);
		sp.edit().putString(MyUtils.getSubStringBut(key,"/"), value).commit(); 	//不要忘记commit操作
	}

	
	/**
	 * @param context 需要获取数据的上下文
	 * @param key 用于存储数据的关键字
	 * @param defaultvalue 默认值
	 * @return
	 */
	public static String getString(Context context,String key,String defaultvalue){
		System.out.println("key = " + key);
		SharedPreferences sp = context.getSharedPreferences(MyUtils.getSubStringBut(key,"/"), Context.MODE_PRIVATE);
		String result = sp.getString(MyUtils.getSubStringBut(key,"/"),defaultvalue);
		return result;
	}

	/**
	 * @param context 需要存放数据的上下文
	 * @param key 存放数据的关键字
	 * @param value 需要存放数据的值
	 */
	public static void setInt(Context context,String key, int value){
		SharedPreferences sp = context.getSharedPreferences(MyUtils.getSubStringBut(key,"/"), Context.MODE_PRIVATE);
		sp.edit().putInt(MyUtils.getSubStringBut(key,"/"), value).commit(); 	//不要忘记commit操作
	}
	
	
	/**
	 * @param context 需要获取数据的上下文
	 * @param key 用于存储数据的关键字
	 * @param defaultvalue 默认值
	 * @return
	 */
	public static int getInt(Context context,String key,int defaultvalue){
		SharedPreferences sp = context.getSharedPreferences(MyUtils.getSubStringBut(key,"/"), Context.MODE_PRIVATE);
		int result = sp.getInt(MyUtils.getSubStringBut(key,"/"),defaultvalue);
		return result;
	}
	
}
