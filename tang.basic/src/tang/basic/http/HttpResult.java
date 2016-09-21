package tang.basic.http;

import java.io.UnsupportedEncodingException;
import java.util.List;

import tang.basic.common.StringUtil;
import android.util.Log;




public class HttpResult {
	private int  connectstatus=0;
	private byte[] Data;
	private String ContentType;
	private String url;
	
	public HttpResult(){
		setStatus(0);
		setData(null);
		setContentType(null);
		setContentEncoding(null);
	}
	
	public String getString(){
		if(getData()==null)return "";
		try {
			return new String(getData(),getContentEncoding());
		} catch (UnsupportedEncodingException e) {
			Log.d("HttpResult", "getStringΩ‚Œˆ ß∞‹");
			e.printStackTrace();
			return new String(getData());
		}
	}
	
	public <T> T getJsonObject(Class<T> clazz){
		String str=getString();
		Log.d("HttpResult-Data", str);
		if(str==null||str=="")return null;
		return Jsonhelper.parseObject(str, clazz);
	}
	
	public <T> List<T> getJsonArray(Class<T> clazz){
		String str=getString();
		Log.d("HttpResult-Data", str);
		if(str==null||str=="")return null;
		return Jsonhelper.parseArray(str, clazz);
	}

	public int getStatus() {
		return connectstatus;
	}

	public void setStatus(int status) {
		connectstatus = status;
	}

	public String getContentType() {
		if(ContentType==null)return null;
		return ContentType.toLowerCase().split(";")[0].trim().toString();
	}

	public void setContentType(String contentType) {
		ContentType = contentType;
	}
	public String getContentEncoding() {
		if(StringUtil.isEmpty(ContentType)){
			return "utf-8";
		}
		int x = ContentType.indexOf("charset=");    
	     int y = ContentType.lastIndexOf('\"');    
	     if(x<0)    
	         return "utf-8";
	     else if(y>=0)   
	         return ContentType.substring(x+8, y);   
	     else 
	       return ContentType.substring(x+8);  
		//return ContentEncoding;
	}

	public void setContentEncoding(String contentEncoding) {
	}

	public byte[] getData() {
		return Data;
	}

	public void setData(byte[] data) {
		Data = data;
	}
	
	public boolean isSuccess(){
		return getStatus()==0;
	}
	
	public HttpResultType getResultType(){
		String ctp=getContentType();
		if(ctp==null)return HttpResultType.Content;
		if(	ctp.equals("image/jpeg")||
			ctp.equals("application/x-jpg")||
			ctp.equals("application/x-bmp")||
			ctp.equals("image/png")||
			ctp.equals("application/x-png")){
			return HttpResultType.Image;
		}
		if(	ctp.equals("image/gif")){
				return HttpResultType.GifImage;
		}
		if(	ctp.equals("application/x-shockwave-flash")){
			return HttpResultType.Swf;
		}
		if(ctp.equals("text/html")||ctp.equals("application/json")){
			return HttpResultType.Content;
		}
		return HttpResultType.File;
	}
	
	public String getFileExtensions(){
		String ctp=getContentType();
		if(ctp==null)return "";
		if(	ctp.equals("image/jpeg")||ctp.equals("application/x-jpg")){
			return ".jpg";
		}
		if(	ctp.equals("application/x-bmp")){
				return ".bmp";
		}
		if(ctp.equals("image/png")||ctp.equals("application/x-png")){
				return ".png";
		}
		if(	ctp.equals("image/gif")){
			return ".gif";
		}
		if(	ctp.equals("application/x-shockwave-flash")){
			return ".swf";
		}
		if(ctp.equals("text/html")){
			return ".html";
		}
		if(ctp.equals("application/json")){
			return ".json";
		}
		if(ctp.equals("application/vnd.android.package-archive")){
			return ".apk";
		}
		return "";
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
