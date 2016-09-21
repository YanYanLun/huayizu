package tang.huayizu.common;

import java.util.List;

import tang.basic.baseactivity.TANGV4Fragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class TabPagerAdapter extends FragmentStatePagerAdapter {
	private List<TANGV4Fragment> list;
	
	//¹¹Ôìº¯Êı
	public TabPagerAdapter(FragmentManager fm, List<TANGV4Fragment> list) {
		super(fm);
		this.list = list;
	}

	@Override
	public Fragment getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public int getCount() {
		return list.size();
	}

}
