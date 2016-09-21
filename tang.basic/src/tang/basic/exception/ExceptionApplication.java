package tang.basic.exception;

import tang.basic.baseactivity.TANGActivity;
import tang.basic.baseactivity.TANGBarActivity;
import tang.basic.baseactivity.TANGFragment;
import tang.basic.baseactivity.TANGFragmentActivity;
import tang.basic.baseactivity.TANGV4Fragment;
import tang.basic.netstate.TANGNetChangeObserver;
import tang.basic.netstate.TANGNetWorkUtil.netType;
import tang.basic.netstate.TANGNetworkStateReceiver;
import android.app.Application;

public class ExceptionApplication extends Application {

	private TANGNetChangeObserver tangNetChangeObserver;
	private Boolean networkAvailable = false;
	private TANGActivity currentActivity;
	private TANGFragmentActivity currentFragmentActivity;
	private TANGFragment currentFragment;
	private TANGV4Fragment currentV4Fragment;
	private TANGBarActivity currentBarActivity;

	@Override
	public void onCreate() {
		super.onCreate();

//		UncaughtException mUncaughtException = UncaughtException.getInstance();
//		mUncaughtException.init();
		// mUncaughtException.setContext(this);
		doOncreate();
	}

	private void doOncreate() {
		tangNetChangeObserver = new TANGNetChangeObserver() {

			@Override
			public void onConnect(netType type) {
				super.onConnect(type);
				ExceptionApplication.this.onConnect(type);
			}

			@Override
			public void onDisConnect() {
				super.onDisConnect();
				ExceptionApplication.this.onDisConnect();
			}

		};
		TANGNetworkStateReceiver.registerObserver(tangNetChangeObserver);
	}

	/**
	 * µ±Ç°Ã»ÓÐÍøÂçÁ¬½Ó
	 */
	public void onDisConnect() {
		networkAvailable = false;
		if (currentActivity != null) {
			currentActivity.onDisConnect();
		}
		if (currentBarActivity != null) {
			currentBarActivity.onDisConnect();
		}
		if (currentFragmentActivity != null) {
			currentFragmentActivity.onDisConnect();
		}
		if (currentFragment != null) {
			currentFragment.onDisConnect();
		}
		if (currentV4Fragment != null) {
			currentV4Fragment.onDisConnect();
		}
	}

	/**
	 * ÍøÂçÁ¬½ÓÁ¬½ÓÊ±µ÷ÓÃ
	 */
	protected void onConnect(netType type) {
		networkAvailable = true;
		if (currentActivity != null) {
			currentActivity.onConnect(type);
			currentActivity.onConnect();
		}
		if (currentBarActivity != null) {
			currentBarActivity.onConnect(type);
			currentBarActivity.onConnect();
		}
		if (currentFragmentActivity != null) {
			currentFragmentActivity.onConnect(type);
			currentFragmentActivity.onConnect();
		}
		if (currentFragment != null) {
			currentFragment.onConnect(type);
			currentFragment.onConnect();
		}
		if (currentV4Fragment != null) {
			currentV4Fragment.onConnect(type);
			currentV4Fragment.onConnect();
		}
	}

	/**
	 * ×¢²áÍøÂç¼àÌýµÄActivity
	 * 
	 * @param activity
	 */
	public void onActivityCreated(TANGActivity activity) {
		if (currentActivity != null) {
			// currentActivity.finish();
		}
		currentActivity = activity;
	}

	/**
	 * ×¢²áÍøÂç¼àÌýµÄActivity
	 * 
	 * @param activity
	 */
	public void onActivityBarCreated(TANGBarActivity activity) {
		if (currentBarActivity != null) {
			// currentActivity.finish();
		}
		currentBarActivity = activity;
	}

	/**
	 * ×¢²áÍøÂç¼àÌýµÄActivity
	 * 
	 * @param activity
	 */
	public void onActivityFragmentCreated(TANGFragmentActivity activity) {
		if (currentFragmentActivity != null) {
			// currentActivity.finish();
		}
		currentFragmentActivity = activity;
	}

	/**
	 * ×¢²áÍøÂç¼àÌýµÄActivity
	 * 
	 * @param activity
	 */
	public void onFragmentCreated(TANGFragment activity) {
		if (currentFragment != null) {
			// currentActivity.finish();
		}
		currentFragment = activity;
	}

	/**
	 * ×¢²áÍøÂç¼àÌýµÄActivity
	 * 
	 * @param activity
	 */
	public void onV4FragmentCreated(TANGV4Fragment activity) {
		if (currentV4Fragment != null) {
			// currentActivity.finish();
		}
		currentV4Fragment = activity;
	}
}
