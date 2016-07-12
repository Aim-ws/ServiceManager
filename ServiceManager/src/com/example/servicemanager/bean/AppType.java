package com.example.servicemanager.bean;

import java.util.HashMap;

public class AppType {
	public static HashMap<Integer, String> appType = new HashMap<Integer, String>();
	static{
		appType.put(0, "系统应用");
		appType.put(1, "普通应用");
		appType.put(2, "正在运行的应用");
	}

}
