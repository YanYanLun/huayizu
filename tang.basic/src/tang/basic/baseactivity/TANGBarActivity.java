package tang.basic.baseactivity;

import java.util.ArrayList;
import java.util.List;

import tang.basic.common.ActivityUtil;
import tang.basic.common.NetBrowse;
import tang.basic.common.StringUtil;
import tang.basic.exception.ExceptionApplication;
import tang.basic.netstate.TANGNetWorkUtil.netType;
import tang.basic.start.ActivityStart;
import tang.basic.view.FlipImageView;
import tang.basic.view.FlipImageView.OnFlipListener;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.DecelerateInterpolator;

import com.gc.materialdesign.views.LayoutRipple;
import com.nineoldandroids.view.ViewHelper;

public abstract class TANGBarActivity extends ActionBarActivity implements
		OnFlipListener, OnClickListener {
	private static final int DIALOG_ID_PROGRESS_DEFAULT = 0x174980;

	protected abstract int layoutID();

	protected abstract View layoutView();

	protected abstract void fouseChange();

	public boolean isHaveNet = false;
	private List<BroadcastReceiver> broadcastReceivers;
	protected ActivityUtil Util;
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
		getTangApplication().onActivityBarCreated(this);
	}

	/**
	 * ��Ҫ�Զ��������������д
	 */
	protected void showProgress() {
		showDialog(DIALOG_ID_PROGRESS_DEFAULT);
	}

	/**
	 * ���ؽ���������Ҫ��д������д
	 */
	protected void hideProgress() {
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
		// ��һ��Activity���ٷ��غ�����ע�����������Ӧ��Activity
		notifiyApplicationActivityCreated();
	}

	@Override
	protected void onStop() {
		super.onStop();
		// Activity��ת��ֹͣ���ȼ���
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
			dlg.setMessage("���ڼ���...");
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
			dlg.setMessage("���ڼ���...");
			dlg.setCancelable(true);
			return dlg;
		default:
			return super.onCreateDialog(id);
		}
	}

	/**
	 * ������������ʱ����
	 */
	public void onConnect(netType type) {

	}

	/**
	 * ������������ʱ����
	 */
	public void onConnect() {

	}

	/**
	 * ��ǰû����������
	 */
	public void onDisConnect() {

	}

	/**
	 * ������״̬
	 */
	public void NoWifi(int id) {
		this.findViewById(id).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				openWifi();
				TANGBarActivity.this.finish();
			}
		});
	}

	public void openWifi() {
		// ֱ�ӵ���ϵͳ�Դ���WIFI���ý�����Android�İ汾�й�ϵ
		// ��Android�汾10���£����õ��ǣ�ACTION_WIRELESS_SETTINGS���汾��10���ϵĵ��ã�ACTION_SETTINGS��
		// �������£�
		if (android.os.Build.VERSION.SDK_INT > 10) {
			// 3.0���ϴ����ý��棬Ҳ����ֱ����ACTION_WIRELESS_SETTINGS�򿪵�wifi����
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

	@Override
	public void onFlipClick(FlipImageView view) {
		view.toggleFlip();
		view.setInterpolator(new DecelerateInterpolator());
		view.setDuration(500);
		view.setRotationXEnabled(false);
		view.setRotationYEnabled(false);
		view.setRotationZEnabled(false);
		view.setRotationReversed(true);
	}

	@Override
	public void onFlipStart(FlipImageView view) {

	}

	@Override
	public void onFlipEnd(FlipImageView view) {
		onClick(view);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}