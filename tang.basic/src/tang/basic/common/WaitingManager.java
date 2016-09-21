package tang.basic.common;

import tang.basic.model.FlipImageBean;
import tang.basic.util.ViewHelper;
import android.content.Context;
import android.graphics.PixelFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

public class WaitingManager {
	private Context context;
	WindowManager wManager;
	ProgressBar pgbar;
	View view;
	private boolean isOnWindow = false;
	private String text = "请稍等...";

	public WaitingManager(Context ctx) {
		context = ctx;
		wManager = (WindowManager) context.getApplicationContext()
				.getSystemService(Context.WINDOW_SERVICE);
		// pgbar=new ProgressBar(context.getApplicationContext());
		// pgbar.setBackgroundColor(Color.TRANSPARENT);

	}

	public synchronized void begin() {
		begin(null);
	}

	public synchronized void begin(String txt) {
		if (!isOnWindow) {
			isOnWindow = true;

			WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams(
					ViewHelper.getWindowWidth(context),
					ViewHelper.getWindowHeight(context));
			// 设置窗口的类型
			wmParams.type = WindowManager.LayoutParams.TYPE_PHONE;

			// 设置行为选项
			wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
					| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
			wmParams.format = PixelFormat.RGBA_8888;
			// 设置悬浮窗口长宽数据
			wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
			wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
			wmParams.dimAmount = 0.3f;
			text = txt;
			view = GetView();
			wManager.addView(view, wmParams);
		}
	}

	public synchronized void release() {
		if (isOnWindow) {
			wManager.removeView(view);
			isOnWindow = false;
		}
	}

	private View GetView() {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(FlipImageBean.widgit_wating, null);
		return layout;
	}
}
