package cn.cart.service;

import java.sql.SQLException;
import java.util.List;

import cn.book.dao.BookDao;
import cn.book.domain.Book;
import cn.cart.dao.CartDao;
import cn.cart.domain.Cart;
import cn.cart.domain.CartItem;
import cn.user.entity.User;

public class CartService {
	private CartDao dao = new CartDao();
	private BookDao bDao = new BookDao();
	
	//添加商品到购物车
	public void addBook(int bid, int count, User user) throws SQLException {
		//查询用户是否有购物车
		Cart cart = dao.getCartByUid(user.getUid());
		if(cart == null) {
			//添加一个购物车
			dao.insertCart(user.getUid());
			cart = dao.getCartByUid(user.getUid());
		}
		
		//添加购物车明细或修改购物车明细数量
		CartItem cItem = dao.getItemByBid(cart.getCtid(), bid);
		if(cItem == null) {
			dao.insertCartItem(cart.getCtid(), bid, count);
		}else {
			dao.updateItemCount(cItem.getCiid(), count);
		}
	}
	
	public Cart getCartByUid(String uid) throws SQLException {
		return dao.getCartByUid(uid);
	}
	
	public List<CartItem> getCartItemByUid(String uid) throws SQLException{
		Cart cart = dao.getCartByUid(uid);
		
		List<CartItem> list = dao.getCartItemByCtid(cart.getCtid());
		for (CartItem item : list) {
			setCartItemBook(item);
		}
		return list;
	}
	
	public void deleteItem(int ciid) throws SQLException {
		dao.deleteCartItem(ciid);
	}
	
	public void deleteAllItem(String uid) throws SQLException {
		Cart cart = dao.getCartByUid(uid);
		dao.deleteAllCartItem(cart.getCtid());
	}
	
	public List<CartItem> getItemByCiids(String ciids) throws SQLException{
		List<CartItem> list = dao.getItemByCiids(ciids);
		for (CartItem item : list) {
			setCartItemBook(item);
			//System.out.println(item.toString());
		}
		return list;
	}
	
	//批量删除购物车记录
	public void deletItemByCiids(String ciids) throws SQLException {
		dao.deleteItemByCiids(ciids);
	}
	
	private void setCartItemBook(CartItem item) throws SQLException {
		Book b = bDao.getBookByCiid(item.getCiid());
		item.setBook(b);
	}
	
}
