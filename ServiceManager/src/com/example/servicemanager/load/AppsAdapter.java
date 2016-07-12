package com.example.servicemanager.load;

import java.util.List;

import com.example.servicemanager.R;
import com.example.servicemanager.bean.AppBeanInfo;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AppsAdapter extends BaseAdapter{
	private Context mContext;
	private List<AppBeanInfo> appBeans;
	private LayoutInflater mInflater;
	private PackageManager packageManager;
	
	public AppsAdapter(Context context,List<AppBeanInfo> infos) {
		// TODO Auto-generated constructor stub
		mInflater = LayoutInflater.from(context);
		packageManager = context.getPackageManager();
		this.appBeans = infos;
		this.mContext = context;
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return appBeans.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return appBeans.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.row_app, null);
			viewHolder = new ViewHolder();
			viewHolder.mIvAppIcon = (ImageView) convertView.findViewById(R.id.iv_app_icon);
			viewHolder.mTvAppName = (TextView) convertView.findViewById(R.id.tv_app_name);
			viewHolder.mTvAppDate = (TextView) convertView.findViewById(R.id.tv_app_date);
			viewHolder.mTvAppType = (TextView) convertView.findViewById(R.id.tv_app_type);
			viewHolder.mTvAppPkg = (TextView) convertView.findViewById(R.id.tv_app_pkg);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		AppBeanInfo info = appBeans.get(position);
		viewHolder.mTvAppName.setText(info.app_name);
		viewHolder.mTvAppType.setText(info.app_app_type);
		viewHolder.mTvAppPkg.setText(info.app_pkg);
		viewHolder.mTvAppDate.setText(DateFormat.format("yyyy-MM-dd hh:MM:ss", info.app_install_date));
		try {
			Drawable drawable = packageManager.getApplicationIcon(info.app_pkg);
			viewHolder.mIvAppIcon.setImageDrawable(drawable);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return convertView;
	}
	
	static class ViewHolder{
		public ImageView mIvAppIcon;
		public TextView mTvAppName;
		public TextView mTvAppType;
		public TextView mTvAppPkg;
		public TextView mTvAppDate;
	}

}
