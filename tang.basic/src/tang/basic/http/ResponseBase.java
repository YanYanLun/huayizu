package tang.basic.http;

import java.io.Serializable;

/** @pdOid 75e7a9cd-01c2-44fc-a5e7-e2ddb3e94ec2 */
@SuppressWarnings("serial")
public class ResponseBase implements Serializable {
	/**
	 * ͨѶ״̬��
	 */
	public int StatusCode;
	/**
	 * ͨѶ��Ϣ
	 */
	public String StatusMessage;
	/**
	 * 1��ʾ����2��ʾ��ȷ
	 */
	// public int ret;
	/**
	 * ��ʾ��Ϣ
	 */
	// public String msg;
}