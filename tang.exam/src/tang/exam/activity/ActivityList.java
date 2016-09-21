package tang.exam.activity;

import java.util.ArrayList;
import java.util.List;

import tang.basic.common.GenericRemoteBroadcastReceiver;
import tang.basic.common.StringUtil;
import tang.basic.http.TxException;
import tang.basic.listviewanimations.swinginadapters.prepared.SwingLeftInAnimationAdapter;
import tang.basic.niftynotification.Effects;
import tang.basic.niftynotification.NiftyNotificationView;
import ximi.exam.R;
import tang.exam.common.ActivityBarList;
import tang.exam.common.ActivityBarList.onBroFinishAllListener;
import tang.exam.common.ActivityBarList.onFinishListener;
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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.gc.materialdesign.views.LayoutRipple;

public class ActivityList extends ActivityBarList implements
		onBroFinishAllListener, onFinishListener {

	private ListAdapter listAdapter;
	private List<Menu> list;
	private Menu menu;
	private Menu menu2;
	private int loadIndex = 0;

	@Override
	protected int layoutID() {
		return R.layout.activity_list;
	}

	private ListView Get_listView() {
		return (ListView) this.findViewById(R.id.list);
	}

	private void showNiftynotification(String value) {
		NiftyNotificationView.build(this, value, Effects.flip, R.id.mLyout)
				.setIcon(R.drawable.icon_logo).show();
	}

	@Override
	protected void fouseChange() {
		setOnFinishListener(this);
		super.fouseChange();
		menu = (Menu) getIntent().getSerializableExtra("Menu");
		loadIndex = Resources.loadIndex;
		this.setOnBroFinishAllListener(this);
		list = new ArrayList<Menu>();
		listAdapter = new ListAdapter(this, list);
		SwingLeftInAnimationAdapter swingLeftInAnimationAdapter = new SwingLeftInAnimationAdapter(
				listAdapter);
		swingLeftInAnimationAdapter.setAbsListView(Get_listView());
		Get_listView().setAdapter(swingLeftInAnimationAdapter);
		reg();
		if (menu != null) {
			setTitle(menu.Name);
			RequestMenu request = Util.getRequest(RequestMenu.class);
			request.code = menu.ID;
			NetServerCenter.GetMenuRequest(this, request, null, "MenuTwo"
					+ loadIndex);
			Util.beginWaiting();
		}
	}

	private class ListAdapter extends BaseAdapter {
		private Context context;
		private List<Menu> mList;

		public ListAdapter(Context context, List<Menu> list) {
			this.context = context;
			this.mList = list;
		}

		public int getCount() {
			return mList.size();
		}

		public Object getItem(int position) {
			return null;
		}

		public long getItemId(int position) {
			return 0;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_list, null);
			LayoutRipple jj = (LayoutRipple) convertView
					.findViewById(R.id.LayoutRipple);
			int random = (int) (Math.random() * 3);
			jj.setBackgroundColor(Color.parseColor(Resources.backColor[random]));
			ImageView imageView = (ImageView) convertView
					.findViewById(R.id.image);
			imageView.setImageResource(Resources.uri[1]);
			TextView textView = (TextView) convertView.findViewById(R.id.text);
			if (!StringUtil.isEmpty(mList.get(position).Name)) {
				textView.setText(mList.get(position).Name);
			}
			jj.setTag(mList.get(position));
			jj.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					menu2 = (Menu) v.getTag();
					RequestMenu request = Util.getRequest(RequestMenu.class);
					request.code = menu2.ID;
					NetServerCenter.GetMenuRequest(ActivityList.this, request,
							null, "MenuThree" + loadIndex);
					Util.beginWaiting();
				}
			});
			return convertView;
		}

	}

	private void reg() {
		IntentFilter filter = new IntentFilter();
		filter.addAction(Util.getCompletAction(ResponseMenu.class) + "_MenuTwo"
				+ loadIndex);
		filter.addAction(Util.getErrorAction(ResponseMenu.class) + "_MenuTwo"
				+ loadIndex);
		registerReceiver(receiver, filter);
		IntentFilter filter2 = new IntentFilter();
		filter2.addAction(Util.getCompletAction(ResponseMenu.class)
				+ "_MenuThree" + loadIndex);
		filter2.addAction(Util.getErrorAction(ResponseMenu.class)
				+ "_MenuThree" + loadIndex);
		registerReceiver(receiver2, filter2);
		regFinish();
	}

	private BroadcastReceiver receiver = new GenericRemoteBroadcastReceiver<ResponseMenu>() {

		@Override
		public void onComplet(ResponseMenu data) {
			Util.releaseWaiting();
			if (data != null) {
				if (data.StatusCode != 1) {
					Util.handlerFailResponse(data);
					showNiftynotification("暂无数据");
					return;
				}
				List<Menu> mlist = data.CarSerieses;
				if (mlist != null) {
					if (mlist.size() > 0) {
						for (int i = 0; i < mlist.size(); i++) {
							list.add(mlist.get(i));
						}
						listAdapter.notifyDataSetChanged();
					}
				}
				// showNiftynotification(menu.Name + "数据加载完成");
			} else {
				showNiftynotification("暂无数据");
			}
		}

		@Override
		public void onError(TxException error) {
			Util.releaseWaiting();
			Util.handlerTxException(error);
			showNiftynotification("没有找到数据");
		}
	};
	private BroadcastReceiver receiver2 = new GenericRemoteBroadcastReceiver<ResponseMenu>() {

		@Override
		public void onComplet(ResponseMenu data) {
			Util.releaseWaiting();
			if (data != null) {
				if (data.StatusCode == 1) {
					Bundle bundle = new Bundle();
					bundle.putSerializable("Menu", menu2);
					int a = loadIndex + 1;
					Resources.loadIndex = a;
					ActivityList.this.doActivity(ActivityList.class, bundle);
				} else if (data.StatusCode == 0) {
					Bundle bundle = new Bundle();
					bundle.putSerializable("Menu", menu2);
					ActivityList.this.doActivity(ActivityExamAlone.class,
							bundle);
				}
			} else {
				showNiftynotification("暂无数据");
			}
		}

		@Override
		public void onError(TxException error) {
			Util.releaseWaiting();
			Util.handlerTxException(error);
			showNiftynotification("没有找到数据");
		}
	};

	@Override
	public void onFinishAllListener() {
		Intent intent = new Intent("new.finish");
		sendBroadcast(intent);
	}

	private void regFinish() {
		IntentFilter filter = new IntentFilter();
		filter.addAction("new.finish");
		registerReceiver(receiver3, filter);
	}

	private BroadcastReceiver receiver3 = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals("new.finish")) {
				finish();
			}
		}
	};

	@Override
	public void onFinish() {
		finish();
	}
}
