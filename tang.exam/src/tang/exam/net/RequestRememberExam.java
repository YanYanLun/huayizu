package tang.exam.net;

import tang.basic.http.RequestBase;

public class RequestRememberExam extends RequestBase {
	/**
	 * 会员ID
	 */
	public int mebcode;
	/**
	 * 当前所做课程菜单ID
	 */
	public int coursecode;
	/**
	 * 传1则不继续答题；传2则修改或新增答题进度；传0则查询是否存在答题进度记忆
	 */
	public int gostate;
	/**
	 * 已做习题ID 以|分隔
	 */
	public String exerID;
	/**
	 * 已做习题答案 以|分隔
	 */
	public String exerAnswer;
	public String token;
}
