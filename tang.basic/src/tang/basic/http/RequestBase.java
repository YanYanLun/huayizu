package tang.basic.http;

/** @pdOid 0aca90cc-f8f3-40bd-8579-5a183e905461 */
public class RequestBase {
	/**
	 * SessionKey
	 */
	// public String SessionKey;
	/**
	 * 用户唯一标识号
	 */
	// public String UserID;
	/**
	 * 客户端版本号
	 */
	// public String AppVersion;
	/**
	 * Token手机令牌
	 */
	public String Token;
	/**
	 * 手机版本
	 */
	// public String PhoneVersion;
	/**
	 * 手机型号
	 */
	// public String PhoneModel;
	public String mobileinfo = "Android," + android.os.Build.MODEL + ","
			+ android.os.Build.VERSION.RELEASE;

}