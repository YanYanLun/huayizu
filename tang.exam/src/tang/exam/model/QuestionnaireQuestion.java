package tang.exam.model;

import java.io.Serializable;

/**
 * �����б�
 * 
 * @author Administrator
 * 
 */
public class QuestionnaireQuestion implements Serializable {
	public int QuestionnaireItemId;
	public String Name;
	/**
	 * ��ѡ 0 ���Ƿ� 1 ����ѡ 2 ���������� 3�� �������� 4
	 */
	public int Type;
	/**
	 * ��ѡ������ڵ�ѡ����","�ָ�
	 */
	public String CandidateItems;
	/**
	 * �Ƿ��������ڵ��л��������
	 */
	public boolean IsRquired;
	/**
	 * ���ַ���,�������Ƿ�
	 */
	public String TrueName;
	/**
	 * ���ַ���,�������Ƿ�
	 */
	public String FalseName;

}
