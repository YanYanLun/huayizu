package tang.huayizu.common;

import android.content.Context;
import tang.basic.exception.ExceptionApplication;
import tang.basic.model.ClearEditTextBean;
import tang.huayizu.R;
import tang.huayizu.model.Resources;
import tang.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import tang.universalimageloader.core.ImageLoader;
import tang.universalimageloader.core.ImageLoaderConfiguration;
import tang.universalimageloader.core.assist.QueueProcessingType;

public class BaseApplication extends ExceptionApplication {

	@Override
	public void onCreate() {
		super.onCreate();
		ClearEditTextBean.delete_selector = R.drawable.delete_selector;
		Resources.Get();
		initImageLoader(getApplicationContext());
	}
	public static void initImageLoader(Context context) {
		// This configuration tuning is custom. You can tune every option, you
		// may tune some of them,
		// or you can create default configuration by
		// ImageLoaderConfiguration.createDefault(this);
		// method.
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs() // Remove for release app
				.build();
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}
}
