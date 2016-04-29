package com.chunfeng.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Sptools {

	public Sptools() {
	}
	
	/**
	 * @param context 需要存放数据的上下文
	 * @param key 存放数据的关键字
	 * @param value 需要存放数据的值
	 */
	public static void setBoolean(Context context,String key, boolean value){
		SharedPreferences sp = context.getSharedPreferences(MyConstants.SAVED_DATA, Context.MODE_PRIVATE);
		sp.edit().putBoolean(key, value).commit(); 	//不要忘记commit操作
		boolean result = sp.getBoolean(key, false);
		System.out.println("setBoolean result = " + result);
	}

	
	/**
	 * @param context 需要获取数据的上下文
	 * @param key 用于存储数据的关键字
	 * @param defaultvalue 默认值
	 * @return
	 */
	public static boolean getBoolean(Context context,String key,boolean defaultvalue){
		SharedPreferences sp = context.getSharedPreferences(MyConstants.SAVED_DATA, Context.MODE_PRIVATE);
		boolean result = sp.getBoolean(key, false);
		System.out.println("getBoolean result = " + result);
		return result;
	}
}
