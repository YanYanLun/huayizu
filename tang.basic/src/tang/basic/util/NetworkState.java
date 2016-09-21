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
	 * 获取移动数据开关状态
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
	 * 通过反射实现开启或关闭移动数据
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
			// 得到ConnectivityManager类的成员变量mService（ConnectivityService类型）
			Field iConMgrField = conMgrClass.getDeclaredField("mService");
			iConMgrField.setAccessible(true);
			// mService成员初始化
			Object iConMgr = iConMgrField.get(mConnectivityManager);
			// 得到mService对应的Class对象
			Class<?> iConMgrClass = Class.forName(iConMgr.getClass().getName());
			/*
			 * 得到mService的setMobileDataEnabled(该方法在android源码的ConnectivityService类中实现
			 * )， 该方法的参数为布尔型，所以第二个参数为Boolean.TYPE
			 */
			Method setMobileDataEnabledMethod = iConMgrClass.getDeclaredMethod(
					"setMobileDataEnabled", Boolean.TYPE);
			setMobileDataEnabledMethod.setAccessible(true);
			/*
			 * 调用ConnectivityManager的setMobileDataEnabled方法（方法是隐藏的），
			 * 实际上该方法的实现是在ConnectivityService(系统服务实现类)中的
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
	 * 开关飞行模式
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
	 * 判断是否为飞行模式
	 * 
	 * @param activity
	 * @return
	 */
	public static boolean isFly(Activity activity) {
		return Settings.System.getInt(activity.getContentResolver(),
				Settings.System.AIRPLANE_MODE_ON, 0) == 1;
	}
}
