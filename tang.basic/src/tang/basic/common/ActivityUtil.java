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
	 * ��ȡandroid SDK�汾��
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

	// �����쳣�������
	public void handlerTxException(TxException e) {
		if (e.getMessage().equals("�����ѶϿ�") == false) {
			Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT)
					.show();
		}
	}

	public void handlerFailResponse(ResponseBase response) {
		// if (response.StatusCode == 1)
		// return;
		// if (StringUtil.isEmpty(response.StatusMessage))
		// return;
		// if (response.StatusMessage.contains("�Ż�ȯ")
		// || response.StatusMessage.contains("��Ա��")
		// || response.StatusMessage.contains("ԤԼ��ʷ"))
		// return;
		Toast.makeText(getContext(), response.StatusMessage, Toast.LENGTH_SHORT).show();
		/**
		 * ��¼��ʱ(Sessionkey����,�˺��ڱ�Ļ��ϵ�¼)
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
	 * �����ý���
	 */
	public void openSetting() {
		try {
			// ֱ�ӵ���ϵͳ�Դ���WIFI���ý�����Android�İ汾�й�ϵ
			// ��Android�汾10���£����õ��ǣ�ACTION_WIRELESS_SETTINGS���汾��10���ϵĵ��ã�ACTION_SETTINGS��
			// �������£�
			if (android.os.Build.VERSION.SDK_INT > 10) {
				// 3.0���ϴ����ý��棬Ҳ����ֱ����ACTION_WIRELESS_SETTINGS�򿪵�wifi����
				_context.startActivity(new Intent(
						android.provider.Settings.ACTION_SETTINGS));
			} else {
				_context.startActivity(new Intent(
						android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
			}
		} catch (Exception e) {
			// ֱ�ӵ���ϵͳ�Դ���WIFI���ý�����Android�İ汾�й�ϵ
			// ��Android�汾10���£����õ��ǣ�ACTION_WIRELESS_SETTINGS���汾��10���ϵĵ��ã�ACTION_SETTINGS��
			// �������£�
			if (android.os.Build.VERSION.SDK_INT > 10) {
				// 3.0���ϴ����ý��棬Ҳ����ֱ����ACTION_WIRELESS_SETTINGS�򿪵�wifi����
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
	 * ����android�汾�Ƿ����3.0
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
