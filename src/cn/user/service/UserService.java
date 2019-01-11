package cn.user.service;

import java.sql.SQLException;

import cn.user.dao.UserDao;
import cn.user.entity.User;

public class UserService {
	private UserDao dao = new UserDao();
	
	//登录
	public Object login(String username, String password) throws SQLException {
		User user = dao.getUserByField("username", username);
		if(user == null) {
			return "用户不存在，请先注册";
		}else if(!password.equals(user.getPassword())) {
			return "密码不正确";
		}
		return user;
	}
	
	//注册
	public boolean regist(User user) throws SQLException {
		System.out.println("UserService regist ! ! !");
		boolean result = dao.addUser(user);
		return result;
	}
	
	//判断用户是否已存在
	public boolean checkUser(String field, String value) throws SQLException {
		User user = dao.getUserByField(field, value);
		if(user != null) {
			return true;
		}
		return false;
	}
	//激活用户
	public String active(String state) throws SQLException {
		User user = dao.getUserByState(state);
		if(user == null) {
			return "激活码无效";
		}
		if(user.isState()) {
			return "该用户已经被激活，无需再激活";
		}
		
		boolean result = dao.updateUserState(user.getUid());
		if(result) {
			return "激活成功";
		}else {
			return "激活失败";
		}
	}
	
	public User getUserByName(String username) throws SQLException {
		return dao.getUserByName(username);
	}
}
