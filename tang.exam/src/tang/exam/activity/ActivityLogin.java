package tang.exam.activity;

import tang.basic.util.ViewHelper;
import ximi.exam.R;
import tang.exam.common.ActivityBarList;
import tang.exam.common.ActivityBarList.onFinishListener;
import tang.exam.common.DisplayUtil;
import tang.exam.fragment.LoginFragment;
import tang.exam.fragment.RegisterFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

public class ActivityLogin extends ActivityBarList implements onFinishListener {
	RelativeLayout rl_parent;
	Fragment mLoginFragment, mRegisterFragment;
	ViewPager mViewPager;
	private TextView mLoginText, mRegisterText;

	@Override
	protected int layoutID() {
		return R.layout.activity_login;
	}

	@Override
	protected void fouseChange() {
		setOnFinishListener(this);
		rl_parent = (RelativeLayout) this.findViewById(R.id.rl_parent);
		mViewPager = (ViewPager) this.findViewById(R.id.view_pager_fragment);
		mLoginFragment = new LoginFragment();
		mRegisterFragment = new RegisterFragment();
		mLoginText = (TextView) this.findViewById(R.id.tv_Login);
		mRegisterText = (TextView) this.findViewById(R.id.tv_reg);
		mRegisterText.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mViewPager.setCurrentItem(1);
				setBarTitle(1);
			}
		});
		mLoginText.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mViewPager.setCurrentItem(0);
				setBarTitle(0);
			}
		});
		mViewPager
				.setAdapter(new ContainerAdapter(getSupportFragmentManager()));
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						super.onPageSelected(position);
						if (position == 0) {
							mLoginText.setCompoundDrawablesWithIntrinsicBounds(
									0, 0, 0, R.drawable.ic_triangle);
							mRegisterText
									.setCompoundDrawablesWithIntrinsicBounds(0,
											0, 0, 0);
							setBarTitle(0);
						} else {
							mLoginText.setCompoundDrawablesWithIntrinsicBounds(
									0, 0, 0, 0);
							mRegisterText
									.setCompoundDrawablesWithIntrinsicBounds(0,
											0, 0, R.drawable.ic_triangle);
							setBarTitle(1);
						}
					}
				});
		mViewPager.postDelayed(new Runnable() {
			public void run() {
				playInAnim();
			}
		}, 300);

		setBarTitle(0);
	}

	public void playInAnim() {
		rl_parent.setVisibility(View.VISIBLE);

		AnimatorSet mAnimatorSet;
		ObjectAnimator anim3 = ObjectAnimator.ofFloat(rl_parent, "y",
				DisplayUtil.getDisplayheightPixels(this),
				ViewHelper.dip2px(this, 160));

		mAnimatorSet = new AnimatorSet();
		mAnimatorSet.play(anim3);
		mAnimatorSet.setDuration(1000);
		mAnimatorSet.start();
	}

	public class ContainerAdapter extends FragmentPagerAdapter {

		public ContainerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:
				return mLoginFragment;
			case 1:
				return mRegisterFragment;
			}
			return null;
		}

		@Override
		public int getCount() {
			return 2;
		}
	}

	@Override
	public void onFinish() {
		finish();
	}

	private void setBarTitle(int set) {
		switch (set) {
		case 0:
			setTitle("µÇÂ¼");
			break;
		case 1:
			setTitle("×¢²á");
			break;
		}
	}
}
