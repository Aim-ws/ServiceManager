package com.example.servicemanager.load;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.servicemanager.bean.AppBeanInfo;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class LoadAppsTask {
	private static final String TAG = "LoadAppsTask";
	private OnObtainAppsInterface mOnObtainAppsInterface;
	private List<AppBeanInfo> appBeanInfos;
	private List<AppBeanInfo> activeBeanInfos;
	private Context mContext;

	public LoadAppsTask(Context context, OnObtainAppsInterface appsInterface) {
		// TODO Auto-generated constructor stub
		this.mOnObtainAppsInterface = appsInterface;
		this.mContext = context;
		appBeanInfos = new ArrayList<AppBeanInfo>();
		activeBeanInfos = new ArrayList<AppBeanInfo>();
	}
	
	public void run(){
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				updateHandler.sendEmptyMessage(WHAT_START); // 开始
				try {
					Thread.sleep(2000);
					PackageManager pManager = mContext.getPackageManager();
					List<PackageInfo> packageInfos = pManager.getInstalledPackages(PackageManager.GET_UNINSTALLED_PACKAGES); // 获取所有已安装的应用包名
					for (int i = 0; i < packageInfos.size(); i++) {
						PackageInfo packageInfo = packageInfos.get(i);
						AppBeanInfo appBeanInfo = new AppBeanInfo();
						appBeanInfo.app_name = (String) packageInfo.applicationInfo.loadLabel(pManager);
						appBeanInfo.app_install_date = packageInfo.firstInstallTime;
						appBeanInfo.app_pkg = packageInfo.applicationInfo.packageName;
						appBeanInfo.app_icon = packageInfo.applicationInfo.icon;
						appBeanInfo.app_version = packageInfo.versionName;
						appBeanInfo.app_version_code = packageInfo.versionCode;
						if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
							// 非系统应用
							appBeanInfo.app_app_type = 1;
						} else {
							// 系统应用
							appBeanInfo.app_app_type = 0;
						}
						appBeanInfos.add(appBeanInfo);
					}

					Map<String, ActivityManager.RunningAppProcessInfo> runningAppMap = new HashMap<String, ActivityManager.RunningAppProcessInfo>();
					ActivityManager activityManager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
					List<RunningAppProcessInfo> processInfos = activityManager.getRunningAppProcesses();
					for (int i = 0; i < processInfos.size(); i++) {
						RunningAppProcessInfo info = processInfos.get(i);
						String[] pkgList = info.pkgList;
						for (int j = 0; j < pkgList.length; j++) {
							String pkgName = pkgList[j];
							runningAppMap.put(pkgName, info);
						}
					}

					for (int i = 0; i < packageInfos.size(); i++) {
						PackageInfo packageInfo = packageInfos.get(i);
						if (runningAppMap.containsKey(packageInfo.packageName)) {
							AppBeanInfo appBeanInfo = new AppBeanInfo();
							appBeanInfo.app_name = (String) packageInfo.applicationInfo.loadLabel(pManager);
							appBeanInfo.app_install_date = packageInfo.firstInstallTime;
							appBeanInfo.app_pkg = packageInfo.applicationInfo.packageName;
							appBeanInfo.app_icon = packageInfo.applicationInfo.icon;
							appBeanInfo.app_version = packageInfo.versionName;
							appBeanInfo.app_version_code = packageInfo.versionCode;
							appBeanInfo.app_app_type = 2;
							activeBeanInfos.add(appBeanInfo);
						}
					}
					
					Collections.sort(appBeanInfos, new SortComparator());
					updateHandler.sendEmptyMessage(WHAT_SUCCESS);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					updateHandler.sendEmptyMessage(WHAT_FAILED);
				}
			}
		}).start();
	}
	
	class SortComparator implements Comparator<AppBeanInfo>{

		@Override
		public int compare(AppBeanInfo lhs, AppBeanInfo rhs) {
			// TODO Auto-generated method stub
			if (lhs.app_install_date > rhs.app_install_date) {
				return -1;
			}
			return 1;
		}
		
	}

	private static final int WHAT_START = 0x01;
	private static final int WHAT_FAILED = 0x02;
	private static final int WHAT_SUCCESS = 0x03;
	private Handler updateHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			Log.i(TAG, msg.toString());
			switch (msg.what) {
			case WHAT_START:
				Log.i(TAG, "开始");
				if (mOnObtainAppsInterface != null) {
					mOnObtainAppsInterface.onObtainStart();
				}
				break;
			case WHAT_SUCCESS:
				Log.i(TAG, "成功");
				if (mOnObtainAppsInterface != null) {
					mOnObtainAppsInterface.onObtainSuccess(appBeanInfos, activeBeanInfos);
				}
				break;
			case WHAT_FAILED:
				Log.i(TAG, "失败");
				if (mOnObtainAppsInterface != null) {
					mOnObtainAppsInterface.onObtainFailed();
				}
				break;

			default:
				break;
			}
		}

	};

}
