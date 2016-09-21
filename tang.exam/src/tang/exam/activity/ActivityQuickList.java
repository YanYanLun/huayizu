package tang.exam.activity;

import java.util.ArrayList;
import java.util.List;

import tang.basic.common.GenericRemoteBroadcastReceiver;
import tang.basic.common.StringUtil;
import tang.basic.common.Utils;
import tang.basic.http.TxException;
import tang.basic.listviewanimations.swinginadapters.prepared.SwingLeftInAnimationAdapter;
import tang.basic.model.ShortcutButton;
import tang.basic.niftynotification.Effects;
import tang.basic.niftynotification.NiftyNotificationView;
import ximi.exam.R;
import tang.exam.common.ActivityBarList;
import tang.exam.common.ActivityBarList.onFinishListener;
import tang.exam.model.Article;
import tang.exam.model.Resources;
import tang.exam.net.NetServerCenter;
import tang.exam.net.RequestArticle;
import tang.exam.net.ResponseArticle;
import android.content.BroadcastReceiver;
import android.content.Context;
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

public class ActivityQuickList extends ActivityBarList implements
		onFinishListener {

	private ListAdapter listAdapter;
	private List<Article> list;
	private ShortcutButton menu;

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
		menu = (ShortcutButton) getIntent().getSerializableExtra(
				"ShortcutButton");
		list = new ArrayList<Article>();
		listAdapter = new ListAdapter(this, list);
		SwingLeftInAnimationAdapter swingLeftInAnimationAdapter = new SwingLeftInAnimationAdapter(
				listAdapter);
		swingLeftInAnimationAdapter.setAbsListView(Get_listView());
		Get_listView().setAdapter(swingLeftInAnimationAdapter);
		listAdapter.notifyDataSetChanged();
		reg();
		if (menu != null) {
			setTitle(menu.Netitle);
			RequestArticle request = Util.getRequest(RequestArticle.class);
			request.ncode = menu.ID;
			NetServerCenter.GetArticleRequest(this, request, null, "Article");
		}
	}

	private class ListAdapter extends BaseAdapter {
		private Context context;
		private List<Article> mList;

		public ListAdapter(Context context, List<Article> list) {
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
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(
						R.layout.item_list, null);
				LayoutRipple jj = (LayoutRipple) convertView
						.findViewById(R.id.LayoutRipple);
				int random = (int) (Math.random() * 3);
				jj.setBackgroundColor(Color
						.parseColor(Resources.backColor[random]));
				ImageView imageView = (ImageView) convertView
						.findViewById(R.id.image);
				imageView.setImageResource(Resources.uri[1]);
				TextView textView = (TextView) convertView
						.findViewById(R.id.text);
				if (!StringUtil.isEmpty(mList.get(position).Newtitle)) {
					textView.setText(mList.get(position).Newtitle);
				}
				jj.setTag(mList.get(position));
				jj.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Article button = (Article) v.getTag();
						Bundle bundle = new Bundle();
						bundle.putSerializable("Article", button);
						doActivity(ActivityQuickDetailed.class, bundle);
					}
				});
			}

			return convertView;
		}

	}

	private void reg() {
		IntentFilter filter = new IntentFilter();
		filter.addAction(Util.getCompletAction(ResponseArticle.class)
				+ "_Article");
		filter.addAction(Util.getErrorAction(ResponseArticle.class)
				+ "_Article");
		registerReceiver(receiver, filter);
		Util.beginWaiting();
	}

	private BroadcastReceiver receiver = new GenericRemoteBroadcastReceiver<ResponseArticle>() {

		@Override
		public void onComplet(ResponseArticle data) {
			Util.releaseWaiting();
			if (data != null) {
				List<Article> mlist = data.CarSerieses;
				if (mlist != null) {
					if (mlist.size() > 0) {
						for (int i = 0; i < mlist.size(); i++) {
							list.add(mlist.get(i));
						}
						listAdapter.notifyDataSetChanged();
					} else {
						showNiftynotification("暂无数据");
					}
				} else {
					showNiftynotification("暂无数据");
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
	public void onFinish() {
		finish();
	}
}
