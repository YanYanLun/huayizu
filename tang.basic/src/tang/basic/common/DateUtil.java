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
 * ����ʱ�����ڿؼ�
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

	// ������תΪ�ַ���
	public static String ConverToString(Date date) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(date);
	}

	// ���ַ���תΪ����
	public static Date ConverToDate(String strDate) throws Exception {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.parse(strDate);
	}

	// ������תΪ�ַ���
	public static String ConverToStringCHS(Date date) {
		try {
			if (date == null) {
				return null;
			} else {
				DateFormat df = new SimpleDateFormat("yyyy��MM��dd��");
				return df.format(date);
			}
		} catch (Exception e) {
			return null;
		}
	}

	// ���ַ���תΪ����
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
	 * @param Ҫת���ĺ�����
	 * @return �ú�����ת��Ϊ * days * hours * minutes * seconds ��ĸ�ʽ
	 * @author fy.zhang
	 */
	public static String formatDuring(long mss) {
		long days = mss / (1000 * 60 * 60 * 24);
		long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
		long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
		long seconds = (mss % (1000 * 60)) / 1000;
		String time = "";
		if (days > 0) {
			time += days + "��";
		}
		if (hours > 0) {
			time += hours + "ʱ";
		}
		if (minutes > 0) {
			time += minutes + "��";
		}
		if (seconds > 0) {
			time += seconds + "��";
		}
		return time;
	}

	/**
	 * 
	 * @param begin
	 *            ʱ��εĿ�ʼ
	 * @param end
	 *            ʱ��εĽ���
	 * @return ���������Date��������֮���ʱ������* days * hours * minutes * seconds�ĸ�ʽչʾ
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
