package tang.basic.model;

import java.io.Serializable;

import android.content.ContentValues;
import tang.basic.common.StringUtil;
import tang.basic.sql.ISQLiteHelper;

/**
 * 快捷按钮
 * 
 * @author shown_xiang
 *
 */
public class ShortcutButton implements Serializable {
	public int ID;
	/**
	 * 类型
	 */
	public int Typeid;
	/**
	 * 名称
	 */
	public String Netitle;
	/**
	 * 排序
	 */
	public int sort;
	/**
	 * 备注
	 */
	public String Remark;

	public long Save(ISQLiteHelper dao) {
		ContentValues values = new ContentValues();
		values.put("ID", this.ID);
		values.put("Typeid", this.Typeid);
		if (StringUtil.isEmpty(this.Netitle)) {
			values.putNull("Netitle");
		} else {
			values.put("Netitle", this.Netitle);
		}
		values.put("sort", this.sort);
		if (!StringUtil.isEmpty(this.Remark)) {
			values.put("Remark", this.Remark);
		} else {
			values.putNull("Remark");
		}
		return dao.insertOrUpdate("ID=?", null, values, "ShortcutButton");
	}

	public long Delete(ISQLiteHelper dao) {
		return dao.delete("ShortcutButton", "ID=?", String.valueOf(this.ID));
	}

	public static long DeleteAll(ISQLiteHelper dao) {
		return dao.delete("ShortcutButton");
	}
}
