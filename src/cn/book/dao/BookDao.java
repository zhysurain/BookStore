package cn.book.dao;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.book.domain.Book;
import cn.category.domain.Category;
import cn.jdbc.JdbcUtils;

public class BookDao {
	private DataSource dataSource = JdbcUtils.getDataSource();
	private QueryRunner qr = new QueryRunner(dataSource);
	
	public List<Book> findAllBooks() throws SQLException{
		String sql = "SELECT * FROM book";
		List<Book> list = qr.query(sql, new BeanListHandler<Book>(Book.class));
		return list;
	}
	
	public List<Book> getBooksByCid(int cid) throws SQLException{
		String sql = "SELECT * FROM book WHERE cid = ?";
		List<Book> list = qr.query(sql, cid, new BeanListHandler<Book>(Book.class));
		return list;
	}
	
	public Book getBookByBid(int bid) throws SQLException {
		String sql = "SELECT * FROM book WHERE bid = ?";
		Book book = qr.query(sql, bid, new BeanHandler<Book>(Book.class));
		return book;
	}
	
	public Book getBookByCiid(int ciid) throws SQLException {
		String sql = "SELECT b.* FROM cartitem ci LEFT JOIN book b ON ci.bid = b.bid WHERE ci.ciid = ?";
		return qr.query(sql, ciid, new BeanHandler<Book>(Book.class));
	}
	
	public Book getBookByIid(int iid) throws SQLException {
		String sql = "SELECT b.* FROM orderitem oi LEFT JOIN book b ON oi.bid = b.bid WHERE oi.iid = ?";
		return qr.query(sql, iid, new BeanHandler<Book>(Book.class));
	}
	
	//查询图书的分类
	public Category getCategoryByBid(int bid) throws SQLException {
		String sql = "SELECT c.* FROM book b LEFT JOIN category c ON b.cid = c.cid WHERE b.bid = ?";
		return qr.query(sql, bid, new BeanHandler<Category>(Category.class));
	}
	
	public void insert(Book book) throws SQLException {
		String sql = "INSERT INTO book(bname, price, author, image, cid) VALUES(?, ?, ?, ?, ?)";
		Object[] params = {book.getBname(), book.getPrice(), book.getAuthor(), book.getImage(), book.getCategory().getCid()};
		qr.update(sql, params);
	}
	
	public void update(Book book) throws SQLException {
		String sql = "UPDATE book SET bname = ?, price = ?, author = ?, image = ?, cid = ? WHERE bid = ?";
		Object[] params = {book.getBname(), book.getPrice(), book.getAuthor(), book.getImage(), book.getCategory().getCid(), book.getBid()};
		qr.update(sql, params);
	}
	
	public void delete(int bid) throws SQLException {
		String sql = "DELETE FROM book WHERE bid = ? LIMIT 1";
		qr.update(sql, bid);
	}
}
