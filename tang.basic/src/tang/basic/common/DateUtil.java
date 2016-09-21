package tang.basic.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

/**
 * 调用时间日期控件
 * 
 * @author Tang
 * 
 */
public class DateUtil {
	private final Calendar cd = Calendar.getInstance(Locale.CHINA);
	private TextView text;
	private Context context;
	private String title;

	public DateUtil(Context context, TextView text, String title) {
		super();
		this.context = context;
		this.text = text;
		this.title = title;
	}

	// 把日期转为字符串
	public static String ConverToString(Date date) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(date);
	}

	// 把字符串转为日期
	public static Date ConverToDate(String strDate) throws Exception {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.parse(strDate);
	}

	// 把日期转为字符串
	public static String ConverToStringCHS(Date date) {
		try {
			if (date == null) {
				return null;
			} else {
				DateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
				return df.format(date);
			}
		} catch (Exception e) {
			return null;
		}
	}

	// 把字符串转为日期
	public static Date Conver2Date(String strDate) {
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			return df.parse(strDate);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 
	 * @param 要转换的毫秒数
	 * @return 该毫秒数转换为 * days * hours * minutes * seconds 后的格式
	 * @author fy.zhang
	 */
	public static String formatDuring(long mss) {
		long days = mss / (1000 * 60 * 60 * 24);
		long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
		long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
		long seconds = (mss % (1000 * 60)) / 1000;
		String time = "";
		if (days > 0) {
			time += days + "天";
		}
		if (hours > 0) {
			time += hours + "时";
		}
		if (minutes > 0) {
			time += minutes + "分";
		}
		if (seconds > 0) {
			time += seconds + "秒";
		}
		return time;
	}

	/**
	 * 
	 * @param begin
	 *            时间段的开始
	 * @param end
	 *            时间段的结束
	 * @return 输入的两个Date类型数据之间的时间间格用* days * hours * minutes * seconds的格式展示
	 * @author fy.zhang
	 */
	public static String formatDuring(Date begin, Date end) {
		return formatDuring(end.getTime() - begin.getTime());
	}

	public static String formatDay(long mss) {
		long hours = mss / (1000 * 60 * 60);
		long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
		String time = "";
		if (hours > 0) {
			time += hours + ":";
		} else {
			time += "00:";
		}
		if (minutes > 0) {
			time += minutes;
		} else {
			time += "00";
		}
		return time;
	}
}
