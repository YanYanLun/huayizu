package tang.exam.net;

import tang.basic.http.RequestBase;

public class RequestOnlineRegistration extends RequestBase {
	/**
	 * 会员ID
	 */
	public int MemberID;
	/**
	 * 登录验证
	 */
	public String token;
	/**
	 * 报名姓名
	 */
	public String Enname;
	/**
	 * 报名考试地址
	 */
	public String Enadd;
	/**
	 * 报名邮箱
	 */
	public String Enemail;
	/**
	 * 报名电话
	 */
	public String Entel;
	/**
	 * 报名 备注说明
	 */
	public String Remark;
	/**
	 * 报名 报名类型id
	 */
	public int RID;
}
