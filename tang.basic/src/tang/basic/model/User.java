package tang.basic.model;

import java.io.Serializable;

import android.content.ContentValues;
import tang.basic.common.StringUtil;
import tang.basic.sql.ISQLiteHelper;

public class User implements Serializable {
	public int ID;
	/**
	 * ��Ա���
	 */
	public String Membernum;
	/**
	 * ����
	 */
	public String Membername;
	/**
	 * �Ա�
	 */
	public String Membersex;
	/**
	 * ���֤
	 */
	public String Membercard;
	/**
	 * �绰
	 */
	public String Membertel;
	/**
	 * ��������
	 */
	public String Email;
	/**
	 * ��ַ
	 */
	public String Memberadd;
	/**
	 * QQ
	 */
	public String QQ;
	/**
	 * ��Ա����
	 */
	public String Mstate;
	/**
	 * ��������
	 */
	public String EndTime;
	public String token;

	public long Save(ISQLiteHelper dao) {
		ContentValues values = new ContentValues();
		values.put("ID", this.ID);
		if (StringUtil.isEmpty(this.Membernum)) {
			values.putNull("Membernum");
		} else {
			values.put("Membernum", this.Membernum);
		}
		if (StringUtil.isEmpty(this.Membername)) {
			values.putNull("Membername");
		} else {
			values.put("Membername", this.Membername);
		}
		if (!StringUtil.isEmpty(this.Membersex)) {
			values.put("Membersex", this.Membersex);
		} else {
			values.putNull("Membersex");
		}
		if (!StringUtil.isEmpty(this.Membercard)) {
			values.put("Membercard", this.Membercard);
		} else {
			values.putNull("Membercard");
		}
		if (!StringUtil.isEmpty(this.Email)) {
			values.put("Email", this.Email);
		} else {
			values.putNull("Email");
		}
		if (!StringUtil.isEmpty(this.Membertel)) {
			values.put("Membertel", this.Membertel);
		} else {
			values.putNull("Membertel");
		}
		if (!StringUtil.isEmpty(this.Memberadd)) {
			values.put("Memberadd", this.Memberadd);
		} else {
			values.putNull("Memberadd");
		}
		if (!StringUtil.isEmpty(this.QQ)) {
			values.put("QQ", this.QQ);
		} else {
			values.putNull("QQ");
		}
		if (!StringUtil.isEmpty(this.Mstate)) {
			values.put("Mstate", this.Mstate);
		} else {
			values.putNull("Mstate");
		}
		if (!StringUtil.isEmpty(this.EndTime)) {
			values.put("EndTime", this.EndTime);
		} else {
			values.putNull("EndTime");
		}
		if (!StringUtil.isEmpty(this.token)) {
			values.put("token", this.token);
		} else {
			values.putNull("token");
		}
		return dao.insertOrUpdate(null, null, values, "User");
	}

	public static long deleteAll(ISQLiteHelper dao) {
		return dao.delete("User");
	}
}
