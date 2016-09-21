package tang.huayizu.net;

import tang.basic.http.HttpClient;
import tang.basic.http.IResponseCallback;
import android.content.Context;

public class NetService {
	/**
	 * ึ๗าณ
	 * 
	 * @param context
	 * @param request
	 * @param callback
	 * @param key
	 */
	public static void GetHomeRequest(Context context, HomeRequest request,
			IResponseCallback<HomeResponse> callback, String key) {
		String agid = "_" + key;
		new HttpClient(context, agid).AsyncGet(Settings.InterFaceUrl + "index",
				request, HomeResponse.class, callback);
	}
}
