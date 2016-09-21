package tang.basic.http;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import org.apache.http.Header;

import android.content.Context;
import android.content.Intent;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class AsyncHttpClientGet {
	private String _agid = "";
	private Context _context;
	private String IntentOnComplet = "OnComplet:";
	private String IntentOnError = "OnError:";

	public AsyncHttpClientGet(Context context, String agid) {
		this._context = context;
		this._agid = agid;
	}

	public <T extends Serializable> void AsyncGet(String url,
			RequestParams params, Class<T> clazzResponse) {
		new ResponseCallbackHelper<T>(url, params, clazzResponse).start();
	}

	class ResponseCallbackHelper<T extends Serializable> extends Thread {
		String url;
		RequestParams params;
		Class<T> clazzResponse;

		public ResponseCallbackHelper(String _url, RequestParams _params,
				Class<T> _clazzResponse) {
			url = _url;
			params = _params;
			clazzResponse = _clazzResponse;
			Get();
		}

		private void Get() {
			AsyncHttpClient client = new AsyncHttpClient();
			client.get(url, params, new AsyncHttpResponseHandler() {

				@Override
				public void onFailure(int arg0, Header[] arg1, byte[] arg2,
						Throwable arg3) {
					String str = new String(arg2, 0, arg2.length);
					Intent intent = new Intent(IntentOnError
							+ clazzResponse.getName() + _agid);
					intent.putExtra("ERROR", str);
					_context.sendBroadcast(intent);

				}

				@Override
				public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
					String str = new String(arg2, 0, arg2.length);
					T o = GetData(clazzResponse, str);
					Intent intent = new Intent(IntentOnComplet
							+ clazzResponse.getName()
							+ AsyncHttpClientGet.this._agid);
					intent.putExtra("DATA", o);
					_context.sendBroadcast(intent);
				}

			});
		}
	}

	public <T extends Serializable> T GetData(Class<T> clazzResponse,
			String content) {
		return Jsonhelper.parseObject(content, clazzResponse);
	}
}
