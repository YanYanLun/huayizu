package tang.huayizu.model;

import java.io.Serializable;
import java.util.List;

public class Home implements Serializable {
	public List<Banner> carousel_list;
	public List<Recommend> selected_list;
	public List<Exercise> special_list;
}
