package tang.exam.model;

import java.io.Serializable;

public class Exam implements Serializable {
	public int ID;
	public String Extitle;
	public String ChoiceA;
	public String ChoiceB;
	public String ChoiceC;
	public String ChoiceD;
	public String ChoiceE;
	public String ChoiceF;
	/**
	 * �� ����Ƕ�ѡ�⣬���ء�A��B���� ��ѡ�𰸶��ŷָ������������Ļ�����Ӣ���ַ��� ������Ƿ����򷵻ء��ǡ��򡰷ǡ�
	 */
	public String Choicetrue;
	/**
	 * ϰ������ ��0��Ϊ��ѡ���⣬��1��Ϊ�Ƿ��⣬2Ϊ��ѡ
	 */
	public int Choicestate;
	/**
	 * �������
	 */
	public String Remark;
	/**
	 * �û�ѡ��Ĵ�
	 */
	public String MemberChoice;
}
