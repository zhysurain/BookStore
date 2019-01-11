package cn.user.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.commons.CommonUtils;
import cn.servlet.BaseServlet;
import cn.user.entity.User;
import cn.user.service.UserService;
import net.sf.json.JSONObject;

public class UserServlet extends BaseServlet {
	private UserService service = new UserService();
	
	public String login(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		Object result = service.login(username, password);
		if(result instanceof String) {
			request.setAttribute("msg", result);
			return "/jsp/user/login.jsp";
		}
		
		User user = (User)result;
		request.getSession().setAttribute("user", user);
		request.getSession().setMaxInactiveInterval(7200);
		request.setAttribute("username", user.getUsername());
		try {
			response.getWriter().print(new HashMap<String, String>().put("username", user.getUsername()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "/jsp/main.jsp";
	}
	
	//退出
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		//注销用户session
		request.getSession().invalidate();
		return "/jsp/main.jsp";
	}
	
	//注册
	public String regist(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		System.out.println("regist start ! ! !");
		String msg = "";
		// 封装表单数据
		User user = CommonUtils.toBean(request.getParameterMap(), User.class);
		
		//check表单
		if(service.checkUser("username", user.getUsername())) {
			msg = "用户已存在,不能重复注册";
		}else if(service.checkUser("email", user.getEmail())){
			msg = "邮箱已注册,不能重复注册";
		}else {
			//添加用户
			String uuid = CommonUtils.uuid();
			user.setUid(uuid);
			user.setCode(uuid + uuid); 
			Timestamp time = new Timestamp(new Date().getTime());
			user.setInsert_time(time);
			user.setUpdate_time(time);
			boolean result = service.regist(user);
			if(result) {
				msg = "用户添加成功,请激活";
			}else {
				msg = "用户添加失败";
			}
		}
		request.setAttribute("user", user);
		request.setAttribute("msg", msg);
		return "/jsp/user/regist.jsp";
	}
	
	//激活
	public void active(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		System.out.println("state start ! ! !");
		String code = request.getParameter("code");
		String msg = service.active(code);
		
		JSONObject jo = new JSONObject();
		jo.put("msg", msg);
		try {
			response.getWriter().print(jo);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
