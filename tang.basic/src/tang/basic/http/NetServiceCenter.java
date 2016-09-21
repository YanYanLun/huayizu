package tang.basic.http;

import android.content.Context;

public class NetServiceCenter {

	/**
	 * 获取最新版本
	 * 
	 * @param context
	 * @param request
	 * @param callback
	 */
	public static void GetSoftVersionReqeust(Context context,
			RequestBase request,
			IResponseCallback<ResponseBase> callback) {
		String agid = "";
		new HttpClient(context, agid).AsyncGet(Settings.BusinessInterFaceUrl
				+ "GetSoftVersion", request, ResponseBase.class,
				callback);
	}
	//
	// /**
	// * 发送程序崩溃
	// *
	// * @param context
	// * @param request
	// * @param callback
	// */
	// public static void BugReportRequest(Context context,
	// BugReportRequest request, IResponseCallback<ResponseBase> callback) {
	// String agid = "";
	// new HttpClient(context, agid).AsyncGet(Settings.BusinessInterFaceUrl
	// + "BugReport", request, ResponseBase.class, callback);
	// }
	//
	// /**
	// * 请求锁屏
	// *
	// * @param context
	// * @param request
	// * @param callback
	// */
	// public static void PullRequest(Context context, GetPullRequest request,
	// IResponseCallback<GetPullResponse> callback) {
	// String agid = "";
	// new HttpClient(context, agid).AsyncGet(Settings.BusinessInterFaceUrl
	// + "pull", request, GetPullResponse.class, callback);
	// }
	//
	// /**
	// * 解锁反馈
	// *
	// * @param context
	// * @param request
	// * @param callback
	// */
	// public static void ConfirmRequest(Context context,
	// GetConfirmRequest request,
	// IResponseCallback<GetConfirmResponse> callback) {
	// String agid = "";
	// new HttpClient(context, agid).AsyncGet(Settings.BusinessInterFaceUrl
	// + "confirm", request, GetConfirmResponse.class, callback);
	// }
	//
	// /**
	// * 未读消息
	// *
	// * @param context
	// * @param request
	// * @param callback
	// */
	// public static void GetUnreadMessageRequest(Context context,
	// GetUnreadMessagesRequest request,
	// IResponseCallback<GetUnreadMessagesResponse> callback) {
	// String agid = "";
	// new HttpClient(context, agid).AsyncGet(Settings.BusinessInterFaceUrl
	// + "GetMessage", request, GetUnreadMessagesResponse.class,
	// callback);
	// }
	//
	// /**
	// * 标记已读
	// *
	// * @param context
	// * @param request
	// * @param callback
	// */
	// public static void ConfirmMessageRequest(Context context,
	// ConfirmMessageRequest request,
	// IResponseCallback<ConfirmMessageResponse> callback) {
	// String agid = "";
	// new HttpClient(context, agid).AsyncGet(Settings.BusinessInterFaceUrl
	// + "ConfirmMessage", request, ConfirmMessageResponse.class,
	// callback);
	// }
}
