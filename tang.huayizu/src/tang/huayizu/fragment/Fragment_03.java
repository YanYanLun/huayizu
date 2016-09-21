package tang.huayizu.fragment;

import tang.basic.baseactivity.TANGV4Fragment;
import tang.huayizu.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Fragment_03 extends TANGV4Fragment {
	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_03, null);
		return view;
	}
}
