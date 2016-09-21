package tang.basic.versionupdate;

import android.app.Activity;

public class ActivityUtil {

	private boolean isDownload;
	private static Activity activity;
	
	public boolean isDownload() {
		return isDownload;
	}

	public void setDownload(boolean isDownload) {
		this.isDownload = isDownload;
	}

	public static Activity getActivity() {
		return activity;
	}

	public static void setActivity(Activity _activity) {
		ActivityUtil.activity = _activity;
	}
}
