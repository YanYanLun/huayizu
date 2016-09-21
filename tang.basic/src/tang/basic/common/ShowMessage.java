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
 * ��Ϣ��ʾ
 * 
 * @author Administrator
 * 
 */
public class ShowMessage {
	/**
	 * ��ʾToast��Ϣ
	 * 
	 * @param msg
	 */
	public static void showToast(String msg, Context mContext, boolean isSuccess) {
		// LayoutInflater���������ʵ����XML�ļ�������Ӧ����ͼ����Ĳ���
		LayoutInflater inflater = LayoutInflater.from(mContext);
		// ͨ���ƶ�XML�ļ�������ID�����һ����ͼ����
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
		// ���ò�����ͼƬ��ͼ��ͼƬ
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
