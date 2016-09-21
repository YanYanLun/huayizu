package tang.huayizu.fragment;

import java.util.ArrayList;
import java.util.List;

import tang.basic.baseactivity.TANGV4Fragment;
import tang.basic.util.ViewHelper;
import tang.basic.view.FlipImageView;
import tang.huayizu.R;
import tang.huayizu.widget.HomeGuideAdapter;
import tang.huayizu.widget.HomeRecommendAdapter;
import tang.universalimageloader.core.DisplayImageOptions;
import tang.universalimageloader.core.ImageLoader;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;

import com.desarrollodroide.libraryfragmenttransactionextended.SlidingRelativeLayout;

public class Fragment_01 extends TANGV4Fragment implements OnPageChangeListener {
	private View view;
	private LinearLayout layout;
	private List<ImageView> imageViewList;
	private LinearLayout llPoints;
	private int previousSelectPosition = 0;
	private ViewPager mViewPager;
	private boolean isLoop = true;
	private List<LinearLayout> FlipList, RList;
	private ScrollView Scroll;
	private int scrW = 0;
	private DisplayImageOptions options;
	private String[] imageUrls = new String[] {
			"http://sdl36.yunpan.cn/share.php?method=Share.download&cqid=4cc08f5d3ebcc09d5de8b829eac53350&dt=36.e9aa1f53096854f865377d8e1ce81bf1&e=1433472424&fhash=08a58d3089b0351a6fd6bd33b982444f0dfb9be3&fname=x280x330.png&fsize=198916&nid=14332993969421440&st=7a07f840df7470757b76f38ad1c21eee&xqid=36116568",
			"http://sdl36.yunpan.cn/share.php?method=Share.download&cqid=4cc08f5d3ebcc09d5de8b829eac53350&dt=36.c0fb576459d87cd3b2e3cb34e9f680e1&e=1433472473&fhash=67fc17a3c21c700aa2bf8b8636177c3a785f0470&fname=x410x160%2B%25282%2529.png&fsize=153382&nid=14332993979428335&st=1f5ddd8cd4b449ac00ad2a31d1f81ec9&xqid=36116568",
			"http://sdl36.yunpan.cn/share.php?method=Share.download&cqid=4cc08f5d3ebcc09d5de8b829eac53350&dt=36.90cfd0bd4e6bb7db4ee16f7e0885e30a&e=1433472512&fhash=58ab5b1179a500db09868199759a79d6eff3b62b&fname=x410x160.png&fsize=140432&nid=14332993989434596&st=d83be5f57bb4514b870e1d85374e1017&xqid=36116568" };
	protected ImageLoader imageLoader = ImageLoader.getInstance();
	private String[] imageUrls2 = new String[] {
			"http://sdl36.yunpan.cn/share.php?method=Share.download&cqid=4cc08f5d3ebcc09d5de8b829eac53350&dt=36.d168111bc85217f43dfc031c5f2869b9&e=1433555211&fhash=2afb6d6402e0b7aa8dde1042e9e7da48646e5b18&fname=x700x200.png&fsize=295059&nid=14333822499430596&st=dd5fb9d99c2d290f158debed19ce3cd5&xqid=36116568",
			"http://sdl36.yunpan.cn/share.php?method=Share.download&cqid=4cc08f5d3ebcc09d5de8b829eac53350&dt=36.d168111bc85217f43dfc031c5f2869b9&e=1433555211&fhash=2afb6d6402e0b7aa8dde1042e9e7da48646e5b18&fname=x700x200.png&fsize=295059&nid=14333822499430596&st=dd5fb9d99c2d290f158debed19ce3cd5&xqid=36116568" };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_01, null);
		layout = (LinearLayout) view.findViewById(R.id.layout);
		init();
		return view;
	}

	private void init() {
		initView();
		// 自动切换页面功能
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (isLoop) {
					SystemClock.sleep(5000);
					handler.sendEmptyMessage(0);
				}
			}
		}).start();
		handler.sendEmptyMessageDelayed(1, 1000);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int position) {
		// 切换选中的点
		llPoints.getChildAt(previousSelectPosition).setEnabled(false); // 把前一个点置为normal状态
		llPoints.getChildAt(position % imageViewList.size()).setEnabled(true); // 把当前选中的position对应的点置为enabled状态
		previousSelectPosition = position % imageViewList.size();
	}

	private LinearLayout Get_Recommend_Left() {
		return (LinearLayout) view.findViewById(R.id.Recommend_Left);
	}

	private LinearLayout Get_Recommend_Top() {
		return (LinearLayout) view.findViewById(R.id.Recommend_Top);
	}

	private LinearLayout Get_Recommend_Bottom() {
		return (LinearLayout) view.findViewById(R.id.Recommend_Bottom);
	}

	private LinearLayout Get_layout2() {
		return (LinearLayout) view.findViewById(R.id.layout2);
	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
				break;
			case 1:
				FocusChangedWindow();
				loadRecommend();
				break;
			default:
				break;
			}
		}
	};

	private void initView() {
		mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
		llPoints = (LinearLayout) view.findViewById(R.id.ll_points);
		Scroll = (ScrollView) view.findViewById(R.id.Scroll);
		prepareData();

		HomeGuideAdapter adapter = new HomeGuideAdapter(imageViewList);
		mViewPager.removeAllViews();
		mViewPager.setAdapter(adapter);
		mViewPager.setOnPageChangeListener(this);
		llPoints.getChildAt(previousSelectPosition).setEnabled(true);

		/**
		 * 2147483647 / 2 = 1073741820 - 1
		 */
		int n = Integer.MAX_VALUE / 2 % imageViewList.size();
		int itemPosition = Integer.MAX_VALUE / 2 - n;

		mViewPager.setCurrentItem(itemPosition);

		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.empty_photo)
				.showImageForEmptyUri(R.drawable.empty_photo)
				.showImageOnFail(R.drawable.empty_photo).cacheInMemory(true)
				.cacheOnDisc(true).considerExifParams(true)
				.bitmapConfig(Bitmap.Config.RGB_565).build();
	}

	private void prepareData() {
		imageViewList = new ArrayList<ImageView>();
		int[] imageResIDs = getImageResIDs();

		ImageView iv;
		View view;
		for (int i = 0; i < imageResIDs.length; i++) {
			iv = new ImageView(getActivity());
			iv.setBackgroundResource(imageResIDs[i]);
			imageViewList.add(iv);

			// 添加点view对象
			view = new View(getActivity());
			view.setBackgroundDrawable(getResources().getDrawable(
					R.drawable.point_background));
			LayoutParams lp = new LayoutParams(ViewHelper.dip2px(getActivity(),
					6), ViewHelper.dip2px(getActivity(), 6));
			lp.leftMargin = 10;
			view.setLayoutParams(lp);
			view.setEnabled(false);
			llPoints.addView(view);
		}
	}

	private int[] getImageResIDs() {
		return new int[] { R.drawable.x720x382, R.drawable.x720x382,
				R.drawable.x720x382, R.drawable.x720x382 };
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		isLoop = false;
	}

	/**
	 * 窗口焦点改变时重新定义图标位置
	 */
	private void FocusChangedWindow() {
		FlipList = new ArrayList<LinearLayout>();
		FlipImageView chinese_oil = (FlipImageView) view
				.findViewById(R.id.chinese_oil);
		chinese_oil.setOnFlipListener(this);
		FlipList.add((LinearLayout) view.findViewById(R.id.layout_oil));
		FlipImageView chinese_painting = (FlipImageView) view
				.findViewById(R.id.chinese_painting);
		chinese_painting.setOnFlipListener(this);
		FlipList.add((LinearLayout) view.findViewById(R.id.layout_painting));
		FlipImageView chinese_calligraphy = (FlipImageView) view
				.findViewById(R.id.chinese_calligraphy);
		chinese_calligraphy.setOnFlipListener(this);
		FlipList.add((LinearLayout) view.findViewById(R.id.layout_calligraphy));
		FlipImageView chinese_lnk = (FlipImageView) view
				.findViewById(R.id.chinese_lnk);
		chinese_lnk.setOnFlipListener(this);
		FlipList.add((LinearLayout) view.findViewById(R.id.layout_lnk));
		// 排除小辣椒LA2-F
		if (!android.os.Build.MODEL.equals("LA2-F")) {
			TextView textView = (TextView) view.findViewById(R.id.txt_lnk);
			// 每个图标的宽度
			double needWidth = textView.getWidth();
			scrW = ViewHelper.getWindowWidth(getActivity());
			// 得到除去4个按钮的宽度
			double picW = (double) scrW - needWidth * 4;
			// 得到间距
			double picAvgW = picW / 5;
			// 实际屏幕图标与设计屏幕图标宽度比
			double aa = (double) picAvgW / (double) needWidth;
			for (int i = 0; i < FlipList.size(); i++) {
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
						(int) needWidth, LinearLayout.LayoutParams.WRAP_CONTENT);
				params.leftMargin = (int) picAvgW;
				params.topMargin = ViewHelper.dip2px(getActivity(), 10);
				params.bottomMargin = ViewHelper.dip2px(getActivity(), 10);
				FlipList.get(i).setLayoutParams(params);
			}
		}
		view.findViewById(R.id.linear).setVisibility(View.VISIBLE);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.chinese_oil:

			break;
		case R.id.chinese_painting:

			break;
		case R.id.chinese_calligraphy:

			break;
		case R.id.chinese_lnk:

			break;
		default:
			break;
		}
		super.onClick(v);
	}

	private void loadRecommend() {
		RList = new ArrayList<LinearLayout>();
		RList.add(Get_Recommend_Left());
		RList.add(Get_Recommend_Top());
		RList.add(Get_Recommend_Bottom());
//		new HomeRecommendAdapter(getActivity(), imageUrls, imageLoader,
//				options, RList).load();
//		new HomeRecommendAdapter(getActivity(), imageUrls2, imageLoader,
//				options, Get_layout2()).load2();
	}

}
