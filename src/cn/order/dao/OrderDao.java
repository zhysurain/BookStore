package cn.order.dao;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.jdbc.JdbcUtils;
import cn.order.domain.Order;
import cn.order.domain.OrderItem;

public class OrderDao {
	private DataSource dataSource = JdbcUtils.getDataSource();
	private QueryRunner qr = new QueryRunner(dataSource);
	
	public int addOrder(Order order) throws SQLException {
		String sql = "INSERT INTO orders(oid, ordertime, total, state, uid) VALUES(?, ?, ?, ?, ?)";
		Object[] params = {order.getOid(), order.getOrdertime(), order.getTotal(), order.getState(), order.getUid()};
		return qr.update(sql, params);
	}
	
	public int[] addOrderItem(List<OrderItem> list) throws SQLException {
		String sql = "INSERT INTO orderitem(price, `count`, subtotal, oid, bid) VALUES(?, ?, ?, ?, ?)";
		Object[][] params = new Object[list.size()][];
		for (int i = 0; i < list.size(); i++) {
			OrderItem item = list.get(i);
			params[i] = new Object[]{item.getPrice(), item.getCount(), item.getSubtotal(), item.getOid(), item.getBook().getBid()};
		}
		return qr.batch(sql, params);
	}
	
	public List<Order> getOrdersByUid(String uid) throws SQLException{
		String sql = "SELECT * FROM orders WHERE uid = ?";
		return qr.query(sql, uid, new BeanListHandler<Order>(Order.class));
	}
	
	public Order getOrderByOid(String oid) throws SQLException {
		String sql = "SELECT * FROM orders WHERE oid = ?";
		return qr.query(sql, oid, new BeanHandler<Order>(Order.class));
	}
	
	public List<OrderItem> getOrderItemByOid(String oid) throws SQLException{
		String sql = "SELECT * FROM orderitem WHERE oid = ?";
		return qr.query(sql, oid, new BeanListHandler<OrderItem>(OrderItem.class));
	}
	//修改订单状态
	public void updateOrderState(String oid, int state) throws SQLException {
		String sql = "UPDATE orders SET state = ? WHERE oid = ? LIMIT 1";
		Object[] params = {state, oid};
		qr.update(sql, params);
	}
	
	//修改订单字段
	public void updateOrder(String oid, String field, String value) throws SQLException {
		String sql = "UPDATE orders SET " + field + " = ? WHERE oid = ? LIMIT 1";
		Object[] params = {value, oid};
		qr.update(sql, params);
	}
	
	//根据条件查询订单
	public List<Order> getOrdersByWhere(String where) throws SQLException{
		String sql = "SELECT * FROM orders WHERE 1" + where;
		return qr.query(sql, new BeanListHandler<Order>(Order.class));
	}
	
}
