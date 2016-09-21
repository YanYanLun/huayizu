package tang.basic.sql;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MSQLiteOpenHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "exam.db";// 数据库名字
	private static final String DB_ShortcutButton = "ShortcutButton";// 模块列表
	private static final String DB_User = "User";// 模块列表

	public static int VERSION = 1;
	public SQLiteDatabase mRDB;
	public SQLiteDatabase mWDB;
	public static MSQLiteOpenHelper mHelper;

	private MSQLiteOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, VERSION);
	}

	public synchronized static MSQLiteOpenHelper getInstance(Context context) {
		if (mHelper == null) {
			mHelper = new MSQLiteOpenHelper(context);
		}
		return mHelper;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d("xx", "onCreate");
		if (!tabbleIsExist(DB_ShortcutButton)) {
			db.execSQL("create table if not exists "
					+ DB_ShortcutButton
					+ "(ID varchar(50),Typeid varchar(50),Netitle varchar(50),sort varchar(50),Remark varchar(500))");
		}
		if (!tabbleIsExist(DB_ShortcutButton)) {
			db.execSQL("create table if not exists "
					+ DB_User
					+ "(ID varchar(50),Membernum varchar(50),Membername varchar(50),Membersex varchar(50),Membercard varchar(500),Email varchar(500),Membertel varchar(500),Memberadd varchar(500),QQ varchar(500),Mstate varchar(500),EndTime varchar(500),token varchar(500))");
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		Log.d("xx", "onUpgrade");
		// if (oldVersion <= 1) {
		// db.execSQL("DROP TABLE " + DB_S4ProjectSetting);
		// }
		// else if (oldVersion <= 2) {
		// db.execSQL("DROP TABLE " + DB_CardInfo);
		// } else if (oldVersion <= 3) {
		// db.execSQL("DROP TABLE " + DB_CardInfo);
		// }
		// db.execSQL("ALTER table " + DB_S4ProjectSetting
		// + " ADD COLUMN BookingHotline varchar(500)");// 增减一项 保存用户数据
		// onCreate(db);
	}

	public void closeDb() {
		if (mRDB != null && mRDB.isOpen()) {
			mRDB.close();
			mRDB = null;
		}
		if (mWDB != null && mWDB.isOpen()) {
			mWDB.close();
			mWDB = null;
		}
	}

	public SQLiteDatabase openReadable() {
		if (mRDB == null) {
			mRDB = this.getReadableDatabase();
		} else if (!mRDB.isOpen()) {
			mRDB = this.getReadableDatabase();
		}
		// if (mDB != null) {
		// if (!mDB.isOpen()) {
		// mDB = this.getWritableDatabase();
		// // mDB = this.getReadableDatabase();
		// } else {
		// return mDB;
		// }
		// }
		// else {
		// mDB = this.getWritableDatabase();
		// }
		return mRDB;
	}

	public SQLiteDatabase openWritable() {
		if (mWDB == null) {
			mWDB = this.getWritableDatabase();
		} else if (!mWDB.isOpen()) {
			mWDB = this.getWritableDatabase();
		}
		// if (mDB != null) {
		// if (!mDB.isOpen()) {
		// mDB = this.getWritableDatabase();
		// } else {
		// return mDB;
		// }
		// } else {
		// mDB = this.getWritableDatabase();
		// }
		return mWDB;
	}

	/**
	 * 判断某张表是否存在
	 * 
	 * @param tabName
	 *            表名
	 * @return
	 */
	public boolean tabbleIsExist(String tableName) {
		boolean result = false;
		if (tableName == null) {
			return false;
		}
		Cursor cursor = null;
		try {
			openReadable();
			String sql = "select count(*) as c from Sqlite_master where type ='table' and name ='"
					+ tableName.trim() + "' ";
			cursor = mRDB.rawQuery(sql, null);
			if (cursor.moveToNext()) {
				int count = cursor.getInt(0);
				if (count > 0) {
					result = true;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (cursor != null) {
				cursor.close();
			}
			closeDb();
		}
		return result;
	}
}
