package tang.basic.common;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

public class NetBrowse {
	private static final boolean enabled = false;
	private static WifiManager WifiMana;
	private static ConnectivityManager conMgre;

	/**
	 * 
	 * 检查是否有网络
	 */
	public static boolean isConn(Context context) {
		boolean bisConnFlag = false;
		ConnectivityManager conManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo network = conManager.getActiveNetworkInfo();
		if (network != null) {
			bisConnFlag = network.isAvailable();
			network.getType();// 0表示数据连接，1表示wife网络
		}
		return bisConnFlag;
	}

	/**
	 * 0表示数据连接，1表示wifi网络， 检查网络是数据还是wifi
	 */
	public static int isNetType(Context context) {
		int bisConnFlag = -1;
		ConnectivityManager conManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo network = conManager.getActiveNetworkInfo();
		if (network != null) {
			bisConnFlag = network.getType();// 0表示数据连接，1表示wifi网络
		}
		return bisConnFlag;
	}

	public static void WIFI_T(Context context, boolean enabled) {
		try {
			WifiMana = (WifiManager) context
					.getSystemService(Context.WIFI_SERVICE);
			if (WifiMana.isWifiEnabled()) {
				WifiMana.setWifiEnabled(enabled);// 关闭wifi false
			} else {
				WifiMana.setWifiEnabled(enabled);// 打开wifi true
			}
		} catch (Exception e) {
		}
	}

	/**
	 * 打开或者关闭移动网络
	 */
	public static void Mobile_Too(Context context, boolean enabled) {
		conMgre = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		Class<?> conMgrClass = null; // ConnectivityManager类
		Field iConMgrField = null; // ConnectivityManager类中的字段
		Object iConMgr = null; // IConnectivityManager类的引用
		Class<?> iConMgrClass = null; // IConnectivityManager类
		Method setMobileDataEnabledMethod = null; // setMobileDataEnabled方法

		try {
			// 取得ConnectivityManager类
			conMgrClass = Class.forName(conMgre.getClass().getName());
			// 取得ConnectivityManager类中的对象mService
			iConMgrField = conMgrClass.getDeclaredField("mService");
			// 设置mService可访问
			iConMgrField.setAccessible(true);
			// 取得mService的实例化类IConnectivityManager
			iConMgr = iConMgrField.get(conMgre);
			// 取得IConnectivityManager类
			iConMgrClass = Class.forName(iConMgr.getClass().getName());
			// 取得IConnectivityManager类中的setMobileDataEnabled(boolean)方法
			setMobileDataEnabledMethod = iConMgrClass.getDeclaredMethod(
					"setMobileDataEnabled", Boolean.TYPE);
			// 设置setMobileDataEnabled方法可访问
			setMobileDataEnabledMethod.setAccessible(true);
			// 调用setMobileDataEnabled方法
			setMobileDataEnabledMethod.invoke(iConMgr, enabled);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}
