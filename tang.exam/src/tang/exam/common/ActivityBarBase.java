package tang.exam.common;

import java.util.ArrayList;
import java.util.List;

import tang.basic.baseactivity.TANGBarActivity;
import tang.basic.common.NetBrowse;
import tang.basic.common.ShowMessage;
import tang.basic.common.StringUtil;
import tang.basic.model.ShortcutButton;
import tang.basic.model.User;
import ximi.exam.R;
import tang.exam.activity.ActivityLogin;
import tang.exam.activity.ActivityOnlineRegistration;
import tang.exam.activity.ActivityQuickList;
import tang.exam.activity.ActivityReviseInfo;
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

public class ActivityBarBase extends TANGBarActivity implements ViewAnimator.ViewAnimatorListener {
	private DrawerLayout drawerLayout;
	private ActionBarDrawerToggle drawerToggle;
	private List<SlideMenuItem> list = new ArrayList<>();
	private ContentFragment contentFragment;
	private ViewAnimator viewAnimator;
	private int res = R.drawable.content_music;
	private LinearLayout linearLayout;
	private int[] resouse = { R.drawable.icn_1, R.drawable.icn_2, R.drawable.icn_3, R.drawable.icn_4, R.drawable.icn_5,
			R.drawable.icn_6, R.drawable.icn_7 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_main);
		if (savedInstanceState == null) {
			contentFragment = ContentFragment.newInstance(R.drawable.content_music);
			getSupportFragmentManager().beginTransaction().add(R.id.content_frame, contentFragment).commit();
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
		viewAnimator = new ViewAnimator<>(this, list, contentFragment, drawerLayout, this);

		QueryPower();
	}

	@Override
	public void setContentView(int layoutResID) {
		View content = LayoutInflater.from(this).inflate(layoutResID, null);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT);
		content.setLayoutParams(params);
		((LinearLayout) this.findViewById(R.id.content_frame)).removeAllViews();
		((LinearLayout) this.findViewById(R.id.content_frame)).addView(content);
	}

	@Override
	public void setContentView(View view) {

	}

	private void createMenuList() {
		SlideMenuItem menuItem0 = new SlideMenuItem(ContentFragment.CLOSE, R.drawable.icn_close);
		list.add(menuItem0);
		ShortcutButton button1 = new ShortcutButton();
		button1.Netitle = "登录";
		SlideMenuItem menuItem8 = new SlideMenuItem(ContentFragment.BUILDING, R.drawable.x_7, "登录", button1);
		list.add(menuItem8);
		ShortcutButton button2 = new ShortcutButton();
		button2.Netitle = "在线支付";
		SlideMenuItem menuItem9 = new SlideMenuItem(ContentFragment.BUILDING, R.drawable.pay, "在线支付", button2);
		list.add(menuItem9);
		ShortcutButton button3 = new ShortcutButton();
		button3.Netitle = "在线报名";
		SlideMenuItem menuItem10 = new SlideMenuItem(ContentFragment.BOOK, R.drawable.icn_4, "在线报名", button3);
		list.add(menuItem10);
		List<ShortcutButton> mList = Util.getDao().QueryShortcutButton();
		if (mList != null) {
			for (int i = 0; i < mList.size(); i++) {
				int random = (int) (Math.random() * 7);
				ShortcutButton button = mList.get(i);
				SlideMenuItem menuItem = new SlideMenuItem(ContentFragment.BUILDING, resouse[random], button.Netitle,
						button);
				list.add(menuItem);
			}
		}
		// ShortcutButton button4 = new ShortcutButton();
		// button4.Netitle = "关于";
		// SlideMenuItem menuItem11 = new SlideMenuItem(ContentFragment.BOOK,
		// R.drawable.icn_2, "关于", button4);
		// list.add(menuItem11);
	}

	private void setActionBar() {
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		// host Activity ,DrawerLayout object,nav drawer icon to replace 'Up'
		// caret,"open drawer" description,"close drawer" description
		drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open,
				R.string.drawer_close) {

			/**
			 * Called when a drawer has settled in a completely closed state.
			 */
			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				linearLayout.removeAllViews();
				linearLayout.invalidate();
			}

			@Override
			public void onDrawerSlide(View drawerView, float slideOffset) {
				super.onDrawerSlide(drawerView, slideOffset);
				if (slideOffset > 0.6 && linearLayout.getChildCount() == 0)
					viewAnimator.showMenuContent();
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
		drawerToggle.syncState();
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

	private ScreenShotable replaceFragment(ScreenShotable screenShotable, int topPosition, SlideMenuItem item) {
		// switch (index) {
		// case 1:
		// doActivity(ActivityLogin.class);
		// break;
		//
		// default:
		// Toast.makeText(this, "我被点击" , Toast.LENGTH_SHORT).show();
		// break;
		// }
		// if (drawerLayout != null) {
		// drawerLayout.closeDrawers();
		// }
		ShortcutButton button = item.getButton();
		if (button.Netitle.equals("登录")) {
			User user = Util.getDao().getUserinfo();
			if (StringUtil.isEmpty(user.token)) {
				doActivity(ActivityLogin.class);
			} else {
				doActivity(ActivityReviseInfo.class);
			}
		} else if (button.Netitle.equals("在线支付")) {
			ShowMessage.showToast("暂未开放支付功能", this, false);
		} else if (button.Netitle.equals("关于")) {

		} else if (button.Netitle.equals("在线报名")) {
			User user = Util.getDao().getUserinfo();
			if (StringUtil.isEmpty(user.token)) {
				doActivity(ActivityLogin.class);
			} else {
				doActivity(ActivityOnlineRegistration.class);
			}
		} else {
			Bundle bundle = new Bundle();
			bundle.putSerializable("ShortcutButton", button);
			doActivity(ActivityQuickList.class, bundle);
		}
		return null;
	}

	@Override
	public ScreenShotable onSwitch(Resourceble slideMenuItem, ScreenShotable screenShotable, int position,
			SlideMenuItem item) {
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
