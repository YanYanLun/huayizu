package tang.exam.model;

import java.io.Serializable;

/**
 * 问题列表
 * 
 * @author Administrator
 * 
 */
public class QuestionnaireQuestion implements Serializable {
	public int QuestionnaireItemId;
	public String Name;
	/**
	 * 单选 0 ；是否 1 ；多选 2 ；单行输入 3； 多行输入 4
	 */
	public int Type;
	/**
	 * 候选项，仅用于单选或多项，","分隔
	 */
	public String CandidateItems;
	/**
	 * 是否必填，仅用于单行或多行输入
	 */
	public boolean IsRquired;
	/**
	 * 是字符串,仅用于是否
	 */
	public String TrueName;
	/**
	 * 否字符串,仅用于是否
	 */
	public String FalseName;

}
