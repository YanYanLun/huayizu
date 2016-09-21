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
	 * ��ȡandroid SDK�汾��
	 */
	private static int currentapiVersion = android.os.Build.VERSION.SDK_INT;

	@SuppressLint("NewApi")
	@SuppressWarnings("finally")
	public static String openUrl(String url, int timeout) {
		// if (currentapiVersion > 11) {
		// // ���StrictMode�ĵ���������4.0
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
				// ˳���ȡ�ļ�text������ݲ���ֵ�����ͱ���b,ֱ���ļ�����Ϊֹ��
				while ((b = inread.read()) != -1) {
					if ((char) b == '\n') {
						sb.append("\n");
					} else {
						sb.append((char) b);
					}
				}
				inread.close();// ����д��ر�
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
			// ��������ʽΪGET��ʽ�������൱��������򿪰ٶ���ҳ
			connection.setRequestMethod("POST");
			// �������ó�ʱʱ��Ϊ5�룬5���������Ӳ��ϣ���֪ͨ�û���������������
			connection.setReadTimeout(timeout);
			// ��������֮�󣬵õ�����������������ݾ�����ҳԴ����ֽ���
			InputStream inStream = connection.getInputStream();
			// ���뽫��ת��Ϊ�ַ������ܹ���ȷ����ʾ���û�
			ByteArrayOutputStream data = new ByteArrayOutputStream();// �½�һ�ֽ����������
			byte[] buffer = new byte[1024];// ���ڴ��п���һ�λ���������������������
			int len = 0;
			while ((len = inStream.read(buffer)) != -1) {
				data.write(buffer, 0, len); // ����������֮�󽫻�����������д�������
			}
			inStream.close();
			return new String(data.toByteArray(), "utf-8");// �����Խ��õ��������ת��utf-8������ַ�������ɽ�һ������

		} catch (Exception e) {
			return null;
		}
	}

	public static String Http(String url) {
		try {
			String result = "";
			// ��һ��������HttpGet����
			HttpGet httpGet = new HttpGet(url);
			// �ڶ�����ʹ��execute��������HTTP GET���󣬲�����HttpResponse����
			HttpResponse iii = new DefaultHttpClient().execute(httpGet);
			if (iii.getStatusLine().getStatusCode() == 200) {
				// ��������ʹ��getEntity������÷��ؽ��
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
