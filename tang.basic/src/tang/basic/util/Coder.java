package tang.basic.util;

import java.security.MessageDigest;
import java.util.Random;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import tang.basic.common.InputFormat;
import android.util.Base64;

public class Coder {
	public static final String KEY_SHA = "SHA";
	public static final String KEY_MD5 = "MD5";
	private static final String allChar = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private static final String allCharCode = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ+/";

	/**
	 * MAC算法可选以下多种算法
	 * 
	 * <pre>
	 * HmacMD5  
	 * HmacSHA1  
	 * HmacSHA256  
	 * HmacSHA384  
	 * HmacSHA512
	 * </pre>
	 */
	public static final String KEY_MAC = "HmacMD5";

	/**
	 * BASE64解密
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptBASE64(String key) throws Exception {
		return Base64.decode(key, Base64.DEFAULT);
	}

	/**
	 * BASE64加密
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encryptBASE64(byte[] key) throws Exception {
		return Base64.encodeToString(key, Base64.DEFAULT);
	}

	/**
	 * MD5加密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptMD5(byte[] data) throws Exception {

		MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
		md5.update(data);

		return md5.digest();

	}

	/**
	 * SHA加密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptSHA(byte[] data) throws Exception {

		MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
		sha.update(data);

		return sha.digest();

	}

	/**
	 * 初始化HMAC密钥
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String initMacKey() throws Exception {
		KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_MAC);
		SecretKey secretKey = keyGenerator.generateKey();
		return encryptBASE64(secretKey.getEncoded());
	}

	/**
	 * HMAC加密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptHMAC(byte[] data, String key) throws Exception {

		SecretKey secretKey = new SecretKeySpec(decryptBASE64(key), KEY_MAC);
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		mac.init(secretKey);

		return mac.doFinal(data);

	}

	/**
	 * 字符串的加密与解密
	 * 
	 * @param string
	 *            加密解密的对象
	 * @param key
	 *            密匙
	 * @param flag
	 *            false为加密，true为解密
	 * @return
	 */
	public static String authcode(String string, String key, boolean flag) {
		int expiry = 6;
		return authcode(string, key, flag, expiry);
	}

	/**
	 * 字符串的加密与解密
	 * 
	 * @param string
	 *            加密解密的对象
	 * @param flag
	 *            false为加密，true为解密
	 * @return
	 */
	public static String authcode(String string, boolean flag) {
		return authcode(string, "gc9s4f5da8ofha56jff45de", flag, 6);
	}

	/**
	 * 字符串的加密与解密
	 * 
	 * @param string
	 *            加密解密的对象
	 * @param key
	 *            密匙
	 * @param flag
	 *            false为加密，true为解密
	 * @param expiry
	 *            随机数的位数
	 * @return
	 */
	public static String authcode(String string, String key, boolean flag,
			int expiry) {
		try {
			TempData tempData = new TempData();
			String[] str = StrArray(allCharCode);
			int str_1 = str.length;
			if (flag) {
				string = string.replaceAll(" ", "+");
			}
			tempData.random = flag ? string.substring(0, expiry)
					: generateString(expiry);
			tempData.keya = InputFormat.MD5(key + tempData.random);
			for (int i = 0; i < 32; i++) {
				tempData.rndkey += stringToAscii(tempData.keya.substring(i,
						i + 1));
			}
			tempData.rndkey_1 = tempData.rndkey.length();
			tempData.s = flag ? string.substring(expiry) : encryptBASE64(
					string.getBytes()).replaceAll("=", "");
			tempData.s = tempData.s.trim();
			tempData.s1 = tempData.s.length();
			for (int i = 0; i < tempData.s1; i++) {
				tempData.a = tempData.s.substring(i, i + 1);
				tempData.b = ArrayIndex(str, tempData.a);
				tempData.c = StrArray(tempData.rndkey)[i % tempData.rndkey_1];
				tempData.d = flag ? ((tempData.b + str_1 - Integer
						.parseInt(tempData.c)) % str_1) : (Integer
						.parseInt(tempData.c) + tempData.b) % str_1;
				tempData.data += str[tempData.d];
			}
			tempData.data = flag ? new String(decryptBASE64(tempData.data))
					: (tempData.random + tempData.data);
			return tempData.data;
		} catch (Exception e) {
			return "";
		}
	}

	public static class TempData {
		public String data = "";
		public String rndkey = "";
		public String random;
		public String keya;
		public int rndkey_1;
		public String s;
		public int s1;
		public String a;
		public int b;
		public String c;
		public int d;
	}

	/**
	 * 随机生成字符串
	 * 
	 * @param length
	 * @return
	 */
	public static String generateString(int length) // 参数为返回随机数的长度
	{
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(allChar.charAt(random.nextInt(allChar.length())));
		}
		return sb.toString();
	}

	/**
	 * string转Ascii
	 * 
	 * @param value
	 * @return
	 */
	public static String stringToAscii(String value) {
		StringBuffer sbu = new StringBuffer();
		char[] chars = value.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (i != chars.length - 1) {
				sbu.append((int) chars[i]).append(",");
			} else {
				sbu.append((int) chars[i]);
			}
		}
		return sbu.toString();
	}

	/**
	 * Ascii转string
	 * 
	 * @param value
	 * @return
	 */
	public static String asciiToString(String value) {
		StringBuffer sbu = new StringBuffer();
		String[] chars = value.split(",");
		for (int i = 0; i < chars.length; i++) {
			sbu.append((char) Integer.parseInt(chars[i]));
		}
		return sbu.toString();
	}

	/**
	 * 字符串转数组
	 * 
	 * @param str
	 * @return
	 */
	public static String[] StrArray(String str) {
		String[] temp = new String[str.length()];
		for (int i = 0; i < temp.length; i++) {
			temp[i] = (new Character(str.charAt(i))).toString();
		}
		return temp;
	}

	/**
	 * 遍历数组，返回所匹配的位置
	 * 
	 * @param array
	 * @param str
	 * @return
	 */
	public static int ArrayIndex(String[] array, String str) {
		int cc = 0;
		for (int i = 0; i < array.length; i++) {
			if (str.equals(array[i])) {
				cc = i;
				break;
			}
		}
		return cc;
	}
}
