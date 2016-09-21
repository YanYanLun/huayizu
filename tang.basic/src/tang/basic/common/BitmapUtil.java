package tang.basic.common;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class BitmapUtil {
	/**
	 * Bitmap �� byte[]
	 * 
	 * @param bm
	 * @return
	 */
	public static byte[] Bitmap2Bytes(Bitmap bm) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}

	/**
	 * byte[] �� Bitmap
	 * 
	 * @param b
	 * @return
	 */
	public static Bitmap Bytes2Bimap(byte[] b) throws Exception {
		if (b.length != 0) {
			return BitmapFactory.decodeByteArray(b, 0, b.length);
		} else {
			return null;
		}
	}

	/**
	 * Bitmap����
	 * 
	 * @param bitmap
	 *            ���Ŷ���
	 * @param width
	 *            ���ſ��
	 * @param height
	 *            ���Ÿ߶�
	 * @return
	 */
	public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height)
			throws Exception {
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		Matrix matrix = new Matrix();
		float scaleWidth = ((float) width / w);
		float scaleHeight = ((float) height / h);
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
		return newbmp;
	}

	/**
	 * ��Drawableת��ΪBitmap
	 * 
	 * @param drawable
	 * @return
	 */
	public static Bitmap drawableToBitmap(Drawable drawable) throws Exception {
		// ȡ drawable �ĳ���
		int w = drawable.getIntrinsicWidth();
		int h = drawable.getIntrinsicHeight();

		// ȡ drawable ����ɫ��ʽ
		Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
				: Bitmap.Config.RGB_565;
		// ������Ӧ bitmap
		Bitmap bitmap = Bitmap.createBitmap(w, h, config);
		// ������Ӧ bitmap �Ļ���
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, w, h);
		// �� drawable ���ݻ���������
		drawable.draw(canvas);
		return bitmap;
	}

	/**
	 * ���Բ��ͼƬ
	 * 
	 * @param bitmap
	 * @param roundPx
	 * @return
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx)
			throws Exception {
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		Bitmap output = Bitmap.createBitmap(w, h, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, w, h);
		final RectF rectF = new RectF(rect);
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}

	/**
	 * ��ô���Ӱ��ͼƬ
	 * 
	 * @param bitmap
	 * @return
	 */
	public static Bitmap createReflectionImageWithOrigin(Bitmap bitmap)
			throws Exception {
		final int reflectionGap = 0;// ͼƬ����Ӱ��ľ���
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();

		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);

		Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, h / 2, w,
				h / 2, matrix, false);

		Bitmap bitmapWithReflection = Bitmap.createBitmap(w, (h + h / 2),
				Config.ARGB_8888);

		Canvas canvas = new Canvas(bitmapWithReflection);
		canvas.drawBitmap(bitmap, 0, 0, null);
		Paint deafalutPaint = new Paint();
		canvas.drawRect(0, h, w, h + reflectionGap, deafalutPaint);

		canvas.drawBitmap(reflectionImage, 0, h + reflectionGap, null);

		Paint paint = new Paint();
		LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0,
				bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff,
				0x00ffffff, TileMode.CLAMP);
		paint.setShader(shader);
		// Set the Transfer mode to be porter duff and destination in
		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		// Draw a rectangle using the paint with our linear gradient
		canvas.drawRect(0, h, w, bitmapWithReflection.getHeight()
				+ reflectionGap, paint);

		return bitmapWithReflection;
	}

	/**
	 * ����ͼƬ��Ӱ
	 * 
	 * @param bitmap
	 * @param posX
	 * @param posY
	 * @return
	 */
	@TargetApi(19)
	public static Bitmap shadowBitmap(Bitmap bitmap, int pos, String color,
			int round) {
		try {
			BlurMaskFilter blurFilter = new BlurMaskFilter(round,
					BlurMaskFilter.Blur.OUTER);
			Paint shadowPaint = new Paint();
			shadowPaint.setMaskFilter(blurFilter);
			shadowPaint.setShadowLayer(round, 0, 0, Color.parseColor(color));
			int[] offsetXY = new int[2];
			bitmap = getRoundedCornerBitmap(bitmap, round);
			Bitmap shadowImage = bitmap.extractAlpha(shadowPaint, offsetXY);
			Bitmap shadowImage32 = shadowImage.copy(Bitmap.Config.ARGB_8888,
					true);
			// Fix the non pre-multiplied exception for API 19+.
			if (android.os.Build.VERSION.SDK_INT >= 19
					&& !shadowImage32.isPremultiplied()) {
				shadowImage32.setPremultiplied(true);
			}
			Canvas c = new Canvas(shadowImage32);
			c.drawBitmap(bitmap, -offsetXY[0], -offsetXY[1], null);

			return shadowImage32;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap; // if error return the original bitmap
	}

	/**
	 * Drawable����
	 * 
	 * @param drawable
	 * @param w
	 *            ���ſ��
	 * @param h
	 *            ���Ÿ߶�
	 * @return
	 */
	public static Drawable zoomDrawable(Drawable drawable, int w, int h)
			throws Exception {
		int width = drawable.getIntrinsicWidth();
		int height = drawable.getIntrinsicHeight();
		// drawableת����bitmap
		Bitmap oldbmp = drawableToBitmap(drawable);
		// ��������ͼƬ�õ�Matrix����
		Matrix matrix = new Matrix();
		// �������ű���
		float sx = ((float) w / width);
		float sy = ((float) h / height);
		// �������ű���
		matrix.postScale(sx, sy);
		// �����µ�bitmap���������Ƕ�ԭbitmap�����ź��ͼ
		Bitmap newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height,
				matrix, true);
		return new BitmapDrawable(newbmp);
	}

	/**
	 * Bitmapת����Drawable
	 * 
	 * @param bitmap
	 * @return
	 */
	public static Drawable daDrawable(Bitmap bitmap) throws Exception {
		BitmapDrawable bd = new BitmapDrawable(bitmap);
		return bd;
	}

	/***
	 * ������Դ�ļ���ȡBitmap
	 * 
	 * @param context
	 * @param drawableId
	 * @return
	 */
	public static Bitmap ReadBitmapById(Context context, int drawableId) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPreferredConfig = Config.ARGB_8888;
		options.inInputShareable = true;
		options.inPurgeable = true;
		InputStream stream = context.getResources().openRawResource(drawableId);
		Bitmap bitmap = BitmapFactory.decodeStream(stream, null, options);
		return bitmap;
	}

	/**
	 * ����ƽ��ͼƬ
	 * 
	 * @param width
	 * @param src
	 * @return
	 */
	public static Bitmap createRepeater(int width, Bitmap src) {
		int count = (width + src.getWidth() - 1) / src.getWidth();
		Bitmap bitmap = Bitmap.createBitmap(width, src.getHeight(),
				Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		for (int idx = 0; idx < count; ++idx) {
			canvas.drawBitmap(src, idx * src.getWidth(), 0, null);
		}
		return bitmap;
	}
}
