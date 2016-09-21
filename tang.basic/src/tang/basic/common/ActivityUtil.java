package tang.basic.common;

import tang.basic.http.RequestBase;
import tang.basic.http.ResponseBase;
import tang.basic.http.TxException;
import tang.basic.sql.MSQLiteDAO;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ActivityUtil {
	Activity _activity;
	Context _context;
	MSQLiteDAO dao;
	ApplicationHelper applicationHelper;
	WaitingManager wManager;
	/**
	 * 获取android SDK版本号
	 */
	public int currentapiVersion = android.os.Build.VERSION.SDK_INT;

	public ActivityUtil(Activity activity) {
		_activity = activity;
		dao = new MSQLiteDAO(_activity);
		applicationHelper = new ApplicationHelper(_activity);
		wManager = new WaitingManager(_activity);

	}

	public ActivityUtil(Context context) {
		_context = context;
		dao = new MSQLiteDAO(_context);
		applicationHelper = new ApplicationHelper(_context);
		wManager = new WaitingManager(_context);
	}

	public void fillRequest(RequestBase request) {
		// request.AppVersion = applicationHelper.getVersionName();
		// request.UserID = applicationHelper.WiFiMacAddress();
		// request.PhoneModel = android.os.Build.MODEL;
		// request.PhoneVersion = android.os.Build.VERSION.RELEASE;
	}

	public <T extends RequestBase> T getRequest(Class<T> clazz) {
		try {
			T instance = clazz.newInstance();
			fillRequest(instance);
			return instance;
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Context getApplicationContext() {
		return getContext().getApplicationContext();
	}

	public Context getContext() {
		if (_activity != null)
			return _activity;
		return _context;
	}

	public String getCompletAction(Class<?> clazz) {
		return "OnComplet:" + clazz.getName();
	}

	public String getErrorAction(Class<?> clazz) {
		return "OnError:" + clazz.getName();
	}

	// 处理异常网络错误
	public void handlerTxException(TxException e) {
		if (e.getMessage().equals("网络已断开") == false) {
			Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT)
					.show();
		}
	}

	public void handlerFailResponse(ResponseBase response) {
		// if (response.StatusCode == 1)
		// return;
		// if (StringUtil.isEmpty(response.StatusMessage))
		// return;
		// if (response.StatusMessage.contains("优惠券")
		// || response.StatusMessage.contains("会员卡")
		// || response.StatusMessage.contains("预约历史"))
		// return;
		Toast.makeText(getContext(), response.StatusMessage, Toast.LENGTH_SHORT).show();
		/**
		 * 登录超时(Sessionkey错误,账号在别的机上登录)
		 */
		// if (response.StatusCode == 1003) {
		// Userinfo info = dao.getUserinfo();
		// if (!StringUtil.isEmpty(info.UserCode)) {
		// dao.delete("UserInfo", "UserCode=?",
		// String.valueOf(info.UserCode));
		// }
		// if (_activity != null) {
		// _activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri
		// .parse("ford://login")));
		// } else if (_context != null) {
		// _context.startActivity(new Intent(Intent.ACTION_VIEW, Uri
		// .parse("ford://login")));
		// }
		// }
	}

	public MSQLiteDAO getDao() {
		return dao;
	}

	public ApplicationHelper getApplicationHelper() {
		return applicationHelper;
	}

	/**
	 * 打开设置界面
	 */
	public void openSetting() {
		try {
			// 直接调用系统自带的WIFI设置界面与Android的版本有关系
			// 在Android版本10以下，调用的是：ACTION_WIRELESS_SETTINGS，版本在10以上的调用：ACTION_SETTINGS。
			// 代码如下：
			if (android.os.Build.VERSION.SDK_INT > 10) {
				// 3.0以上打开设置界面，也可以直接用ACTION_WIRELESS_SETTINGS打开到wifi界面
				_context.startActivity(new Intent(
						android.provider.Settings.ACTION_SETTINGS));
			} else {
				_context.startActivity(new Intent(
						android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
			}
		} catch (Exception e) {
			// 直接调用系统自带的WIFI设置界面与Android的版本有关系
			// 在Android版本10以下，调用的是：ACTION_WIRELESS_SETTINGS，版本在10以上的调用：ACTION_SETTINGS。
			// 代码如下：
			if (android.os.Build.VERSION.SDK_INT > 10) {
				// 3.0以上打开设置界面，也可以直接用ACTION_WIRELESS_SETTINGS打开到wifi界面
				_activity.startActivity(new Intent(
						android.provider.Settings.ACTION_SETTINGS));
			} else {
				_activity
						.startActivity(new Intent(
								android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
			}
		}

	}

	public void beginWaiting() {
		wManager.release();
		wManager.begin();
	}

	public void beginWaiting(String text) {
		wManager.release();
		wManager.begin(text);
	}

	public void releaseWaiting() {
		wManager.release();
	}

	/**
	 * 返回android版本是否大于3.0
	 * 
	 * @return
	 */
	public boolean isLowerHoneycomb() {
		if (currentapiVersion > 10) {
			return false;
		} else {
			return true;
		}
	}
}
