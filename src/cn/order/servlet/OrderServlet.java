package cn.order.servlet;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.cart.domain.CartItem;
import cn.cart.service.CartService;
import cn.itcast.commons.CommonUtils;
import cn.order.domain.Order;
import cn.order.domain.OrderItem;
import cn.order.service.OrderService;
import cn.servlet.BaseServlet;
import cn.user.entity.User;

public class OrderServlet extends BaseServlet {
	private OrderService service = new OrderService();
	private CartService cService = new CartService();
	
	public String list(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		User user = (User) request.getSession().getAttribute("user");
		List<Order> orders = service.getOrdersByUid(user.getUid());
		request.setAttribute("orders", orders);
		
		return "jsp/order/list.jsp";
	}
	
	public String add(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		User user = (User) request.getSession().getAttribute("user");
		
		String ciids = request.getParameter("cartItems");
		ciids = ciids.substring(0, ciids.length()-1);
		System.out.println("ciids:" + ciids);
		List<CartItem> cartItems = cService.getItemByCiids(ciids);
		
		String oid = CommonUtils.uuid();
		Order order = new Order();
		order.setOid(oid);
		order.setState(0);
		order.setUid(user.getUid());
		order.setOrdertime(new Timestamp(new Date().getTime()));
		List<OrderItem> orderItems = new ArrayList<OrderItem>();
		double total = 0.00;
		for (CartItem cItem : cartItems) {
			OrderItem oItem = new OrderItem();
			oItem.setOid(oid);
			oItem.setBook(cItem.getBook());
			oItem.setCount(cItem.getCount());
			oItem.setPrice(cItem.getBook().getPrice());
			oItem.setSubtotal(multiply(oItem.getPrice(), oItem.getCount()));
			orderItems.add(oItem);
			total += oItem.getSubtotal();
		}
		order.setOrderItem(orderItems);
		order.setTotal(total);
		//System.out.println("total = " + total);
		//添加订单
		service.add(order);
		//删除相关购物车记录
		cService.deletItemByCiids(ciids);
		
		return "/OrderServlet?method=list";
	}
	
	//订单详情页加载
	public String load(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		String oid = request.getParameter("oid");
		Order order = service.getOrderByOid(oid);
		request.setAttribute("order", order);
		
		return "/jsp/order/desc.jsp";
	}
	
	//订单付款
	public String payment(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		String oid = request.getParameter("oid");
		String address = request.getParameter("address");
		
		Order order = new Order();
		order.setOid(oid);
		order.setAddress(address);
		service.payment(order);
		
		return "/OrderServlet?method=list";
	}
	
	//确认收货
	public String confirm(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		String oid = request.getParameter("oid");
		service.confirm(oid);
		
		return "/OrderServlet?method=list";
	}
	
	private double multiply(double price, int count) {
		BigDecimal p = new BigDecimal(price);
		BigDecimal c = new BigDecimal(count);
		double subtotal = p.multiply(c).doubleValue();
		return subtotal;
	}
}
