package tang.huayizu.activity;

import java.util.ArrayList;
import java.util.List;

import tang.basic.baseactivity.TANGFragmentActivity;
import tang.basic.baseactivity.TANGV4Fragment;
import tang.huayizu.R;
import tang.huayizu.common.TabPagerAdapter;
import tang.huayizu.fragment.Fragment_01;
import tang.huayizu.fragment.Fragment_02;
import tang.huayizu.fragment.Fragment_03;
import tang.huayizu.fragment.Fragment_04;
import tang.huayizu.fragment.Fragment_05;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.gc.materialdesign.views.RadioRipple;

public class ActivityHome extends TANGFragmentActivity implements
		OnCheckedChangeListener, OnPageChangeListener {

	/**
	 * ViewPager对象的引用
	 */
	private ViewPager mViewPager;

	/**
	 * 装载Fragment的容器，我们的每一个界面都是一个Fragment
	 */
	private List<TANGV4Fragment> mFragmentList;
	/**
	 * 实例化Fragment
	 */
	private TANGV4Fragment[] frag = { new Fragment_01(), new Fragment_02(),
			new Fragment_03(), new Fragment_04(), new Fragment_05() };
	private RadioGroup radioGroup;

	@Override
	protected int layoutID() {
		return R.layout.activity_home;
	}

	@Override
	protected View layoutView() {
		return null;
	}

	@Override
	protected void fouseChange() {
		init();
	}

	private void init() {
		radioGroup = (RadioGroup) findViewById(R.id.rg_tab);
		radioGroup.setOnCheckedChangeListener(this);
		mFragmentList = new ArrayList<TANGV4Fragment>();
		mViewPager = (ViewPager) findViewById(R.id.viewPager);
		TabPagerAdapter adapter = new TabPagerAdapter(
				getSupportFragmentManager(), mFragmentList);
		// 设置Adapter
		mViewPager.setAdapter(adapter);
		mViewPager.dispatchDisplayHint(5);
		// 设置监听
		mViewPager.setOnPageChangeListener(this);
		// 将Fragment加入到List中，并将Tab的title传递给Fragment
		for (int i = 0; i < frag.length; i++) {
			mFragmentList.add(frag[i]);
		}
		adapter.notifyDataSetChanged();
		// 指定所要显示的tab
		mViewPager.setCurrentItem(getIntent().getIntExtra("Fault", 0));
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		RadioRipple ra = (RadioRipple) group.findViewById(checkedId);
		mViewPager.setCurrentItem(Integer.parseInt(ra.getTag().toString()));
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int arg0) {
		// 点击ActionBar Tab的时候切换不同的Fragment界面
		mViewPager.setCurrentItem(arg0);
	}
}
