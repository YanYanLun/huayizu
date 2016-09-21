package tang.huayizu.fragment;

import java.util.ArrayList;
import java.util.List;

import tang.basic.baseactivity.TANGFragment;
import tang.basic.common.GenericRemoteBroadcastReceiver;
import tang.basic.http.TxException;
import tang.basic.util.ViewHelper;
import tang.basic.view.FlipImageView;
import tang.huayizu.R;
import tang.huayizu.model.Banner;
import tang.huayizu.model.Exercise;
import tang.huayizu.model.Home;
import tang.huayizu.model.Recommend;
import tang.huayizu.net.HomeRequest;
import tang.huayizu.net.HomeResponse;
import tang.huayizu.net.NetService;
import tang.huayizu.widget.HomeGuideAdapter;
import tang.huayizu.widget.HomeRecommendAdapter;
import tang.universalimageloader.core.DisplayImageOptions;
import tang.universalimageloader.core.ImageLoader;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
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
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;

public class Fragment_Home_01 extends TANGFragment implements
		OnPageChangeListener {
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
	protected ImageLoader imageLoader = ImageLoader.getInstance();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		view = inflater.inflate(R.layout.fragment_01, null);
		layout = (LinearLayout) view.findViewById(R.id.layout);
		init();
		return view;
	}

	private void init() {
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.empty_photo)
				.showImageForEmptyUri(R.drawable.empty_photo)
				.showImageOnFail(R.drawable.empty_photo).cacheInMemory(true)
				.cacheOnDisc(true).considerExifParams(true)
				.bitmapConfig(Bitmap.Config.RGB_565).build();
		handler.sendEmptyMessageDelayed(1, 1000);
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		isLoop = false;
		// ((SlidingRelativeLayout) view).removeAllViews();
		// isLoop = false;
		// Intent intent = new Intent();
		// intent.setAction("onDestroyView.Fragment");
		// getActivity().sendBroadcast(intent);
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

	private void initView(List<Banner> list) {
		mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
		llPoints = (LinearLayout) view.findViewById(R.id.ll_points);
		Scroll = (ScrollView) view.findViewById(R.id.Scroll);
		prepareData(list);

		HomeGuideAdapter adapter = new HomeGuideAdapter(imageViewList);
		mViewPager.setAdapter(adapter);
		mViewPager.setOnPageChangeListener(this);
		llPoints.getChildAt(previousSelectPosition).setEnabled(true);
		mViewPager.setCurrentItem(0);

		// 自动切换页面功能
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (isLoop) {
					SystemClock.sleep(3000);
					handler.sendEmptyMessage(0);
				}
			}
		}).start();
	}

	private void prepareData(List<Banner> list) {
		imageViewList = new ArrayList<ImageView>();

		ImageView iv;
		View view;
		for (int i = 0; i < list.size(); i++) {
			iv = new ImageView(getActivity());
			ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT, ViewHelper.dip2px(
							getActivity(), 190));
			iv.setLayoutParams(params);
			iv.setScaleType(ScaleType.FIT_XY);
			new HomeRecommendAdapter().loadBanner(list.get(i), iv, imageLoader,
					options);
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

	@Override
	public void onDestroy() {
		super.onDestroy();
		isLoop = false;
		Util.releaseWaiting();
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
		addRec();
		HomeRequest request = Util.getRequest(HomeRequest.class);
		NetService.GetHomeRequest(getActivity(), request, null, "Home");
		Util.beginWaiting();
	}

	private void addRec() {
		IntentFilter filter = new IntentFilter();
		filter.addAction(Util.getCompletAction(HomeResponse.class) + "_Home");
		filter.addAction(Util.getErrorAction(HomeResponse.class) + "_Home");
		getActivity().registerReceiver(receiver, filter);
	}

	private BroadcastReceiver receiver = new GenericRemoteBroadcastReceiver<HomeResponse>() {

		@Override
		public void onComplet(HomeResponse data) {
			Util.releaseWaiting();
			if (data != null) {
				if (data.StatusCode != 2) {
					Util.handlerFailResponse(data);
					return;
				}
				Home home = data.data;
				List<Banner> banners = home.carousel_list;
				List<Recommend> recommends = home.selected_list;
				List<Exercise> exercises = home.special_list;
				if (banners != null) {
					initView(banners);
				}
				if (recommends != null) {
					new HomeRecommendAdapter(getActivity(), imageLoader,
							options, recommends, RList).load();
				}
				if (exercises != null) {
					new HomeRecommendAdapter(getActivity(), exercises,
							imageLoader, options, Get_layout2()).load2();
				}
			}
		}

		@Override
		public void onError(TxException error) {
			Util.releaseWaiting();
		}
	};
}
