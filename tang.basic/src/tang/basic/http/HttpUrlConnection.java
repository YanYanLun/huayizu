package tang.basic.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.loopj.android.http.HttpGet;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

public class HttpUrlConnection {
	public final static String GET = "GET";
	public final static String POST = "POST";
	/**
	 * 获取android SDK版本号
	 */
	private static int currentapiVersion = android.os.Build.VERSION.SDK_INT;

	@SuppressLint("NewApi")
	@SuppressWarnings("finally")
	public static String openUrl(String url, int timeout) {
		// if (currentapiVersion > 11) {
		// // 详见StrictMode文档，适用于4.0
		// StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
		// .detectDiskReads().detectDiskWrites().detectNetwork()
		// .penaltyLog().build());
		// StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
		// .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
		// .penaltyLog().penaltyDeath().build());
		// }
		String result = null;
		StringBuffer sb = new StringBuffer();
		URL httpURL;
		HttpURLConnection connection = null;
		try {
			httpURL = new URL(url);
			try {
				connection = (HttpURLConnection) httpURL.openConnection();
				connection.setDoInput(true);
				connection.setDoOutput(false);
				connection.setRequestMethod(GET);
				connection.setConnectTimeout(timeout);
				connection.setReadTimeout(timeout);
				connection.connect();
				InputStream in = connection.getInputStream();
				InputStreamReader inread = new InputStreamReader(in, "utf-8");
				int b;
				// 顺序读取文件text里的内容并赋值给整型变量b,直到文件结束为止。
				while ((b = inread.read()) != -1) {
					if ((char) b == '\n') {
						sb.append("\n");
					} else {
						sb.append((char) b);
					}
				}
				inread.close();// 数据写入关闭
				result = sb.toString();
			} catch (IOException e) {
				System.out.println("HttpURLConnection:" + e.getMessage());
				result = null;
			}
		} catch (MalformedURLException e) {
			System.out.println("HttpURLConnection:" + e.getMessage());
			Log.i("HttpURLConnection", e.getMessage());
			result = null;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
			return result;
		}
	}

	public static String HttpGetOrPost(String nurl, int timeout) {
		try {
			URL url = new URL(nurl);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			// 设置请求方式为GET方式，就是相当于浏览器打开百度网页
			connection.setRequestMethod("POST");
			// 接着设置超时时间为5秒，5秒内若连接不上，则通知用户网络连接有问题
			connection.setReadTimeout(timeout);
			// 若连接上之后，得到网络的输入流，内容就是网页源码的字节码
			InputStream inStream = connection.getInputStream();
			// 必须将其转换为字符串才能够正确的显示给用户
			ByteArrayOutputStream data = new ByteArrayOutputStream();// 新建一字节数组输出流
			byte[] buffer = new byte[1024];// 在内存中开辟一段缓冲区，接受网络输入流
			int len = 0;
			while ((len = inStream.read(buffer)) != -1) {
				data.write(buffer, 0, len); // 缓冲区满了之后将缓冲区的内容写到输出流
			}
			inStream.close();
			return new String(data.toByteArray(), "utf-8");// 最后可以将得到的输出流转成utf-8编码的字符串，便可进一步处理

		} catch (Exception e) {
			return null;
		}
	}

	public static String Http(String url) {
		try {
			String result = "";
			// 第一步，创建HttpGet对象
			HttpGet httpGet = new HttpGet(url);
			// 第二步，使用execute方法发送HTTP GET请求，并返回HttpResponse对象
			HttpResponse iii = new DefaultHttpClient().execute(httpGet);
			if (iii.getStatusLine().getStatusCode() == 200) {
				// 第三步，使用getEntity方法活得返回结果
				result = EntityUtils.toString(iii.getEntity());
				result = result.substring(result.length() - 100,
						result.length());
			}
			return result;
		} catch (Exception e) {
			return "";
		}
	}
}
