package com.example.servicemanager.load;

import java.util.List;

import com.example.servicemanager.bean.AppBeanInfo;

public interface OnObtainAppsInterface {
	
	void onObtainStart();
	
	void onObtainFailed();
	
	void onObtainSuccess(List<AppBeanInfo> appBeans,List<AppBeanInfo> activeBeans);

}
