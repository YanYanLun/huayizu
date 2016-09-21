package tang.huayizu.fragment;

import tang.basic.baseactivity.TANGFragment;
import tang.huayizu.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Fragment_Home_04 extends TANGFragment {
	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_04, null);
		return view;
	}
}
