package tang.basic.view;

import tang.basic.model.RoundAngleBean;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

public class RoundAngleImageView extends ImageView {

	private Paint paint;
	private int RoundWidth = 0;
	private int RoundHeight = 0;
	private Paint paint2;
	private int LiftUpW = 0, LiftUpH = 0, LiftDownW = 0, LiftDownH = 0,
			RightDownW = 0, RightDownH = 0, RightUpW = 0, RightUpH = 0;
	private Context mContext;
	private AttributeSet mSet;

	public RoundAngleImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.mContext = context;
		this.mSet = attrs;
	}

	public RoundAngleImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		this.mSet = attrs;
	}

	public RoundAngleImageView(Context context) {
		super(context);
		this.mContext = context;
		this.mSet = null;
	}

	/**
	 * 设置圆角参数
	 * 
	 * @param liftUp
	 * @param liftDown
	 * @param rightDown
	 * @param rightUp
	 * @param round
	 */
	public void setParameters(int liftUp, int liftDown, int rightDown,
			int rightUp, int round) {
		this.LiftUpW = liftUp;
		this.LiftUpH = liftUp;
		this.LiftDownW = liftDown;
		this.LiftDownH = liftDown;
		this.RightDownW = rightDown;
		this.RightDownH = rightDown;
		this.RightUpW = rightUp;
		this.RightUpH = rightUp;
		this.RoundWidth = round;
		this.RoundHeight = round;
	}

	/**
	 * 执行绘制
	 */
	public void initGo() {
		init(mContext, mSet);
	}

	private void init(Context context, AttributeSet attrs) {

		if (attrs != null) {
			TypedArray a = context.obtainStyledAttributes(attrs,
					RoundAngleBean.RoundAngleImageView);
			RoundWidth = a.getDimensionPixelSize(
					RoundAngleBean.roundWidth, RoundWidth);
			RoundHeight = a.getDimensionPixelSize(
					RoundAngleBean.roundHeight, RoundHeight);
		} else {
			float density = context.getResources().getDisplayMetrics().density;
			RoundWidth = (int) (RoundWidth * density);
			RoundHeight = (int) (RoundHeight * density);
		}

		paint = new Paint();
		paint.setColor(Color.WHITE);
		paint.setAntiAlias(true);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));

		paint2 = new Paint();
		paint2.setXfermode(null);
	}

	@Override
	public void draw(Canvas canvas) {
		Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(),
				Config.ARGB_8888);
		Canvas canvas2 = new Canvas(bitmap);
		super.draw(canvas2);
		drawLiftUp(canvas2);
		drawRightUp(canvas2);
		drawLiftDown(canvas2);
		drawRightDown(canvas2);
		canvas.drawBitmap(bitmap, 0, 0, paint2);
		bitmap.recycle();
	}

	private void drawLiftUp(Canvas canvas) {
		Path path = new Path();
		path.moveTo(0, LiftUpH);
		path.lineTo(0, 0);
		path.lineTo(LiftUpW, 0);
		path.arcTo(new RectF(0, 0, LiftUpW * 2, LiftUpH * 2), -90, -90);
		path.close();
		canvas.drawPath(path, paint);
	}

	private void drawLiftDown(Canvas canvas) {
		Path path = new Path();
		path.moveTo(0, getHeight() - LiftDownH);
		path.lineTo(0, getHeight());
		path.lineTo(LiftDownW, getHeight());
		path.arcTo(new RectF(0, getHeight() - LiftDownH * 2, 0 + LiftDownW * 2,
				getHeight()), 90, 90);
		path.close();
		canvas.drawPath(path, paint);
	}

	private void drawRightDown(Canvas canvas) {
		Path path = new Path();
		path.moveTo(getWidth() - RightDownW, getHeight());
		path.lineTo(getWidth(), getHeight());
		path.lineTo(getWidth(), getHeight() - RightDownH);
		path.arcTo(new RectF(getWidth() - RightDownW * 2, getHeight()
				- RightDownH * 2, getWidth(), getHeight()), 0, 90);
		path.close();
		canvas.drawPath(path, paint);
	}

	private void drawRightUp(Canvas canvas) {
		Path path = new Path();
		path.moveTo(getWidth(), RightUpH);
		path.lineTo(getWidth(), 0);
		path.lineTo(getWidth() - RightUpW, 0);
		path.arcTo(new RectF(getWidth() - RightUpW * 2, 0, getWidth(),
				0 + RightUpH * 2), -90, 90);
		path.close();
		canvas.drawPath(path, paint);
	}

}
