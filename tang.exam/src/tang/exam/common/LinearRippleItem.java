package tang.exam.common;

import tang.basic.common.ActivityUtil;
import tang.basic.common.StringUtil;
import tang.basic.model.ShortcutButton;
import tang.basic.model.User;
import ximi.exam.R;
import tang.exam.model.Resources;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gc.materialdesign.views.LinearRipple;

public class LinearRippleItem extends LinearLayout {
	private Context mContext;
	private View view;
	private ActivityUtil util;
	private User user;

	public LinearRippleItem(Context context) {
		super(context);
		this.mContext = context;
		util = new ActivityUtil(context);
		render(context);
	}

	private void render(Context context) {
		view = LayoutInflater.from(context).inflate(R.layout.item, this);
	}

	public void setProduct(ShortcutButton menu) {
		user = util.getDao().getUserinfo();
		ImageView imageView = (ImageView) view.findViewById(R.id.image);
		LinearRipple ripple=(LinearRipple) view.findViewById(R.id.ripple);
		title().setText(menu.Netitle);
		if (menu.Netitle.equals("登录") || menu.Netitle.equals(user.Membername)) {
			reg();
			imageView.setImageResource(R.drawable.x_7);
		} else if (menu.Netitle.equals("在线支付")) {
			imageView.setImageResource(R.drawable.pay);
		} else if (menu.Netitle.equals("在线报名")) {
			imageView.setImageResource(R.drawable.x_1);
		} else {
			imageView.setImageResource(Resources.uri[1]);
		}
		ripple.setBackgroundColor(Color.parseColor("#7CC76A"));
	}

	private TextView title() {
		return (TextView) view.findViewById(R.id.title);
	}

	private void reg() {
		IntentFilter filter = new IntentFilter();
		filter.addAction("Login.success");
		mContext.registerReceiver(receiver, filter);
	}

	private BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals("Login.success")) {
				User user = util.getDao().getUserinfo();
				if (!StringUtil.isEmpty(user.token)) {
					if (title() != null) {
						title().setText(user.Membername);
					}
				} else {
					if (title() != null) {
						title().setText("登录");
					}
				}
			}
		}
	};
}
