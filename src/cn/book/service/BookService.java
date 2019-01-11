package cn.book.service;

import java.sql.SQLException;
import java.util.List;
import cn.book.dao.BookDao;
import cn.book.domain.Book;

public class BookService {
	private BookDao dao = new BookDao();
	
	public List<Book> findAllBooks() throws SQLException{
		List<Book> list = dao.findAllBooks();
		for (Book book : list) {
			book.setCategory(dao.getCategoryByBid(book.getBid()));
		}
		return list;
	}
	
	public List<Book> getBooksByCid(int cid) throws SQLException{
		List<Book> list = dao.getBooksByCid(cid);
		for (Book book : list) {
			book.setCategory(dao.getCategoryByBid(book.getBid()));
		}
		return list;
	}
	
	public Book getBookByBid(int bid) throws SQLException {
		Book book = dao.getBookByBid(bid);
		book.setCategory(dao.getCategoryByBid(bid));
		return book;
	}
	
	//insert and update
	public void editBook(Book book) throws SQLException {
		if(book.getBid() > 0) {
			dao.update(book);
		}else {
			dao.insert(book);
		}
	}
	
	public void deleteBook(int bid) throws SQLException {
		dao.delete(bid);
	}
}
