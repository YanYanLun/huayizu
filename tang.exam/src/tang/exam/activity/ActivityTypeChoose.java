package tang.exam.activity;

import java.util.ArrayList;
import java.util.List;

import tang.basic.common.GenericRemoteBroadcastReceiver;
import tang.basic.http.TxException;
import tang.basic.model.User;
import ximi.exam.R;
import tang.exam.common.ActivityBarList;
import tang.exam.common.ActivityBarList.onFinishListener;
import tang.exam.model.Type;
import tang.exam.net.NetServerCenter;
import tang.exam.net.RequestType;
import tang.exam.net.ResponseType;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

public class ActivityTypeChoose extends ActivityBarList implements
		onFinishListener {
	private ListAdapter listAdapter;
	private List<Type> list;
	private User user;

	@Override
	protected int layoutID() {
		return R.layout.activity_address;
	}

	@Override
	protected void fouseChange() {
		super.fouseChange();
		setTitle("—°‘Ò¿‡–Õ");
		Reg();
		this.setOnFinishListener(this);
		user = Util.getDao().getUserinfo();
		list = new ArrayList<Type>();
		listAdapter = new ListAdapter(ActivityTypeChoose.this, list);
		Get_abbreviation_list().setAdapter(listAdapter);
		RequestType request = Util.getRequest(RequestType.class);
		request.MemberID = user.ID;
		request.token = user.token;
		NetServerCenter.GetTypeRequest(this, request, null, "Type");
		Util.beginWaiting();
	}

	private ListView Get_abbreviation_list() {
		return (ListView) findViewById(R.id.abbreviation_list);
	}

	private class ListAdapter extends BaseAdapter {
		private Context context;
		private List<Type> mList;

		public ListAdapter(Context context, List<Type> list) {
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
					R.layout.item_address_choose, null);
			TextView mFullName = (TextView) convertView
					.findViewById(R.id.FullName);
			mFullName.setText(mList.get(position).Classtitle);
			convertView.setTag(R.layout.item_address_choose,
					mList.get(position));
			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					RadioButton myRadioButton = (RadioButton) v
							.findViewById(R.id.myRadioButton);
					myRadioButton.setChecked(true);
					Intent intent = new Intent();
					intent.putExtra("enroll_type",
							(Type) v.getTag(R.layout.item_address_choose));
					intent.setAction("enroll_type");
					ActivityTypeChoose.this.sendBroadcast(intent);
					ActivityTypeChoose.this.finish();
				}
			});
			return convertView;
		}
	}

	private void Reg() {
		IntentFilter filter = new IntentFilter();
		filter.addAction(Util.getCompletAction(ResponseType.class) + "_Type");
		filter.addAction(Util.getErrorAction(ResponseType.class) + "_Type");
		registerReceiver(receiver, filter);
	}

	private BroadcastReceiver receiver = new GenericRemoteBroadcastReceiver<ResponseType>() {

		@Override
		public void onComplet(ResponseType data) {
			Util.releaseWaiting();
			if (data != null) {
				if (data.StatusCode != 1) {
					Util.handlerFailResponse(data);
					return;
				}
				List<Type> listme = data.CarSerieses;
				if (listme != null) {
					for (int i = 0; i < listme.size(); i++) {
						list.add(listme.get(i));
					}
					listAdapter.notifyDataSetChanged();
				}
			}
		}

		@Override
		public void onError(TxException error) {
			Util.releaseWaiting();
			Util.handlerTxException(error);
		}
	};

	@Override
	public void onFinish() {
		finish();
	}
}
