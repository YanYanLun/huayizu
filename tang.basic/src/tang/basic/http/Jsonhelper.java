package tang.basic.http;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tang.basic.common.StringUtil;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class Jsonhelper {
	/** 将对象转换成Json字符串 **/
	public static String toJSON(Object obj) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss") //
				.create();

		return gson.toJson(obj);
	}

	public static String toQueryString(Object obj) {
		return toQueryString(null, obj);
	}

	private static String toQueryString(String fieldname, Object obj) {
		if (obj instanceof List) {
			List<?> objlist = (List<?>) obj;
			List<String> data = new ArrayList<String>();
			for (int i = 0; i < objlist.size(); i++) {
				Object o = objlist.get(i);
				data.add(toQueryString(fieldname + "[" + i + "]", o));
			}
			return listToString(data, '&');
		} else if (obj instanceof Integer) {
			if (StringUtil.isEmpty(fieldname)) {
				return obj.toString();
			} else {
				return fieldname + "=" + obj.toString();
			}
		} else if (obj instanceof BigDecimal) {
			if (StringUtil.isEmpty(fieldname)) {
				return obj.toString();
			} else {
				return fieldname + "=" + obj.toString();
			}
		} else if (obj instanceof String) {
			if (StringUtil.isEmpty(fieldname)) {
				return obj.toString();
			} else {
				return fieldname + "=" + obj.toString();
			}
		} else if (obj instanceof Date) {
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			if (StringUtil.isEmpty(fieldname)) {
				return format.format((Date) obj);
			} else {
				return fieldname + "=" + format.format((Date) obj);
			}
		} else if (obj instanceof Boolean) {
			if (StringUtil.isEmpty(fieldname)) {
				return obj.toString();
			} else {
				return fieldname + "=" + obj.toString();
			}
		} else {
			try {
				Class<?> clz = obj.getClass();
				Field[] flds = clz.getFields();
				List<String> data = new ArrayList<String>();
				for (Field f : flds) {
					try {
						Object value = f.get(obj);
						if (value != null) {
							if (StringUtil.isEmpty(fieldname)) {
								data.add(toQueryString(f.getName(), value));
							} else {
								data.add(toQueryString(
										fieldname + "." + f.getName(), value));
							}

						}
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				return listToString(data, '&');
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	// public static String toQueryString(Object obj,int index) {
	// if(obj instanceof List){
	// List objlist=(List)obj;
	// List<String> data=new ArrayList<String>();
	// for(int i=0;i<objlist.size();i++){
	// Object o=objlist.get(i);
	// data.add(toQueryString(o,i));
	// }
	// return listToString(data,'&');
	// }else if(obj instanceof Integer){
	//
	// }
	//
	//
	// Class clz=obj.getClass();
	// Field[] flds=clz.getFields();
	// List<String> data=new ArrayList<String>();
	// for(Field f:flds){
	// try {
	// Object value=f.get(obj);
	// if(obj instanceof List){
	// data.add(toQueryString(value,index));
	// }else{
	// String strValue="null";
	// if(value!=null){
	// strValue=value.toString();
	// }
	// String strKey=f.getName();
	// data.add(strKey+"="+strValue);
	// }
	// } catch (IllegalArgumentException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } catch (IllegalAccessException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	//
	// }
	// return listToString(data,'&');
	// }

	private static String listToString(List<?> list, char separator) {
		if (list == null || list.size() == 0)
			return "";
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < list.size(); i++) {
			sb.append(list.get(i)).append(separator);
		}
		String str = sb.toString();
		// Log.d("JsonHelper", "listToString:"+str);
		return str.substring(0, str.length() - 1);
	}

	/**
	 * 反序列化简单对象
	 * 
	 * @throws
	 **/
	public static <T> T parseObject(String jsonString, Class<T> clazz) {
		if (clazz == null || jsonString == null || jsonString.length() == 0) {
			return null;
		}

		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
				.create();

		try {
			return gson.fromJson(jsonString, clazz);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 反序列化数组对象
	 * 
	 * @throws
	 **/
	public static <T> List<T> parseArray(String jsonString, Class<T> clazz) {
		if (clazz == null || jsonString == null || jsonString.length() == 0) {
			return null;
		}
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
				.create();
		Type type = new TypeToken<List<T>>() {
		}.getType();
		Object fromJson2 = gson.fromJson(jsonString, type);
		List<T> list = (List<T>) fromJson2;
		return list;

	}

}
