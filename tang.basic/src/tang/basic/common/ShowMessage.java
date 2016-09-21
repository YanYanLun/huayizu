package tang.basic.common;

import tang.basic.model.ShowMessageBean;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 消息提示
 * 
 * @author Administrator
 * 
 */
public class ShowMessage {
	/**
	 * 显示Toast消息
	 * 
	 * @param msg
	 */
	public static void showToast(String msg, Context mContext, boolean isSuccess) {
		// LayoutInflater这个类用来实例化XML文件到其相应的视图对象的布局
		LayoutInflater inflater = LayoutInflater.from(mContext);
		// 通过制定XML文件及布局ID来填充一个视图对象
		View layout;
		if (isSuccess) {
			layout = inflater.inflate(ShowMessageBean.toast_success, null);
		} else {
			layout = inflater.inflate(ShowMessageBean.toast, null);
		}
		ImageView image = (ImageView) layout
				.findViewById(ShowMessageBean.toast_img);
		TextView title = (TextView) layout
				.findViewById(ShowMessageBean.toast_txt);
		// 设置布局中图片视图中图片
		if (isSuccess) {
			image.setImageResource(ShowMessageBean.propmt_success);
			title.setText(msg);
		} else {
			image.setImageResource(ShowMessageBean.propmt_warning);
			title.setText(msg);
		}
		Toast toast = new Toast(mContext);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.setView(layout);
		toast.show();
	}
}
