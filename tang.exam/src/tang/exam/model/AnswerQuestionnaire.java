package tang.exam.model;

import java.io.Serializable;

/**
 * ��
 * 
 * @author Administrator
 * 
 */
public class AnswerQuestionnaire implements Serializable {
	/**
	 * id
	 */
	public int QuestionnaireItemId;
	/**
	 * ��
	 */
	public String Answer;
	/**
	 * �������
	 */
	public int SerialNumber;
	/**
	 * ��ѡ 0 ���Ƿ� 1 ����ѡ 2 ���������� 3�� �������� 4
	 */
	public int Type;
	/**
	 * �Ƿ����
	 */
	public boolean IsRquired;
	/**
	 * �Ƿ����
	 */
	public boolean isError = false;
}
