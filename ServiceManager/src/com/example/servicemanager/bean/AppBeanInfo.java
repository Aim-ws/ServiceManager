package com.example.servicemanager.bean;

public class AppBeanInfo {
	public String app_name;
	public String app_pkg;
	public String app_version;
	public long app_install_date;
	public int app_app_type;     //0 --- ϵͳӦ�ã�1 --- ��ͨӦ�ã�2 --- �������е�Ӧ��
	public int app_version_code;
	public int app_icon;
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AppBeanInfo [app_name=" + app_name + ", app_pkg=" + app_pkg + ", app_version=" + app_version + ", app_install_date="
				+ app_install_date + ", app_app_type=" + app_app_type + ", app_version_code=" + app_version_code + ", app_icon=" + app_icon
				+ "]";
	}
	
	
	

}
