package tang.exam.net;

import tang.basic.http.RequestBase;

public class RequestErrorExam extends RequestBase {
	/**
	 * 用户id
	 */
	public int meID;
	/**
	 * 答题数量
	 */
	public int qusnum;
	/**
	 * 错题数量
	 */
	public int errorqusnum;
	/**
	 * 得分，直接传100
	 */
	public int myscore;
	/**
	 * 错题ID，用|分割
	 */
	public String Errorid;
	/**
	 * 当前所做课程菜单ID
	 */
	public int CID;
	public String token;

}
