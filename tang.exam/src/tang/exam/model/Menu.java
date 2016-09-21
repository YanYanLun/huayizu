package tang.exam.model;

import java.io.Serializable;

public class Menu implements Serializable {
	public int ID;
	public String Name;
	public String Remark;
	public int SortIndex;
	public String Parent;
	public String Children;
	public String Exercisess;
	public String Memberss;
	public int TreeLevel;
	public boolean Enabled;
	public boolean IsTreeLeaf;
}
