package tang.exam.activity;

import ximi.exam.R;
import tang.exam.common.ActivityBarList;
import tang.exam.common.ActivityBarList.onFinishListener;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

public class ActivityAddressChoose extends ActivityBarList implements
		onFinishListener {
	private ListAdapter listAdapter;
	private final String ACTION = "AbbreviationChoose";

	@Override
	protected int layoutID() {
		return R.layout.activity_address;
	}

	@Override
	protected void fouseChange() {
		super.fouseChange();
		setTitle("—°‘Òµÿ÷∑");
		this.setOnFinishListener(this);
		listAdapter = new ListAdapter(ActivityAddressChoose.this,
				getResources().getStringArray(R.array.FullName));
		Get_abbreviation_list().setAdapter(listAdapter);
	}

	private ListView Get_abbreviation_list() {
		return (ListView) findViewById(R.id.abbreviation_list);
	}

	private class ListAdapter extends BaseAdapter {
		private Context context;
		private String[] FullName;

		public ListAdapter(Context context, String[] fullName) {
			this.context = context;
			this.FullName = fullName;
		}

		public int getCount() {
			return FullName.length;
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
			mFullName.setText(FullName[position]);
			convertView.setTag(FullName[position]);
			convertView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					RadioButton myRadioButton = (RadioButton) v
							.findViewById(R.id.myRadioButton);
					myRadioButton.setChecked(true);
					Intent intent = new Intent();
					intent.putExtra("abbreviation", v.getTag().toString());
					intent.setAction(ACTION);
					ActivityAddressChoose.this.sendBroadcast(intent);
					ActivityAddressChoose.this.finish();
				}
			});
			return convertView;
		}
	}

	@Override
	public void onFinish() {
		finish();
	}
}
