package cn.order.service;

import java.sql.SQLException;
import java.util.List;

import cn.book.dao.BookDao;
import cn.jdbc.JdbcUtils;
import cn.order.dao.OrderDao;
import cn.order.domain.Order;
import cn.order.domain.OrderItem;

public class OrderService {
	private OrderDao dao = new OrderDao();
	private BookDao bDao = new BookDao();
	
	public void add(Order order) throws SQLException {
		JdbcUtils.beginTransction();
		try {
			int result = dao.addOrder(order);
			System.out.println("result :" + result);
			if(result < 1) {
				JdbcUtils.rollbackTransction();
			}else {
				int[] r = dao.addOrderItem(order.getOrderItem());
				System.out.println(r.toString());
				JdbcUtils.commitTransction();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JdbcUtils.rollbackTransction();
		}
	}
	//根据用户查询订单
	public List<Order> getOrdersByUid(String uid) throws SQLException{
		List<Order> orders = dao.getOrdersByUid(uid);
		for (Order order : orders) {
			order.setOrderItem(getOrderItemByOid(order.getOid()));
		}
		return orders;
	}
	
	//根据订单号查询订单
	public Order getOrderByOid(String oid) throws SQLException {
		Order order = dao.getOrderByOid(oid);
		order.setOrderItem(getOrderItemByOid(oid));
		return order;
	}
	
	//根据订单号查询订单明细
	public List<OrderItem> getOrderItemByOid(String oid) throws SQLException{
		List<OrderItem> items = dao.getOrderItemByOid(oid);
		for (OrderItem item : items) {
			item.setBook(bDao.getBookByIid(item.getIid()));
		}
		return items;
	}
	
	//订单付款
	public void payment(Order order) throws SQLException {
		//订单状态修改成 已付款待发货
		dao.updateOrderState(order.getOid(), 1);
		//添加订单地址
		dao.updateOrder(order.getOid(), "address", order.getAddress());
	}
	
	//根据条件查询订单
	public List<Order> getOrdersByWhere(String where) throws SQLException{
		List<Order> orders = dao.getOrdersByWhere(where);
		for (Order order : orders) {
			order.setOrderItem(getOrderItemByOid(order.getOid()));
		}
		return orders;
	}
	
	//发货
	public void orderDeliver(String oid) throws SQLException {
		dao.updateOrderState(oid, 2);
	}
	
	//确认收货
	public void confirm(String oid) throws SQLException {
		dao.updateOrderState(oid, 3);
	}
}
