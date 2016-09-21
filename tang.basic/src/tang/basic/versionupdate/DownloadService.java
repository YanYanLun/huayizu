package tang.basic.versionupdate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Binder;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.RemoteViews;

public class DownloadService extends Service {
	private static final int NOTIFY_ID = 0;
	private int progress;
	private NotificationManager mNotificationManager;
	private boolean canceled;
	// ���صİ�װ��url
	private String apkUrl;
	/* ���ذ���װ·�� */
	private static String savePath;
	private DownloadBinder binder;
	private boolean serviceIsDestroy = false;
	private String apkName, appName;
	public int icon, layout, text, tv_progress, progressbar;
	private boolean sdCardExist = Environment.getExternalStorageState().equals(
			android.os.Environment.MEDIA_MOUNTED); // �ж�sd���Ƿ��

	private Context mContext = this;
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				// �������
				// ȡ��֪ͨ
				mNotificationManager.cancel(NOTIFY_ID);
				installApk();
				break;
			case 2:
				// �������û������ֶ�ȡ�������Իᾭ��activity��onDestroy();����
				// ȡ��֪ͨ
				mNotificationManager.cancel(NOTIFY_ID);
				break;
			case 1:
				int rate = msg.arg1;
				if (rate < 100) {
					RemoteViews contentview = mNotification.contentView;
					contentview.setTextViewText(tv_progress, rate + "%");
					contentview.setProgressBar(progressbar, 100, rate, false);
				} else {
					System.out.println("�������!!!!!!!!!!!");
					//
					serviceIsDestroy = true;
				}
				mNotificationManager.notify(NOTIFY_ID, mNotification);
				break;
			}
		}
	};

	//
	// @Override
	// public int onStartCommand(Intent intent, int flags, int startId) {
	// // TODO Auto-generated method stub
	// return START_STICKY;
	// }

	@Override
	public void onStart(Intent intent, int startId) {
		// apkͼ��
		icon = intent.getIntExtra("Icon", 0);
		// ���ص�ַ
		apkUrl = intent.getStringExtra("ApkUrl");
		// ��sd������·��
		savePath = "/mnt/sdcard/Android/data/" + getPackageName() + "/files";
		// ����İ���
		apkName = intent.getStringExtra("ApkName");
		//
		layout = intent.getIntExtra("Layout", 0);
		text = intent.getIntExtra("Text", 0);
		// app����
		appName = intent.getStringExtra("AppName");
		tv_progress = intent.getIntExtra("Tv_progress", 0);
		progressbar = intent.getIntExtra("Progressbar", 0);
		lastRate = 0;
		downLoadThread = null;
		if (binder != null) {
			binder.start();
		} else {
			binder = new DownloadBinder();
		}
		if (mNotificationManager == null) {
			mNotificationManager = (NotificationManager) getSystemService(android.content.Context.NOTIFICATION_SERVICE);
		}
		// setForeground(true);// �����ȷ���Ƿ�������
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		System.out.println("�Ƿ�ִ���� onBind");
		return binder;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		System.out.println("downloadservice ondestroy");
	}

	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		System.out.println("downloadservice onUnbind");
		return super.onUnbind(intent);
	}

	@Override
	public void onRebind(Intent intent) {
		// TODO Auto-generated method stub

		super.onRebind(intent);
		System.out.println("downloadservice onRebind");
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}

	public class DownloadBinder extends Binder {
		public void start() {
			if (downLoadThread == null || !downLoadThread.isAlive()) {

				progress = 0;
				setUpNotification();
				new Thread() {
					public void run() {
						// ����
						startDownload();
					};
				}.start();
			}
		}

		public void cancel() {
			canceled = true;
		}

		public int getProgress() {
			return progress;
		}

		public boolean isCanceled() {
			return canceled;
		}

		public boolean serviceIsDestroy() {
			return serviceIsDestroy;
		}

		public void cancelNotification() {
			mHandler.sendEmptyMessage(2);
		}
	}

	private void startDownload() {
		// TODO Auto-generated method stub
		canceled = false;
		downloadApk();
	}

	//
	Notification mNotification;

	// ֪ͨ��
	/**
	 * ����֪ͨ
	 */
	private void setUpNotification() {
		CharSequence tickerText = "��ʼ����";
		long when = System.currentTimeMillis();
		mNotification = new Notification(icon, tickerText, when);
		// ������"��������"��Ŀ��
		mNotification.flags = Notification.FLAG_ONGOING_EVENT;

		RemoteViews contentView = new RemoteViews(getPackageName(), layout);
		contentView.setTextViewText(text, appName + "��������...");
		// ָ�����Ի���ͼ
		mNotification.contentView = contentView;

		Intent intent = new Intent(this, ActivityUtil.getActivity().getClass());
		// ���������� �ڰ�home�󣬵��֪ͨ��������֮ǰactivity ״̬;
		// ����������Ļ�������service���ں�̨���أ� �ڵ������ͼƬ���½������ʱ��ֱ�ӵ����ؽ��棬�൱�ڰѳ���MAIN ��ڸ��� - -
		// ����ô���ô������
		// intent.setAction(Intent.ACTION_MAIN);
		// intent.addCategory(Intent.CATEGORY_LAUNCHER);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				intent, PendingIntent.FLAG_UPDATE_CURRENT);

		// ָ��������ͼ
		mNotification.contentIntent = contentIntent;
		mNotificationManager.notify(NOTIFY_ID, mNotification);
	}

	//
	/**
	 * ����apk
	 * 
	 * @param url
	 */
	private Thread downLoadThread;

	private void downloadApk() {
		downLoadThread = new Thread(mdownApkRunnable);
		downLoadThread.start();
	}

	/**
	 * ��װapk
	 * 
	 * @param url
	 */
	private void installApk() {
		File apkfile;
		if (sdCardExist) {
			String apkFile = savePath + apkName;
			apkfile = new File(apkFile);
		} else {
			apkfile = getFileStreamPath(apkName);
		}
		if (!apkfile.exists()) {
			return;
		}
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
				"application/vnd.android.package-archive");
		mContext.startActivity(i);
		stopSelf();// ͣ����������
	}

	private int lastRate = 0;
	private Runnable mdownApkRunnable = new Runnable() {
		@Override
		public void run() {
			try {
				URL url = new URL(apkUrl);

				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.connect();
				int length = conn.getContentLength();
				InputStream is = conn.getInputStream();
				File file = new File(savePath);
				if (!file.exists()) {
					file.mkdirs();
				}
				FileOutputStream fos;
				if (sdCardExist) {
					String apkFile = savePath + apkName;
					File ApkFile = new File(apkFile);
					fos = new FileOutputStream(ApkFile);
				} else {
					fos = openFileOutput(apkName, Context.MODE_WORLD_READABLE
							| Context.MODE_WORLD_WRITEABLE);
				}
				int count = 0;
				byte buf[] = new byte[1024];
				do {
					int numread = is.read(buf);
					count += numread;
					progress = (int) (((float) count / length) * 100);
					// ���½���
					Message msg = mHandler.obtainMessage();
					msg.what = 1;
					msg.arg1 = progress;
					if (progress >= lastRate + 1) {
						mHandler.sendMessage(msg);
						lastRate = progress;
					}
					if (numread <= 0) {
						// �������֪ͨ��װ
						mHandler.sendEmptyMessage(0);
						// �������ˣ�cancelledҲҪ����
						canceled = true;
						break;
					}
					fos.write(buf, 0, numread);
				} while (!canceled);// ���ȡ����ֹͣ����.

				fos.close();
				is.close();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	};
	private static DownloadBinder binder2;
	public static ServiceConnection conn = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			binder2 = (DownloadBinder) service;
			System.out.println("��������!!!");
			binder2.start();

		}
	};
}
