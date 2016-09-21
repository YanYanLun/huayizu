package tang.exam.model;

import tang.basic.model.FlipImageBean;
import tang.basic.model.ShapeLoading;
import tang.basic.model.ShowMessageBean;
import ximi.exam.R;

public class Resources {
	public static int[] resourceId = { R.drawable.icon_1, R.drawable.icon_2,
			R.drawable.icon_3, R.drawable.icon_4, R.drawable.icon_5,
			R.drawable.icon_6, R.drawable.icon_7, R.drawable.icon_8,
			R.drawable.icon_9, R.drawable.icon_10, R.drawable.icon_11,
			R.drawable.icon_12, R.drawable.icon_13 };

	public static String[] backColor = { "#2CCFF0", "#7BC769", "#7ABED1" };
	public static int[] uri = { R.drawable.x_1, R.drawable.x_2, R.drawable.x_3,
			R.drawable.x_4, R.drawable.x_5, R.drawable.x_6, R.drawable.x_8 };
	public static int loadIndex;

	public static void Get() {
		FlipImageBean.default_fiv_duration = R.integer.default_fiv_duration;
		FlipImageBean.default_fiv_rotations = R.integer.default_fiv_rotations;
		FlipImageBean.default_fiv_isAnimated = R.bool.default_fiv_isAnimated;
		FlipImageBean.default_fiv_isFlipped = R.bool.default_fiv_isFlipped;
		FlipImageBean.default_fiv_isRotationReversed = R.bool.default_fiv_isRotationReversed;
		FlipImageBean.FlipImageView = R.styleable.FlipImageView;
		FlipImageBean.FlipImageView_isAnimated = R.styleable.FlipImageView_isAnimated;
		FlipImageBean.FlipImageView_isFlipped = R.styleable.FlipImageView_isFlipped;
		FlipImageBean.FlipImageView_flipDrawable = R.styleable.FlipImageView_flipDrawable;
		FlipImageBean.FlipImageView_flipDuration = R.styleable.FlipImageView_flipDuration;
		FlipImageBean.FlipImageView_flipInterpolator = R.styleable.FlipImageView_flipInterpolator;
		FlipImageBean.FlipImageView_flipRotations = R.styleable.FlipImageView_flipRotations;
		FlipImageBean.FlipImageView_reverseRotation = R.styleable.FlipImageView_reverseRotation;
		FlipImageBean.CircularProgress = R.styleable.CircularProgress;
		FlipImageBean.borderWidth = R.styleable.CircularProgress_borderWidth;
		// FlipImageBean.widgit_wating = R.layout.widgit_wating;
		FlipImageBean.widgit_wating = R.layout.load_view2;

		ShowMessageBean.toast_success = R.layout.toast_success;
		ShowMessageBean.toast = R.layout.toast;
		ShowMessageBean.toast_img = R.id.toast_img;
		ShowMessageBean.toast_txt = R.id.toast_txt;
		ShowMessageBean.propmt_success = R.drawable.propmt_success;
		ShowMessageBean.propmt_warning = R.drawable.propmt_warning;

		ShapeLoading.LoadingView = R.styleable.LoadingView;
		ShapeLoading.loadingText = R.styleable.LoadingView_loadingText;
		ShapeLoading.load_view = R.layout.load_view;
		ShapeLoading.shapeLoadingView = R.id.shapeLoadingView;
		ShapeLoading.indication = R.id.indication;
		ShapeLoading.promptTV = R.id.promptTV;
		ShapeLoading.color_triangle = R.color.triangle;
		ShapeLoading.color_view_bg = R.color.view_bg;
		ShapeLoading.color_circle = R.color.circle;
		ShapeLoading.color_rect = R.color.rect;

		// CityPickerBean.city_picker = R.layout.city_picker;
		// CityPickerBean.province = R.id.province;
		// CityPickerBean.city = R.id.city;
		// CityPickerBean.couny = R.id.couny;
		//
		// ScrollerNumberPickerBean.NumberPicker = R.styleable.NumberPicker;
		// ScrollerNumberPickerBean.NumberPicker_unitHight =
		// R.styleable.NumberPicker_unitHight;
		// ScrollerNumberPickerBean.NumberPicker_normalTextSize =
		// R.styleable.NumberPicker_normalTextSize;
		// ScrollerNumberPickerBean.NumberPicker_selecredTextSize =
		// R.styleable.NumberPicker_selecredTextSize;
		// ScrollerNumberPickerBean.NumberPicker_itemNumber =
		// R.styleable.NumberPicker_itemNumber;
		// ScrollerNumberPickerBean.NumberPicker_normalTextColor =
		// R.styleable.NumberPicker_normalTextColor;
		// ScrollerNumberPickerBean.NumberPicker_selecredTextColor =
		// R.styleable.NumberPicker_selecredTextColor;
		// ScrollerNumberPickerBean.NumberPicker_lineColor =
		// R.styleable.NumberPicker_lineColor;
		// ScrollerNumberPickerBean.NumberPicker_maskHight =
		// R.styleable.NumberPicker_maskHight;
		// ScrollerNumberPickerBean.NumberPicker_noEmpty =
		// R.styleable.NumberPicker_noEmpty;
		// ScrollerNumberPickerBean.NumberPicker_isEnable =
		// R.styleable.NumberPicker_isEnable;
		//
		// StaggeredGridViewBean.StaggeredGridView =
		// R.styleable.StaggeredGridView;
		// StaggeredGridViewBean.column_count_portrait =
		// R.styleable.StaggeredGridView_column_count_portrait;
		// StaggeredGridViewBean.column_count_landscape =
		// R.styleable.StaggeredGridView_column_count_landscape;
		// StaggeredGridViewBean.item_margin =
		// R.styleable.StaggeredGridView_item_margin;
		// StaggeredGridViewBean.grid_paddingLeft =
		// R.styleable.StaggeredGridView_grid_paddingLeft;
		// StaggeredGridViewBean.grid_paddingRight =
		// R.styleable.StaggeredGridView_grid_paddingRight;
		// StaggeredGridViewBean.grid_paddingTop =
		// R.styleable.StaggeredGridView_grid_paddingTop;
		// StaggeredGridViewBean.grid_paddingBottom =
		// R.styleable.StaggeredGridView_grid_paddingBottom;
		//
		// RoundAngleBean.RoundAngleImageView = R.styleable.RoundAngleImageView;
		// RoundAngleBean.roundWidth =
		// R.styleable.RoundAngleImageView_roundWidth;
		// RoundAngleBean.roundHeight =
		// R.styleable.RoundAngleImageView_roundHeight;
	}
}
