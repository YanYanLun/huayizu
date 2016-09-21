package tang.basic.common;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.TypedValue;

public class Utils {
	public static final String TAG = "PushDemoActivity";
	public static final String RESPONSE_METHOD = "method";
	public static final String RESPONSE_CONTENT = "content";
	public static final String RESPONSE_ERRCODE = "errcode";
	protected static final String ACTION_LOGIN = "com.baidu.pushdemo.action.LOGIN";
	public static final String ACTION_MESSAGE = "com.baiud.pushdemo.action.MESSAGE";
	public static final String ACTION_RESPONSE = "bccsclient.action.RESPONSE";
	public static final String ACTION_SHOW_MESSAGE = "bccsclient.action.SHOW_MESSAGE";
	protected static final String EXTRA_ACCESS_TOKEN = "access_token";
	public static final String EXTRA_MESSAGE = "message";

	public static String logStringCache = "";

	// 获取ApiKey
	public static String getMetaValue(Context context, String metaKey) {
		Bundle metaData = null;
		String apiKey = null;
		if (context == null || metaKey == null) {
			return null;
		}
		try {
			ApplicationInfo ai = context.getPackageManager()
					.getApplicationInfo(context.getPackageName(),
							PackageManager.GET_META_DATA);
			if (null != ai) {
				metaData = ai.metaData;
			}
			if (null != metaData) {
				apiKey = metaData.getString(metaKey);
			}
		} catch (NameNotFoundException e) {

		}
		return apiKey;
	}

	// 获取ApiKey
	public static int getMetaIntValue(Context context, String metaKey) {
		Bundle metaData = null;
		int apiKey = 0;
		if (context == null || metaKey == null) {
			return 0;
		}
		try {
			ApplicationInfo ai = context.getPackageManager()
					.getApplicationInfo(context.getPackageName(),
							PackageManager.GET_META_DATA);
			if (null != ai) {
				metaData = ai.metaData;
			}
			if (null != metaData) {
				apiKey = metaData.getInt(metaKey);
			}
		} catch (NameNotFoundException e) {

		}
		return apiKey;
	}

	// 用share preference来实现是否绑定的开关。在ionBind且成功时设置true，unBind且成功时设置false
	public static boolean hasBind(Context context) {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(context);
		String flag = sp.getString("bind_flag", "");
		if ("ok".equalsIgnoreCase(flag)) {
			return true;
		}
		return false;
	}

	public static void setBind(Context context, boolean flag) {
		String flagStr = "not";
		if (flag) {
			flagStr = "ok";
		}
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(context);
		Editor editor = sp.edit();
		editor.putString("bind_flag", flagStr);
		editor.commit();
	}

	public static List<String> getTagsList(String originalText) {
		if (originalText == null || originalText.equals("")) {
			return null;
		}
		List<String> tags = new ArrayList<String>();
		int indexOfComma = originalText.indexOf(',');
		String tag;
		while (indexOfComma != -1) {
			tag = originalText.substring(0, indexOfComma);
			tags.add(tag);

			originalText = originalText.substring(indexOfComma + 1);
			indexOfComma = originalText.indexOf(',');
		}

		tags.add(originalText);
		return tags;
	}

	public static String getLogText(Context context) {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(context);
		return sp.getString("log_text", "");
	}

	public static void setLogText(Context context, String text) {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(context);
		Editor editor = sp.edit();
		editor.putString("log_text", text);
		editor.commit();
	}

	/**
	 * 生成UUID
	 * 
	 * @return
	 */
	public static String getUUID() {
		String s = UUID.randomUUID().toString();// 用来生成数据库的主键id非常不错。。
		return s;
	}

	/**
	 * 获取手机状态栏高度
	 * 
	 * @param context
	 * @return
	 */
	public static int getStatusBarHeight(Context context) {
		Class<?> c = null;
		Object obj = null;
		Field field = null;
		int x = 0, statusBarHeight = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			statusBarHeight = context.getResources().getDimensionPixelSize(x);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return statusBarHeight;
	}

	/**
	 * 获取ActionBar的高度
	 * 
	 * @param context
	 * @return
	 */
	public static int getActionBarHeight(Context context) {
		TypedValue tv = new TypedValue();
		int actionBarHeight = 0;
		if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize,
				tv, true))// 如果资源是存在的、有效的
		{
			actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,
					context.getResources().getDisplayMetrics());
		}
		return actionBarHeight;
	}

	/**
	 * 获取随机颜色
	 * 
	 * @return
	 */
	public static int getRandomColor() {
		return Color.argb(255, rndInt(0, 255), rndInt(0, 255), rndInt(0, 255));
	}

	static int rndInt(int min, int max) {
		return (int) (min + Math.random() * (max - min + 1));
	}

	public static String getCurrentTime(String format) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
		String currentTime = sdf.format(date);
		return currentTime;
	}

	public static String getCurrentTime() {
		return getCurrentTime("yyyy-MM-dd  HH:mm:ss");
	}

	/**
	 * 半角字符转全角
	 * 
	 * @param input
	 * @return
	 */
	public static String ToDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}

	/**
	 * 判断是否为最新版本方法 将版本号根据.切分为int数组 比较
	 *
	 * @param localVersion
	 *            本地版本号
	 * @param onlineVersion
	 *            线上版本号
	 * @return
	 */
	public static boolean isAppNewVersion(String localVersion,
			String onlineVersion) {
		if (localVersion.equals(onlineVersion)) {
			return false;
		}
		String[] localArray = localVersion.split("\\.");
		String[] onlineArray = onlineVersion.split("\\.");

		int length = localArray.length < onlineArray.length ? localArray.length
				: onlineArray.length;

		for (int i = 0; i < length; i++) {
			if (Integer.parseInt(onlineArray[i]) > Integer
					.parseInt(localArray[i])) {
				return true;
			} else if (Integer.parseInt(onlineArray[i]) < Integer
					.parseInt(localArray[i])) {
				return false;
			}
			// 相等 比较下一组值
		}

		return true;
	}
}
