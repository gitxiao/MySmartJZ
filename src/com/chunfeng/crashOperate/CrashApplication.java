/**
 * 
 */
package com.chunfeng.crashOperate;

import android.app.Application;

/**
 * @author Cfrjkj
 * @创建时间 2016年4月27日 下午10:36:15
 * @描述 TODO
 * @svn提交者:$Author$
 * @提交时间: $Date$
 * @当前版本: $Rev$
 */
public class CrashApplication extends Application {
	/* (non-Javadoc)
	 * @see android.app.Application#onCreate()
	 */
	@Override
	public void onCreate() {
		super.onCreate();
		CrashHandler crashHandler = CrashHandler.getInstance(); 
		crashHandler.init(getApplicationContext());
	}
}
