package tang.exam.activity;

import tang.basic.common.StringUtil;
import tang.basic.niftynotification.Effects;
import tang.basic.niftynotification.NiftyNotificationView;
import ximi.exam.R;
import tang.exam.common.ActivityBarList;
import tang.exam.common.ActivityBarList.onFinishListener;
import tang.exam.model.Article;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

public class ActivityQuickDetailed extends ActivityBarList implements onFinishListener {

	@Override
	protected int layoutID() {
		return R.layout.activity_quick_detailed;
	}

	@Override
	protected void fouseChange() {
		setOnFinishListener(this);
		Article mlist = (Article) getIntent().getSerializableExtra("Article");
		if (mlist != null) {
			if (!StringUtil.isEmpty(mlist.Newtitle)) {
				Get_text().setText(mlist.Newtitle);
				setTitle(mlist.Newtitle);
			}
			String layuan = "";
			if (!StringUtil.isEmpty(mlist.Newauthor)) {
				layuan += "���ߣ�" + mlist.Newauthor;
			}
			if (!StringUtil.isEmpty(mlist.Newfrom)) {
				layuan += "     ��Դ��" + mlist.Newfrom;
			}
			if (!StringUtil.isEmpty(layuan)) {
				Get_time().setText(layuan);
			} else {
				Get_time().setVisibility(View.GONE);
			}
			if (!StringUtil.isEmpty(mlist.Newcont)) {
				Get_content().getSettings().setDefaultTextEncodingName("UTF-8");
				Get_content().loadDataWithBaseURL(null, mlist.Newcont, "text/html", "UTF-8", null);
				Get_content().setBackgroundColor(0); // ���ñ���ɫ
				// Get_content().getBackground().setAlpha(0); // �������͸����
				// ��Χ��0-255
			}
		} else {
			showNiftynotification("����δ֪������");
		}
	}

	private TextView Get_text() {
		return (TextView) findViewById(R.id.text);
	}

	private TextView Get_time() {
		return (TextView) findViewById(R.id.time);
	}

	private WebView Get_content() {
		return (WebView) findViewById(R.id.content);
	}

	private void showNiftynotification(String value) {
		NiftyNotificationView.build(this, value, Effects.flip, R.id.mLyout).setIcon(R.drawable.icon_logo).show();
	}

	@Override
	public void onFinish() {
		finish();
	}
}
