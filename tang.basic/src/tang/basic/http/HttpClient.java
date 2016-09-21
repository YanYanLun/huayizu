package tang.basic.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class HttpClient {

	private String agid = "";

	private Context _context;
	// 识别码
	private String Agent = "Android Httphelper";
	// 接受格式
	private String Accept = "*/*";
	// 连接超时时间(秒)
	private int ConnectTimeout = 10;
	// 读取超时时间(秒)
	private int ReadTimeout = 0;

	private String RequestContentType = null;
	private String IntentOnComplet = "OnComplet:";
	private String IntentOnError = "OnError:";

	/**
	 * 
	 * @param context
	 */
	public HttpClient(Context context) {
		_context = context;
	}

	/**
	 * 
	 * @param context
	 * @param agid
	 *            协议标识
	 */
	public HttpClient(Context context, String agid) {
		_context = context;
		this.agid = agid;
	}

	public String getAgent() {
		return Agent;
	}

	public void setAgent(String agent) {
		Agent = agent;
	}

	public String getAccept() {
		return Accept;
	}

	public void setAccept(String accept) {
		Accept = accept;
	}

	public int getConnectTimeout() {
		return ConnectTimeout;
	}

	public void setConnectTimeout(int connectTimeout) {
		ConnectTimeout = connectTimeout;
	}

	public int getReadTimeout() {
		return ReadTimeout;
	}

	public void setReadTimeout(int readTimeout) {
		ReadTimeout = readTimeout;
	}

	public String getRequestContentType() {
		return RequestContentType;
	}

	public void setRequestContentType(String requestContentType) {
		RequestContentType = requestContentType;
	}

	private HttpResult post(String url, Object data) {
		HttpResult result = new HttpResult();
		URLConnection conn = null;
		try {
			conn = getConnection(url, "POST", data);
			// Log.d("Httphelper",url+"请求完成");
			int statuscode = HttpURLConnection.HTTP_OK;
			if (url.indexOf("https:") == -1) {
				statuscode = ((HttpURLConnection) conn).getResponseCode();
			} else {
				statuscode = ((HttpsURLConnection) conn).getResponseCode();
			}
			if (statuscode != HttpURLConnection.HTTP_OK) {
				Log.d("Httphelper", "post error:" + statuscode);
				result.setStatus(ErrorCode.NETWORKDISCONNECT);
				// return result;
			} else {
				result.setContentEncoding(conn.getContentEncoding());
				result.setContentType(conn.getContentType());
				result.setData(fetchData(conn));
				result.setStatus(0);
			}
			// return result;
		} catch (IOException e) {
			Log.d("Httphelper", "请求发生错误IOException");
			if (e instanceof java.net.SocketTimeoutException) {
				result.setStatus(ErrorCode.CONNECTTIMEOUT);
			}
			e.printStackTrace();
			// return result;
		} catch (Exception e) {
			Log.d("Httphelper", "请求发生错误");
			if (e instanceof java.net.SocketTimeoutException) {
				result.setStatus(ErrorCode.CONNECTTIMEOUT);
			}
			e.printStackTrace();
			// return result;
		} finally {
			if (conn != null) {
				if (url.indexOf("https:") == -1) {
					((HttpURLConnection) conn).disconnect();
				} else {
					((HttpsURLConnection) conn).disconnect();
				}
			}
		}
		Log.d("Httphelper", "result");
		return result;
	}

	/**
	 * 获取一个连接里面数据
	 * 
	 * @param conn
	 * @return
	 * @throws IOException
	 */
	private byte[] fetchData(URLConnection conn) throws IOException {
		InputStream in = conn.getInputStream();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buff = new byte[1024];
		int len = 0;
		while ((len = in.read(buff, 0, 1024)) != -1) {
			out.write(buff, 0, len);
		}
		byte[] result = out.toByteArray();
		out.close();
		in.close();
		return result;
	}

	public <T extends Serializable> void AsyncGet(String url,
			RequestBase request, Class<T> clazzResponse,
			IResponseCallback<T> callback) {
		new ResponseCallbackHelper<T>(url, request, clazzResponse, callback)
				.start();
	}

	class ResponseCallbackHelper<T extends Serializable> extends Thread {
		IResponseCallback<T> callback;
		String url;
		RequestBase request;
		Class<T> clazzResponse;

		public ResponseCallbackHelper(String _url, RequestBase _request,
				Class<T> _clazzResponse, IResponseCallback<T> _callback) {
			callback = _callback;
			url = _url;
			request = _request;
			clazzResponse = _clazzResponse;
		}

		@Override
		public void run() {
			try {
				T o = GetData(url, request, clazzResponse);
				if (callback != null)
					callback.onResponse(o, request.getClass().getName());
				Intent intent = new Intent(IntentOnComplet
						+ clazzResponse.getName() + agid);
				intent.putExtra("DATA", o);
				_context.sendBroadcast(intent);
			} catch (TxException e) {
				if (callback != null)
					callback.onError(e, request.getClass().getName());
				Intent intent = new Intent(IntentOnError
						+ clazzResponse.getName() + agid);
				intent.putExtra("ERROR", e);
				_context.sendBroadcast(intent);
			}
		}
	}

	public <T extends Serializable> T GetData(String url, RequestBase request,
			Class<T> clazzResponse) throws TxException {
		CheckNetwork();
		HttpResult result = post(url, request);
		Log.d("DataGate", "Finish post");
		if (result.getStatus() != 0) {
			Log.d("DataGate", "Err:" + result.getStatus());
			throw new TxException(result.getStatus());
		}

		return result.getJsonObject(clazzResponse);
	}

	private String GetUrl(Object request) {
		String Method = request.getClass().getSimpleName()
				.replace("Request", "");
		return Method;
	}

	private void CheckNetwork() throws TxException {
		if (!HavaNetwork()) {
			Log.d("DataGate", "Err:Disconnection");
			throw new TxException(ErrorCode.NETWORKDISCONNECT);
		}
	}

	private boolean HavaNetwork() {
		ConnectivityManager connMgr = (ConnectivityManager) _context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		return networkInfo != null;
	}

	private java.net.Proxy getHttpProxy() {
		NetworkInfo networkInfo = ((android.net.ConnectivityManager) _context
				.getSystemService(Context.CONNECTIVITY_SERVICE))
				.getActiveNetworkInfo();
		if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
			String proxyHost = android.net.Proxy.getHost(_context);// android.net.Proxy.getDefaultHost();
			if (proxyHost == null || proxyHost == "") {
				// Log.d("Httphelper","没网关");
				return null;
			} else {
				// Log.d("Httphelper","nmb真有代理网关："+android.net.Proxy.getHost(TXApplication.Current));//android.net.Proxy.getDefaultHost());
				java.net.Proxy p = new java.net.Proxy(java.net.Proxy.Type.HTTP,
						new InetSocketAddress(proxyHost,
								android.net.Proxy.getDefaultPort()));
				return p;
			}
		} else {
			return null;

		}
	}

	private class EmptyX509TrustManager implements X509TrustManager {
		@Override
		public void checkClientTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}

		@Override
		public void checkServerTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

	}

	private URLConnection getConnection(String url, String method, Object data)
			throws IOException {
		java.net.Proxy proxy = getHttpProxy();
		URL uri = new URL(url);
		URLConnection conn = null;
		if (url.indexOf("https:") != -1) {
			HostnameVerifier hv = new HostnameVerifier() {
				@Override
				public boolean verify(String urlHostName, SSLSession session) {
					return true;
				}

			};
			HttpsURLConnection.setDefaultHostnameVerifier(hv);
			SSLContext sc;
			try {
				sc = SSLContext.getInstance("TLS");
				sc.init(null,
						new TrustManager[] { new EmptyX509TrustManager() },
						new SecureRandom());
				HttpsURLConnection.setDefaultSSLSocketFactory(sc
						.getSocketFactory());
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (KeyManagementException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (proxy != null) {
			conn = uri.openConnection(proxy);
		} else {
			conn = uri.openConnection();
		}
		// conn.setRequestProperty("Content-type",
		// "application/x-java-serialized-object");
		Log.d("Httphelper", "请求地址：" + url);
		conn.setRequestProperty("User-Agent", getAgent());
		conn.setRequestProperty("Accept", getAccept());
		if (getRequestContentType() != null)
			conn.setRequestProperty("Content-Type", getRequestContentType());
		conn.setRequestProperty("Cache-Control", "no-cache");
		if (getConnectTimeout() > 0)
			conn.setConnectTimeout(getConnectTimeout() * 1000);
		if (getReadTimeout() > 0)
			conn.setReadTimeout(getReadTimeout() * 1000);
		try {
			if (method.equals("POST") && data != null) {
				conn.setDoOutput(true);
				String str = Jsonhelper.toQueryString(data);
				Log.d("Httphelper", "POST数据：" + str);
				OutputStream outStrm = conn.getOutputStream();
				outStrm.write(str.getBytes());
				outStrm.flush();
				outStrm.close();
			} else {
				// /Log.d("Httphelper","method:"+method);
				if (data == null) {
					// Log.d("Httphelper","data:null");
				}
			}
		} catch (IOException e) {
			if (url.indexOf("https:") == -1) {
				((HttpURLConnection) conn).disconnect();
			} else {
				((HttpsURLConnection) conn).disconnect();
			}
			throw e;
		}
		return conn;
	}
}
