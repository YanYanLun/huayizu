package tang.basic.http;

import java.io.Serializable;

/** @pdOid 75e7a9cd-01c2-44fc-a5e7-e2ddb3e94ec2 */
@SuppressWarnings("serial")
public class ResponseBase implements Serializable {
	/**
	 * 通讯状态码
	 */
	public int StatusCode;
	/**
	 * 通讯消息
	 */
	public String StatusMessage;
	/**
	 * 1表示错误，2表示正确
	 */
	// public int ret;
	/**
	 * 提示信息
	 */
	// public String msg;
}