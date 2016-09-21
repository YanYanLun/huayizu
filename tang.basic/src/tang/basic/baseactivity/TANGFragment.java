package tang.basic.baseactivity;

import tang.basic.common.ActivityUtil;
import tang.basic.common.StringUtil;
import tang.basic.exception.ExceptionApplication;
import tang.basic.netstate.TANGNetWorkUtil.netType;
import tang.basic.start.ActivityStart;
import tang.basic.view.FlipImageView;
import tang.basic.view.FlipImageView.OnFlipListener;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.DecelerateInterpolator;

import com.gc.materialdesign.views.LayoutRipple;
import com.nineoldandroids.view.ViewHelper;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class TANGFragment extends Fragment implements OnFlipListener,
		OnClickListener {
	protected ActivityUtil Util;
	protected View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		try {
			notifiyApplicationActivityCreated();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Util = new ActivityUtil(getActivity());
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	public void doActivity(Class<? extends Activity> clz) {
		if (clz != null) {
			ActivityStart.Start(getActivity(), clz);
		}
	}

	public void doActivity(Class<? extends Activity> clz, Bundle extras) {
		if (clz != null) {
			ActivityStart.Start(getActivity(), clz, extras);
		}
	}

	public ExceptionApplication getTangApplication() {
		return (ExceptionApplication) getActivity().getApplication();
	}

	private void notifiyApplicationActivityCreated() {
		getTangApplication().onFragmentCreated(this);
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

	protected void setOriginRiple(final LayoutRipple layoutRipple,
			final int color) {

		layoutRipple.post(new Runnable() {

			@Override
			public void run() {
				View v = layoutRipple.getChildAt(0);
				layoutRipple.setxRippleOrigin(ViewHelper.getX(v) + v.getWidth()
						/ 2);
				layoutRipple.setyRippleOrigin(ViewHelper.getY(v)
						+ v.getHeight() / 2);
				if (color == 0) {
					layoutRipple.setRippleColor(Color.parseColor("#1E88E5"));
				} else {
					layoutRipple.setRippleColor(color);
				}

				layoutRipple.setRippleSpeed(30);
			}
		});

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub

	}

	@Override
	public void onFlipEnd(FlipImageView view) {
		onClick(view);
	}
}
