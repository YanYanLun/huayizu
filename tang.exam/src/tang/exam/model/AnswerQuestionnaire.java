package tang.exam.model;

import java.io.Serializable;

/**
 * 答案
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
	 * 答案
	 */
	public String Answer;
	/**
	 * 试题序号
	 */
	public int SerialNumber;
	/**
	 * 单选 0 ；是否 1 ；多选 2 ；单行输入 3； 多行输入 4
	 */
	public int Type;
	/**
	 * 是否必填
	 */
	public boolean IsRquired;
	/**
	 * 是否错误
	 */
	public boolean isError = false;
}
