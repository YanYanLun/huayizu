package tang.huayizu.fragment;

import tang.basic.baseactivity.TANGV4Fragment;
import tang.huayizu.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentGuide_1 extends TANGV4Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_guide_1, container, false);
		return view;
	}

}
