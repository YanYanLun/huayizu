package tang.huayizu.widget;

import java.util.List;

import tang.basic.util.ViewHelper;
import tang.huayizu.R;
import tang.huayizu.model.Banner;
import tang.huayizu.model.Exercise;
import tang.huayizu.model.Recommend;
import tang.huayizu.model.ViewHolder;
import tang.universalimageloader.core.DisplayImageOptions;
import tang.universalimageloader.core.ImageLoader;
import tang.universalimageloader.core.assist.FailReason;
import tang.universalimageloader.core.listener.ImageLoadingProgressListener;
import tang.universalimageloader.core.listener.SimpleImageLoadingListener;
import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class HomeRecommendAdapter {
	private List<Recommend> recommends;
	private Activity mContext;
	private ImageLoader mImageLoader;
	private DisplayImageOptions mOptions;
	private ViewHolder holder;
	private List<LinearLayout> mList;
	private LinearLayout mLayout;
	private List<Exercise> exercise;
	public static int height = 0;

	public HomeRecommendAdapter(Activity mActivity, ImageLoader imageLoader,
			DisplayImageOptions options, List<Recommend> recommends,
			List<LinearLayout> list) {
		this.mContext = mActivity;
		this.recommends = recommends;
		this.mImageLoader = imageLoader;
		this.mOptions = options;
		this.mList = list;
	}

	public HomeRecommendAdapter() {
	}

	public HomeRecommendAdapter(Activity mActivity, List<Exercise> exercise,
			ImageLoader imageLoader, DisplayImageOptions options,
			LinearLayout layout) {
		this.mContext = mActivity;
		this.exercise = exercise;
		this.mImageLoader = imageLoader;
		this.mOptions = options;
		this.mLayout = layout;
	}

	public void load() {
		for (int i = 0; i < 3; i++) {
			View view = mContext.getLayoutInflater().inflate(
					R.layout.item_index_01, null);
			holder = new ViewHolder();
			holder.view = (RelativeLayout) view;
			holder.image = tang.basic.util.ViewHolder.get(view, R.id.image);
			holder.progress = tang.basic.util.ViewHolder.get(view,
					R.id.progress);
			holder.blurred_image_header = tang.basic.util.ViewHolder.get(view,
					R.id.blurred_image_header);
			holder.image.setTag(R.id.image, holder);
			holder.image.setTag(i + "");
			holder.index = i;
			mImageLoader.displayImage(recommends.get(i).imgurl, holder.image,
					mOptions, new SimpleImageLoadingListener() {
						@Override
						public void onLoadingStarted(String imageUri, View view) {
							ViewHolder holder2 = (ViewHolder) view
									.getTag(R.id.image);
							// holder2.progress.start();
							holder2.blurred_image_header
									.setVisibility(View.GONE);
						}

						@Override
						public void onLoadingCancelled(String imageUri,
								View view) {
							ViewHolder holder2 = (ViewHolder) view
									.getTag(R.id.image);
							holder2.progress.stop();
							holder2.view.removeView(holder2.progress);
							super.onLoadingCancelled(imageUri, view);
						}

						@Override
						public void onLoadingFailed(String imageUri, View view,
								FailReason failReason) {
							ViewHolder holder2 = (ViewHolder) view
									.getTag(R.id.image);
							holder2.progress.stop();
							holder2.blurred_image_header
									.setVisibility(View.VISIBLE);
							holder2.view.removeView(holder2.progress);
							switch (failReason.getType()) {
							case IO_ERROR:

								break;
							case DECODING_ERROR:

								break;
							case NETWORK_DENIED:

								break;
							case OUT_OF_MEMORY:

								break;
							case UNKNOWN:

								break;
							}
						}

						@Override
						public void onLoadingComplete(String imageUri,
								View view, Bitmap loadedImage) {
							ViewHolder holder2 = (ViewHolder) view
									.getTag(R.id.image);
							holder2.progress.stop();
							holder2.blurred_image_header
									.setVisibility(View.VISIBLE);
							holder2.view.removeView(holder2.progress);
							if (holder2.index == 0) {
								double w = ViewHelper.getWindowWidth(mContext)
										- ViewHelper.dip2px(mContext, 16);
								HomeRecommendAdapter.height = (int) (w / 5 * 2
										/ loadedImage.getWidth() * loadedImage
										.getHeight());
								LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
										(int) w / 5 * 2,
										HomeRecommendAdapter.height);
								holder2.view.setLayoutParams(params);
								RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(
										RelativeLayout.LayoutParams.MATCH_PARENT,
										RelativeLayout.LayoutParams.MATCH_PARENT);
								params2.addRule(
										RelativeLayout.ALIGN_PARENT_TOP,
										RelativeLayout.TRUE);
								holder2.image.setScaleType(ScaleType.FIT_XY);
								holder2.image.setLayoutParams(params2);
							} else if (holder2.index == 1) {
								LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
										LinearLayout.LayoutParams.MATCH_PARENT,
										HomeRecommendAdapter.height
												/ 2
												- ViewHelper
														.dip2px(mContext, 3));
								params.bottomMargin = ViewHelper.dip2px(
										mContext, 3);
								holder2.view.setLayoutParams(params);
								RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(
										RelativeLayout.LayoutParams.MATCH_PARENT,
										RelativeLayout.LayoutParams.MATCH_PARENT);
								params2.addRule(
										RelativeLayout.ALIGN_PARENT_TOP,
										RelativeLayout.TRUE);
								holder2.image.setScaleType(ScaleType.FIT_XY);
								holder2.image.setLayoutParams(params2);
							} else if (holder2.index == 2) {
								LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
										LinearLayout.LayoutParams.MATCH_PARENT,
										HomeRecommendAdapter.height
												/ 2
												- ViewHelper
														.dip2px(mContext, 3));
								holder2.view.setLayoutParams(params);
								RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(
										RelativeLayout.LayoutParams.MATCH_PARENT,
										RelativeLayout.LayoutParams.MATCH_PARENT);
								params2.addRule(
										RelativeLayout.ALIGN_PARENT_TOP,
										RelativeLayout.TRUE);
								holder2.image.setScaleType(ScaleType.FIT_XY);
								holder2.image.setLayoutParams(params2);
							}

						}
					}, new ImageLoadingProgressListener() {
						@Override
						public void onProgressUpdate(String imageUri,
								View view, int current, int total) {

						}
					});
			view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

				}
			});
			mList.get(i).removeAllViews();
			mList.get(i).addView(view);
		}
	}

	public void load2() {
		mLayout.removeAllViews();
		for (int i = 0; i < exercise.size(); i++) {
			View view = mContext.getLayoutInflater().inflate(
					R.layout.item_index_02, null);
			holder = new ViewHolder();
			holder.view = (RelativeLayout) view;
			holder.image = tang.basic.util.ViewHolder.get(view, R.id.image);
			holder.progress = tang.basic.util.ViewHolder.get(view,
					R.id.progress);
			holder.image.setTag(R.id.image, holder);
			holder.image.setTag(i + "");
			mImageLoader.displayImage(exercise.get(i).imgurl, holder.image,
					mOptions, new SimpleImageLoadingListener() {
						@Override
						public void onLoadingStarted(String imageUri, View view) {
							ViewHolder holder2 = (ViewHolder) view
									.getTag(R.id.image);
							// holder2.progress.start();
						}

						@Override
						public void onLoadingCancelled(String imageUri,
								View view) {
							ViewHolder holder2 = (ViewHolder) view
									.getTag(R.id.image);
							holder2.progress.stop();
							holder2.view.removeView(holder2.progress);
							super.onLoadingCancelled(imageUri, view);
						}

						@Override
						public void onLoadingFailed(String imageUri, View view,
								FailReason failReason) {
							ViewHolder holder2 = (ViewHolder) view
									.getTag(R.id.image);
							holder2.progress.stop();
							switch (failReason.getType()) {
							case IO_ERROR:

								break;
							case DECODING_ERROR:

								break;
							case NETWORK_DENIED:

								break;
							case OUT_OF_MEMORY:

								break;
							case UNKNOWN:

								break;
							}
						}

						@Override
						public void onLoadingComplete(String imageUri,
								View view, Bitmap loadedImage) {
							ViewHolder holder2 = (ViewHolder) view
									.getTag(R.id.image);
							holder2.progress.stop();
							holder2.view.removeView(holder2.progress);
							double w = ViewHelper.getWindowWidth(mContext)
									- ViewHelper.dip2px(mContext, 10);
							LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
									(int) w,
									(int) (w / loadedImage.getWidth() * loadedImage
											.getHeight()));
							params.topMargin = ViewHelper.dip2px(mContext, 5);
							holder2.view.setLayoutParams(params);
							RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(
									RelativeLayout.LayoutParams.MATCH_PARENT,
									RelativeLayout.LayoutParams.MATCH_PARENT);
							params2.addRule(RelativeLayout.ALIGN_PARENT_TOP,
									RelativeLayout.TRUE);
							holder2.image.setScaleType(ScaleType.FIT_XY);
							holder2.image.setLayoutParams(params2);
						}
					}, new ImageLoadingProgressListener() {
						@Override
						public void onProgressUpdate(String imageUri,
								View view, int current, int total) {

						}
					});
			view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

				}
			});
			mLayout.addView(view);
		}
	}

	/**
	 * ¼ÓÔØbannerÒ³
	 * 
	 * @param banner
	 * @param imageView
	 * @param imageLoader
	 * @param options
	 */
	public void loadBanner(Banner banner, ImageView imageView,
			ImageLoader imageLoader, DisplayImageOptions options) {
		imageLoader.displayImage(banner.imgurl, imageView, options,
				new SimpleImageLoadingListener() {
					@Override
					public void onLoadingStarted(String imageUri, View view) {

					}

					@Override
					public void onLoadingCancelled(String imageUri, View view) {

						super.onLoadingCancelled(imageUri, view);
					}

					@Override
					public void onLoadingFailed(String imageUri, View view,
							FailReason failReason) {

						switch (failReason.getType()) {
						case IO_ERROR:

							break;
						case DECODING_ERROR:

							break;
						case NETWORK_DENIED:

							break;
						case OUT_OF_MEMORY:

							break;
						case UNKNOWN:

							break;
						}
					}

					@Override
					public void onLoadingComplete(String imageUri, View view,
							Bitmap loadedImage) {

					}
				}, new ImageLoadingProgressListener() {
					@Override
					public void onProgressUpdate(String imageUri, View view,
							int current, int total) {

					}
				});
		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});
	}
}
