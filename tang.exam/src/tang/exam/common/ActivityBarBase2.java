package tang.exam.common;

import tang.basic.baseactivity.TANGBarActivity;
import tang.basic.common.NetBrowse;
import ximi.exam.R;
import yalantis.com.sidemenu.interfaces.Resourceble;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.model.SlideMenuItem;
import yalantis.com.sidemenu.util.ViewAnimator;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

public class ActivityBarBase2 extends TANGBarActivity implements
		ViewAnimator.ViewAnimatorListener {
	private DrawerLayout drawerLayout;
	private LinearLayout linearLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_bar_list2);
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerLayout.setScrimColor(Color.TRANSPARENT);
		linearLayout = (LinearLayout) findViewById(R.id.left_drawer);
		setActionBar();
		QueryPower();
	}

	@Override
	public void setContentView(int layoutResID) {
		View content = LayoutInflater.from(this).inflate(layoutResID, null);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		content.setLayoutParams(params);
		((LinearLayout) this.findViewById(R.id.content_frame)).removeAllViews();
		((LinearLayout) this.findViewById(R.id.content_frame)).addView(content);
	}

	@Override
	public void setContentView(View view) {

	}
	private void setActionBar() {
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setHomeButtonEnabled(false);
		getSupportActionBar().setDisplayHomeAsUpEnabled(false);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// getMenuInflater().inflate(R.menu.menu_main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private ScreenShotable replaceFragment(ScreenShotable screenShotable,
			int topPosition, SlideMenuItem item) {

		return null;
	}

	@Override
	public ScreenShotable onSwitch(Resourceble slideMenuItem,
			ScreenShotable screenShotable, int position, SlideMenuItem item) {
		switch (slideMenuItem.getName()) {
		case ContentFragment.CLOSE:
			return screenShotable;
		default:
			return replaceFragment(screenShotable, position, item);
		}
	}

	@Override
	public void disableHomeButton() {
		getSupportActionBar().setHomeButtonEnabled(false);

	}

	@Override
	public void enableHomeButton() {
		getSupportActionBar().setHomeButtonEnabled(true);
		drawerLayout.closeDrawers();

	}

	@Override
	public void addViewToContainer(View view) {
		linearLayout.addView(view);
	}

	@Override
	protected int layoutID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected View layoutView() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void fouseChange() {
		// TODO Auto-generated method stub

	}

	/**
	 * 查询手机网络权限
	 */
	public void QueryPower() {
		try {
			int netType = NetBrowse.isNetType(this);
			if (netType == -1) {
				NoInternet();
				isHaveNet = false;
			} else if (netType == 0) {
				IsMobileNet();
				isHaveNet = true;
			} else if (netType == 1) {
				IsWifiNet();
				isHaveNet = true;
			}
		} catch (Exception e) {
			e.getMessage();
		}
	}

	/**
	 * 没有网络
	 */
	public void NoInternet() {
		if (layoutID() != 0) {
			setContentView(layoutID());
		} else {
			setContentView(layoutView());
		}
	};

	/**
	 * 手机网络
	 */
	public void IsMobileNet() {
		setContentView(layoutID());
	};

	/**
	 * wifi
	 */
	public void IsWifiNet() {
		setContentView(layoutID());
	};
}
