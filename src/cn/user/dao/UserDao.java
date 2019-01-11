package cn.user.dao;

import java.sql.SQLException;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import cn.jdbc.JdbcUtils;
import cn.user.entity.User;

public class UserDao {
	QueryRunner qr = new QueryRunner(JdbcUtils.getDataSource());
	//添加用户
	public boolean addUser(User user) throws SQLException {
		System.out.println("UserDao addUser ! ! !");
		String sql = "INSERT INTO tb_user(uid, username, `password`, email, `code`, insert_time, update_time) VALUES(?, ?, ?, ?, ?, ?, ?)";
		Object[] param = {user.getUid(), user.getUsername(), user.getPassword(), user.getEmail(), user.getCode(), user.getInsert_time(), user.getUpdate_time()};
		int result = qr.update(sql, param);
		if(result > 0) {
			return true;
		}
		return false;
	}
	
	//根据field字段查询用户
	public User getUserByField(String field, String value) throws SQLException {
		String sql = "SELECT * FROM tb_user WHERE " + field + " = ? AND state = 1";
		User user = qr.query(sql, value, new BeanHandler<User>(User.class));
		return user;
	}
	//根据激活码查询用户
	public User getUserByState(String code) throws SQLException {
		String sql = "SELECT * FROM tb_user WHERE code = ?";
		User user = qr.query(sql, code, new BeanHandler<User>(User.class));
		return user;
	}
	
	//修改用户状态
	public boolean updateUserState(String uid) throws SQLException {
		String sql = "UPDATE tb_user SET state = 1 WHERE uid = ?";
		int result = qr.update(sql, uid);
		if(result > 0) {
			return true;
		}
		return false;
	}
	
	public User getUserByName(String username) throws SQLException {
		String sql = "SELECT * FROM tb_user WHERE username like '%" + username + "%' AND state = 1";
		return qr.query(sql, new BeanHandler<User>(User.class));
	}
}
