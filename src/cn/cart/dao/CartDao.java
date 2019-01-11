package cn.cart.dao;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.cart.domain.Cart;
import cn.cart.domain.CartItem;
import cn.jdbc.JdbcUtils;

public class CartDao {
	private DataSource dataSource = JdbcUtils.getDataSource();
	private QueryRunner qr = new QueryRunner(dataSource);
	
	public Cart getCartByUid(String uid) throws SQLException {
		String sql = "SELECT * FROM cart WHERE uid = ? LIMIT 1";
		Cart cart = qr.query(sql, uid, new BeanHandler<Cart>(Cart.class));
		return cart;
	}
	
	public void insertCart(String uid) throws SQLException {
		String sql = "INSERT INTO cart(uid) VALUES(?)";
		qr.update(sql, uid);
	}
	
	public CartItem getItemByBid(int ctid, int bid) throws SQLException {
		String sql = "SELECT * FROM cartitem WHERE ctid = ? AND bid = ? limit 1";
		Object[] params = {ctid, bid};
		CartItem item = qr.query(sql, params, new BeanHandler<CartItem>(CartItem.class));
		return item;
	}
	
	public void insertCartItem(int ctid, int bid, int count) throws SQLException {
		String sql = "INSERT INTO cartitem(ctid, bid, count) VALUES(?, ?, ?)";
		Object[] params = {ctid, bid, count};
		qr.update(sql, params);
	}
	
	public void updateItemCount(int ciid, int count) throws SQLException {
		String sql = "UPDATE cartitem SET count = count + ? WHERE ciid = ?";
		Object[] params = {count, ciid};
		qr.update(sql, params);
	}
	
	public List<CartItem> getCartItemByCtid(int ctid) throws SQLException{
		String sql = "SELECT * FROM cartitem WHERE ctid = ?";
		List<CartItem> list = qr.query(sql, ctid, new BeanListHandler<CartItem>(CartItem.class));
		return list;
	}
	
	public void deleteCartItem(int ciid) throws SQLException {
		String sql = "DELETE FROM cartitem WHERE ciid = ? LIMIT 1";
		qr.update(sql, ciid);
	}
	
	public void deleteAllCartItem(int ctid) throws SQLException {
		String sql = "DELETE FROM cartitem WHERE ctid = ?";
		qr.update(sql, ctid);
	}
	
	public List<CartItem> getItemByCiids(String ciids) throws SQLException{
		//System.out.println(ciids);
		String sql = "SELECT * FROM cartitem WHERE ciid IN (" + ciids + ")";
		List<CartItem> list = qr.query(sql, new BeanListHandler<CartItem>(CartItem.class));
		//System.out.println(list.size());
		return list;
	}
	
	public void deleteItemByCiids(String ciids) throws SQLException {
		String sql = "DELETE FROM cartitem WHERE ciid IN (" + ciids + ")";
		qr.update(sql);
	}
}
