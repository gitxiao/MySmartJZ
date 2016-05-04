/**
 * 
 */
package com.chunfeng.utils;

/**
 * @author Cfrjkj
 * @date 2016-5-3
 * @time 下午8:24:03
 * @todo TODO
 */
public class MyUtils {
	/**
	 * 删除一个字符串中指定的字符
	 * @param sign
	 * @return
	 */
	public static String getSubStringBut(String strOri,String sign){
		
		System.out.println("before strOri = " + strOri);
		int position = strOri.indexOf(sign);
		while(position >= 0){
			strOri = strOri.substring(0,position) + strOri.substring(position + 1, strOri.length());
			position = strOri.indexOf(sign);
		}
		System.out.println("after strOri = " + strOri);
		return strOri;
	}
	
	
	
//	/**
//	 * 删除一个字符串中指定的字符
//	 * @param sign
//	 * @return
//	 */
//	public static String replaceWithIfHave(String haveStr,String sign){
//		sign.r
//		System.out.println("before strOri = " + strOri);
//		int position = strOri.indexOf(sign);
//		while(position >= 0){
//			strOri = strOri.substring(0,position) + strOri.substring(position + 1, strOri.length());
//			position = strOri.indexOf(sign);
//		}
//		System.out.println("after strOri = " + strOri);
//		return strOri;
//	}
	
}
