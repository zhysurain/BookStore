package cn.cart.domain;

import java.math.BigDecimal;
import cn.book.domain.Book;

public class CartItem {
	private int ciid;
	private int ctid;
	private Book book;
	private int count;
	
	public int getCiid() {
		return ciid;
	}
	public void setCiid(int ciid) {
		this.ciid = ciid;
	}
	public int getCtid() {
		return ctid;
	}
	public void setCtid(int ctid) {
		this.ctid = ctid;
	}
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	public double getSubTotal() {
		BigDecimal price = new BigDecimal(book.getPrice());
		BigDecimal d2 = new BigDecimal(count + "");
		return price.multiply(d2).doubleValue();
	}
	@Override
	public String toString() {
		return "CartItem [ciid=" + ciid + ", ctid=" + ctid + ", book=" + book + ", count=" + count + "]";
	}
	
	
}
