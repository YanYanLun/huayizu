package tang.exam.common;

import java.util.ArrayList;
import java.util.List;

import tang.basic.baseactivity.TANGBarActivity;
import tang.basic.common.NetBrowse;
import ximi.exam.R;
import tang.pulltorefresh.PullToRefreshView;
import yalantis.com.sidemenu.interfaces.Resourceble;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.model.SlideMenuItem;
import yalantis.com.sidemenu.util.ViewAnimator;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ActivityBarAc extends TANGBarActivity implements
		ViewAnimator.ViewAnimatorListener {
	private DrawerLayout drawerLayout;
	private ActionBarDrawerToggle drawerToggle;
	private List<SlideMenuItem> list = new ArrayList<>();
	private ContentFragment contentFragment;
	private ViewAnimator viewAnimator;
	private int res = R.drawable.content_music;
	private LinearLayout linearLayout;
	public static final int REFRESH_DELAY = 2000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_bar_list);
		if (savedInstanceState == null) {
			contentFragment = ContentFragment
					.newInstance(R.drawable.content_music);
			getSupportFragmentManager().beginTransaction()
					.add(R.id.content_frame, contentFragment).commit();
		}
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerLayout.setScrimColor(Color.TRANSPARENT);
		linearLayout = (LinearLayout) findViewById(R.id.left_drawer);
		linearLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				drawerLayout.closeDrawers();
			}
		});

		setActionBar();
		createMenuList();
		viewAnimator = new ViewAnimator<>(this, list, contentFragment,
				drawerLayout, this);

		QueryPower();
//		loadPull();
	}

//	private PullToRefreshView Get_PullToRefreshView() {
//		return (PullToRefreshView) this.findViewById(R.id.pull_to_refresh);
//	}
//
//	private void loadPull() {
//		Get_PullToRefreshView().setOnRefreshListener(
//				new PullToRefreshView.OnRefreshListener() {
//					@Override
//					public void onRefresh() {
//						Get_PullToRefreshView().postDelayed(new Runnable() {
//							@Override
//							public void run() {
//								Get_PullToRefreshView().setRefreshing(false);
//								Toast.makeText(ActivityBarList.this, "一刷新",
//										Toast.LENGTH_SHORT).show();
//							}
//						}, REFRESH_DELAY);
//						Get_PullToRefreshView().post(new Runnable() {
//
//							@Override
//							public void run() {
//								Toast.makeText(ActivityBarList.this, "开始",
//										Toast.LENGTH_SHORT).show();
//							}
//						});
//					}
//				});
//	}

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

	private void createMenuList() {
		SlideMenuItem menuItem0 = new SlideMenuItem(ContentFragment.CLOSE,
				R.drawable.icn_close);
		list.add(menuItem0);
		SlideMenuItem menuItem = new SlideMenuItem(ContentFragment.BUILDING,
				R.drawable.icn_1);
		list.add(menuItem);
		SlideMenuItem menuItem2 = new SlideMenuItem(ContentFragment.BOOK,
				R.drawable.icn_2);
		list.add(menuItem2);
		SlideMenuItem menuItem3 = new SlideMenuItem(ContentFragment.PAINT,
				R.drawable.icn_3);
		list.add(menuItem3);
		SlideMenuItem menuItem4 = new SlideMenuItem(ContentFragment.CASE,
				R.drawable.icn_4);
		list.add(menuItem4);
		SlideMenuItem menuItem5 = new SlideMenuItem(ContentFragment.SHOP,
				R.drawable.icn_5);
		list.add(menuItem5);
		SlideMenuItem menuItem6 = new SlideMenuItem(ContentFragment.PARTY,
				R.drawable.icn_6);
		list.add(menuItem6);
		SlideMenuItem menuItem7 = new SlideMenuItem(ContentFragment.MOVIE,
				R.drawable.icn_7);
		list.add(menuItem7);
	}

	private void setActionBar() {
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		// host Activity ,DrawerLayout object,nav drawer icon to replace 'Up'
		// caret,"open drawer" description,"close drawer" description
		drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
				R.string.drawer_open, R.string.drawer_close) {

			/** Called when a drawer has settled in a completely closed state. */
			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				linearLayout.removeAllViews();
				linearLayout.invalidate();
			}

			@Override
			public void onDrawerSlide(View drawerView, float slideOffset) {
				super.onDrawerSlide(drawerView, slideOffset);
				// if (slideOffset > 0.6 && linearLayout.getChildCount() == 0)
				// viewAnimator.showMenuContent();
				if (slideOffset > 0.6)
					finish();
			}

			/** Called when a drawer has settled in a completely open state. */
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
			}
		};
		drawerLayout.setDrawerListener(drawerToggle);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// drawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		drawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// getMenuInflater().inflate(R.menu.menu_main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (drawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		switch (item.getItemId()) {
		case R.id.action_settings:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private ScreenShotable replaceFragment(ScreenShotable screenShotable,
			int topPosition, SlideMenuItem item) {
		Toast.makeText(this, "我被点击" , Toast.LENGTH_SHORT).show();
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
