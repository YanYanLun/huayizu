package tang.exam.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import tang.basic.common.GenericRemoteBroadcastReceiver;
import tang.basic.common.ShowMessage;
import tang.basic.common.StringUtil;
import tang.basic.common.Utils;
import tang.basic.http.TxException;
import tang.basic.model.ShortcutButton;
import tang.basic.model.User;
import tang.basic.util.ViewHelper;
import ximi.exam.R;
import tang.exam.common.ActivityBarBase2;
import tang.exam.common.LinearRippleItem;
import tang.exam.model.Menu;
import tang.exam.model.Resources;
import tang.exam.net.NetServerCenter;
import tang.exam.net.RequestMenu;
import tang.exam.net.RequestUpdate;
import tang.exam.net.ResponseMenu;
import tang.exam.net.ResponseUpdate;
import tang.exam.widget.MessageAlertDialog;
import tang.exam.widget.MessageAlertDialog.MessageClick;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.apk.update.ActivityUtil;
import com.apk.update.DownloadService;
import com.gc.materialdesign.views.LinearRipple;

public class ActivityIndex extends ActivityBarBase2 implements MessageClick {
	private User user;
	private MessageAlertDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_index);
	}

	@Override
	protected void fouseChange() {
		super.fouseChange();
		user = Util.getDao().getUserinfo();
		if (!StringUtil.isEmpty(user.token)) {
			UserName().setText(user.Membername);
		} else {
			UserName().setText("游客");
		}
		toUser().setOnClickListener(this);
		dialog = new MessageAlertDialog(this);
		dialog.set_temp(this);
		reg();
		RequestMenu request = Util.getRequest(RequestMenu.class);
		request.code = 0;
		NetServerCenter.GetMenuRequest(this, request, null, "Menu");
		Util.beginWaiting();
		RequestUpdate update = Util.getRequest(RequestUpdate.class);
		update.client = "android";
		update.version = Util.getApplicationHelper().getVersionName();
		NetServerCenter.GetVersionRequest(this, update, null, "Update");
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exitBy2Click(); // 调用双击退出函数
		}
		return false;
	}

	private void loadView(List<Menu> list) {
		Get_layout_1().removeAllViews();
		int sw = ViewHelper.getWindowWidth(this) - ViewHelper.dip2px(this, 56);
		int j = 0;
		int a = 0;
		boolean bool = false;
		boolean bool2 = false;
		RelativeLayout layout = new RelativeLayout(this);
		int margen = ViewHelper.dip2px(this, 12);
		int max = 3;
		int mag = sw / max;
		for (int i = 0; i < list.size(); i++) {
			Menu menu = list.get(i);
			View view = LayoutInflater.from(this).inflate(R.layout.item, null);
			ImageView imageView = (ImageView) view.findViewById(R.id.image);
			TextView title = (TextView) view.findViewById(R.id.title);
			title.setText(menu.Name);
			imageView.setImageResource(Resources.uri[1]);
			int random = (int) (Math.random() * 3);
			view.setBackgroundColor(Color
					.parseColor(Resources.backColor[random]));
			view.setTag(menu);
			RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(
					mag, mag);
			params2.addRule(RelativeLayout.ALIGN_PARENT_TOP,
					RelativeLayout.TRUE);
			params2.addRule(RelativeLayout.ALIGN_PARENT_LEFT,
					RelativeLayout.TRUE);
			if (i + 1 > max) {
				if ((i + 1) % max == 1) {
					j++;
					a = 0;
					bool = false;
					bool2 = true;
				}
			}
			if (bool && bool2) {
				params2.leftMargin = (int) margen * a + mag * a; // 横坐标定位
				params2.topMargin = ViewHelper.dip2px(this, 12 * j) + mag * j; // 纵坐标定位
			} else if (!bool2 && bool) {
				params2.leftMargin = (int) margen * a + mag * a; // 横坐标定位
				params2.topMargin = mag * j; // 纵坐标定位
			} else if (bool2 && !bool) {
				params2.leftMargin = mag * a; // 横坐标定位
				params2.topMargin = ViewHelper.dip2px(this, 12 * j) + mag * j; // 纵坐标定位
			} else {
				params2.leftMargin = mag * a; // 横坐标定位
				params2.topMargin = mag * j; // 纵坐标定位
			}
			layout.addView(view, params2);
			a++;
			bool = true;
			view.setOnClickListener(this);
		}
		Get_layout_1().addView(layout);
	}

	private void loadMenu() {
		List<ShortcutButton> mList = new ArrayList<ShortcutButton>();
		ShortcutButton button1 = new ShortcutButton();
		button1.ID = 100001;
		if (!StringUtil.isEmpty(user.token)) {
			button1.Netitle = user.Membername;
		} else {
			button1.Netitle = "登录";
		}
		mList.add(button1);
		ShortcutButton button2 = new ShortcutButton();
		button2.ID = 100002;
		button2.Netitle = "在线支付";
		mList.add(button2);
		ShortcutButton button3 = new ShortcutButton();
		button3.ID = 100003;
		button3.Netitle = "在线报名";
		mList.add(button3);
		List<ShortcutButton> mList2 = Util.getDao().QueryShortcutButton();
		if (mList2 != null) {
			for (int i = 0; i < mList2.size(); i++) {
				mList.add(mList2.get(i));
			}
		}
		loadView2(mList);
	}

	private void loadView2(List<ShortcutButton> list) {
		Get_layout_2().removeAllViews();
		int sw = ViewHelper.getWindowWidth(this) - ViewHelper.dip2px(this, 56);
		int j = 0;
		int a = 0;
		boolean bool = false;
		boolean bool2 = false;
		RelativeLayout layout = new RelativeLayout(this);
		int margen = ViewHelper.dip2px(this, 12);
		int max = 3;
		int mag = sw / max;
		for (int i = 0; i < list.size(); i++) {
			ShortcutButton menu = list.get(i);
			LinearRippleItem item = new LinearRippleItem(this);
			item.setProduct(menu);
			LinearRipple ripple = (LinearRipple) item.findViewById(R.id.ripple);
			ripple.setTag(menu);
			RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(
					mag, mag);
			params2.addRule(RelativeLayout.ALIGN_PARENT_TOP,
					RelativeLayout.TRUE);
			params2.addRule(RelativeLayout.ALIGN_PARENT_LEFT,
					RelativeLayout.TRUE);
			if (i + 1 > max) {
				if ((i + 1) % max == 1) {
					j++;
					a = 0;
					bool = false;
					bool2 = true;
				}
			}
			if (bool && bool2) {
				params2.leftMargin = (int) margen * a + mag * a; // 横坐标定位
				params2.topMargin = ViewHelper.dip2px(this, 12 * j) + mag * j; // 纵坐标定位
			} else if (!bool2 && bool) {
				params2.leftMargin = (int) margen * a + mag * a; // 横坐标定位
				params2.topMargin = mag * j; // 纵坐标定位
			} else if (bool2 && !bool) {
				params2.leftMargin = mag * a; // 横坐标定位
				params2.topMargin = ViewHelper.dip2px(this, 12 * j) + mag * j; // 纵坐标定位
			} else {
				params2.leftMargin = mag * a; // 横坐标定位
				params2.topMargin = mag * j; // 纵坐标定位
			}
			layout.addView(item, params2);
			a++;
			bool = true;
			ripple.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					ShortcutButton button = (ShortcutButton) v.getTag();
					if (button.Netitle.equals("登录")
							|| button.Netitle.equals(user.Membername)) {
						User user = Util.getDao().getUserinfo();
						if (StringUtil.isEmpty(user.token)) {
							doActivity(ActivityLogin.class);
						} else {
							doActivity(ActivityReviseInfo.class);
						}
					} else if (button.Netitle.equals("在线支付")) {
						ShowMessage.showToast("暂未开放支付功能", ActivityIndex.this,
								false);
					} else if (button.Netitle.equals("关于")) {

					} else if (button.Netitle.equals("在线报名")) {
						User user = Util.getDao().getUserinfo();
						if (StringUtil.isEmpty(user.token)) {
							doActivity(ActivityLogin.class);
						} else {
							doActivity(ActivityOnlineRegistration.class);
						}
					} else {
						Bundle bundle = new Bundle();
						bundle.putSerializable("ShortcutButton", button);
						doActivity(ActivityQuickList.class, bundle);
					}
				}
			});
		}
		Get_layout_2().addView(layout);
	}

	private LinearLayout Get_layout_1() {
		return (LinearLayout) findViewById(R.id.layout_1);
	}

	private LinearLayout Get_layout_2() {
		return (LinearLayout) findViewById(R.id.layout_2);
	}

	private LinearLayout toUser() {
		return (LinearLayout) findViewById(R.id.toUser);
	}

	private TextView UserName() {
		return (TextView) findViewById(R.id.UserName);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.toUser) {
			User user = Util.getDao().getUserinfo();
			if (StringUtil.isEmpty(user.token)) {
				doActivity(ActivityLogin.class);
			} else {
				doActivity(ActivityReviseInfo.class);
			}
		} else {
			Menu menu = (Menu) v.getTag();
			Bundle bundle = new Bundle();
			bundle.putSerializable("Menu", menu);
			doActivity(ActivityList.class, bundle);
		}
	}

	private void reg() {
		IntentFilter filter = new IntentFilter();
		filter.addAction(Util.getCompletAction(ResponseMenu.class) + "_Menu");
		filter.addAction(Util.getErrorAction(ResponseMenu.class) + "_Menu");
		registerReceiver(receiver, filter);
		filter = new IntentFilter();
		filter.addAction("Login.success");
		registerReceiver(receiver2, filter);
		filter = new IntentFilter();
		filter.addAction(Util.getCompletAction(ResponseUpdate.class)
				+ "_Update");
		filter.addAction(Util.getErrorAction(ResponseUpdate.class) + "_Update");
		registerReceiver(receiver3, filter);
	}

	private BroadcastReceiver receiver = new GenericRemoteBroadcastReceiver<ResponseMenu>() {

		@Override
		public void onComplet(ResponseMenu data) {
			Util.releaseWaiting();
			if (data != null) {
				if (data.StatusCode != 1) {
					Util.handlerFailResponse(data);
					return;
				}
				List<Menu> list = data.CarSerieses;
				if (list != null) {
					if (list.size() > 0) {
						loadView(list);
						loadMenu();
					}
				}
			}
		}

		@Override
		public void onError(TxException error) {
			Util.releaseWaiting();
			Util.handlerTxException(error);
		}
	};

	private BroadcastReceiver receiver2 = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals("Login.success")) {
				User user = Util.getDao().getUserinfo();
				if (!StringUtil.isEmpty(user.token)) {
					UserName().setText(user.Membername);
				} else {
					UserName().setText("游客");
				}
			}
		}
	};

	private BroadcastReceiver receiver3 = new GenericRemoteBroadcastReceiver<ResponseUpdate>() {

		@Override
		public void onComplet(ResponseUpdate data) {
			if (data == null) {
				return;
			}
			if (data.StatusCode != 1) {
				return;
			}
			String version = data.Version;
			String apkVersion = Util.getApplicationHelper().getVersionName();
			boolean isUpdate = false;
			if (!StringUtil.isEmpty(version) && !StringUtil.isEmpty(apkVersion)) {
				isUpdate = Utils.isAppNewVersion(apkVersion, version);
			}
			if (isUpdate) {
				// version().setText("有新版本v" + version + "，点击更新");
				// update_version().setTag(alone);
				// update_version().setOnClickListener(ActivitySetting.this);
				View view = new View(ActivityIndex.this);
				view.setTag(data);
				dialog.setView(view);
				dialog.setTitle("发现新版本v" + version + "，确定更新？");
				dialog.setLookText("确定");
				dialog.PromptInternet();
			}
		}

		@Override
		public void onError(TxException error) {
			// TODO Auto-generated method stub

		}
	};

	@Override
	public void YesClick(View v) {
		if (v == null) {
			return;
		}
		ResponseUpdate update = (ResponseUpdate) v.getTag();
		if (update == null) {
			Log.i("更新问题", "ModelAlone对象为空");
			Toast.makeText(ActivityIndex.this, "更新出现问题了", Toast.LENGTH_SHORT)
					.show();
			return;
		}
		if (StringUtil.isEmpty(update.DownloadLink)) {
			Log.i("更新问题", "URL解析错误");
			Toast.makeText(ActivityIndex.this, "更新出现问题了", Toast.LENGTH_SHORT)
					.show();
			return;
		}
		ActivityUtil.setActivity(this);
		Intent it = new Intent(this, DownloadService.class);
		it.putExtra("Icon", R.drawable.icon_logo);
		it.putExtra("ApkUrl", update.DownloadLink);
		it.putExtra("ApkName", "littleExam_v_" + update.Version + "_"
				+ new Date().getTime() + ".apk");
		it.putExtra("Layout", R.layout.download_notification_layout);
		it.putExtra("Text", R.id.text_name);
		it.putExtra("AppName", getString(R.string.app_name));
		it.putExtra("Tv_progress", R.id.tv_progress);
		it.putExtra("Progressbar", R.id.progressbar);
		startService(it);
		bindService(it, DownloadService.conn, Context.BIND_AUTO_CREATE);
		it = new Intent("version.is.update");
		sendBroadcast(it);
	}

	@Override
	public void NoClick(View v) {
		// TODO Auto-generated method stub

	}
	/**
	 * 双击退出函数
	 */
	private static Boolean isExit = false;

	private void exitBy2Click() {
		Timer tExit = null;
		if (isExit == false) {
			isExit = true; // 准备退出
			Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
			tExit = new Timer();
			tExit.schedule(new TimerTask() {
				@Override
				public void run() {
					isExit = false; // 取消退出
				}
			}, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

		} else {
			finish();
			// System.exit(0);
			android.os.Process.killProcess(android.os.Process.myPid());
		}
	}
}
