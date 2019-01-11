package cn.book.servlet;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.book.domain.Book;
import cn.book.service.BookService;
import cn.servlet.BaseServlet;

public class BookServlet extends BaseServlet {
	private BookService service = new BookService();
	
	public String findAllBooks(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		System.out.println("findAllBooks start ! ! !");
		
		List<Book> list = service.findAllBooks();
		request.setAttribute("bookList", list);
		return "/jsp/book/list.jsp";
	}
	
	public String findBooksByCategory(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		System.out.println("findBooksByCategory start ! ! !");
		
		int cid = Integer.valueOf(request.getParameter("cid"));
		List<Book> list = service.getBooksByCid(cid);
		request.setAttribute("bookList", list);
		return "/jsp/book/list.jsp";
	}
	
	//获取图书的具体信息
	public String load(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		int bid = Integer.valueOf(request.getParameter("bid"));
		
		Book book = service.getBookByBid(bid);
		request.setAttribute("book", book);
		
		return "/jsp/book/desc.jsp";
	}
}
