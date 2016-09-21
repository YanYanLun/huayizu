package tang.exam.activity;

import java.util.List;

import tang.basic.common.GenericRemoteBroadcastReceiver;
import tang.basic.common.StringUtil;
import tang.basic.http.TxException;
import tang.basic.model.User;
import tang.basic.util.ViewHelper;
import ximi.exam.R;
import tang.exam.common.ActivityBarBase;
import tang.exam.model.Menu;
import tang.exam.model.Resources;
import tang.exam.net.NetServerCenter;
import tang.exam.net.RequestMenu;
import tang.exam.net.ResponseMenu;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ActivityHome extends ActivityBarBase {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
	}

	@Override
	protected void fouseChange() {
		super.fouseChange();
		User user = Util.getDao().getUserinfo();
		if (!StringUtil.isEmpty(user.token)) {
			UserName().setText(user.Membername);
		} else {
			UserName().setText("游客");
		}
		toUser().setOnClickListener(this);
		reg();
		RequestMenu request = Util.getRequest(RequestMenu.class);
		request.code = 0;
		NetServerCenter.GetMenuRequest(this, request, null, "Menu");
		Util.beginWaiting();
	}

	private void loadView(List<Menu> list) {
		Get_layout_1().removeAllViews();
		int sw = ViewHelper.getWindowWidth(this) - ViewHelper.dip2px(this, 56);
		int j = 0;
		int a = 0;
		boolean bool = false;
		boolean bool2 = false;
		RelativeLayout layout = new RelativeLayout(this);
		int margen = ViewHelper.dip2px(this, 12);
		int max = 3;
		int mag = sw / max;
		for (int i = 0; i < list.size(); i++) {
			Menu menu = list.get(i);
			View view = LayoutInflater.from(this).inflate(R.layout.item, null);
			ImageView imageView = (ImageView) view.findViewById(R.id.image);
			TextView title = (TextView) view.findViewById(R.id.title);
			title.setText(menu.Name);
			imageView.setImageResource(Resources.uri[1]);
			int random = (int) (Math.random() * 3);
			view.setBackgroundColor(Color
					.parseColor(Resources.backColor[random]));
			view.setTag(menu);
			RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(
					mag, mag);
			params2.addRule(RelativeLayout.ALIGN_PARENT_TOP,
					RelativeLayout.TRUE);
			params2.addRule(RelativeLayout.ALIGN_PARENT_LEFT,
					RelativeLayout.TRUE);
			if (i + 1 > max) {
				if ((i + 1) % max == 1) {
					j++;
					a = 0;
					bool = false;
					bool2 = true;
				}
			}
			if (bool && bool2) {
				params2.leftMargin = (int) margen * a + mag * a; // 横坐标定位
				params2.topMargin = ViewHelper.dip2px(this, 12 * j) + mag * j; // 纵坐标定位
			} else if (!bool2 && bool) {
				params2.leftMargin = (int) margen * a + mag * a; // 横坐标定位
				params2.topMargin = mag * j; // 纵坐标定位
			} else if (bool2 && !bool) {
				params2.leftMargin = mag * a; // 横坐标定位
				params2.topMargin = ViewHelper.dip2px(this, 12 * j) + mag * j; // 纵坐标定位
			} else {
				params2.leftMargin = mag * a; // 横坐标定位
				params2.topMargin = mag * j; // 纵坐标定位
			}
			layout.addView(view, params2);
			a++;
			bool = true;
			view.setOnClickListener(this);
		}
		Get_layout_1().addView(layout);
	}

	private LinearLayout Get_layout_1() {
		return (LinearLayout) findViewById(R.id.layout_1);
	}

	private LinearLayout toUser() {
		return (LinearLayout) findViewById(R.id.toUser);
	}

	private TextView UserName() {
		return (TextView) findViewById(R.id.UserName);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.toUser) {
			User user = Util.getDao().getUserinfo();
			if (StringUtil.isEmpty(user.token)) {
				doActivity(ActivityLogin.class);
			} else {
				doActivity(ActivityReviseInfo.class);
			}
		} else {
			Menu menu = (Menu) v.getTag();
			Bundle bundle = new Bundle();
			bundle.putSerializable("Menu", menu);
			doActivity(ActivityList.class, bundle);
		}
	}

	private void reg() {
		IntentFilter filter = new IntentFilter();
		filter.addAction(Util.getCompletAction(ResponseMenu.class) + "_Menu");
		filter.addAction(Util.getErrorAction(ResponseMenu.class) + "_Menu");
		registerReceiver(receiver, filter);
		filter = new IntentFilter();
		filter.addAction("Login.success");
		registerReceiver(receiver2, filter);
	}

	private BroadcastReceiver receiver = new GenericRemoteBroadcastReceiver<ResponseMenu>() {

		@Override
		public void onComplet(ResponseMenu data) {
			Util.releaseWaiting();
			if (data != null) {
				if (data.StatusCode != 1) {
					Util.handlerFailResponse(data);
					return;
				}
				List<Menu> list = data.CarSerieses;
				if (list != null) {
					if (list.size() > 0) {
						loadView(list);
					}
				}
			}
		}

		@Override
		public void onError(TxException error) {
			Util.releaseWaiting();
			Util.handlerTxException(error);
		}
	};
	private BroadcastReceiver receiver2 = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals("Login.success")) {
				User user = Util.getDao().getUserinfo();
				if (!StringUtil.isEmpty(user.token)) {
					UserName().setText(user.Membername);
				} else {
					UserName().setText("游客");
				}
			}
		}
	};
}
