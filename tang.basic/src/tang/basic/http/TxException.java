package tang.basic.http;

@SuppressWarnings("serial")
public class TxException extends Exception {
	public int errorCode = 1;// 1没有错误

	public TxException(int code) {
		this.errorCode = code;
	}

	public String getMessage() {
		if (errorCode == ErrorCode.FAIL) {
			return "操作失败";
		}
		if (errorCode == ErrorCode.SERVERERROR) {
			return "服务器异常";
		}
		if (errorCode == ErrorCode.AGREEMENTEXPIRES) {
			return "服务协议过期";
		}
		if (errorCode == ErrorCode.LOGINTIMEOUT) {
			return "登陆超时";
		}
		if (errorCode == ErrorCode.ACCOUNTFREEZE) {
			return "账号已冻结";
		}

		if (errorCode == ErrorCode.SOFTEXPIRES) {
			return "软件版本已过期";
		}
		if (errorCode == ErrorCode.NETWORKDISCONNECT) {
			return "网络已断开";
		}
		if (errorCode == ErrorCode.CONNECTTIMEOUT) {
			return "网络请求超时";
		}

		return super.getMessage();
	}
}
