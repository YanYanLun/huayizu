package tang.huayizu.fragment;

import tang.basic.baseactivity.TANGV4Fragment;
import tang.huayizu.R;
import tang.huayizu.activity.ActivityIndex;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.gc.materialdesign.views.LayoutRipple;

public class FragmentGuide_3 extends TANGV4Fragment {
	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		view = inflater.inflate(R.layout.fragment_guide_3, container, false);
		init();
		return view;
	}

	private void init() {
		setOriginRiple(Get_goway(), R.color.blue);
		Get_goway().setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				doActivity(ActivityIndex.class);
				getActivity().finish();
			}
		});
	}

	private LayoutRipple Get_goway() {
		return (LayoutRipple) view.findViewById(R.id.goway);
	}
}
