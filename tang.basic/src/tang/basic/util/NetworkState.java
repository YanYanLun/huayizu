package tang.basic.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.R.bool;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.provider.Settings;

public class NetworkState {
	/**
	 * ��ȡ�ƶ����ݿ���״̬
	 * 
	 * @param activity
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static boolean getMobileDataStatus(Activity activity) {
		ConnectivityManager mConnectivityManager = (ConnectivityManager) activity
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		String methodName = "getMobileDataEnabled";
		Class cmClass = mConnectivityManager.getClass();
		Boolean isOpen = null;

		try {
			Method method = cmClass.getMethod(methodName, null);

			isOpen = (Boolean) method.invoke(mConnectivityManager, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isOpen;
	}

	/**
	 * ͨ������ʵ�ֿ�����ر��ƶ�����
	 * 
	 * @param enabled
	 * @param activity
	 */
	public static void setMobileDataStatus(boolean enabled, Activity activity) {
		try {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) activity
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			Class<?> conMgrClass = Class.forName(mConnectivityManager
					.getClass().getName());
			// �õ�ConnectivityManager��ĳ�Ա����mService��ConnectivityService���ͣ�
			Field iConMgrField = conMgrClass.getDeclaredField("mService");
			iConMgrField.setAccessible(true);
			// mService��Ա��ʼ��
			Object iConMgr = iConMgrField.get(mConnectivityManager);
			// �õ�mService��Ӧ��Class����
			Class<?> iConMgrClass = Class.forName(iConMgr.getClass().getName());
			/*
			 * �õ�mService��setMobileDataEnabled(�÷�����androidԴ���ConnectivityService����ʵ��
			 * )�� �÷����Ĳ���Ϊ�����ͣ����Եڶ�������ΪBoolean.TYPE
			 */
			Method setMobileDataEnabledMethod = iConMgrClass.getDeclaredMethod(
					"setMobileDataEnabled", Boolean.TYPE);
			setMobileDataEnabledMethod.setAccessible(true);
			/*
			 * ����ConnectivityManager��setMobileDataEnabled���������������صģ���
			 * ʵ���ϸ÷�����ʵ������ConnectivityService(ϵͳ����ʵ����)�е�
			 */
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

	/**
	 * ���ط���ģʽ
	 */
	public static void openFly(Activity activity) {
		try {
			boolean isEnabled = Settings.System.getInt(
					activity.getContentResolver(),
					Settings.System.AIRPLANE_MODE_ON, 0) == 1;
			Settings.System.putInt(activity.getContentResolver(),
					Settings.System.AIRPLANE_MODE_ON, isEnabled ? 0 : 1);
			Intent i = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
			i.putExtra("state", !isEnabled);
			activity.sendBroadcast(i);
		} catch (Exception e) {
			System.out.println("iiiiii");
		}
	}

	/**
	 * �ж��Ƿ�Ϊ����ģʽ
	 * 
	 * @param activity
	 * @return
	 */
	public static boolean isFly(Activity activity) {
		return Settings.System.getInt(activity.getContentResolver(),
				Settings.System.AIRPLANE_MODE_ON, 0) == 1;
	}
}
