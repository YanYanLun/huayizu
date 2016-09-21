package tang.basic.sql;

import java.util.ArrayList;
import java.util.List;

import tang.basic.model.Router;
import tang.basic.model.ShortcutButton;
import tang.basic.model.User;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class MSQLiteDAO implements ISQLiteHelper {
	private MSQLiteOpenHelper msq;
	private final static byte[] _writeLock = new byte[0];

	public MSQLiteDAO(Context mContext) {
		msq = MSQLiteOpenHelper.getInstance(mContext);
	}

	/**
	 * 数据新增和更新
	 * 
	 * @param selection
	 *            检索字段名
	 * @param selectionArgs
	 *            查询条件
	 * @param values
	 *            数据集合
	 * @param tableName
	 *            表名
	 * @return
	 */
	public long insertOrUpdate(String selection, String[] selectionArgs,
			ContentValues values, String tableName) {
		synchronized (_writeLock) {
			msq.openWritable();
			Cursor cursor = null;
			try {
				cursor = msq.mWDB.query(tableName, null, selection,
						selectionArgs, null, null, null);
				long rowId = 0;
				if (cursor != null && cursor.getCount() > 0) { // update
					rowId = msq.mWDB.update(tableName, values, selection,
							selectionArgs);
				} else {// insert
					rowId = msq.mWDB.insert(tableName, null, values);
				}
				return rowId;
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			} finally {
				if (cursor != null) {
					cursor.close();
				}
				msq.closeDb();
			}
		}
	}

	/**
	 * 数据新增
	 * 
	 * @param selection
	 *            检索字段名
	 * @param selectionArgs
	 *            查询条件
	 * @param values
	 *            数据集合
	 * @param tableName
	 *            表名
	 * @return
	 */
	public long insert(String selection, String[] selectionArgs,
			ContentValues values, String tableName) {
		synchronized (_writeLock) {
			msq.openWritable();
			try {
				return msq.mWDB.insert(tableName, null, values);
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			} finally {
				msq.closeDb();
			}
		}
	}

	/**
	 * 数据检索
	 * 
	 * @param tableName
	 *            表名
	 * @return
	 */
	public Cursor query(String tableName) {
		synchronized (_writeLock) {
			Cursor cursor = null;
			try {
				msq.openReadable();
				cursor = msq.mRDB.query(tableName, null, null, null, null,
						null, null);
			} catch (Exception e) {
				e.getMessage();
			}
			return cursor;
		}
	}

	/**
	 * 数据检索
	 * 
	 * @param tableName
	 *            表名
	 * @param key
	 *            检索字段
	 * @param value
	 *            值
	 * @return
	 */
	public Cursor query(String tableName, String key, String value) {
		synchronized (_writeLock) {
			Cursor cursor = null;
			try {
				msq.openReadable();
				cursor = msq.mRDB.query(tableName, null, key + "=" + value,
						null, null, null, null);
			} catch (Exception e) {
				e.getMessage();
			}
			return cursor;
		}
	}

	/**
	 * 根据字段删除数据
	 * 
	 * @param tableName
	 *            表名
	 * @param key
	 *            字段名 name=?
	 * @param value
	 *            值
	 * @return
	 */
	public long delete(String tableName, String key, String value) {
		synchronized (_writeLock) {
			msq.openWritable();
			try {
				return msq.mWDB.delete(tableName, key, new String[] { value });
			} finally {
				msq.closeDb();
			}
		}
	}

	@Override
	public long delete(String tableName) {
		synchronized (_writeLock) {
			msq.openWritable();
			try {
				return msq.mWDB.delete(tableName, null, null);
			} finally {
				msq.closeDb();
			}
		}
	}

	public boolean exist(String tableName, String selection,
			String[] selectionArgs) {
		synchronized (_writeLock) {
			msq.openReadable();
			Cursor cursor = null;
			try {
				cursor = msq.mRDB.query(tableName, null, selection,
						selectionArgs, null, null, null);
				if (cursor != null && cursor.getCount() > 0)
					return true;
				return false;
			} catch (Exception e) {
				return false;
			} finally {
				if (cursor != null) {
					cursor.close();
				}
				msq.closeDb();

			}
		}
	}

	@Override
	public long delete(String tableName, String key, String[] value) {
		synchronized (_writeLock) {
			msq.openWritable();
			try {
				return msq.mWDB.delete(tableName, key, value);
			} finally {
				msq.closeDb();
			}
		}
	}

	/**
	 * 获取数据
	 * 
	 * @return
	 */
	public List<ShortcutButton> QueryShortcutButton() {
		synchronized (_writeLock) {
			List<ShortcutButton> list = new ArrayList<ShortcutButton>();
			Cursor cursor = null;
			try {
				msq.openReadable();
				cursor = msq.mRDB.query("ShortcutButton", null, null, null,
						null, null, null);
				if (cursor != null && cursor.getCount() > 0) {
					while (cursor.moveToNext()) {
						ShortcutButton info = new ShortcutButton();
						if (cursor.getString(cursor.getColumnIndex("ID")) != null) {
							info.ID = Integer.parseInt(cursor.getString(cursor
									.getColumnIndex("ID")));
						} else {
							info.ID = 0;
						}
						if (cursor.getString(cursor.getColumnIndex("Typeid")) != null) {
							info.Typeid = Integer
									.parseInt(cursor.getString(cursor
											.getColumnIndex("Typeid")));
						} else {
							info.Typeid = 0;
						}
						info.Netitle = cursor.getString(cursor
								.getColumnIndex("Netitle"));
						if (cursor.getString(cursor.getColumnIndex("sort")) != null) {
							info.sort = Integer.parseInt(cursor
									.getString(cursor.getColumnIndex("sort")));
						} else {
							info.sort = 0;
						}
						info.Remark = cursor.getString(cursor
								.getColumnIndex("Remark"));
						list.add(info);
					}
				}
			} catch (Exception e) {
				e.getMessage();
				return null;
			} finally {
				if (cursor != null) {
					cursor.close();
				}
				msq.closeDb();
			}
			return list;
		}
	}

	/**
	 * 获取数据
	 * 
	 * @return
	 */
	public List<Router> QueryRouter() {
		synchronized (_writeLock) {
			List<Router> list = new ArrayList<Router>();
			Cursor cursor = null;
			try {
				msq.openReadable();
				cursor = msq.mRDB.query("Router", null, null, null, null, null,
						null);
				if (cursor != null && cursor.getCount() > 0) {
					while (cursor.moveToNext()) {
						Router info = new Router();
						info.name = cursor.getString(cursor
								.getColumnIndex("name"));
						info.disconnurl = cursor.getString(cursor
								.getColumnIndex("disconnurl"));
						info.disconnpost = cursor.getString(cursor
								.getColumnIndex("disconnpost"));
						info.connurl = cursor.getString(cursor
								.getColumnIndex("connurl"));
						info.connpost = cursor.getString(cursor
								.getColumnIndex("connpost"));
						if (cursor.getString(cursor.getColumnIndex("id")) != null) {
							info.id = Integer.parseInt(cursor.getString(cursor
									.getColumnIndex("id")));
						} else {
							info.id = 0;
						}
						list.add(info);
					}
				}
			} catch (Exception e) {
				e.getMessage();
				return null;
			} finally {
				if (cursor != null) {
					cursor.close();
				}
				msq.closeDb();
			}
			return list;
		}
	}

	/**
	 * 获取用户信息
	 * 
	 * @return
	 */
	public User getUserinfo() {
		synchronized (_writeLock) {
			User info = new User();
			Cursor cursor = query("User");
			try {
				if (cursor != null && cursor.getCount() > 0
						&& cursor.moveToNext()) {
					if (cursor.getString(cursor.getColumnIndex("ID")) != null) {
						info.ID = Integer.parseInt(cursor.getString(cursor
								.getColumnIndex("ID")));
					} else {
						info.ID = 0;
					}
					info.Membernum = cursor.getString(cursor
							.getColumnIndex("Membernum"));
					info.Membername = cursor.getString(cursor
							.getColumnIndex("Membername"));
					info.Membersex = cursor.getString(cursor
							.getColumnIndex("Membersex"));
					info.QQ = cursor.getString(cursor.getColumnIndex("QQ"));
					info.Email = cursor.getString(cursor
							.getColumnIndex("Email"));
					info.Membercard = cursor.getString(cursor
							.getColumnIndex("Membercard"));
					info.Membertel = cursor.getString(cursor
							.getColumnIndex("Membertel"));
					info.Mstate = cursor.getString(cursor
							.getColumnIndex("Mstate"));
					info.EndTime = cursor.getString(cursor
							.getColumnIndex("EndTime"));
					info.token = cursor.getString(cursor
							.getColumnIndex("token"));
					info.Memberadd = cursor.getString(cursor
							.getColumnIndex("Memberadd"));
				}
			} catch (Exception e) {
				e.getMessage();
				return null;
			} finally {
				if (cursor != null) {
					cursor.close();
				}
				msq.closeDb();
			}
			return info;
		}
	}
}
