package tang.basic.sql;

import android.content.ContentValues;
import android.database.Cursor;

public interface ISQLiteHelper {
	public long insertOrUpdate(String selection, String[] selectionArgs,
			ContentValues values, String tableName);

	public long insert(String selection, String[] selectionArgs,
			ContentValues values, String tableName);

	public Cursor query(String tableName);

	public long delete(String tableName, String key, String value);

	public long delete(String tableName, String key, String[] value);

	public long delete(String tableName);

	public boolean exist(String tableName, String selection,
			String[] selectionArgs);
}
