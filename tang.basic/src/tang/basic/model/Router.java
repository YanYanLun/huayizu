package tang.basic.model;

import java.io.Serializable;

import tang.basic.common.StringUtil;
import tang.basic.sql.ISQLiteHelper;
import android.content.ContentValues;

public class Router implements Serializable {
	public int id;
	public String name;
	public String disconnurl;
	public String disconnpost;
	public String connurl;
	public String connpost;

	public long Save(ISQLiteHelper dao) {
		ContentValues values = new ContentValues();
		values.put("id", this.id);
		if (StringUtil.isEmpty(this.name)) {
			values.putNull("name");
		} else {
			values.put("name", this.name);
		}
		if (!StringUtil.isEmpty(this.disconnurl)) {
			values.put("disconnurl", this.disconnurl);
		} else {
			values.putNull("disconnurl");
		}
		if (!StringUtil.isEmpty(this.disconnpost)) {
			values.put("disconnpost", this.disconnpost);
		} else {
			values.putNull("disconnpost");
		}
		if (!StringUtil.isEmpty(this.connurl)) {
			values.put("connurl", this.connurl);
		} else {
			values.putNull("connurl");
		}
		if (!StringUtil.isEmpty(this.connpost)) {
			values.put("connpost", this.connpost);
		} else {
			values.putNull("connpost");
		}
		return dao.insert(null, null, values, "Router");
	}
}
