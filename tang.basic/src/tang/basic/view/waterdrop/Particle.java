package tang.basic.view.waterdrop;

import tang.basic.util.ViewHelper;
import android.R.integer;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;

public class Particle {

	public static final int STATE_ALIVE = 0; // particle is alive
	public static final int STATE_DEAD = 1; // particle is dead

	public static final int DEFAULT_LIFETIME = 100; // play with this
	public static int MAX_DIMENSION = 30; // 最大的宽度或高度
	public static final int MAX_SPEED = 10; // maximum speed (per update)

	private int state; // 粒子是活着的状态
	private float widht; // 粒子的宽度
	private float height; // 粒子的高度
	private float x, y; // 水平和垂直位置
	private double xv, yv; // 垂直和水平速度
	private int age; // 粒子的当前年龄
	private static int lifetime; // 粒子死亡达到这个值时
	private int color; // 粒子的颜色
	private Paint paint; // 内部使用的避免实例化
	private final static float DEGREE = 36; //五角星角度 

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public float getWidht() {
		return widht;
	}

	public void setWidht(float widht) {
		this.widht = widht;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public double getXv() {
		return xv;
	}

	public void setXv(double xv) {
		this.xv = xv;
	}

	public double getYv() {
		return yv;
	}

	public void setYv(double yv) {
		this.yv = yv;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getLifetime() {
		return lifetime;
	}

	public void setLifetime(int lifetime) {
		Particle.lifetime = lifetime;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	// helper methods -------------------------
	public boolean isAlive() {
		return this.state == STATE_ALIVE;
	}

	public boolean isDead() {
		return this.state == STATE_DEAD;
	}

	public Particle(int x, int y, Context context) {
		MAX_DIMENSION = ViewHelper.dip2px(context, 16);
		this.x = x;
		this.y = y;
		this.state = Particle.STATE_ALIVE;
		this.widht = rndInt(1, MAX_DIMENSION);
		this.height = this.widht;// this.height = rnd(1, MAX_DIMENSION);
		Particle.lifetime = DEFAULT_LIFETIME;
		this.age = 0;
		this.xv = (rndDbl(0, MAX_SPEED * 2) - MAX_SPEED);
		this.yv = (rndDbl(0, MAX_SPEED * 2) - MAX_SPEED);
		// smoothing out the diagonal speed
		if (xv * xv + yv * yv > MAX_SPEED * MAX_SPEED) {
			xv *= 0.7;
			yv *= 0.7;
		}
		this.color = Color.argb(255, rndInt(0, 255), rndInt(0, 255),
				rndInt(0, 255));
		this.paint = new Paint(this.color);
	}

	/**
	 * Resets the particle
	 * 
	 * @param x
	 * @param y
	 */
	public void reset(float x, float y) {
		this.state = Particle.STATE_ALIVE;
		this.x = x;
		this.y = y;
		this.age = 0;
	}

	// Return an integer that ranges from min inclusive to max inclusive.
	static int rndInt(int min, int max) {
		return (int) (min + Math.random() * (max - min + 1));
	}

	static double rndDbl(double min, double max) {
		return min + (max - min) * Math.random();
	}

	public static void setLifeTime(int lifeTime) {
		lifetime = lifeTime;
	}

	public void update() {
		if (this.state != STATE_DEAD) {
			this.x += this.xv;
			this.y += this.yv;

			// extract alpha
			int a = this.color >>> 24;
			a -= 4; // fade out
			if (a <= 0) { // if reached transparency kill the particle
				this.state = STATE_DEAD;
			} else {
				this.color = (this.color & 0x00ffffff) + (a << 24); // set the
																	// new alpha
				this.paint.setAlpha(a);
				this.age++; // increase the age of the particle// this.widht *=
							// 1.05;// this.height *= 1.05;
			}
			if (this.age >= Particle.lifetime) { // reached the end if its life
				this.state = STATE_DEAD;
			}

			// http://lab.polygonal.de/2007/05/10/bitwise-gems-fast-integer-math/
			// 32bit// var color:uint = 0xff336699;// var a:uint = color >>>
			// 24;// var r:uint = color >>> 16 & 0xFF;// var g:uint = color >>>
			// 8 & 0xFF;// var b:uint = color & 0xFF;

		}
	}

	public void update(Rect container) {
		// update with collision
		if (this.isAlive()) {
			if (this.x <= container.left
					|| this.x >= container.right - this.widht) {
				this.xv *= -1;
			}
			// Bottom is 480 and top is 0 !!!
			if (this.y <= container.top
					|| this.y >= container.bottom - this.height) {
				this.yv *= -1;
			}
		}
		update();
	}

	public void draw(Canvas canvas) {// paint.setARGB(255, 128, 255, 50);
		paint.setColor(this.color);
		// 画方
		// canvas.drawRect(this.x, this.y, this.x + this.widht, this.y
		// + this.height, paint);
		// 画圆
		canvas.drawCircle(x, y, widht / 2, paint);
//		paint.setColor(this.color);
//		paint.setAntiAlias(true);
//		Path path = new Path(); 
//		int radius=MAX_DIMENSION;
//		float radian = degree2Radian(DEGREE);
//		float radius_in = (float) (radius * Math.sin(radian / 2) / Math.cos(radian)); //中间五边形的半径
//        
//		path.moveTo((float) (radius * Math.cos(radian / 2)), 0);
//		path.lineTo((float) (radius * Math.cos(radian / 2) + radius_in * Math.sin(radian)), (float) (radius - radius * Math.sin(radian / 2)));
//		path.lineTo((float) (radius * Math.cos(radian / 2) * 2), (float) (radius - radius * Math.sin(radian / 2)));
//		path.lineTo((float) (radius * Math.cos(radian / 2) + radius_in * Math.cos( radian /2)),(float) (radius + radius_in * Math.sin( radian /2)));
//		path.lineTo((float) (radius * Math.cos(radian / 2) + radius * Math.sin(radian)), (float) (radius + radius * Math.cos(radian)));
//		path.lineTo((float) (radius * Math.cos(radian / 2)), (float) (radius + radius_in));
//		path.lineTo((float) (radius * Math.cos(radian / 2) - radius * Math.sin(radian)), (float) (radius + radius * Math.cos(radian)));
//		path.lineTo((float) (radius * Math.cos(radian / 2) - radius_in * Math.cos( radian /2)), (float) (radius + radius_in * Math.sin(radian / 2)));
//		path.lineTo(0, (float) (radius - radius * Math.sin(radian /2)));
//		path.lineTo((float) (radius * Math.cos(radian / 2) - radius_in * Math.sin(radian)) , (float) (radius - radius * Math.sin(radian /2)));
//		
//		path.close();
//		canvas.drawPath(path, paint); 
//		Bitmap bitmap = Bitmap.createBitmap(10, 10, Bitmap.Config.ARGB_8888);
//		canvas.drawBitmap(bitmap, 10, 10, paint);
	}
	/**
	 * 角度转弧度
	 * @param degree
	 * @return
	 */
	private float degree2Radian(float degree){
		return (float) (Math.PI * degree / 180);
	}
}
