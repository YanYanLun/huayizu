package yalantis.com.sidemenu.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtil {
	public static boolean isEmpty(String value) {  
		return value==null||value.trim().equals("");
	}  
	
	public static String getLimitNumber(int value){
		if(value>9){
			return "9+";
		}else{
			return String.valueOf(value);
		}
	}
	
	public static String getDateString(Date date){
		return getDateString(date,"yyyy-MM-dd");
	}
	public static String getDateString(Date date,String fomart){
		if(date==null){return null;}
		SimpleDateFormat format = new SimpleDateFormat(fomart);
		return format.format(date);		
	}
	
	public static String replaceHTML(String str){  
		if(isEmpty(str))return "";
	    String s1=str.replaceAll("<(link|style)[^>]+>*?</(link|style)[^>]>", "");  
	    s1=s1.replaceAll("</?[^>]+>", " ");  
	    s1=s1.replaceAll("\\&nbsp;", " ");  
	    s1=s1.replaceAll("\\&lt;", "<");  
	    s1=s1.replaceAll("\\&gt;", ">");  
	    s1=s1.replaceAll("\\&mdash;", "");  
	    s1=s1.replaceAll("\\&deg;", "");  
	    s1=s1.replaceAll("\\&ldquo;", "");  
	    s1=s1.replaceAll("\\&rdquo;", "");  
	    s1=s1.replaceAll("\\&middot;", "");  
	    s1=s1.replaceAll("\\&lsquo;", "¡®");  
	    s1=s1.replaceAll("\\&rsquo;", "¡¯");  
	    s1=s1.replaceAll("\\&hellip;", "¡­");  
	    return s1;  
	}  
}
