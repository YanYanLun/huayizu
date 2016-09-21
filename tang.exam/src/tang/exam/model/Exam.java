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
	 * 答案 如果是多选题，返回“A，B”； 多选答案逗号分隔符可能是中文或者是英文字符； 如果是是非题则返回“是”或“非”
	 */
	public String Choicetrue;
	/**
	 * 习题类型 “0”为单选择题，“1”为是非题，2为多选
	 */
	public int Choicestate;
	/**
	 * 错题解析
	 */
	public String Remark;
	/**
	 * 用户选择的答案
	 */
	public String MemberChoice;
}
