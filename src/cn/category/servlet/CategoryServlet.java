package cn.category.servlet;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.category.domain.Category;
import cn.category.service.CategoryService;
import cn.servlet.BaseServlet;

public class CategoryServlet extends BaseServlet {
	CategoryService service = new CategoryService();
	//图书分类列表
	public String categoryMenu(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		List<Category> list = service.listCategory();
		request.setAttribute("categoryList", list);
		return "/left.jsp";
	}
}
