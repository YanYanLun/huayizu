package tang.huayizu.widget;

import java.util.ArrayList;
import java.util.List;

import tang.basic.baseactivity.TANGV4Fragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class GuidePagerAdapter extends FragmentPagerAdapter {
	private List<TANGV4Fragment> fragmentList=new ArrayList<TANGV4Fragment>();
	public GuidePagerAdapter(FragmentManager fm) {
		super(fm);		
	}
	public GuidePagerAdapter(FragmentManager fragmentManager,List<TANGV4Fragment> arrayList) {
		super(fragmentManager);
		this.fragmentList=arrayList;
	}
	@Override
	public Fragment getItem(int arg0) {
		return fragmentList.get(arg0);
	}

	@Override
	public int getCount() {
		return fragmentList.size();
	}


}
