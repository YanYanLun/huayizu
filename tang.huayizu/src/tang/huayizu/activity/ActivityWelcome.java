package tang.huayizu.activity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Handler;
import android.view.View;
import tang.basic.baseactivity.TANGActivity;
import tang.huayizu.R;
import tang.huayizu.common.BaseApplication;
import tang.huayizu.model.Resources;

@SuppressLint("HandlerLeak")
public class ActivityWelcome extends TANGActivity {
	private SharedPreferences sp;

	@Override
	protected int layoutID() {
		return R.layout.activity_welcome;
	}

	@Override
	protected View layoutView() {
		return null;
	}

	private void init() {
		sp = getSharedPreferences("Welcome", 0);
		int tr = sp.getInt("haveGuide", 0);
		if (tr == 1) {
			mHandler.sendEmptyMessageDelayed(1, 2000);
		} else {
			sp.edit().putInt("haveGuide", 1).commit();
			mHandler.sendEmptyMessageDelayed(0, 2000);
		}
	}

	private Handler mHandler = new Handler() {
		// 注意：在各个case后面不能做太耗时的操作，否则出现ANR对话框
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				ActivityWelcome.this.doActivity(ActivityIndex.class);
				finish();
				break;
			case 0:
				ActivityWelcome.this.doActivity(ActivityGuide.class);
				finish();
				break;
			default:
				break;
			}
		}
	};

	@Override
	protected void fouseChange() {
		init();
	}
}
