package tang.basic.http;

/** @pdOid 0aca90cc-f8f3-40bd-8579-5a183e905461 */
public class RequestBase {
	/**
	 * SessionKey
	 */
	// public String SessionKey;
	/**
	 * �û�Ψһ��ʶ��
	 */
	// public String UserID;
	/**
	 * �ͻ��˰汾��
	 */
	// public String AppVersion;
	/**
	 * Token�ֻ�����
	 */
	public String Token;
	/**
	 * �ֻ��汾
	 */
	// public String PhoneVersion;
	/**
	 * �ֻ��ͺ�
	 */
	// public String PhoneModel;
	public String mobileinfo = "Android," + android.os.Build.MODEL + ","
			+ android.os.Build.VERSION.RELEASE;

}