package cn.user.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.servlet.BaseServlet;
import cn.user.service.UserService;
import net.sf.json.JSONObject;

public class AjaxUserServlet extends BaseServlet {
	private UserService service = new UserService();
	
	public void checkUser(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		System.out.println("AjaxUserServlet start ! ! !");
		String type = request.getParameter("type");
		String value = request.getParameter("value");
		boolean result = service.checkUser(type, value);
//		System.out.println("result = " + result);
		String msg = "";
		if(result) {
			msg = type + "已存在";
		}
		
		JSONObject json = new JSONObject();
		json.put("msg", msg);
		try {
			response.getWriter().print(json);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
