package tang.basic.sql;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import tang.basic.model.Router;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

@SuppressWarnings("rawtypes")
public class DataBaseHelper extends OrmLiteSqliteOpenHelper {

	private static final String TABLE_NAME = "sqlite-test.db";

	private Map<String, Dao> daos = new HashMap<String, Dao>();

	private DataBaseHelper(Context context) {
		super(context, TABLE_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase database,
			ConnectionSource connectionSource) {
		try {
			TableUtils.createTable(connectionSource, Router.class);
			// TableUtils.createTable(connectionSource, Article.class);
			// TableUtils.createTable(connectionSource, Student.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase database,
			ConnectionSource connectionSource, int oldVersion, int newVersion) {
		try {
			TableUtils.dropTable(connectionSource, Router.class, true);
			// TableUtils.dropTable(connectionSource, Article.class, true);
			// TableUtils.dropTable(connectionSource, Student.class, true);
			onCreate(database, connectionSource);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static DataBaseHelper instance;

	/**
	 * 单例获取该Helper
	 * 
	 * @param context
	 * @return
	 */
	public static synchronized DataBaseHelper getHelper(Context context) {
		context = context.getApplicationContext();
		if (instance == null) {
			synchronized (DataBaseHelper.class) {
				if (instance == null)
					instance = new DataBaseHelper(context);
			}
		}

		return instance;
	}

	@SuppressWarnings("unchecked")
	public synchronized Dao getDao(Class clazz) {
		Dao dao = null;
		try {
			String className = clazz.getSimpleName();

			if (daos.containsKey(className)) {
				dao = daos.get(className);
			}
			if (dao == null) {
				dao = super.getDao(clazz);
				daos.put(className, dao);
			}

		} catch (SQLException e) {
			e.getMessage();
		}
		return dao;
	}

	/**
	 * 释放资源
	 */
	@Override
	public void close() {
		super.close();

		for (String key : daos.keySet()) {
			@SuppressWarnings("unused")
			Dao dao = daos.get(key);
			dao = null;
		}
	}

}
