package tang.basic.sql;

import tang.basic.model.Router;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

import android.content.Context;

public class RouterDao {
	@SuppressWarnings("unused")
	private Context context;
	private Dao<Router, Integer> userDaoOpe;
	private DataBaseHelper helper;

	@SuppressWarnings("unchecked")
	public RouterDao(Context context) {
		this.context = context;
		helper = DataBaseHelper.getHelper(context);
		userDaoOpe = helper.getDao(Router.class);
	}

	/**
	 * 增加一个用户
	 * 
	 * @param user
	 */
	public void add(Router user) {
		try {
			userDaoOpe.create(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
