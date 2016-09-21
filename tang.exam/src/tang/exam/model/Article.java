package tang.exam.model;

import java.io.Serializable;

public class Article implements Serializable {
	public int ID;
	/**
	 * 标题
	 */
	public String Newtitle;
	/**
	 * 作者
	 */
	public String Newauthor;
	/**
	 * 来源
	 */
	public String Newfrom;
	/**
	 * 排序
	 */
	public int Newsort;
	/**
	 * 内容
	 */
	public String Newcont;

	public String Newimages;

}
