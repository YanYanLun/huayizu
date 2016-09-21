package tang.exam.widget;

import ximi.exam.R;
import tang.exam.widget.AlertDialog.ConfirmationClick;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;

public class MessageAlertDialog {
	private Context mContext;
	private AlertDialog dialog;
	private String _title;
	private String _content;
	private View view = null;

	public MessageAlertDialog(Context context) {
		super();
		this.mContext = context;
		dialog = new AlertDialog(mContext, R.style.translucentdialog);// 创建Dialog并设置样式主题
	}

	public void setTitle(String title) {
		this._title = title;
	}

	public void setContent(String content) {
		this._content = content;
	}

	public void setView(View v) {
		this.view = v;
	}

	/**
	 * 设置确认按钮文字
	 * 
	 * @param txt
	 */
	public void setLookText(String txt) {
		dialog.setLookText(txt);
	}

	/**
	 * 设置取消按钮文字
	 * 
	 * @param txt
	 */
	public void setCancelText(String txt) {
		dialog.setCancelText(txt);
	}

	/**
	 * 隐藏取消按钮
	 */
	public void setCancelGone() {
		dialog.setCancelGone();
	}

	/**
	 * 网络提示
	 */
	public void PromptInternet() {
		Window win = dialog.getWindow();
		win.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
		dialog.SetTitle(_title);
		dialog.SetContent(_content);
		dialog.setCanceledOnTouchOutside(false);// 设置点击Dialog外部任意区域关闭Dialog
		dialog.set_temp(new ConfirmationClick() {

			@Override
			public void YesClick() {
				_temp.YesClick(view);
				dialog.dismiss();
			}

			@Override
			public void NoClick() {
				_temp.NoClick(view);
				dialog.dismiss();
			}
		});
		dialog.show();
	}

	private MessageClick _temp;

	public interface MessageClick {
		/**
		 * 确定
		 */
		public void YesClick(View v);

		/**
		 * 取消
		 */
		public void NoClick(View v);
	}

	public MessageClick get_temp() {
		return _temp;
	}

	public void set_temp(MessageClick _temp) {
		this._temp = _temp;
	}

	public void dismiss() {
		if (dialog != null) {
			dialog.dismiss();
		}
	}
}
