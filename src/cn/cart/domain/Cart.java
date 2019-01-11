package cn.cart.domain;

import java.util.List;

public class Cart {
	private int ctid;
	private String uid;
	private List<CartItem> cartItem;
	public int getCtid() {
		return ctid;
	}
	public void setCtid(int ctid) {
		this.ctid = ctid;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public List<CartItem> getCartItem() {
		return cartItem;
	}
	public void setCartItem(List<CartItem> cartItem) {
		this.cartItem = cartItem;
	}
	@Override
	public String toString() {
		return "Cart [ctid=" + ctid + ", uid=" + uid + "]";
	}
	
}
