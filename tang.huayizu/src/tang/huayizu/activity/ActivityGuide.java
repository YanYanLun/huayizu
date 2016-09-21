package tang.huayizu.activity;

import java.util.ArrayList;
import java.util.List;

import tang.basic.baseactivity.TANGFragmentActivity;
import tang.basic.baseactivity.TANGV4Fragment;
import tang.huayizu.R;
import tang.huayizu.fragment.FragmentGuide_1;
import tang.huayizu.fragment.FragmentGuide_2;
import tang.huayizu.fragment.FragmentGuide_3;
import tang.huayizu.widget.GuidePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Òýµ¼
 * 
 * @author Tx
 *
 */
public class ActivityGuide extends TANGFragmentActivity {
	private ViewPager mVPActivity;
	private FragmentGuide_1 mFragment1;
	private FragmentGuide_2 mFragment2;
	private FragmentGuide_3 mFragment3;
	private List<TANGV4Fragment> mListFragment = new ArrayList<TANGV4Fragment>();
	private GuidePagerAdapter mPgAdapter;

	@Override
	protected int layoutID() {
		return R.layout.activity_guide;
	}

	@Override
	protected View layoutView() {
		return null;
	}

	private void init() {
		mVPActivity = (ViewPager) findViewById(R.id.vp_activity);
		mFragment1 = new FragmentGuide_1();
		mFragment2 = new FragmentGuide_2();
		mFragment3 = new FragmentGuide_3();
		mListFragment.add(mFragment1);
		mListFragment.add(mFragment2);
		mListFragment.add(mFragment3);
		mPgAdapter = new GuidePagerAdapter(getSupportFragmentManager(),
				mListFragment);
		mVPActivity.setAdapter(mPgAdapter);
	}

	@Override
	protected void fouseChange() {
		init();
	}
}
