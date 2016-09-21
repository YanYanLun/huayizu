package tang.exam.activity;

import java.util.List;

import tang.basic.baseactivity.TANGActivity;
import tang.basic.common.GenericRemoteBroadcastReceiver;
import tang.basic.http.TxException;
import tang.basic.model.ShortcutButton;
import tang.basic.view.FlipImageView;
import ximi.exam.R;
import tang.exam.net.NetServerCenter;
import tang.exam.net.RequestQuickButton;
import tang.exam.net.ResponseQuickButton;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;

@SuppressLint("HandlerLeak")
public class ActivityWelcome extends TANGActivity {
	private SharedPreferences sp;
	private FlipImageView mFlipImageView;

	@Override
	protected int layoutID() {
		return R.layout.activity_welcome;
	}

	@Override
	protected View layoutView() {
		return null;
	}

	private void init() {
		reg();
		RequestQuickButton request = Util.getRequest(RequestQuickButton.class);
		request.ncode = 0;
		NetServerCenter.GetQuickButtonRequest(this, request, null,
				"QuickButton");
		logAnim();
	}

	/**
	 * 加载动画
	 */
	private void logAnim() {
		mFlipImageView = (FlipImageView) findViewById(R.id.imageview);
		mFlipImageView.setOnFlipListener(this);
		// 开始动画
		mFlipImageView.toggleFlip();
		mFlipImageView.setInterpolator(new AnticipateOvershootInterpolator());
		mFlipImageView.setDuration(4500);
		mFlipImageView.setRotationXEnabled(false);
		mFlipImageView.setRotationYEnabled(false);
		mFlipImageView.setRotationZEnabled(false);
		mFlipImageView.setRotationReversed(true);
	}

	@Override
	protected void fouseChange() {
		init();
	}

	private void reg() {
		IntentFilter filter = new IntentFilter();
		filter.addAction(Util.getCompletAction(ResponseQuickButton.class)
				+ "_QuickButton");
		filter.addAction(Util.getErrorAction(ResponseQuickButton.class)
				+ "_QuickButton");
		registerReceiver(receiver, filter);
	}

	private BroadcastReceiver receiver = new GenericRemoteBroadcastReceiver<ResponseQuickButton>() {

		@Override
		public void onComplet(ResponseQuickButton data) {
			if (data != null) {
				if (data.StatusCode != 1) {
					Util.handlerFailResponse(data);
					return;
				}
				List<ShortcutButton> list = data.CarSerieses;
				if (list != null) {
					if (list.size() > 0) {
						// 删除老数据
						ShortcutButton.DeleteAll(Util.getDao());
						for (int i = 0; i < list.size(); i++) {
							// list.get(i).Delete(Util.getDao());
							list.get(i).Save(Util.getDao());
						}
					}
				}
			}
			doActivity(ActivityIndex.class);
			finish();
		}

		@Override
		public void onError(TxException error) {
			doActivity(ActivityHome.class);
			Util.handlerTxException(error);
			finish();
		}
	};
}
