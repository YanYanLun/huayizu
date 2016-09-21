package tang.basic.baseactivity;

import java.util.ArrayList;
import java.util.List;

import tang.basic.common.ActivityUtil;
import tang.basic.common.NetBrowse;
import tang.basic.common.StringUtil;
import tang.basic.exception.ExceptionApplication;
import tang.basic.netstate.TANGNetWorkUtil.netType;
import tang.basic.start.ActivityStart;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;

import com.gc.materialdesign.views.LayoutRipple;
import com.nineoldandroids.view.ViewHelper;

public abstract class TANGFragmentActivity extends FragmentActivity {
	private static final int DIALOG_ID_PROGRESS_DEFAULT = 0x174980;

	protected abstract int layoutID();

	protected abstract View layoutView();

	protected abstract void fouseChange();

	public boolean isHaveNet = false;
	protected ActivityUtil Util;
	private List<BroadcastReceiver> broadcastReceivers;
	protected boolean isFouse = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			notifiyApplicationActivityCreated();
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.onCreate(savedInstanceState);
		Util = new ActivityUtil(this);
		broadcastReceivers = new ArrayList<BroadcastReceiver>();
		QueryPower();
	}

	public void doActivity(Class<? extends Activity> clz) {
		showProgress();
		if (clz != null) {
			ActivityStart.Start(this, clz);
		}
	}

	public void doActivity(Class<? extends Activity> clz, Bundle extras) {
		if (clz != null) {
			ActivityStart.Start(this, clz, extras);
		}
	}

	public ExceptionApplication getTangApplication() {
		return (ExceptionApplication) getApplication();
	}

	private void notifiyApplicationActivityCreated() {
		getTangApplication().onActivityFragmentCreated(this);
	}

	/**
	 * 需要自定义进度条，请重写
	 */
	public void showProgress() {
		showDialog(DIALOG_ID_PROGRESS_DEFAULT);
	}

	/**
	 * 隐藏进度跳，需要重写的请重写
	 */
	public void hideProgress() {
		try {
			removeDialog(DIALOG_ID_PROGRESS_DEFAULT);
		} catch (IllegalArgumentException iae) {
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		// 上一个Activity销毁返回后重新注册网络监听对应的Activity
		notifiyApplicationActivityCreated();
	}

	@Override
	protected void onStop() {
		super.onStop();
		// Activity跳转后停止过度加载
		hideProgress();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			this.finish();
			break;

		default:
			break;
		}
		return true;
	}

	@Override
	protected Dialog onCreateDialog(int id, Bundle args) {
		switch (id) {
		case DIALOG_ID_PROGRESS_DEFAULT:
			ProgressDialog dlg = new ProgressDialog(this);
			dlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			dlg.setMessage("正在加载...");
			dlg.setCancelable(true);
			return dlg;
		default:
			return super.onCreateDialog(id);
		}
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_ID_PROGRESS_DEFAULT:
			ProgressDialog dlg = new ProgressDialog(this);
			dlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			dlg.setMessage("正在加载...");
			dlg.setCancelable(true);
			return dlg;
		default:
			return super.onCreateDialog(id);
		}
	}

	/**
	 * 网络连接连接时调用
	 */
	public void onConnect(netType type) {

	}

	/**
	 * 网络连接连接时调用
	 */
	public void onConnect() {

	}

	/**
	 * 当前没有网络连接
	 */
	public void onDisConnect() {

	}

	/**
	 * 查询手机网络权限
	 */
	public void QueryPower() {
		try {
			int netType = NetBrowse.isNetType(this);
			if (netType == -1) {
				NoInternet();
				isHaveNet = false;
			} else if (netType == 0) {
				IsMobileNet();
				isHaveNet = true;
			} else if (netType == 1) {
				IsWifiNet();
				isHaveNet = true;
			}
		} catch (Exception e) {
			e.getMessage();
		}
	}

	/**
	 * 没有网络
	 */
	public void NoInternet() {
		if (layoutID() != 0) {
			setContentView(layoutID());
		} else {
			setContentView(layoutView());
		}
	};

	/**
	 * 手机网络
	 */
	public void IsMobileNet() {
		setContentView(layoutID());
	};

	/**
	 * wifi
	 */
	public void IsWifiNet() {
		setContentView(layoutID());
	};

	/**
	 * 无网络状态
	 */
	public void NoWifi(int id) {
		this.findViewById(id).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				openWifi();
				TANGFragmentActivity.this.finish();
			}
		});
	}

	public void openWifi() {
		// 直接调用系统自带的WIFI设置界面与Android的版本有关系
		// 在Android版本10以下，调用的是：ACTION_WIRELESS_SETTINGS，版本在10以上的调用：ACTION_SETTINGS。
		// 代码如下：
		if (android.os.Build.VERSION.SDK_INT > 10) {
			// 3.0以上打开设置界面，也可以直接用ACTION_WIRELESS_SETTINGS打开到wifi界面
			this.startActivity(new Intent(
					android.provider.Settings.ACTION_SETTINGS));
		} else {
			this.startActivity(new Intent(
					android.provider.Settings.ACTION_WIRELESS_SETTINGS));
		}

	}

	protected void setOriginRiple(final LayoutRipple layoutRipple,
			final String color) {

		layoutRipple.post(new Runnable() {

			@Override
			public void run() {
				View v = layoutRipple.getChildAt(0);
				layoutRipple.setxRippleOrigin(ViewHelper.getX(v) + v.getWidth()
						/ 2);
				layoutRipple.setyRippleOrigin(ViewHelper.getY(v)
						+ v.getHeight() / 2);
				if (StringUtil.isEmpty(color)) {
					layoutRipple.setRippleColor(Color.parseColor("#1E88E5"));
				} else {
					layoutRipple.setRippleColor(Color.parseColor(color));
				}

				layoutRipple.setRippleSpeed(30);
			}
		});

	}

	@Override
	public Intent registerReceiver(BroadcastReceiver receiver,
			IntentFilter filter) {
		try {
			if (receiver != null) {
				broadcastReceivers.add(receiver);
			}
		} catch (Exception e) {
			e.getMessage();
		}
		return super.registerReceiver(receiver, filter);
	}

	@Override
	protected void onDestroy() {
		if (broadcastReceivers != null) {
			for (BroadcastReceiver receiver : broadcastReceivers) {
				try {
					unregisterReceiver(receiver);
				} catch (Exception e) {
					e.getMessage();
				}
			}
		}
		super.onDestroy();
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		if (isFouse) {
			fouseChange();
			isFouse = false;
		}
	}
}
