package tang.basic.http;

public class ErrorCode {
	//操作失败
	public static int FAIL=0;
	
	//服务器异常
	public static int SERVERERROR=1001;
	
	//服务协议过期
	public static int AGREEMENTEXPIRES=1002;
	
	//登陆超时
	public static int LOGINTIMEOUT=1003;
	
	//账号已冻结
	public static int ACCOUNTFREEZE=1004;
	
	//软件版本已过期
	public static int SOFTEXPIRES=1005;
	
	
	/**本机错误码**/
	//网络断开
	public static int NETWORKDISCONNECT=8001;
	
	//请求超时
	public static int CONNECTTIMEOUT=8002;
}
