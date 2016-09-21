package tang.exam.widget;

import tang.basic.util.ViewHelper;
import ximi.exam.R;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AlertDialog extends Dialog implements
		android.view.View.OnClickListener {

	/**
	 * 获取android SDK版本号
	 */
	public int currentapiVersion = android.os.Build.VERSION.SDK_INT;

	public AlertDialog(Context context) {
		super(context);
	}

	public AlertDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
	}

	public AlertDialog(Context context, int theme) {
		super(context, theme);
		setContentView(R.layout.item_message_alert);
		Window window = getWindow();
		WindowManager.LayoutParams params = window.getAttributes();
		params.width = ViewHelper.getWindowWidth(getContext());
		params.height = ViewHelper.getWindowHeight(getContext());
		params.gravity = Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL;
		window.setAttributes(params);
		Get_look().setOnClickListener(this);
		Get_cancel().setOnClickListener(this);
		setBackground();
	}

	private TextView Get_title_txt() {
		return (TextView) this.findViewById(R.id.title_txt);
	}

	/**
	 * 关闭按钮
	 * 
	 * @return
	 */
	private TextView Get_look() {
		return (TextView) this.findViewById(R.id.look);
	}

	private TextView Get_setting_txt() {
		return (TextView) this.findViewById(R.id.setting_txt);
	}

	/**
	 * 设置确认按钮文字
	 * 
	 * @param txt
	 */
	public void setLookText(String txt) {
		Get_look().setText(txt);
	}

	/**
	 * 设置取消按钮文字
	 * 
	 * @param txt
	 */
	public void setCancelText(String txt) {
		Get_cancel().setText(txt);
	}

	/**
	 * 隐藏取消按钮
	 */
	public void setCancelGone() {
		Get_cancel().setVisibility(View.GONE);
	}

	/**
	 * 蓝色
	 * 
	 * @return
	 */
	private RelativeLayout Get_blue() {
		return (RelativeLayout) this.findViewById(R.id.blue);
	}

	/**
	 * 白色背景
	 * 
	 * @return
	 */
	private RelativeLayout Get_white() {
		return (RelativeLayout) this.findViewById(R.id.white);
	}

	/**
	 * 取消按钮
	 * 
	 * @return
	 */
	private TextView Get_cancel() {
		return (TextView) this.findViewById(R.id.cancel);
	}

	public interface ConfirmationClick {
		/**
		 * 确定
		 */
		public void YesClick();

		/**
		 * 取消
		 */
		public void NoClick();
	}

	private ConfirmationClick _temp;

	public ConfirmationClick get_temp() {
		return _temp;
	}

	public void set_temp(ConfirmationClick _temp) {
		this._temp = _temp;
	}

	/**
	 * 设置标题
	 * 
	 * @param temp
	 */
	public void SetTitle(String temp) {
		Get_title_txt().setText(temp);
	}

	/**
	 * 设置提示内容
	 * 
	 * @param temp
	 */
	public void SetContent(String temp) {
		Get_setting_txt().setText(temp);
	}

	@Override
	public void onClick(View v) {
		try {
			switch (v.getId()) {
			case R.id.cancel:
				_temp.NoClick();
				break;
			case R.id.look:
				_temp.YesClick();
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 设置选中按钮背景
	 * 
	 * @param uid
	 */
	private void setBackground() {
		Get_white().setBackgroundResource(R.drawable.rect_white_bottom_6);
		Get_blue().setBackgroundResource(R.drawable.rect_blue_top_6);
	}
}
