package tang.exam.net;

import android.content.Context;
import tang.basic.http.HttpClient;
import tang.basic.http.IResponseCallback;

public class NetServerCenter {
	private static final String Uri = "http://liuapp.gotoip55.com/APPAPI/";

	/**
	 * 获取我的快捷按钮
	 * 
	 * @param context
	 * @param request
	 * @param callback
	 * @param key区分是否是获取数量
	 */
	public static void GetQuickButtonRequest(Context context, RequestQuickButton request,
			IResponseCallback<ResponseQuickButton> callback, String key) {
		String agid = "_" + key;
		new HttpClient(context, agid).AsyncGet(Uri + "WebForm3.aspx", request, ResponseQuickButton.class, callback);
	}

	public static void GetMenuRequest(Context context, RequestMenu request, IResponseCallback<ResponseMenu> callback,
			String key) {
		String agid = "_" + key;
		new HttpClient(context, agid).AsyncGet(Uri + "WebForm1.aspx", request, ResponseMenu.class, callback);
	}

	public static void GetArticleRequest(Context context, RequestArticle request,
			IResponseCallback<ResponseArticle> callback, String key) {
		String agid = "_" + key;
		new HttpClient(context, agid).AsyncGet(Uri + "WebForm3.aspx", request, ResponseArticle.class, callback);
	}

	public static void GetExamRequest(Context context, RequestExam request, IResponseCallback<ResponseExam> callback,
			String key) {
		String agid = "_" + key;
		new HttpClient(context, agid).AsyncGet(Uri + "WebForm1.aspx", request, ResponseExam.class, callback);
	}

	public static void GetLoginRequest(Context context, RequestLogin request, IResponseCallback<ResponseLogin> callback,
			String key) {
		String agid = "_" + key;
		new HttpClient(context, agid).AsyncGet(Uri + "WebForm4.aspx", request, ResponseLogin.class, callback);
	}

	public static void GetExamSpeedRequest(Context context, RequestExamSpeed request,
			IResponseCallback<ResponseExamSpeed> callback, String key) {
		String agid = "_" + key;
		new HttpClient(context, agid).AsyncGet(Uri + "WebForm5.aspx", request, ResponseExamSpeed.class, callback);
	}

	public static void GetResetPwdRequest(Context context, RequestResetPwd request,
			IResponseCallback<ResponseResetPwd> callback, String key) {
		String agid = "_" + key;
		new HttpClient(context, agid).AsyncGet(Uri + "WebForm8.aspx", request, ResponseResetPwd.class, callback);
	}

	public static void GetReviseInfoRequest(Context context, RequestReviseInfo request,
			IResponseCallback<ResponseReviseInfo> callback, String key) {
		String agid = "_" + key;
		new HttpClient(context, agid).AsyncGet(Uri + "WebForm7.aspx", request, ResponseReviseInfo.class, callback);
	}

	public static void GetErrorExamRequest(Context context, RequestErrorExam request,
			IResponseCallback<ResponseErrorExam> callback, String key) {
		String agid = "_" + key;
		new HttpClient(context, agid).AsyncGet(Uri + "WebForm2.aspx", request, ResponseErrorExam.class, callback);
	}

	public static void GetRememberExamRequest(Context context, RequestRememberExam request,
			IResponseCallback<ResponseRememberExam> callback, String key) {
		String agid = "_" + key;
		new HttpClient(context, agid).AsyncGet(Uri + "WebForm5.aspx", request, ResponseRememberExam.class, callback);
	}

	public static void GetTypeRequest(Context context, RequestType request, IResponseCallback<ResponseType> callback,
			String key) {
		String agid = "_" + key;
		new HttpClient(context, agid).AsyncGet(Uri + "WebForm9.aspx", request, ResponseType.class, callback);
	}

	public static void GetOnlineRegistrationRequest(Context context, RequestOnlineRegistration request,
			IResponseCallback<ResponseOnlineRegistration> callback, String key) {
		String agid = "_" + key;
		new HttpClient(context, agid).AsyncGet(Uri + "WebForm10.aspx", request, ResponseOnlineRegistration.class,
				callback);
	}

	public static void GetRegisterRequest(Context context, RequestRegister request,
			IResponseCallback<ResponseRegister> callback, String key) {
		String agid = "_" + key;
		new HttpClient(context, agid).AsyncGet(Uri + "WebForm6.aspx", request, ResponseRegister.class, callback);
	}

	public static void GetVersionRequest(Context context, RequestUpdate request,
			IResponseCallback<ResponseUpdate> callback, String key) {
		String agid = "_" + key;
		new HttpClient(context, agid).AsyncGet(Uri + "AppUpdate.aspx", request, ResponseUpdate.class, callback);
	}
}
