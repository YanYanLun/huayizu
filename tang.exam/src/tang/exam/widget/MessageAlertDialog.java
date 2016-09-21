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
		dialog = new AlertDialog(mContext, R.style.translucentdialog);// ����Dialog��������ʽ����
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
	 * ����ȷ�ϰ�ť����
	 * 
	 * @param txt
	 */
	public void setLookText(String txt) {
		dialog.setLookText(txt);
	}

	/**
	 * ����ȡ����ť����
	 * 
	 * @param txt
	 */
	public void setCancelText(String txt) {
		dialog.setCancelText(txt);
	}

	/**
	 * ����ȡ����ť
	 */
	public void setCancelGone() {
		dialog.setCancelGone();
	}

	/**
	 * ������ʾ
	 */
	public void PromptInternet() {
		Window win = dialog.getWindow();
		win.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
		dialog.SetTitle(_title);
		dialog.SetContent(_content);
		dialog.setCanceledOnTouchOutside(false);// ���õ��Dialog�ⲿ��������ر�Dialog
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
		 * ȷ��
		 */
		public void YesClick(View v);

		/**
		 * ȡ��
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
