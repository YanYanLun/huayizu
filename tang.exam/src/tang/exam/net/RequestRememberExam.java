package tang.exam.net;

import tang.basic.http.RequestBase;

public class RequestRememberExam extends RequestBase {
	/**
	 * ��ԱID
	 */
	public int mebcode;
	/**
	 * ��ǰ�����γ̲˵�ID
	 */
	public int coursecode;
	/**
	 * ��1�򲻼������⣻��2���޸Ļ�����������ȣ���0���ѯ�Ƿ���ڴ�����ȼ���
	 */
	public int gostate;
	/**
	 * ����ϰ��ID ��|�ָ�
	 */
	public String exerID;
	/**
	 * ����ϰ��� ��|�ָ�
	 */
	public String exerAnswer;
	public String token;
}
