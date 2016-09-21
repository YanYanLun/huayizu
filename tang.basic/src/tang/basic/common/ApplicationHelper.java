package tang.basic.common;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.location.LocationManager;
import android.net.DhcpInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class ApplicationHelper {
	private Context app;

	public ApplicationHelper(Context _app) {
		app = _app.getApplicationContext();
	}

	public int getVersionCode() {

		String packageName = app.getPackageName();
		PackageManager pm = app.getPackageManager();
		PackageInfo packageInfo;
		try {
			packageInfo = pm.getPackageInfo(packageName, 0);
			return packageInfo.versionCode;
		} catch (NameNotFoundException e) {
			return 0;
		}
	}

	public String getVersionName() {
		String packageName = app.getPackageName();
		PackageManager pm = app.getPackageManager();
		PackageInfo packageInfo;
		try {
			packageInfo = pm.getPackageInfo(packageName, 0);
			return packageInfo.versionName;
		} catch (NameNotFoundException e) {
			return "";
		}
	}

	public String getIdentification() {
		String id = "";
		TelephonyManager tm = (TelephonyManager) app
				.getSystemService(Context.TELEPHONY_SERVICE);
		id += tm.getDeviceId();
		id += Secure.getString(app.getContentResolver(), Secure.ANDROID_ID);
		id += android.os.Build.SERIAL;
		return InputFormat.MD5(id);
	}

	/**
	 * a��ȡЭ��id
	 */
	public int getAgreementid() {
		ApplicationInfo info;
		try {
			String packageName = app.getPackageName();
			PackageManager pm = app.getPackageManager();
			info = pm.getApplicationInfo(packageName,
					PackageManager.GET_META_DATA);
			int agreementid = info.metaData.getInt("agreementid");
			return agreementid;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * a��ȡЭ��key
	 */
	public String getAgreementkey() {
		ApplicationInfo info;
		try {
			String packageName = app.getPackageName();
			PackageManager pm = app.getPackageManager();
			info = pm.getApplicationInfo(packageName,
					PackageManager.GET_META_DATA);
			String agreementkey = info.metaData.getString("agreementkey");
			return agreementkey;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * a��ȡapp����
	 */
	public String getApptitle() {
		ApplicationInfo info;
		try {
			String packageName = app.getPackageName();
			PackageManager pm = app.getPackageManager();
			info = pm.getApplicationInfo(packageName,
					PackageManager.GET_META_DATA);
			String title = info.metaData.getString("apptitle");
			return title;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * ��ȡwifi��ַ
	 * 
	 * @return
	 */
	public String WiFiMacAddress() {
		String result = null;
		try {
			WifiManager wifi = (WifiManager) this.app
					.getSystemService(Context.WIFI_SERVICE);

			WifiInfo info = wifi.getConnectionInfo();
			result = info.getMacAddress();
		} catch (Exception e) {
			result = null;
		}
		return result;
	}

	/**
	 * ��ȡ�ֻ�IMEI
	 * 
	 * @return
	 */
	public String PhoneIMEI() {
		try {
			TelephonyManager telephonyManager = (TelephonyManager) app
					.getSystemService(Context.TELEPHONY_SERVICE);
			return telephonyManager.getDeviceId();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * �ж�GPS�Ƿ��
	 */
	public boolean GpsIsOpen() {
		LocationManager locationManager = (LocationManager) app
				.getSystemService(Context.LOCATION_SERVICE);
		boolean bool = locationManager
				.isProviderEnabled(LocationManager.GPS_PROVIDER);
		return bool;
	}

	/**
	 * ��ȡwifi����
	 * 
	 * @return
	 */
	public String WiFiGateway() {
		String result = null;
		try {
			WifiManager wifi = (WifiManager) this.app
					.getSystemService(Context.WIFI_SERVICE);

			DhcpInfo di = wifi.getDhcpInfo();
			long getewayIpL = di.gateway;
			result = long2ip(getewayIpL);// ���ص�ַ
		} catch (Exception e) {
			result = null;
		}
		return result;
	}

	String long2ip(long ip) {
		StringBuffer sb = new StringBuffer();
		sb.append(String.valueOf((int) (ip & 0xff)));
		sb.append('.');
		sb.append(String.valueOf((int) ((ip >> 8) & 0xff)));
		sb.append('.');
		sb.append(String.valueOf((int) ((ip >> 16) & 0xff)));
		sb.append('.');
		sb.append(String.valueOf((int) ((ip >> 24) & 0xff)));
		return sb.toString();
	}
}
