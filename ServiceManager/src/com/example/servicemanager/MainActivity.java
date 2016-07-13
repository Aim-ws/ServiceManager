package com.example.servicemanager;

import java.util.ArrayList;
import java.util.List;

import com.example.servicemanager.bean.AppBeanInfo;
import com.example.servicemanager.load.AppsAdapter;
import com.example.servicemanager.load.LoadAppsTask;
import com.example.servicemanager.load.OnObtainAppsInterface;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class MainActivity extends Activity {
	private ListView mListView;
	private AppsAdapter adapter;
	private List<AppBeanInfo> mAppBeans;
	private ProgressDialog mProgressDialog;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mListView = (ListView) findViewById(R.id.lv);
		mAppBeans = new ArrayList<AppBeanInfo>();
		adapter = new AppsAdapter(this, mAppBeans);
		mListView.setAdapter(adapter);
		mProgressDialog = new ProgressDialog(this, AlertDialog.THEME_HOLO_LIGHT);
		mProgressDialog.setCancelable(false);
		
		new LoadAppsTask(this, new OnObtainAppsInterface() {
			
			@Override
			public void onObtainSuccess(List<AppBeanInfo> appBeans, List<AppBeanInfo> activeBeans) {
				// TODO Auto-generated method stub
				mProgressDialog.dismiss();
				Log.i("", ""+appBeans.size());
				mAppBeans.clear();
				mAppBeans.addAll(appBeans);
				adapter.notifyDataSetChanged();
			}
			
			@Override
			public void onObtainStart() {
				// TODO Auto-generated method stub
				mProgressDialog.setMessage("应用加载中...");
				mProgressDialog.show();
				
			}
			
			@Override
			public void onObtainFailed() {
				// TODO Auto-generated method stub
				mProgressDialog.dismiss();
			}
		}).run();
	}
	
	
}
