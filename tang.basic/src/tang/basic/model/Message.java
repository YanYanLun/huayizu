package tang.basic.model;

import tang.basic.common.StringUtil;
import tang.basic.sql.ISQLiteHelper;
import android.content.ContentValues;

public class Message {
	public String id;
	public String ip;
	public String order;
	public int time;

	public long Save(ISQLiteHelper dao) {
		ContentValues values = new ContentValues();
		if (StringUtil.isEmpty(this.ip)) {
			values.putNull("ip");
		} else {
			values.put("ip", this.ip);
		}
		if (!StringUtil.isEmpty(this.order)) {
			values.put("myorder", this.order);
		} else {
			values.putNull("myorder");
		}
		if (!StringUtil.isEmpty(this.id)) {
			values.put("id", this.id);
		} else {
			values.putNull("id");
		}
		values.put("time", this.time);
		return dao.insert(null, null, values, "Message");
	}
}
