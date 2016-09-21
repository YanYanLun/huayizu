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
	 * ����Ƿ�������
	 */
	public static boolean isConn(Context context) {
		boolean bisConnFlag = false;
		ConnectivityManager conManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo network = conManager.getActiveNetworkInfo();
		if (network != null) {
			bisConnFlag = network.isAvailable();
			network.getType();// 0��ʾ�������ӣ�1��ʾwife����
		}
		return bisConnFlag;
	}

	/**
	 * 0��ʾ�������ӣ�1��ʾwifi���磬 ������������ݻ���wifi
	 */
	public static int isNetType(Context context) {
		int bisConnFlag = -1;
		ConnectivityManager conManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo network = conManager.getActiveNetworkInfo();
		if (network != null) {
			bisConnFlag = network.getType();// 0��ʾ�������ӣ�1��ʾwifi����
		}
		return bisConnFlag;
	}

	public static void WIFI_T(Context context, boolean enabled) {
		try {
			WifiMana = (WifiManager) context
					.getSystemService(Context.WIFI_SERVICE);
			if (WifiMana.isWifiEnabled()) {
				WifiMana.setWifiEnabled(enabled);// �ر�wifi false
			} else {
				WifiMana.setWifiEnabled(enabled);// ��wifi true
			}
		} catch (Exception e) {
		}
	}

	/**
	 * �򿪻��߹ر��ƶ�����
	 */
	public static void Mobile_Too(Context context, boolean enabled) {
		conMgre = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		Class<?> conMgrClass = null; // ConnectivityManager��
		Field iConMgrField = null; // ConnectivityManager���е��ֶ�
		Object iConMgr = null; // IConnectivityManager�������
		Class<?> iConMgrClass = null; // IConnectivityManager��
		Method setMobileDataEnabledMethod = null; // setMobileDataEnabled����

		try {
			// ȡ��ConnectivityManager��
			conMgrClass = Class.forName(conMgre.getClass().getName());
			// ȡ��ConnectivityManager���еĶ���mService
			iConMgrField = conMgrClass.getDeclaredField("mService");
			// ����mService�ɷ���
			iConMgrField.setAccessible(true);
			// ȡ��mService��ʵ������IConnectivityManager
			iConMgr = iConMgrField.get(conMgre);
			// ȡ��IConnectivityManager��
			iConMgrClass = Class.forName(iConMgr.getClass().getName());
			// ȡ��IConnectivityManager���е�setMobileDataEnabled(boolean)����
			setMobileDataEnabledMethod = iConMgrClass.getDeclaredMethod(
					"setMobileDataEnabled", Boolean.TYPE);
			// ����setMobileDataEnabled�����ɷ���
			setMobileDataEnabledMethod.setAccessible(true);
			// ����setMobileDataEnabled����
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
