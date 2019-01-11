package cn.cart.servlet;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.cart.domain.Cart;
import cn.cart.domain.CartItem;
import cn.cart.service.CartService;
import cn.servlet.BaseServlet;
import cn.user.entity.User;

public class CartServlet extends BaseServlet {
	private CartService service = new CartService();
	
	public String add(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		int bid = Integer.valueOf(request.getParameter("bid"));
		int count = Integer.valueOf(request.getParameter("count"));
		
		User user = (User) request.getSession().getAttribute("user");
		
		service.addBook(bid, count, user);
		return "/CartServlet?method=list";
	}
	
	//查看购物车
	public String list(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		//System.out.println("Cart list ! ! !");
		User user = (User) request.getSession().getAttribute("user");
		//System.out.println(user.toString());
		List<CartItem> list = service.getCartItemByUid(user.getUid());
		request.setAttribute("cartItemList", list);
		return "/jsp/cart/list.jsp";
	}
	
	public String deleteItem(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		int ciid = Integer.valueOf(request.getParameter("ciid"));
		service.deleteItem(ciid);
		return "/CartServlet?method=list";
	}
	
	public String clearCart(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		User user = (User) request.getSession().getAttribute("user");
		service.deleteAllItem(user.getUid());
		return "/CartServlet?method=list";
	}
}
