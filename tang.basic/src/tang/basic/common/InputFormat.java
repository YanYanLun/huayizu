package tang.basic.common;

import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 数据格式判断
 * 
 * @author Administrator
 * 
 */
public class InputFormat {
	/**
	 * 判断手机格式
	 * 
	 * @param mobiles
	 * @return
	 */
	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	/**
	 * 判断email格式是否正确
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		return m.matches();
	}

	/**
	 * 判断是否全是数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 车牌验证正则表达式,包括特殊军车及其他以字幕开头的车牌
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isPlateNumber(String platenumber) {
		String str = "^[\u4e00-\u9fa5_A-Z]{1}[A-Z]{1}[A-Z_0-9]{5}$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(platenumber);
		return m.matches();
	}

	/**
	 * 汉字验证
	 * 
	 * @param name
	 * @return
	 */
	public static boolean isChineseName(String name) {
		String str = ("^[\u4e00-\u9fa5]*$");
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(name);
		return m.matches(); // true为全部是汉字，否则是false
	}

	/**
	 * 车架号，只能是大写字母和数字组成
	 * 
	 * @param name
	 * @return
	 */
	public static boolean isVIN(String name) {
		String str = ("^[A-Z0-9]+$");
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(name);
		return m.matches(); // true为全部正确，否则是false
	}

	/**
	 * 验证身份证
	 * 
	 * @param name
	 * @return
	 */
	public static boolean isCarDID(String name) {
		String str = ("^(\\d{15}$|^\\d{18}$|^\\d{17}(\\d|X|x))$");
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(name);
		return m.matches(); // true为全部正确，否则是false
	}

	/**
	 * 小写字母自动转换为大写
	 * 
	 * @param val
	 * @return
	 */
	public static void convertCapitalize(final EditText view) {
		view.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@SuppressLint("DefaultLocale")
			@Override
			public void afterTextChanged(Editable arg0) {
				try {
					final String temp = arg0.toString();
					int length = temp.length();
					for (int i = 0; i < length; i++) {
						// String tem = temp.substring(i, i + 1);
						char c = temp.charAt(i);
						// 如果字母是小写执行下面代码
						if (Character.isLowerCase(c)) {
							// char[] temC = tem.toCharArray();
							// int mid = temC[0];
							int mid = c;
							if (mid >= 97 && mid <= 122) {
								// 小写字母
								new Handler().postDelayed(new Runnable() {

									@Override
									public void run() {
										view.setText(temp.toUpperCase());
										view.setSelection(temp.length());// 将光标移至文字末尾
									}
								}, 200);

							}
						}
					}
				} catch (Exception e) {
					e.getMessage();
				}
			}
		});
	}

	/**
	 * 实时输入字符数判断
	 * 
	 * @param val
	 * @return
	 */
	public static void monitorWordSum(final EditText view, final TextView text,
			final int sum, final Context context) {
		view.addTextChangedListener(new TextWatcher() {
			private CharSequence temp;
			private int editStart;
			private int editEnd;
			private boolean isChange = false;

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				temp = arg0;
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
			}

			@SuppressLint("DefaultLocale")
			@Override
			public void afterTextChanged(Editable arg0) {
				try {
					editStart = view.getSelectionStart();
					editEnd = view.getSelectionEnd();
					text.setText(temp.length() + "/" + sum);
					if (temp.length() > sum) {
						Toast.makeText(context, "您的输入已经超过" + sum + "字！",
								Toast.LENGTH_SHORT).show();
						arg0.delete(editStart - 1, editEnd);
						int tempSelection = editStart;
						view.setText(arg0);
						view.setSelection(tempSelection);
						view.setSelection(temp.length());// 将光标移至文字末尾
					}
				} catch (Exception e) {
					e.getMessage();
				}
			}
		});
	}

	/**
	 * EditView To TextView
	 * 
	 * @param val
	 * @return
	 */
	public static void TextForTextView(final EditText view,
			final TextView text, final Context context) {
		view.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
			}

			@SuppressLint("DefaultLocale")
			@Override
			public void afterTextChanged(Editable arg0) {
				try {
					text.setText(arg0);
				} catch (Exception e) {
					e.getMessage();
				}
			}
		});
	}

	/**
	 * 格式化经度，保留六位小数
	 * 
	 * @param f
	 * @return
	 */
	public static double FormatLongitude(double f) {
		try {
			DecimalFormat df = new DecimalFormat("#.000000");
			return Double.parseDouble(df.format(f));
		} catch (Exception e) {
			return f;
		}
	}

	/**
	 * 格式化纬度，保留五位小数
	 * 
	 * @param f
	 * @return
	 */
	public static double FormatLatitude(double f) {
		try {
			DecimalFormat df = new DecimalFormat("#.00000");
			return Double.parseDouble(df.format(f));
		} catch (Exception e) {
			return f;
		}
	}

	/**
	 * 判断是否是mac地址
	 * 
	 * @param mac
	 * @return
	 */
	public static boolean IsMac(String mac) {
		String str = ("^[0-9a-fA-F]{2}:[0-9a-fA-F]{2}:[0-9a-fA-F]{2}:[0-9a-fA-F]{2}:[0-9a-fA-F]{2}:[0-9a-fA-F]{2}$");
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(mac);
		return m.matches(); // true为是，否则是false
	}

	/**
	 * MD5转化
	 * 
	 * @param str
	 * @return
	 */
	public static String MD5(String str) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

		char[] charArray = str.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++) {
			byteArray[i] = (byte) charArray[i];
		}
		byte[] md5Bytes = md5.digest(byteArray);

		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}

	/**
	 * 计算Gps距离
	 * 
	 * @param lng1
	 * @param lat1
	 * @param lng2
	 * @param lat2
	 * @return
	 */
	public static double GpsDistens(double lng1, double lat1, double lng2,
			double lat2) {
		double result = 6370996.81 * Math.acos(Math.cos(lat1 * Math.PI / 180)
				* Math.cos(lat2 * Math.PI / 180)
				* Math.cos(lng1 * Math.PI / 180 - lng2 * Math.PI / 180)
				+ Math.sin(lat1 * Math.PI / 180)
				* Math.sin(lat2 * Math.PI / 180));

		return result;
	}

	/**
	 * 禁止输入
	 * 
	 * @param editText
	 */
	public static void ForbidEdit(EditText editText) {
		editText.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				EditText edit = (EditText) v;
				// 记住EditText的InputType现在是password
				int inType = edit.getInputType(); // backup
													// the
				// input
				// type
				edit.setInputType(InputType.TYPE_NULL); // disable
				// soft
				// input
				edit.onTouchEvent(event); // call native
											// handler
				edit.setInputType(inType); // restore input
											// type
				edit.setSelection(edit.getText().length());
				return true;

			}
		});
	}
}
